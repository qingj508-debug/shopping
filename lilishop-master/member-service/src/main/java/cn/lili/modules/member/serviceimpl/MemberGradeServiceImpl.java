package cn.lili.modules.member.serviceimpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.enums.SwitchEnum;
import cn.lili.common.exception.ServiceException;
import cn.lili.modules.member.entity.dos.Member;
import cn.lili.modules.member.entity.dos.MemberBenefit;
import cn.lili.modules.member.entity.dos.MemberGrade;
import cn.lili.modules.member.entity.vo.MemberGradeDetailVO;
import cn.lili.modules.member.mapper.MemberBenefitMapper;
import cn.lili.modules.member.mapper.MemberGradeMapper;
import cn.lili.modules.member.service.MemberGradeService;
import cn.lili.modules.member.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

/**
 * 客户等级业务层实现
 *
 * @author Bulbasaur
 * @since 2021/5/14 5:58 下午
 */
@Service
public class MemberGradeServiceImpl extends ServiceImpl<MemberGradeMapper, MemberGrade> implements MemberGradeService {

    private static final Pattern HEX_COLOR = Pattern.compile("^#([A-Fa-f0-9]{6})$");

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberBenefitMapper memberBenefitMapper;

    @Override
    public void saveGrade(MemberGrade memberGrade) {
        if (memberGrade.getIsDefault() == null) {
            memberGrade.setIsDefault(false);
        }
        this.validateGrade(memberGrade, null);
        if (this.count() <= 0) {
            memberGrade.setIsDefault(true);
        }
        if (!this.save(memberGrade)) {
            throw new ServiceException(ResultCode.ERROR, "客户等级保存失败");
        }
        if (Boolean.TRUE.equals(memberGrade.getIsDefault())) {
            this.clearOtherDefault(memberGrade.getId());
        }
    }

