package cn.lili.modules.wxchannels.serviceimpl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.lili.modules.system.entity.dos.Setting;
import cn.lili.modules.system.entity.enums.SettingEnum;
import cn.lili.modules.system.service.SettingService;
import cn.lili.modules.wxchannels.entity.dto.WxChannelsCategoryDTO;
import cn.lili.modules.wxchannels.entity.dto.WxChannelsSetting;
import cn.lili.modules.wxchannels.service.WxChannelsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WxChannelsApiServiceImpl implements WxChannelsApiService {

    @Autowired
    private SettingService settingService;

    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();

    @Override
    public void updateInventory(String skuId, Integer stock) {
        WxChannelsSetting setting = getSetting();
        String token = getToken(setting);
        String url = setting.getApiBase() + "/goods/inventory/update?access_token=" + token;
        Map<String, Object> body = MapUtil.builder(new HashMap<String, Object>())
                .put("skuId", skuId)
                .put("stock", stock)
                .build();
        HttpUtil.post(url, JSONUtil.toJsonStr(body));
    }

    @Override
    public void changeStatus(String goodsId, String status) {
        WxChannelsSetting setting = getSetting();
        String token = getToken(setting);
        String url = setting.getApiBase() + "/goods/status/update?access_token=" + token;
        Map<String, Object> body = MapUtil.builder(new HashMap<String, Object>())
                .put("goodsId", goodsId)
                .put("status", status)
                .build();
        HttpUtil.post(url, JSONUtil.toJsonStr(body));
    }

    @Override
    public List<WxChannelsCategoryDTO> getThirdCategories() {
        WxChannelsSetting setting = getSetting();
        String token = getToken(setting);
        String url = setting.getApiBase() + "/cat/get_children_cateogry?access_token=" + token;
        String resp = HttpUtil.get(url);
        JSONObject obj = JSONUtil.parseObj(resp);
        Integer errcode = obj.getInt("errcode", 0);
        if (errcode != null && errcode != 0) {
            return new ArrayList<>();
        }
        JSONArray arr = obj.getJSONArray("third_cat_list");
        List<WxChannelsCategoryDTO> list = new ArrayList<>();
        if (arr == null) {
            return list;
        }
        for (Object o : arr) {
            JSONObject it = JSONUtil.parseObj(o);
            WxChannelsCategoryDTO dto = new WxChannelsCategoryDTO();
            dto.setThirdCatId(it.getLong("third_cat_id"));
            dto.setThirdCatName(it.getStr("third_cat_name"));
            dto.setQualification(it.getStr("qualification"));
            dto.setQualificationType(it.getInt("qualification_type"));
            dto.setProductQualification(it.getStr("product_qualification"));
            dto.setProductQualificationType(it.getInt("product_qualification_type"));
            dto.setSecondCatId(it.getLong("second_cat_id"));
            dto.setSecondCatName(it.getStr("second_cat_name"));
            dto.setFirstCatId(it.getLong("first_cat_id"));
            dto.setFirstCatName(it.getStr("first_cat_name"));
            list.add(dto);
        }
        return list;
    }

    private WxChannelsSetting getSetting() {
        Setting s = settingService.get(SettingEnum.WX_CHANNELS.name());
        if (s == null || s.getSettingValue() == null) {
            return new WxChannelsSetting();
        }
        return JSONUtil.toBean(s.getSettingValue(), WxChannelsSetting.class);
    }

    private String getToken(WxChannelsSetting setting) {
        String key = setting.getAppId();
        if (tokenCache.containsKey(key)) {
            return tokenCache.get(key);
        }
        String url = setting.getTokenUrl() + "?grant_type=client_credential&appid=" + setting.getAppId() + "&secret=" + setting.getAppSecret();
        String resp = HttpUtil.get(url);
        String token = JSONUtil.parseObj(resp).getStr("access_token");
        tokenCache.put(key, token);
        return token;
    }
}
