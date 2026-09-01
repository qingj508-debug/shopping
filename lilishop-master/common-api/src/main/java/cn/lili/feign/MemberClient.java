package cn.lili.feign;

import cn.lili.modules.member.entity.dos.FootPrint;
import cn.lili.modules.member.entity.dos.Member;
import cn.lili.modules.member.entity.dto.EvaluationQueryParams;
import cn.lili.modules.store.entity.dos.StoreLogistics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * member-service 内部调用 Feign 客户端
 * <p>
 * 供其他微服务跨进程调用会员域能力，端点由 member-service 的 InternalMemberController 提供。
 */
@FeignClient(name = "member-service", path = "/internal/member", contextId = "liliMemberClient")
public interface MemberClient {

    /**
     * 根据id获取会员
     */
    @GetMapping("/byId/{id}")
    Member getById(@PathVariable("id") String id);

    /**
     * 通过手机获取用户
     */
    @GetMapping("/findByMobile")
    Member findByMobile(@RequestParam("mobile") String mobile);

    /**
     * 获取指定客户数据
     */
    @PostMapping("/listFieldsByMemberIds")
    List<Map<String, Object>> listFieldsByMemberIds(@RequestParam("columns") String columns,
                                                    @RequestBody List<String> memberIds);

    /**
     * 客户积分变动（带来源）
     */
    @PostMapping("/updateMemberPoint")
    Boolean updateMemberPoint(@RequestParam("point") Long point,
                              @RequestParam("type") String type,
                              @RequestParam("memberId") String memberId,
                              @RequestParam("content") String content,
                              @RequestParam(value = "pointSource", required = false) String pointSource);

    /**
     * 按手机号查询会员（未删除）
     */
    @GetMapping("/listByMobile")
    List<Member> listByMobile(@RequestParam("mobile") String mobile);

    /**
     * 更新会员实体
     */
    @PostMapping("/updateById")
    boolean updateById(@RequestBody Member member);

    /**
     * 修改客户是否拥有店铺
     */
    @PostMapping("/updateHaveShop")
    void updateHaveShop(@RequestParam("haveStore") Boolean haveStore,
                        @RequestParam("storeId") String storeId,
                        @RequestBody List<String> memberIds);

    /**
     * 查询店铺-物流公司设置（按物流公司id+店铺id）
     */
    @GetMapping("/storeLogistics/getOneByLogisticsAndStore")
    StoreLogistics getOneByLogisticsAndStore(@RequestParam("logisticsId") String logisticsId,
                                             @RequestParam("storeId") String storeId);

    /**
     * 查询店铺内指定会员的足迹
     */
    @GetMapping("/footprint/listByStoreAndMember")
    List<FootPrint> listFootprintsByStoreAndMember(@RequestParam("storeId") String storeId,
                                                   @RequestParam("memberId") String memberId);

    /**
     * 商品评价数量统计
     */
    @PostMapping("/evaluation/getEvaluationCount")
    long getEvaluationCount(@RequestBody EvaluationQueryParams evaluationQueryParams);
}
