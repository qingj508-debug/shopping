package cn.lili.modules.member.service;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.member.entity.dos.MemberShareLog;
import cn.lili.modules.member.entity.vo.MemberShareLogVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * 会员分享记录业务层
 */
public interface MemberShareLogService extends IService<MemberShareLog> {

    /**
     * 记录分享并发放分享经验值
     *
     * @param memberId 会员ID
     * @param shareScene 分享场景
     * @param sharePage 分享页面
     * @param relatedId 关联业务ID
     */
    void saveShareAndGrantExperience(String memberId, String shareScene, String sharePage, String relatedId);

    /**
     * 分享记录分页（联表返回会员信息）
     *
     * @param memberMobile 会员手机号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param page 分页参数
     * @return 分享记录分页
     */
    IPage<MemberShareLogVO> getShareLogPage(String memberMobile, Date startTime, Date endTime, PageVO page);
}
