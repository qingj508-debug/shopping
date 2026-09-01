package cn.lili.modules.wxchannels.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_wx_channel_goods")
@Schema(description = "微信视频号商品")
public class WxChannelGoods extends BaseEntity {

    @Schema(description = "平台商品ID")
    private String goodsId;

    @Schema(description = "平台SKU ID")
    private String skuId;

    @Schema(description = "商品名称")
    private String goodsName;

    @Schema(description = "商品图片")
    private String goodsImage;

    @Schema(description = "分类ID")
    private String categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "店铺ID")
    private String storeId;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "销售价")
    private Double costPrice;

    @Schema(description = "视频号销售价")
    private Double channelPrice;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "状态 APPROVED,PENDING,REJECTED")
    private String status;
}
