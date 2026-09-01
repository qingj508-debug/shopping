package cn.lili.modules.goods.entity.dos;

/**
 * 商品分组实体（DO）
 *
 * @author mike
 * @date 2026-03-30
 */
import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分组表：将“商品”按业务维度进行归类，便于后台管理与筛选。
 * <p>
 * 该类同时继承了 {@link BaseEntity}，因此通常包含创建时间、更新时间、删除标记等通用字段。
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("li_goods_group")
@Schema(description = "商品分组")
public class GoodsGroup extends BaseEntity {

    /**
     * 分组名称：不能为空，用于展示给用户/后台管理端识别该分组。
     */
    @NotNull
    @Schema(description = "分组名称")
    private String groupName;

    /**
     * 分组描述：对该分组的补充说明（可选）。
     */
    @Schema(description = "分组描述")
    private String description;
}

