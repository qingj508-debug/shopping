package cn.lili.modules.system.service;

import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.SearchVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.lili.modules.system.entity.dos.ServiceNotice;

/**
 * 服务订阅消息业务层
 *
 * @author Chopper
 * @since 2020/11/17 8:02 下午
 */
public interface ServiceNoticeService extends IService<ServiceNotice> {

    /**
     * 服务订阅消息分页查询
     *
     * @param entity 查询参数
     * @param searchVO 查询条件
     * @param pageVO 分页参数
     * @return 分页结果
     */
    IPage<ServiceNotice> getByPage(ServiceNotice entity, SearchVO searchVO, PageVO pageVO);
}
