package cn.lili.controller.internal;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.goods.entity.dos.Category;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.dos.Wholesale;
import cn.lili.modules.goods.entity.dto.GoodsSkuStockDTO;
import cn.lili.modules.goods.entity.vos.StoreGoodsLabelVO;
import cn.lili.modules.goods.entity.vos.StudioVO;
import cn.lili.modules.goods.service.CardKeyService;
import cn.lili.modules.goods.service.CategoryService;
import cn.lili.modules.goods.service.GoodsService;
import cn.lili.modules.goods.service.GoodsSkuService;
import cn.lili.modules.goods.service.StoreGoodsLabelService;
import cn.lili.modules.goods.service.StudioService;
import cn.lili.modules.goods.service.WholesaleService;
import cn.lili.modules.order.cart.entity.dto.TradeDTO;
import cn.lili.modules.page.entity.dos.Feedback;
import cn.lili.modules.page.service.FeedbackService;
import cn.lili.modules.search.entity.dos.GoodsIndex;
import cn.lili.modules.search.service.GoodsIndexService;
import cn.lili.modules.search.service.GoodsSearchService;
import cn.lili.modules.store.entity.dos.Store;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * goods-service 内部调用端点
 * <p>
 * 供 GoodsClient（common-api Feign）跨服务调用，直接委托本地 service，返回裸类型。
 * 注意：/internal/** 不受买家网关安全链保护，也不会被网关路由。
 */
@RestController
@RequestMapping("/internal/goods")
public class InternalGoodsController {

    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private CardKeyService cardKeyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WholesaleService wholesaleService;
    @Autowired
    private StoreGoodsLabelService storeGoodsLabelService;
    @Autowired
    private StudioService studioService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private GoodsSearchService goodsSearchService;
    @Autowired
    private GoodsIndexService goodsIndexService;

    // ==================== GoodsSku ====================

    @GetMapping("/sku/cache/{id}")
    public GoodsSku getGoodsSkuByIdFromCache(@PathVariable("id") String id) {
        return goodsSkuService.getGoodsSkuByIdFromCache(id);
    }

    @PostMapping("/sku/cache/list")
    public List<GoodsSku> getGoodsSkuByIdFromCache(@RequestBody List<String> ids) {
        return goodsSkuService.getGoodsSkuByIdFromCache(ids);
    }

    @GetMapping("/sku/promotion/{skuId}")
    public GoodsSku getCanPromotionGoodsSkuByIdFromCache(@PathVariable("skuId") String skuId) {
        return goodsSkuService.getCanPromotionGoodsSkuByIdFromCache(skuId);
    }

    @GetMapping("/sku/stock/{skuId}")
    public Integer getStock(@PathVariable("skuId") String skuId) {
        return goodsSkuService.getStock(skuId);
    }

    @GetMapping("/sku/countWarningStock")
    public Long countWarningStock(@RequestParam(value = "storeId", required = false) String storeId) {
        return goodsSkuService.count(Wrappers.<GoodsSku>lambdaQuery()
                .eq(cn.hutool.core.text.CharSequenceUtil.isNotEmpty(storeId), GoodsSku::getStoreId, storeId)
                .eq(GoodsSku::getMarketEnable, cn.lili.modules.goods.entity.enums.GoodsStatusEnum.UPPER.name())
                .apply("quantity < alert_quantity")
                .gt(GoodsSku::getAlertQuantity, 0));
    }

    @GetMapping("/sku/{id}")
    public GoodsSku getById(@PathVariable("id") String id) {
        return goodsSkuService.getById(id);
    }

    @PostMapping("/sku/stock/updateByType")
    public void updateStocksByType(@RequestBody List<GoodsSkuStockDTO> goodsSkuStockDTOS) {
        goodsSkuService.updateStocksByType(goodsSkuStockDTOS);
    }

    @GetMapping("/sku/listByStoreId")
    public List<GoodsSku> listByStoreId(@RequestParam("storeId") String storeId) {
        return goodsSkuService.list(Wrappers.<GoodsSku>lambdaQuery().eq(GoodsSku::getStoreId, storeId));
    }

    // ==================== CardKey ====================

    @PostMapping("/cardKey/isECouponOnlyTrade")
    public boolean isECouponOnlyTrade(@RequestBody TradeDTO tradeDTO) {
        return cardKeyService.isECouponOnlyTrade(tradeDTO);
    }

    @PostMapping("/cardKey/normalizeECouponTrade")
    public void normalizeECouponTrade(@RequestBody TradeDTO tradeDTO) {
        cardKeyService.normalizeECouponTrade(tradeDTO);
    }

    @PostMapping("/cardKey/validateTradeForECoupon")
    public void validateTradeForECoupon(@RequestBody TradeDTO tradeDTO) {
        cardKeyService.validateTradeForECoupon(tradeDTO);
    }

