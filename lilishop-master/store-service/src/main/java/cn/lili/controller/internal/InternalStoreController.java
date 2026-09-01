package cn.lili.controller.internal;

import cn.lili.modules.member.entity.dto.CollectionDTO;
import cn.lili.modules.store.entity.dos.Bill;
import cn.lili.modules.store.entity.dos.FreightTemplate;
import cn.lili.modules.store.entity.dos.Store;
import cn.lili.modules.store.entity.dos.StoreAddress;
import cn.lili.modules.store.entity.dos.StoreDetail;
import cn.lili.modules.store.entity.dto.BillSearchParams;
import cn.lili.modules.store.entity.dto.StoreAfterSaleAddressDTO;
import cn.lili.modules.store.entity.dto.StoreDeliverGoodsAddressDTO;
import cn.lili.modules.store.entity.vos.BillListVO;
import cn.lili.modules.store.entity.vos.FreightTemplateVO;
import cn.lili.modules.store.entity.vos.StoreVO;
import cn.lili.modules.store.mapper.StoreMapper;
import cn.lili.modules.store.service.BillService;
import cn.lili.modules.store.service.FreightTemplateService;
import cn.lili.modules.store.service.StoreAddressService;
import cn.lili.modules.store.service.StoreDetailService;
import cn.lili.modules.store.service.StoreService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * store-service 内部调用端点
 * <p>
 * 供 StoreClient/StoreDetailClient/FreightTemplateClient/StoreAddressClient/BillClient（common-api Feign）跨服务调用，
 * 直接委托本地 service，返回裸类型。
 * 注意：/internal/** 不受买家网关安全链保护，也不会被网关路由。
 */
@RestController
@RequestMapping("/internal/store")
public class InternalStoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreDetailService storeDetailService;
    @Autowired
    private FreightTemplateService freightTemplateService;
    @Autowired
    private StoreAddressService storeAddressService;
    @Autowired
    private BillService billService;
    @Autowired
    private StoreMapper storeMapper;

    // ==================== Store ====================

    @GetMapping("/store/{id}")
    public Store getStoreById(@PathVariable("id") String id) {
        return storeService.getById(id);
    }

    @PostMapping("/store/listByIds")
    public List<Store> listStoresByIds(@RequestBody List<String> ids) {
        return storeService.listByIds(ids);
    }

    @PostMapping("/store/updateStoreCollectionNum")
    public void updateStoreCollectionNum(@RequestBody CollectionDTO collectionDTO) {
        storeService.updateStoreCollectionNum(collectionDTO);
    }

    @GetMapping("/store/detail/{storeId}")
    public StoreVO getStoreDetail(@PathVariable("storeId") String storeId) {
        return storeMapper.getStoreDetail(storeId);
    }

    // ==================== StoreDetail ====================

    @GetMapping("/storeDetail/{storeId}")
    public StoreDetail getStoreDetailById(@PathVariable("storeId") String storeId) {
        return storeDetailService.getStoreDetail(storeId);
    }

    @GetMapping("/storeDetail/afterSaleAddress/{id}")
    public StoreAfterSaleAddressDTO getStoreAfterSaleAddressDTO(@PathVariable("id") String id) {
        return storeDetailService.getStoreAfterSaleAddressDTO(id);
    }

    @GetMapping("/storeDetail/deliverGoodsAddress/{id}")
    public StoreDeliverGoodsAddressDTO getStoreDeliverGoodsAddressDto(@PathVariable("id") String id) {
        return storeDetailService.getStoreDeliverGoodsAddressDto(id);
    }

    // ==================== FreightTemplate ====================

    @GetMapping("/freightTemplate/{id}")
    public FreightTemplate getFreightTemplateById(@PathVariable("id") String id) {
        return freightTemplateService.getById(id);
    }

    @GetMapping("/freightTemplate/vo/{id}")
    public FreightTemplateVO getFreightTemplateVO(@PathVariable("id") String id) {
        return freightTemplateService.getFreightTemplate(id);
    }

    @GetMapping("/freightTemplate/list/{storeId}")
    public List<FreightTemplateVO> getFreightTemplateList(@PathVariable("storeId") String storeId) {
        return freightTemplateService.getFreightTemplateList(storeId);
    }

    // ==================== StoreAddress ====================

    @GetMapping("/storeAddress/{id}")
    public StoreAddress getStoreAddressById(@PathVariable("id") String id) {
        return storeAddressService.getById(id);
    }

    // ==================== Bill ====================

    @GetMapping("/bill/{id}")
    public Bill getBillById(@PathVariable("id") String id) {
        return billService.getById(id);
    }

    @PostMapping("/bill/billPage")
    public IPage<BillListVO> billPage(@RequestBody BillSearchParams billSearchParams) {
        return billService.billPage(billSearchParams);
    }
}