    @Override
    public void updateGrade(String id, MemberGrade memberGrade) {
        MemberGrade original = this.getById(id);
        if (original == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST, "客户等级不存在");
        }
        memberGrade.setId(id);
        if (memberGrade.getIsDefault() == null) {
            memberGrade.setIsDefault(Boolean.TRUE.equals(original.getIsDefault()));
        }
        this.validateGrade(memberGrade, id);
        if (Boolean.FALSE.equals(memberGrade.getIsDefault()) && Boolean.TRUE.equals(original.getIsDefault())) {
            if (!this.hasOtherDefault(id)) {
                throw new ServiceException(ResultCode.PARAMS_ERROR, "至少保留一个默认等级");
            }
        }
        if (!this.updateById(memberGrade)) {
            throw new ServiceException(ResultCode.ERROR, "客户等级更新失败");
        }
        if (Boolean.TRUE.equals(memberGrade.getIsDefault())) {
            this.clearOtherDefault(id);
        }
    }

    @Override
    public void updateGradeState(String id, String state) {
        if (!SwitchEnum.OPEN.name().equals(state) && !SwitchEnum.CLOSE.name().equals(state)) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "等级开关状态非法");
        }
        MemberGrade memberGrade = this.getById(id);
        if (memberGrade == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST, "客户等级不存在");
        }
        if (Boolean.TRUE.equals(memberGrade.getIsDefault()) && SwitchEnum.CLOSE.name().equals(state)) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "默认等级必须为开启状态");
        }
        memberGrade.setGradeState(state);
        this.updateById(memberGrade);
    }

    @Override
    public MemberGrade getCurrentGradeByExperience(Long experience) {
        long currentExperience = experience == null ? 0L : Math.max(experience, 0L);
        LambdaQueryWrapper<MemberGrade> queryWrapper = new LambdaQueryWrapper<MemberGrade>()
                .eq(MemberGrade::getGradeState, SwitchEnum.OPEN.name())
                .le(MemberGrade::getRequiredExperience, currentExperience)
                .orderByDesc(MemberGrade::getRequiredExperience)
                .last("limit 1");
        MemberGrade grade = this.getOne(queryWrapper, false);
        if (grade != null) {
            return grade;
        }
        return this.getDefaultGrade();
    }

    @Override
    public List<MemberGrade> listForUpgrade() {
        LambdaQueryWrapper<MemberGrade> queryWrapper = new LambdaQueryWrapper<MemberGrade>()
                .orderByAsc(MemberGrade::getGradeSort);
        return this.list(queryWrapper);
    }

    @Override
    public List<MemberGradeDetailVO> listForUpgradeWithBenefits() {
        List<MemberGrade> grades = this.listForUpgrade();
        if (CollUtil.isEmpty(grades)) {
            return Collections.emptyList();
        }
        List<MemberGradeDetailVO> result = new ArrayList<>(grades.size());
        for (MemberGrade g : grades) {
            result.add(this.buildGradeDetailVo(g, true));
        }
        return result;
    }

    @Override
    public MemberGradeDetailVO getManagerGradeDetail(String id) {
        MemberGrade grade = this.getById(id);
        if (grade == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST, "客户等级不存在");
        }
        return this.buildGradeDetailVo(grade, false);
    }

    /**
     * @param onlyOpenBenefits 为 true 时仅保留启用（OPEN）权益，用于买家端展示
     */
    private MemberGradeDetailVO buildGradeDetailVo(MemberGrade grade, boolean onlyOpenBenefits) {
        MemberGradeDetailVO vo = new MemberGradeDetailVO();
        vo.setGrade(grade);
        List<String> ids = parseBenefitIdList(grade.getBenefitIds());
        if (CollUtil.isEmpty(ids)) {
            vo.setBenefits(Collections.emptyList());
            return vo;
        }
        List<MemberBenefit> found = this.memberBenefitMapper.selectList(new LambdaQueryWrapper<MemberBenefit>()
                .in(MemberBenefit::getId, ids));
        Map<String, MemberBenefit> byId = found.stream()
                .collect(Collectors.toMap(MemberBenefit::getId, Function.identity(), (a, b) -> a));
        List<MemberBenefit> ordered = new ArrayList<>();
        for (String bid : ids) {
            MemberBenefit b = byId.get(bid);
            if (b == null) {
                continue;
            }
            if (onlyOpenBenefits && !SwitchEnum.OPEN.name().equals(b.getBenefitState())) {
                continue;
            }
            ordered.add(b);
        }
        vo.setBenefits(ordered);
        return vo;
    }

    @Override
    public void deleteGrade(String id) {
        MemberGrade memberGrade = this.getById(id);
        if (memberGrade == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST, "客户等级不存在");
        }
        if (Boolean.TRUE.equals(memberGrade.getIsDefault())) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "默认等级不支持删除");
        }
        LambdaQueryWrapper<Member> memberWrapper = new LambdaQueryWrapper<Member>()
                .eq(Member::getGradeId, id);
        long count = memberService.count(memberWrapper);
        if (count > 0) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "当前等级下存在会员，无法删除");
        }
        if (!this.removeById(id)) {
            throw new ServiceException(ResultCode.ERROR, "客户等级删除失败");
        }
    }

    @Override
    public void updateGradeBenefits(String gradeId, List<String> benefitIds) {
        MemberGrade memberGrade = this.getById(gradeId);
        if (memberGrade == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST, "客户等级不存在");
        }
        List<String> normalized = this.normalizeBenefitIds(benefitIds);
        if (CollUtil.isNotEmpty(normalized)) {
            Long count = memberBenefitMapper.selectCount(new LambdaQueryWrapper<MemberBenefit>()
                    .in(MemberBenefit::getId, normalized));
            long actual = count == null ? 0L : count;
            if (actual != normalized.size()) {
                throw new ServiceException(ResultCode.PARAMS_ERROR, "存在无效的权益ID");
            }
            memberGrade.setBenefitIds(String.join(",", normalized));
        } else {
            memberGrade.setBenefitIds(null);
        }
        if (!this.updateById(memberGrade)) {
            throw new ServiceException(ResultCode.ERROR, "客户等级权益更新失败");
        }
    }

    @Override
    public List<String> getGradeBenefitIdList(String gradeId) {
        MemberGrade memberGrade = this.getById(gradeId);
        if (memberGrade == null) {
            return new ArrayList<>();
        }
        return parseBenefitIdList(memberGrade.getBenefitIds());
    }

    private void validateGrade(MemberGrade memberGrade, String ignoreId) {
        if (memberGrade == null) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "客户等级参数不能为空");
        }
        if (CharSequenceUtil.isBlank(memberGrade.getGradeName())) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "等级名称不能为空");
        }
        if (memberGrade.getRequiredExperience() == null || memberGrade.getRequiredExperience() < 1) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "所需经验值必须为正整数");
        }
        if (memberGrade.getGradeSort() == null || memberGrade.getGradeSort() < 1) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "等级排序必须为正整数");
        }
        if (!SwitchEnum.OPEN.name().equals(memberGrade.getGradeState()) && !SwitchEnum.CLOSE.name().equals(memberGrade.getGradeState())) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "等级开关状态非法");
        }
        if (Boolean.TRUE.equals(memberGrade.getIsDefault()) && SwitchEnum.CLOSE.name().equals(memberGrade.getGradeState())) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "默认等级必须为开启状态");
        }
        if (CharSequenceUtil.isBlank(memberGrade.getGradeImage())) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "等级图标不能为空");
        }
        this.validateUrl(memberGrade.getGradeImage(), "等级图标URL格式错误");
        this.validateOptionalUrl(memberGrade.getGradeBackground(), "等级背景图URL格式错误");
        this.validateOptionalColor(memberGrade.getGradeFontColor(), "等级字体颜色格式错误，仅支持#RRGGBB");

        List<String> benefitIdList = parseBenefitIdList(memberGrade.getBenefitIds());
        List<String> normalizedBenefitIdList = normalizeBenefitIds(benefitIdList);
        if (CollUtil.isNotEmpty(normalizedBenefitIdList)) {
            Long count = memberBenefitMapper.selectCount(new LambdaQueryWrapper<MemberBenefit>()
                    .in(MemberBenefit::getId, normalizedBenefitIdList));
            long actual = count == null ? 0L : count;
            if (actual != normalizedBenefitIdList.size()) {
                throw new ServiceException(ResultCode.PARAMS_ERROR, "存在无效的权益ID");
            }
            memberGrade.setBenefitIds(String.join(",", normalizedBenefitIdList));
        } else {
            memberGrade.setBenefitIds(null);
        }

        LambdaQueryWrapper<MemberGrade> nameWrapper = new LambdaQueryWrapper<MemberGrade>()
                .eq(MemberGrade::getGradeName, memberGrade.getGradeName());
        if (CharSequenceUtil.isNotBlank(ignoreId)) {
            nameWrapper.ne(MemberGrade::getId, ignoreId);
        }
        if (this.count(nameWrapper) > 0) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "等级名称不能重复");
        }

        LambdaQueryWrapper<MemberGrade> expWrapper = new LambdaQueryWrapper<MemberGrade>()
                .eq(MemberGrade::getRequiredExperience, memberGrade.getRequiredExperience());
        if (CharSequenceUtil.isNotBlank(ignoreId)) {
            expWrapper.ne(MemberGrade::getId, ignoreId);
        }
        if (this.count(expWrapper) > 0) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, "所需经验值不能重复");
        }
    }

    private static List<String> parseBenefitIdList(String benefitIds) {
        if (CharSequenceUtil.isBlank(benefitIds)) {
            return new ArrayList<>();
        }
        List<String> parts = CharSequenceUtil.splitTrim(benefitIds, ',');
        List<String> result = new ArrayList<>();
        if (CollUtil.isEmpty(parts)) {
            return result;
        }
        for (String part : parts) {
            if (CharSequenceUtil.isNotBlank(part)) {
                result.add(part);
            }
        }
        return result;
    }

    private List<String> normalizeBenefitIds(List<String> benefitIds) {
        if (CollUtil.isEmpty(benefitIds)) {
            return new ArrayList<>();
        }
        Set<String> set = new LinkedHashSet<>();
        for (String id : benefitIds) {
            if (CharSequenceUtil.isNotBlank(id)) {
                set.add(id.trim());
            }
        }
        return new ArrayList<>(set);
    }

    private void clearOtherDefault(String id) {
        LambdaUpdateWrapper<MemberGrade> updateWrapper = new LambdaUpdateWrapper<MemberGrade>()
                .set(MemberGrade::getIsDefault, false)
                .ne(MemberGrade::getId, id)
                .eq(MemberGrade::getIsDefault, true);
        this.update(updateWrapper);
    }

    private boolean hasOtherDefault(String ignoreId) {
        LambdaQueryWrapper<MemberGrade> wrapper = new LambdaQueryWrapper<MemberGrade>()
                .eq(MemberGrade::getIsDefault, true)
                .ne(Objects.nonNull(ignoreId), MemberGrade::getId, ignoreId);
        return this.count(wrapper) > 0;
    }

    private MemberGrade getDefaultGrade() {
        LambdaQueryWrapper<MemberGrade> wrapper = new LambdaQueryWrapper<MemberGrade>()
                .eq(MemberGrade::getGradeState, SwitchEnum.OPEN.name())
                .eq(MemberGrade::getIsDefault, true)
                .last("limit 1");
        return this.getOne(wrapper, false);
    }

    private void validateOptionalUrl(String url, String message) {
        if (CharSequenceUtil.isBlank(url)) {
            return;
        }
        validateUrl(url, message);
    }

    private void validateUrl(String url, String message) {
        try {
            URI uri = URI.create(url);
            if (CharSequenceUtil.isBlank(uri.getScheme()) || CharSequenceUtil.isBlank(uri.getHost())) {
                throw new ServiceException(ResultCode.PARAMS_ERROR, message);
            }
        } catch (Exception e) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, message);
        }
    }

    private void validateOptionalColor(String color, String message) {
        if (CharSequenceUtil.isBlank(color)) {
            return;
        }
        if (!HEX_COLOR.matcher(color).matches()) {
            throw new ServiceException(ResultCode.PARAMS_ERROR, message);
        }
    }
}
