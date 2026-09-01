package cn.lili.modules.member.service;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.member.entity.dos.MemberBenefit;
import cn.lili.modules.member.entity.vo.EnumValueVO;
import cn.lili.modules.member.entity.vo.MemberBenefitDetailVO;
import cn.lili.modules.member.entity.vo.MemberBenefitGrantRecordVO;
import cn.lili.modules.member.entity.vo.MemberCurrentBenefitVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 客户权益服务
 */
public interface MemberBenefitService extends IService<MemberBenefit> {

    /**
     * 新增客户权益（含业务校验）
     *
     * @param memberBenefit 客户权益
     */
    void saveBenefit(MemberBenefit memberBenefit);

    /**
     * 更新客户权益（含业务校验）
     *
     * @param id 权益ID
     * @param memberBenefit 客户权益
     */
    void updateBenefit(String id, MemberBenefit memberBenefit);

    /**
     * 切换客户权益状态
     *
     * @param id 权益ID
     * @param state 状态 OPEN/CLOSE
     */
    void updateBenefitState(String id, String state);

    /**
     * 删除客户权益（若被等级引用则拒绝删除）
     *
     * @param id 权益ID
     */
    void deleteBenefit(String id);

    /**
     * 客户权益分页查询
     *
     * @param keyword 关键字（权益名称，模糊匹配）
     * @param state 状态 OPEN/CLOSE（可选）
     * @param page 分页参数
     * @return 分页结果
     */
    IPage<MemberBenefit> getBenefitPage(String keyword, String state, PageVO page);

    /**
     * 客户权益列表查询
     *
     * @param onlyOpen 是否仅返回启用权益
     * @return 权益列表
     */
    List<MemberBenefit> listBenefits(Boolean onlyOpen);

    /**
     * 客户权益类型枚举列表（用于下拉选择）
     *
     * @return 类型列表（value/description）
     */
    List<EnumValueVO> listBenefitTypes();

    /**
     * 按ID列表查询权益（保持入参顺序不保证）
     *
     * @param ids 权益ID列表
     * @param onlyOpen 是否仅返回启用权益
     * @return 权益列表
     */
    List<MemberBenefit> listByIdList(List<String> ids, Boolean onlyOpen);

    /**
     * 获取当前会员的等级与权益信息。
     *
     * @param memberId 会员ID
     * @return 当前会员权益信息
     */
    MemberCurrentBenefitVO getCurrentMemberBenefits(String memberId);

    /**
     * 管理端权益详情（券礼包类型联查优惠券名称、面值、有效期等）
     *
     * @param id 权益ID
     * @return 详情
     */
    MemberBenefitDetailVO getManagerBenefitDetail(String id);

    /**
     * 会员首次达到某等级时，发放该等级配置的赠送积分、券礼包等权益（同一会员同一等级仅一次）。
     *
     * @param memberId 会员ID
     * @param gradeId  达到的等级ID
     */
    void tryGrantBenefitsForGradeReached(String memberId, String gradeId);

    /**
     * 管理端：等级权益发放记录分页（会员达到某等级时的一次性发放记录）
     *
     * @param mobile  会员手机号（精确匹配，可选）
     * @param gradeId 等级ID（可选）
     * @param page    分页
     * @return 分页数据
     */
    IPage<MemberBenefitGrantRecordVO> pageBenefitGrantRecords(String mobile, String gradeId, PageVO page);
}
