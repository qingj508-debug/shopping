package cn.lili.controller.buyer.member;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.member.service.StoreCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;

/**
 * 买家端,客户收藏接口
 *
 * @author Chopper
 * @since 2020/11/17 2:32 下午
 */
@RestController
@Tag(name = "买家端,客户店铺收藏接口")
@RequestMapping("/buyer/member/storeCollection")
public class MemberCollectionStoreController {

    /**
     * 客户店铺
     */
    @Autowired
    private StoreCollectionService storeCollectionService;

    @Operation(summary = "查询客户收藏列表")
    @GetMapping("/STORE")
    public ResultMessage<Object> goodsList(PageVO page) {
        return ResultUtil.data(storeCollectionService.storeCollection(page));
    }

    @Operation(summary = "添加客户收藏")
    @Parameters({
            @Parameter(name = "num", description = "值")
    })
    @PostMapping("/add/STORE/{id}")
    public ResultMessage<Object> addGoodsCollection(@NotNull(message = "值不能为空") @PathVariable String id) {
        return ResultUtil.data(storeCollectionService.addStoreCollection(id));

    }

    @Operation(summary = "删除客户收藏")
    @Parameters({
            @Parameter(name = "num", description = "值")
    })
    @DeleteMapping("/delete/STORE/{id}")
    public ResultMessage<Object> deleteGoodsCollection(@NotNull(message = "值不能为空") @PathVariable String id) {
        return ResultUtil.data(storeCollectionService.deleteStoreCollection(id));
    }

    @Operation(summary = "查询客户是否收藏")
    @Parameters({
            @Parameter(name = "id", description = "值")
    })
    @GetMapping("/isCollection/STORE/{id}")
    public ResultMessage<Boolean> isCollection(@NotNull(message = "值不能为空") @PathVariable String id) {
        return ResultUtil.data(this.storeCollectionService.isCollection(id));
    }
}
