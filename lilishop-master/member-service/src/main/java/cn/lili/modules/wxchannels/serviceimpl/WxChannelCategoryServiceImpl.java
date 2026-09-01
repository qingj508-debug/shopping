package cn.lili.modules.wxchannels.serviceimpl;

import cn.hutool.core.collection.CollUtil;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.vo.PageVO;
import cn.lili.cache.Cache;
import cn.lili.cache.CachePrefix;
import cn.lili.modules.wxchannels.entity.dos.WxChannelCategory;
import cn.lili.modules.wxchannels.entity.dto.WxChannelCategorySubmitDTO;
import cn.lili.modules.wxchannels.entity.dto.WxChannelCategorySubmitItemDTO;
import cn.lili.modules.wxchannels.entity.dto.WxChannelsCategoryDTO;
import cn.lili.modules.wxchannels.entity.enums.WxChannelCategoryStatus;
import cn.lili.modules.wxchannels.mapper.WxChannelCategoryMapper;
import cn.lili.modules.wxchannels.service.WxChannelCategoryService;
import cn.lili.modules.wxchannels.service.WxChannelsApiService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class WxChannelCategoryServiceImpl extends ServiceImpl<WxChannelCategoryMapper, WxChannelCategory> implements WxChannelCategoryService {

    @Autowired
    private Cache cache;
    @Autowired
    private WxChannelsApiService wxChannelsApiService;

    @Override
    public IPage<WxChannelCategory> page(PageVO page, String status) {
        LambdaQueryWrapper<WxChannelCategory> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(status)) {
            wrapper.eq(WxChannelCategory::getStatus, status);
        }
        wrapper.orderByDesc(WxChannelCategory::getCreateTime);
        return this.page(PageUtil.initPage(page), wrapper);
    }

    @Override
    public void submit(WxChannelCategorySubmitDTO dto) {
        if (dto == null || CollUtil.isEmpty(dto.getItems())) {
            throw new ServiceException(ResultCode.PARAMS_ERROR);
        }
        if (dto.getItems().size() > 10) {
            throw new ServiceException(ResultCode.ERROR);
        }
        List<WxChannelCategory> list = dto.getItems().stream().map(this::toEntity).collect(Collectors.toList());
        this.saveBatch(list);
    }

    @Override
    public List<WxChannelsCategoryDTO> listAllThirdCategory(boolean forceRefresh) {
        String key = CachePrefix.WX_CHANNELS_THIRD_CATEGORY.getPrefix() + "all";
        if (!forceRefresh) {
            List<WxChannelsCategoryDTO> cached = (List<WxChannelsCategoryDTO>) cache.get(key);
            if (cached != null) {
                return cached;
            }
        }
        List<WxChannelsCategoryDTO> list = wxChannelsApiService.getThirdCategories();
        if (list != null && !list.isEmpty()) {
            cache.put(key, list, 1L, TimeUnit.DAYS);
        }
        return list;
    }

    private WxChannelCategory toEntity(WxChannelCategorySubmitItemDTO item) {
        WxChannelCategory e = new WxChannelCategory();
        e.setWxCategoryId(item.getWxCategoryId());
        e.setWxCategoryName(item.getWxCategoryName());
        e.setPlatformCategoryId(item.getPlatformCategoryId());
        e.setPlatformCategoryName(item.getPlatformCategoryName());
        e.setMaterials(item.getMaterials());
        e.setStatus(WxChannelCategoryStatus.PENDING.name());
        e.setCreateTime(new Date());
        e.setUpdateTime(new Date());
        e.setDeleteFlag(false);
        return e;
    }
}
