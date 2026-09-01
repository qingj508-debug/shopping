package cn.lili.modules.system.entity.dto.payment;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付宝支付设置
 *
 * @author Chopper
 * @since 2020-12-02 10:09
 */
@Data
@Accessors(chain = true)
public class AlipayPaymentSetting {

    /**
     * 应用id
     */
    private String appId;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 应用证书
     */
    private String certPath;

    /**
     * 支付宝公钥
     */
    private String alipayPublicCertPath;

    /**
     * 支付宝根证书
     */
    private String rootCertPath;

    /**
     * 回调API地址
     */
    private String callbackUrl;

    /**
     * 支付宝公钥字符串（普通公钥模式，沙箱环境使用；为空则走证书模式）
     */
    private String alipayPublicKey;

    /**
     * 支付宝网关地址，如沙箱 https://openapi-sandbox.dl.alipaydev.com/gateway.do；
     * 为空时默认生产网关 https://openapi.alipay.com/gateway.do
     */
    private String serverUrl;

}
