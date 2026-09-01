package cn.lili.modules.finance.export;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.feign.StoreFlowClient;
import cn.lili.modules.order.order.entity.dto.StoreFlowQueryDTO;
import cn.lili.modules.order.order.entity.enums.FlowTypeEnum;
import cn.lili.modules.store.entity.dos.Bill;
import cn.lili.modules.store.entity.enums.BillStatusEnum;
import cn.lili.modules.store.entity.vos.StoreFlowPayDownloadVO;
import cn.lili.modules.store.entity.vos.StoreFlowRefundDownloadVO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 财务导出 Excel 工具类。
 * <p>
 * 封装 Hutool POI 的 xlsx 写出、响应头设置及导出行数校验，
 * 供 {@link cn.lili.modules.finance.serviceimpl.FinanceExportServiceImpl}
 * 和 {@link cn.lili.modules.finance.serviceimpl.FinanceReportServiceImpl} 复用。
 */
@Slf4j
public final class FinanceExportHelper {

    /** 单次导出最大行数，超出时抛出业务异常提示缩小筛选范围 */
    public static final int MAX_EXPORT_ROWS = 50_000;

    private FinanceExportHelper() {
    }

    /**
     * 校验导出行数是否超过上限。
     *
     * @param count 待导出行数
     * @throws ServiceException 超过 {@link #MAX_EXPORT_ROWS} 时
     */
    public static void checkRowLimit(long count) {
        if (count > MAX_EXPORT_ROWS) {
            throw new ServiceException(ResultCode.PARAMS_ERROR,
                    "导出数据超过上限 " + MAX_EXPORT_ROWS + " 行，请缩小筛选范围");
        }
    }

