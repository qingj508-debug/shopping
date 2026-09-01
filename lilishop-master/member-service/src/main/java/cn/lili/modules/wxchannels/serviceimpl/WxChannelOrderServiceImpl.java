package cn.lili.modules.wxchannels.serviceimpl;

import cn.lili.modules.wxchannels.entity.dos.WxChannelOrder;
import cn.lili.modules.wxchannels.entity.dto.WxChannelOrderSearchParams;
import cn.lili.modules.wxchannels.mapper.WxChannelOrderMapper;
import cn.lili.modules.wxchannels.service.WxChannelOrderService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxChannelOrderServiceImpl extends ServiceImpl<WxChannelOrderMapper, WxChannelOrder> implements WxChannelOrderService {
    @Override
    public IPage<WxChannelOrder> page(WxChannelOrderSearchParams params) {
        return this.page(PageUtil.initPage(params), params.queryWrapper().orderByDesc("create_time"));
    }
}
