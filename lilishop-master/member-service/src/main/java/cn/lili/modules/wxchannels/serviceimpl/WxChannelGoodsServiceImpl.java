package cn.lili.modules.wxchannels.serviceimpl;

import cn.lili.modules.wxchannels.entity.dos.WxChannelGoods;
import cn.lili.modules.wxchannels.entity.dto.WxChannelGoodsSearchParams;
import cn.lili.modules.wxchannels.mapper.WxChannelGoodsMapper;
import cn.lili.modules.wxchannels.service.WxChannelGoodsService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxChannelGoodsServiceImpl extends ServiceImpl<WxChannelGoodsMapper, WxChannelGoods> implements WxChannelGoodsService {

    @Override
    public IPage<WxChannelGoods> page(WxChannelGoodsSearchParams params) {
        return this.page(PageUtil.initPage(params), params.queryWrapper().orderByDesc("create_time"));
        }
}
