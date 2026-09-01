package cn.lili.feign;

import cn.lili.modules.member.entity.vo.MemberPointsStatisticsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * member-service 积分统计内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用积分统计能力，端点由 member-service 的 InternalMemberController 提供。
 */
@FeignClient(name = "member-service", path = "/internal/member", contextId = "liliMemberPointsClient")
public interface MemberPointsClient {

    /**
     * 客户积分统计
     */
    @GetMapping("/points/statistics")
    MemberPointsStatisticsVO queryMemberPointsStatistics();
}
