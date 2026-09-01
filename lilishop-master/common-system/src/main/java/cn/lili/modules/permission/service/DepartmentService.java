package cn.lili.modules.permission.service;

import cn.lili.common.vo.SearchVO;
import cn.lili.modules.permission.entity.dos.Department;
import cn.lili.modules.permission.entity.vo.DepartmentVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 部门业务层
 *
 * @author Chopper
 * @since 2020/11/17 3:43 下午
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 获取部门树
     *
     * @param initWrapper
     * @return
     */
    List<DepartmentVO> tree(QueryWrapper<Department> initWrapper);

    /**
     * 获取部门树（由 service 内部构造查询条件）
     *
     * @param entity 查询参数
     * @param searchVO 查询条件
     * @return 树结构结果
     */
    List<DepartmentVO> tree(Department entity, SearchVO searchVO);

    /**
     * 删除部门
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
