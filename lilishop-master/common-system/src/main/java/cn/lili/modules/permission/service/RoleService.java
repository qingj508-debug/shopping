package cn.lili.modules.permission.service;


import cn.lili.common.vo.PageVO;
import cn.lili.modules.permission.entity.dos.Role;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色业务层
 *
 * @author Chopper
 * @since 2020/11/17 3:45 下午
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取默认角色
     *
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);


    /**
     * 批量删除角色
     * @param roleIds
     */
    void deleteRoles(List<String> roleIds);

    /**
     * 角色分页查询
     *
     * @param pageVO 分页参数
     * @param role 查询参数
     * @return 分页结果
     */
    Page<Role> getRolePage(PageVO pageVO, Role role);
}
