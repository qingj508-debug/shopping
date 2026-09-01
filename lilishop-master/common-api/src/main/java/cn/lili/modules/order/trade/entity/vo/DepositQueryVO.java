package cn.lili.modules.order.trade.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 预存款充值记录查询条件
 *
 * @author pikachu
 * @since 2020-02-25 14:10:16
 */
@Data
@Schema(description = "预存款变动记录查询条件")
@AllArgsConstructor
@NoArgsConstructor
public class DepositQueryVO implements Serializable {


    private static final long serialVersionUID = -6413611244037073693L;

    /**
     * 客户ID
     */
    @Schema(description = "客户Id")
    private String memberId;
    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String memberName;

    @Schema(description = "起始日期")
    private String startDate;

    @Schema(description = "结束日期")
    private String endDate;


}