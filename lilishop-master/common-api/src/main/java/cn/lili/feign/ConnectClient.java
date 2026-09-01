package cn.lili.feign;

import cn.lili.modules.connect.entity.Connect;
import cn.lili.modules.member.entity.dto.ConnectQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * member-service 联合登录内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用联合登录能力，端点由 member-service 的 InternalMemberController 提供。
 */
@FeignClient(name = "member-service", path = "/internal/member", contextId = "liliConnectClient")
public interface ConnectClient {

    /**
     * 根据查询dto获取查询对象
     */
    @PostMapping("/connect/query")
    Connect queryConnect(@RequestBody ConnectQueryDTO connectQueryDTO);
}