    /**
     * 将 Excel 内容写入 HTTP 响应，输出格式统一为 xlsx。
     *
     * @param response       HTTP 响应
     * @param fileName       下载文件名（不含扩展名，自动追加 .xlsx）
     * @param writerConsumer 向 {@link ExcelWriter} 写入 Sheet 内容的回调
     */
    public static void writeExcel(HttpServletResponse response, String fileName,
                                  Consumer<ExcelWriter> writerConsumer) {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ServletOutputStream out = null;
        try {
            writerConsumer.accept(writer);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ".xlsx");
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("财务导出失败: {}", fileName, e);
            throw new ServiceException(ResultCode.ERROR);
        } finally {
            writer.close();
            IoUtil.close(out);
        }
    }

    /**
     * 切换到指定 Sheet。单 Sheet 导出时重命名默认 sheet1，避免留下空白页。
     */
    public static void useSheet(ExcelWriter writer, String sheetName) {
        if (writer.getWorkbook().getNumberOfSheets() == 1) {
            writer.renameSheet(0, sheetName);
            writer.setSheet(0);
        } else {
            writer.setSheet(sheetName);
        }
    }

    /**
     * 向指定 Sheet 写入表头与数据行。
     */
    public static void writeSheet(ExcelWriter writer, String sheetName,
                                  List<String> headers, List<List<Object>> rows) {
        useSheet(writer, sheetName);
        writer.writeHeadRow(headers);
        if (rows != null && !rows.isEmpty()) {
            writer.write(rows, false);
        }
    }

    /**
     * 写出单张店铺结算单 Excel（汇总 + 入账订单 + 退款订单三个 Sheet）。
     * <p>
     * 结算单数据归属 store-service，而流水的跨服务查询通过 {@link StoreFlowClient} 完成，
     * 故该工具下沉到 common-api，供 store-service 与 payment-service 复用。
     *
     * @param response          HTTP 响应
     * @param fileName          下载文件名（不含扩展名）
     * @param bill              结算单
     * @param storeFlowService  店铺流水 Feign 客户端
     */
    public static void writeBillExcel(HttpServletResponse response, String fileName,
                                      Bill bill, StoreFlowClient storeFlowService) {
        writeExcel(response, fileName, writer -> writeBillContent(writer, bill, storeFlowService));
    }

    /** 填充结算单 Excel 内容 */
    private static void writeBillContent(ExcelWriter writer, Bill bill, StoreFlowClient storeFlowService) {
        writer.setSheet("店铺结算单");
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("创建时间", DateUtil.format(bill.getCreateTime(), "yyyy-MM-dd"));
        map.put("账单号", bill.getSn());
        map.put("结算开始时间", DateUtil.format(bill.getStartTime(), "yyyy-MM-dd"));
        map.put("结算结束时间", DateUtil.format(bill.getEndTime(), "yyyy-MM-dd"));
        map.put("账单状态", BillStatusEnum.valueOf(bill.getBillStatus()).description());
        map.put("店铺名称", bill.getStoreName());
        map.put("平台付款时间", bill.getPayTime() != null ? DateUtil.format(bill.getPayTime(), "yyyy-MM-dd") : "");
        map.put("银行开户名", bill.getBankAccountName());
        map.put("银行账号", bill.getBankAccountNumber());
        map.put("开户行", bill.getBankName());
        map.put("联行号", bill.getBankCode());
        map.put("订单金额", bill.getOrderPrice());
        map.put("退单金额", bill.getRefundPrice());
        map.put("平台收取服务费", bill.getCommissionPrice());
        map.put("退单退回平台服务费", bill.getRefundCommissionPrice());
        map.put("分销佣金", bill.getDistributionCommission());
        map.put("退单退还分销佣金", bill.getDistributionRefundCommission());
        map.put("平台优惠券补贴", bill.getSiteCouponCommission());
        map.put("退单退回平台优惠券补贴", bill.getSiteCouponRefundCommission());
        map.put("积分商品补贴", bill.getPointSettlementPrice());
        map.put("退单退回积分商品补贴", bill.getPointRefundSettlementPrice());
        map.put("砍价商品补贴", bill.getKanjiaSettlementPrice());
        map.put("退单退回砍价补贴", bill.getKanjiaRefundSettlementPrice());
        map.put("礼品卡平台补贴", bill.getGiftCardSubsidy());
        map.put("退单退回礼品卡补贴", bill.getGiftCardRefundSubsidy());
        map.put("最终结算金额", bill.getBillPrice());
        writer.writeRow(map, true);

        writePayFlowSheet(writer, bill, storeFlowService);
        writeRefundFlowSheet(writer, bill, storeFlowService);
    }

    /** 入账订单明细 Sheet */
    private static void writePayFlowSheet(ExcelWriter writer, Bill bill, StoreFlowClient storeFlowService) {
        writer.setSheet("入账订单");
        configureFlowHeaders(writer, false);
        List<StoreFlowPayDownloadVO> list = storeFlowService.getStoreFlowPayDownloadVO(
                StoreFlowQueryDTO.builder().type(FlowTypeEnum.PAY.name()).bill(bill).build());
        checkRowLimit(list.size());
        writer.write(list, true);
        writer.setOnlyAlias(true);
    }

    /** 退款订单明细 Sheet */
    private static void writeRefundFlowSheet(ExcelWriter writer, Bill bill, StoreFlowClient storeFlowService) {
        writer.setSheet("退款订单");
        configureFlowHeaders(writer, true);
        List<StoreFlowRefundDownloadVO> list = storeFlowService.getStoreFlowRefundDownloadVO(
                StoreFlowQueryDTO.builder().type(FlowTypeEnum.REFUND.name()).bill(bill).build());
        checkRowLimit(list.size());
        writer.write(list, true);
        writer.setOnlyAlias(true);
    }

    /**
     * 配置流水明细列头别名。
     * 入账与退款共用字段映射，退款场景额外展示售后单号。
     */
    private static void configureFlowHeaders(ExcelWriter writer, boolean refund) {
        writer.addHeaderAlias("createTime", "入账时间");
        writer.addHeaderAlias("orderSn", "订单编号");
        if (refund) {
            writer.addHeaderAlias("refundSn", "售后单号");
        }
        writer.addHeaderAlias("storeName", "店铺名称");
        writer.addHeaderAlias("goodsName", "商品名称");
        writer.addHeaderAlias("num", refund ? "退款量" : "销售量");
        writer.addHeaderAlias("finalPrice", refund ? "退款金额" : "订单金额");
        writer.addHeaderAlias("giftCardSubsidyPrice", "礼品卡补贴");
        writer.addHeaderAlias("settlementBasePrice", "结算基数");
        writer.addHeaderAlias("commissionPrice", "平台分佣");
        writer.addHeaderAlias("siteCouponPrice", "平台优惠券");
        writer.addHeaderAlias("distributionRebate", "分销金额");
        writer.addHeaderAlias("pointSettlementPrice", "积分结算金额");
        writer.addHeaderAlias("kanjiaSettlementPrice", "砍价结算金额");
        writer.addHeaderAlias("paymentName", "支付方式");
        writer.addHeaderAlias("transactionId", "第三方流水号");
        writer.addHeaderAlias("billPrice", refund ? "结算金额" : "应结金额");
    }
}