    @GetMapping("/cardKey/validateAddToCart")
    public void validateAddToCart(@RequestParam("skuId") String skuId,
                                  @RequestParam("num") Integer num,
                                  @RequestParam("cartType") String cartType) {
        cardKeyService.validateAddToCart(skuId, num, cartType);
    }

    @GetMapping("/cardKey/countUnusedPoolStock")
    public int countUnusedPoolStock(@RequestParam("skuId") String skuId) {
        return cardKeyService.countUnusedPoolStock(skuId);
    }

    @PostMapping("/cardKey/enrichOrderDetail")
    public void enrichOrderDetail(@RequestBody cn.lili.modules.order.order.entity.vo.OrderDetailVO orderDetailVO) {
        cardKeyService.enrichOrderDetail(orderDetailVO);
    }

    @PostMapping("/cardKey/validatePayOrder")
    public void validatePayOrder(@RequestBody cn.lili.modules.order.order.entity.dos.Order order) {
        cardKeyService.validatePayOrder(order);
    }

    @PostMapping("/cardKey/isECouponFulfillmentEligible")
    public boolean isECouponFulfillmentEligible(@RequestBody cn.lili.modules.order.order.entity.dos.Order order) {
        return cardKeyService.isECouponFulfillmentEligible(order);
    }

    @GetMapping("/cardKey/reserveByOrder")
    public void reserveByOrder(@RequestParam("orderSn") String orderSn) {
        cardKeyService.reserveByOrder(orderSn);
    }

    @GetMapping("/cardKey/fulfillAfterPayment")
    public void fulfillAfterPayment(@RequestParam("orderSn") String orderSn) {
        cardKeyService.fulfillAfterPayment(orderSn);
    }

    @GetMapping("/cardKey/confirmReservationAndComplete")
    public void confirmReservationAndComplete(@RequestParam("orderSn") String orderSn) {
        cardKeyService.confirmReservationAndComplete(orderSn);
    }

    @GetMapping("/cardKey/releaseReservation")
    public void releaseReservation(@RequestParam("orderSn") String orderSn) {
        cardKeyService.releaseReservation(orderSn);
    }

    // ==================== Category ====================

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable("id") String id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/category/firstCategory")
    public List<Category> firstCategory() {
        return categoryService.firstCategory();
    }

    @PostMapping("/category/listByIds")
    public List<Category> listByIds(@RequestBody List<String> ids) {
        return categoryService.listByIds(ids);
    }

    // ==================== Goods ====================

    @PostMapping("/goods/updateStoreDetail")
    public void updateStoreDetail(@RequestBody Store store) {
        goodsService.updateStoreDetail(store);
    }

    @PostMapping("/goods/underStoreGoods")
    public void underStoreGoods(@RequestParam("storeId") String storeId) {
        goodsService.underStoreGoods(storeId);
    }

    // ==================== Wholesale ====================

    @GetMapping("/wholesale/match")
    public Wholesale match(@RequestParam("goodsId") String goodsId, @RequestParam("num") Integer num) {
        return wholesaleService.match(goodsId, num);
    }

    // ==================== StoreGoodsLabel ====================

    @GetMapping("/storeGoodsLabel/listByStoreId")
    public List<StoreGoodsLabelVO> getStoreGoodsLabelByStoreId(@RequestParam("storeId") String storeId) {
        return storeGoodsLabelService.listByStoreId(storeId);
    }

    // ==================== Studio ====================

    @PostMapping("/studio/list")
    public IPage<StudioVO> studioList(@RequestBody PageVO pageVO,
                                      @RequestParam("recommend") Integer recommend,
                                      @RequestParam("status") String status) {
        return studioService.studioList(pageVO, recommend, status);
    }

    @GetMapping("/studio/liveInfo/{roomId}")
    public String getLiveInfo(@PathVariable("roomId") Integer roomId) {
        return studioService.getLiveInfo(roomId);
    }

    // ==================== Feedback ====================

    @PostMapping("/feedback/save")
    public Feedback save(@RequestBody Feedback feedback) {
        feedbackService.save(feedback);
        return feedback;
    }

    // ==================== GoodsSearch ====================

    @PostMapping("/search/getGoodsBySkuIds")
    public List<GoodsIndex> getGoodsBySkuIds(@RequestBody List<String> skuIds) {
        return goodsSearchService.getGoodsBySkuIds(skuIds, null);
    }

    // ==================== GoodsIndex ====================

    @GetMapping("/search/index/{id}")
    public GoodsIndex findById(@PathVariable("id") String id) {
        return goodsIndexService.findById(id);
    }

    @PostMapping("/search/buildGoodsIndex")
    public GoodsIndex buildGoodsIndex(@RequestBody GoodsSku goodsSku) {
        return goodsIndexService.buildGoodsIndex(goodsSku);
    }
}
