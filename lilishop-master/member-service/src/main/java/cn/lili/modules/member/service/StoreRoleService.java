package cn.lili.modules.member.service;


import cn.lili.common.vo.PageVO;
import cn.lili.modules.member.entity.dos.StoreRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色业务层
 *
 * @author Chopper
 * @since 2020/11/17 3:45 下午
 */
public interface StoreRoleService extends IService<StoreRole> {

    /**
     * 获取默认角色
     *
     * @param defaultRole
     * @return
     */
    List<StoreRole> findByDefaultRole(Boolean defaultRole);


    /**
     * 批量删除角色
     *
     * @param roleIds
     */
    void deleteRoles(List<String> roleIds);

    /**
     * 修改角色
     *
     * @param storeRole 店铺角色
     * @return
     */
    Boolean update(StoreRole storeRole);

    /**
     * 保存店铺角色
     *
     * @param storeRole 店铺角色
     * @return
     */
    Boolean saveStoreRole(StoreRole storeRole);

    /**
     * 当前店铺 根据角色id查询角色
     *
     * @param ids 角色id
     * @return
     */
    List<StoreRole> list(List<String> ids);

    /**
     * 店铺角色分页查询
     *
     * @param pageVO 分页参数
     * @param storeRole 查询参数
     * @return 分页结果
     */
    Page<StoreRole> getRolePage(PageVO pageVO, StoreRole storeRole);
}
