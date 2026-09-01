package cn.lili.feign;

import cn.lili.modules.member.entity.dos.MemberAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * member-service 收货地址内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用会员收货地址能力，端点由 member-service 的 InternalMemberController 提供。
 */
@FeignClient(name = "member-service", path = "/internal/member", contextId = "liliMemberAddressClient")
public interface MemberAddressClient {

    /**
     * 根据地址ID获取地址信息
     */
    @GetMapping("/address/byId/{id}")
    MemberAddress getById(@PathVariable("id") String id);

    /**
     * 获取客户默认收货地址
     */
    @GetMapping("/address/default")
    MemberAddress getDefaultMemberAddress(@RequestParam("memberId") String memberId);
}
