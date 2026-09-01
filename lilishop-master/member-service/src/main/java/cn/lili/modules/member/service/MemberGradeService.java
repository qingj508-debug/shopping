package cn.lili.modules.member.service;

import cn.lili.modules.member.entity.dos.MemberGrade;
import cn.lili.modules.member.entity.vo.MemberGradeDetailVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 客户等级业务层
 * @author Bulbasaur
 * @since 2021/5/14 5:57 下午
 *
 */
public interface MemberGradeService extends IService<MemberGrade> {

    /**
     * 保存等级并校验业务规则
     *
     * @param memberGrade 等级
     */
    void saveGrade(MemberGrade memberGrade);

    /**
     * 更新等级并校验业务规则
     *
     * @param id          等级ID
     * @param memberGrade 等级
     */
    void updateGrade(String id, MemberGrade memberGrade);

    /**
     * 切换等级状态
     *
     * @param id    等级ID
     * @param state 状态 OPEN/CLOSE
     */
    void updateGradeState(String id, String state);

    /**
     * 按经验值计算当前等级
     *
     * @param experience 经验值
     * @return 等级
     */
    MemberGrade getCurrentGradeByExperience(Long experience);

    /**
     * 获取客户等级列表（按升级排序）
     *
     * @return 等级列表
     */
    List<MemberGrade> listForUpgrade();

    /**
     * 删除客户等级并校验业务规则
     *
     * @param id 等级ID
     */
    void deleteGrade(String id);

    /**
     * 设置等级权益（覆盖式）
     *
     * @param gradeId 等级ID
     * @param benefitIds 权益ID列表（传空表示清空）
     */
    void updateGradeBenefits(String gradeId, List<String> benefitIds);

    /**
     * 获取等级配置的权益ID列表
     *
     * @param gradeId 等级ID
     * @return 权益ID列表
     */
    List<String> getGradeBenefitIdList(String gradeId);

    /**
     * 管理端等级详情（含关联权益完整信息，顺序与 benefitIds 一致）
     *
     * @param id 等级ID
     * @return 详情
     */
    MemberGradeDetailVO getManagerGradeDetail(String id);

    /**
     * 买家端：升级排序的等级列表，每项含关联权益（仅返回启用 OPEN 的权益，顺序与 benefitIds 一致）
     *
     * @return 等级及权益列表
     */
    List<MemberGradeDetailVO> listForUpgradeWithBenefits();
}
