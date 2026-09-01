package cn.lili.modules.goods.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_goods_group_goods")
@Schema(description = "商品分组商品关联")
public class GoodsGroupGoods extends BaseEntity {

    @NotNull
    @Schema(description = "分组ID")
    private String groupId;

    @NotNull
    @Schema(description = "商品ID")
    private String goodsId;
}

