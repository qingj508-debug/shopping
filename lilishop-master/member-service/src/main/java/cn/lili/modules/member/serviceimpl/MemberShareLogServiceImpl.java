package cn.lili.modules.member.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.vo.PageVO;
import cn.lili.modules.member.entity.dos.Member;
import cn.lili.modules.member.entity.dos.MemberShareLog;
import cn.lili.modules.member.entity.enums.ExperienceRuleEnum;
import cn.lili.modules.member.entity.vo.MemberShareLogVO;
import cn.lili.modules.member.mapper.MemberExperienceLogMapper;
import cn.lili.modules.member.mapper.MemberShareLogMapper;
import cn.lili.modules.member.service.MemberExperienceService;
import cn.lili.modules.member.service.MemberService;
import cn.lili.modules.member.service.MemberShareLogService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 会员分享记录业务层实现
 */
@Service
public class MemberShareLogServiceImpl extends ServiceImpl<MemberShareLogMapper, MemberShareLog> implements MemberShareLogService {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberExperienceService memberExperienceService;
    @Autowired
    private MemberExperienceLogMapper memberExperienceLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveShareAndGrantExperience(String memberId, String shareScene, String sharePage, String relatedId) {
        Member member = memberService.getById(memberId);
        if (member == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        // 优先使用业务主键作为经验值幂等bizId，避免同一业务重复发放
        String experienceBizId = CharSequenceUtil.isNotBlank(relatedId) ? relatedId : null;
        if (CharSequenceUtil.isNotBlank(experienceBizId)) {
            Long count = memberExperienceLogMapper.countByMemberRuleAndBiz(memberId, ExperienceRuleEnum.SHARE.name(), experienceBizId);
            if (count != null && count > 0) {
                return;
            }
        }
        MemberShareLog shareLog = new MemberShareLog();
        shareLog.setMemberId(memberId);
        shareLog.setShareScene(shareScene);
        shareLog.setSharePage(sharePage);
        shareLog.setRelatedId(CharSequenceUtil.blankToDefault(relatedId, ""));
        this.save(shareLog);
        if (CharSequenceUtil.isBlank(experienceBizId)) {
            experienceBizId = shareLog.getId();
        }

        // 基于分享记录发放经验值，确保可追溯与可审计
        memberExperienceService.grantExperience(
                memberId,
                ExperienceRuleEnum.SHARE,
                experienceBizId,
                "会员分享商城页面，赠送经验值"
        );
    }

    @Override
    public IPage<MemberShareLogVO> getShareLogPage(String memberMobile, Date startTime, Date endTime, PageVO page) {
        QueryWrapper<MemberShareLog> wrapper = new QueryWrapper<>();
        if (CharSequenceUtil.isNotBlank(memberMobile)) {
            Member member = memberService.findByMobile(memberMobile);
            if (member == null) {
                wrapper.eq("sl.member_id", "__NOT_EXIST_MEMBER__");
            } else {
                wrapper.eq("sl.member_id", member.getId());
            }
        }
        wrapper.ge(startTime != null, "sl.create_time", startTime);
        wrapper.le(endTime != null, "sl.create_time", endTime);
        wrapper.orderByDesc("sl.create_time");
        return this.baseMapper.pageWithMember(PageUtil.initPage(page), wrapper);
    }
}
