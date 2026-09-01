package cn.lili.modules.payment.kit.plugin.alipay;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.SpringContextUtil;
import cn.lili.modules.system.entity.dos.Setting;
import cn.lili.modules.system.entity.dto.payment.AlipayPaymentSetting;
import cn.lili.modules.system.entity.enums.SettingEnum;
import cn.lili.modules.system.service.SettingService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;

import java.util.Date;

/**
 * AliPayApiConfigKit
 *
 * @author Chopper
 * @since 2020-12-16 09:31
 */
public class AliPayApiConfigKit {

    /**
     * 支付配置
     */
    static DefaultAlipayClient defaultAlipayClient;

    /**
     * 下次刷新时间
     */
    static Date nextRebuildDate;

    /**
     * 间隔时间
     */
    static Long refreshInterval = 1000 * 60 * 3L;

    /**
     * 是否为证书模式（普通公钥模式下为 false）
     */
    static volatile boolean certificateMode = true;

    /**
     * 当前是否为证书模式
     */
    public static boolean isCertificateMode() {
        return certificateMode;
    }

    /**
     * 获取支付宝支付参数
     *
     * @return
     * @throws AlipayApiException
     */
    public static synchronized DefaultAlipayClient getAliPayApiConfig() throws AlipayApiException {
        Date date = new Date();
        //如果过期，则重新构建
        if (nextRebuildDate == null || date.after(nextRebuildDate)) {
            return rebuild();
        }
        return defaultAlipayClient;
    }

    static DefaultAlipayClient rebuild() throws AlipayApiException {
        AlipayPaymentSetting setting;
        try {
            SettingService settingService = (SettingService) SpringContextUtil.getBean("settingServiceImpl");
            Setting systemSetting = settingService.get(SettingEnum.ALIPAY_PAYMENT.name());
            setting = JSON.parseObject(systemSetting.getSettingValue(), AlipayPaymentSetting.class);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.PAY_NOT_SUPPORT);
        }
        // 网关地址：优先取数据库配置（沙箱地址），未配置则默认生产网关
        String serverUrl = cn.hutool.core.util.StrUtil.isNotEmpty(setting.getServerUrl())
                ? setting.getServerUrl()
                : "https://openapi.alipay.com/gateway.do";
        // 配置了支付宝公钥字符串 → 普通公钥模式（沙箱环境常用）；否则证书模式
        if (cn.hutool.core.util.StrUtil.isNotEmpty(setting.getAlipayPublicKey())) {
            certificateMode = false;
            defaultAlipayClient = new DefaultAlipayClient(serverUrl, setting.getAppId(), setting.getPrivateKey(),
                    "json", "utf-8", setting.getAlipayPublicKey(), "RSA2");
        } else {
            certificateMode = true;
            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
            certAlipayRequest.setServerUrl(serverUrl);
            certAlipayRequest.setFormat("json");
            certAlipayRequest.setCharset("utf-8");
            certAlipayRequest.setSignType("RSA2");
            certAlipayRequest.setAppId(setting.getAppId());
            certAlipayRequest.setPrivateKey(setting.getPrivateKey());
            certAlipayRequest.setCertPath(setting.getCertPath());
            certAlipayRequest.setAlipayPublicCertPath(setting.getAlipayPublicCertPath());
            certAlipayRequest.setRootCertPath(setting.getRootCertPath());
            defaultAlipayClient = new DefaultAlipayClient(certAlipayRequest);
        }
        nextRebuildDate = DateUtil.date(System.currentTimeMillis()+ refreshInterval);
        return defaultAlipayClient;
    }
}
