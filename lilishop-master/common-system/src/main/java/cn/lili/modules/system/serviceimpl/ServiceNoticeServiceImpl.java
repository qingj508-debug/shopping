package cn.lili.modules.system.serviceimpl;

import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.SearchVO;
import cn.lili.modules.system.entity.dos.ServiceNotice;
import cn.lili.modules.system.mapper.ServiceNoticeMapper;
import cn.lili.modules.system.service.ServiceNoticeService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务订阅消息业务层实现
 * @author Chopper
 * @since 2020/11/17 8:02 下午
 */
@Service
public class ServiceNoticeServiceImpl extends ServiceImpl<ServiceNoticeMapper, ServiceNotice> implements ServiceNoticeService {

    @Override
    public IPage<ServiceNotice> getByPage(ServiceNotice entity, SearchVO searchVO, PageVO pageVO) {
        return this.page(PageUtil.initPage(pageVO), PageUtil.initWrapper(entity, searchVO));
    }
}
