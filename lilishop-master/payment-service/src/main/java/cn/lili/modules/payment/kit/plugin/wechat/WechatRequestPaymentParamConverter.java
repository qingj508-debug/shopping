package cn.lili.modules.payment.kit.plugin.wechat;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 将 wechatpay-java 的调起支付返回对象转换为当前项目既有的前端字段格式。
 */
final class WechatRequestPaymentParamConverter {

    private WechatRequestPaymentParamConverter() {
    }

    static Map<String, String> fromJsapiResponse(com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse response) {
        Map<String, String> result = new LinkedHashMap<>(6);
        result.put("appId", response.getAppId());
        result.put("timeStamp", response.getTimeStamp());
        result.put("nonceStr", response.getNonceStr());
        result.put("package", response.getPackageVal());
        result.put("signType", response.getSignType());
        result.put("paySign", response.getPaySign());
        return result;
    }

    static Map<String, String> fromAppResponse(com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse response) {
        Map<String, String> result = new LinkedHashMap<>(7);
        result.put("appid", response.getAppid());
        result.put("partnerid", response.getPartnerId());
        result.put("prepayid", response.getPrepayId());
        result.put("package", response.getPackageVal());
        result.put("noncestr", response.getNonceStr());
        result.put("timestamp", response.getTimestamp());
        result.put("sign", response.getSign());
        return result;
    }
}
