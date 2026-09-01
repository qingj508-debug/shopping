package cn.lili.modules.member.service;

import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.SearchVO;
import cn.lili.modules.member.entity.dos.MemberNoticeSenter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 客户消息业务层
 *
 * @author Chopper
 * @since 2020/11/17 3:44 下午
 */
public interface MemberNoticeSenterService extends IService<MemberNoticeSenter> {

    /**
     * 自定义保存方法
     *
     * @param memberNoticeSenter 客户消息
     * @return 操作状态
     */
    boolean customSave(MemberNoticeSenter memberNoticeSenter);

    /**
     * 客户消息分页查询
     *
     * @param entity 查询实体
     * @param searchVO 查询条件（时间范围等）
     * @param pageVO 分页参数
     * @return 分页结果
     */
    IPage<MemberNoticeSenter> getByPage(MemberNoticeSenter entity, SearchVO searchVO, PageVO pageVO);

}
