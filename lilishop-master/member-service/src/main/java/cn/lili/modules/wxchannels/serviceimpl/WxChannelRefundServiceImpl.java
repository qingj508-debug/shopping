package cn.lili.modules.wxchannels.serviceimpl;

import cn.lili.modules.wxchannels.entity.dos.WxChannelRefund;
import cn.lili.modules.wxchannels.entity.dto.WxChannelRefundSearchParams;
import cn.lili.modules.wxchannels.mapper.WxChannelRefundMapper;
import cn.lili.modules.wxchannels.service.WxChannelRefundService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxChannelRefundServiceImpl extends ServiceImpl<WxChannelRefundMapper, WxChannelRefund> implements WxChannelRefundService {
    @Override
    public IPage<WxChannelRefund> page(WxChannelRefundSearchParams params) {
        return this.page(PageUtil.initPage(params), params.queryWrapper().orderByDesc("create_time"));
    }
}
