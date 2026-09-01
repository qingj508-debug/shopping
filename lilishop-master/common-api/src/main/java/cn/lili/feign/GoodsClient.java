package cn.lili.feign;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.goods.entity.dos.Category;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.dos.Wholesale;
import cn.lili.modules.goods.entity.dto.GoodsSkuStockDTO;
import cn.lili.modules.goods.entity.vos.StoreGoodsLabelVO;
import cn.lili.modules.goods.entity.vos.StudioVO;
import cn.lili.modules.order.cart.entity.dto.TradeDTO;
import cn.lili.modules.page.entity.dos.Feedback;
import cn.lili.modules.search.entity.dos.GoodsIndex;
import cn.lili.modules.store.entity.dos.Store;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * goods-service 内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用商品/搜索/页面域能力，端点由 goods-service 的 InternalGoodsController 提供。
 */
@FeignClient(name = "goods-service", path = "/internal/goods", contextId = "liliGoodsClient")
public interface GoodsClient {

    // ==================== GoodsSku ====================

    @GetMapping("/sku/cache/{id}")
    GoodsSku getGoodsSkuByIdFromCache(@PathVariable("id") String id);

    @PostMapping("/sku/cache/list")
    List<GoodsSku> getGoodsSkuByIdFromCache(@RequestBody List<String> ids);

    @GetMapping("/sku/promotion/{skuId}")
    GoodsSku getCanPromotionGoodsSkuByIdFromCache(@PathVariable("skuId") String skuId);

    @GetMapping("/sku/stock/{skuId}")
    Integer getStock(@PathVariable("skuId") String skuId);

    /**
     * 库存预警统计：quantity &lt; alert_quantity 且 market_enable=UPPER 的 SKU 数（storeId 可空）
     */
    @GetMapping("/sku/countWarningStock")
    Long countWarningStock(@RequestParam(value = "storeId", required = false) String storeId);

    @GetMapping("/sku/{id}")
    GoodsSku getById(@PathVariable("id") String id);

    @PostMapping("/sku/stock/updateByType")
    void updateStocksByType(@RequestBody List<GoodsSkuStockDTO> goodsSkuStockDTOS);

    /**
     * 盘点专用：查询指定店铺下全部 SKU
     */
    @GetMapping("/sku/listByStoreId")
    List<GoodsSku> listByStoreId(@RequestParam("storeId") String storeId);

    // ==================== CardKey ====================

    @PostMapping("/cardKey/isECouponOnlyTrade")
    boolean isECouponOnlyTrade(@RequestBody TradeDTO tradeDTO);

    @PostMapping("/cardKey/normalizeECouponTrade")
    void normalizeECouponTrade(@RequestBody TradeDTO tradeDTO);

    @PostMapping("/cardKey/validateTradeForECoupon")
    void validateTradeForECoupon(@RequestBody TradeDTO tradeDTO);

    @GetMapping("/cardKey/validateAddToCart")
    void validateAddToCart(@RequestParam("skuId") String skuId,
                           @RequestParam("num") Integer num,
                           @RequestParam("cartType") String cartType);

    @GetMapping("/cardKey/countUnusedPoolStock")
    int countUnusedPoolStock(@RequestParam("skuId") String skuId);

    @PostMapping("/cardKey/enrichOrderDetail")
    void enrichOrderDetail(@RequestBody cn.lili.modules.order.order.entity.vo.OrderDetailVO orderDetailVO);

    @PostMapping("/cardKey/validatePayOrder")
    void validatePayOrder(@RequestBody cn.lili.modules.order.order.entity.dos.Order order);

    @PostMapping("/cardKey/isECouponFulfillmentEligible")
    boolean isECouponFulfillmentEligible(@RequestBody cn.lili.modules.order.order.entity.dos.Order order);

    @GetMapping("/cardKey/reserveByOrder")
    void reserveByOrder(@RequestParam("orderSn") String orderSn);

    @GetMapping("/cardKey/fulfillAfterPayment")
    void fulfillAfterPayment(@RequestParam("orderSn") String orderSn);

    @GetMapping("/cardKey/confirmReservationAndComplete")
    void confirmReservationAndComplete(@RequestParam("orderSn") String orderSn);

    @GetMapping("/cardKey/releaseReservation")
    void releaseReservation(@RequestParam("orderSn") String orderSn);

    // ==================== Category ====================

    @GetMapping("/category/{id}")
    Category getCategoryById(@PathVariable("id") String id);

    @GetMapping("/category/firstCategory")
    List<Category> firstCategory();

    @PostMapping("/category/listByIds")
    List<Category> listByIds(@RequestBody List<String> ids);

    // ==================== Goods ====================

    @PostMapping("/goods/updateStoreDetail")
    void updateStoreDetail(@RequestBody Store store);

    @PostMapping("/goods/underStoreGoods")
    void underStoreGoods(@RequestParam("storeId") String storeId);

    // ==================== Wholesale ====================

    @GetMapping("/wholesale/match")
    Wholesale match(@RequestParam("goodsId") String goodsId, @RequestParam("num") Integer num);

    // ==================== StoreGoodsLabel ====================

    @GetMapping("/storeGoodsLabel/listByStoreId")
    List<StoreGoodsLabelVO> getStoreGoodsLabelByStoreId(@RequestParam("storeId") String storeId);

    // ==================== Studio ====================

    @PostMapping("/studio/list")
    IPage<StudioVO> studioList(@RequestBody PageVO pageVO,
                               @RequestParam("recommend") Integer recommend,
                               @RequestParam("status") String status);

    @GetMapping("/studio/liveInfo/{roomId}")
    String getLiveInfo(@PathVariable("roomId") Integer roomId);

    // ==================== Feedback ====================

    @PostMapping("/feedback/save")
    Feedback save(@RequestBody Feedback feedback);

    // ==================== GoodsSearch ====================

    @PostMapping("/search/getGoodsBySkuIds")
    List<GoodsIndex> getGoodsBySkuIds(@RequestBody List<String> skuIds);

    // ==================== GoodsIndex ====================

    @GetMapping("/search/index/{id}")
    GoodsIndex findById(@PathVariable("id") String id);

    @PostMapping("/search/buildGoodsIndex")
    GoodsIndex buildGoodsIndex(@RequestBody GoodsSku goodsSku);
}
