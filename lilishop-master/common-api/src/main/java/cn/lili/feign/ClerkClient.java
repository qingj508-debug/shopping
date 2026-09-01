package cn.lili.feign;

import cn.lili.modules.member.entity.dos.Clerk;
import cn.lili.modules.member.entity.dto.ClerkAddDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * member-service 店员内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用店员能力，端点由 member-service 的 InternalMemberController 提供。
 */
@FeignClient(name = "member-service", path = "/internal/member", contextId = "liliClerkClient")
public interface ClerkClient {

    /**
     * 保存店员
     */
    @PostMapping("/clerk/save")
    Clerk saveClerk(@RequestBody ClerkAddDTO clerkAddDTO);

    /**
     * 查询店铺下全部店员
     */
    @GetMapping("/clerk/listByStoreId")
    List<Clerk> listByStoreId(@RequestParam("storeId") String storeId);

    /**
     * 删除所有店主店员记录
     */
    @GetMapping("/clerk/removeShopkeeper")
    void removeShopkeeper();

    /**
     * 批量保存店员
     */
    @PostMapping("/clerk/saveBatch")
    void saveBatch(@RequestBody List<Clerk> clerkList);
}
