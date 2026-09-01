package cn.lili.controller.internal;

import cn.lili.modules.connect.entity.Connect;
import cn.lili.modules.connect.service.ConnectService;
import cn.lili.modules.member.entity.dos.Clerk;
import cn.lili.modules.member.entity.dos.FootPrint;
import cn.lili.modules.member.entity.dos.Member;
import cn.lili.modules.member.entity.dos.MemberAddress;
import cn.lili.modules.member.entity.dto.ClerkAddDTO;
import cn.lili.modules.member.entity.dto.ConnectQueryDTO;
import cn.lili.modules.member.entity.dto.EvaluationQueryParams;
import cn.lili.modules.member.entity.vo.MemberPointsStatisticsVO;
import cn.lili.modules.store.entity.dos.StoreLogistics;
import cn.lili.modules.member.service.ClerkService;
import cn.lili.modules.member.service.FootprintService;
import cn.lili.modules.member.service.MemberAddressService;
import cn.lili.modules.member.service.MemberEvaluationService;
import cn.lili.modules.member.service.MemberPointsHistoryService;
import cn.lili.modules.member.service.MemberService;
import cn.lili.modules.member.service.StoreLogisticsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.Map;

/**
 * 会员内部接口（供其他微服务 Feign 调用）
 * <p>
 * 端点直接返回裸类型，由 common-api 中的 MemberClient/MemberAddressClient/ClerkClient/MemberPointsClient/ConnectClient 对接。
 *
 * @author lili
 */
@RestController
@RequestMapping("/internal/member")
public class InternalMemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private MemberPointsHistoryService memberPointsHistoryService;
    @Autowired
    private ConnectService connectService;
    @Autowired
    private StoreLogisticsService storeLogisticsService;
    @Autowired
    private FootprintService footprintService;
    @Autowired
    private MemberEvaluationService memberEvaluationService;

    /**
     * 根据id获取会员
     */
    @GetMapping("/byId/{id}")
    public Member getById(@PathVariable("id") String id) {
        return memberService.getById(id);
    }

    /**
     * 通过手机获取用户
     */
    @GetMapping("/findByMobile")
    public Member findByMobile(@RequestParam("mobile") String mobile) {
        return memberService.findByMobile(mobile);
    }

    /**
     * 获取指定客户数据
     */
    @PostMapping("/listFieldsByMemberIds")
    public List<Map<String, Object>> listFieldsByMemberIds(@RequestParam("columns") String columns,
                                                           @RequestBody List<String> memberIds) {
        return memberService.listFieldsByMemberIds(columns, memberIds);
    }

    /**
     * 客户积分变动（带来源）
     */
    @PostMapping("/updateMemberPoint")
    public Boolean updateMemberPoint(@RequestParam("point") Long point,
                                     @RequestParam("type") String type,
                                     @RequestParam("memberId") String memberId,
                                     @RequestParam("content") String content,
                                     @RequestParam(value = "pointSource", required = false) String pointSource) {
        return memberService.updateMemberPoint(point, type, memberId, content, pointSource);
    }

    /**
     * 按手机号查询会员（未删除）
     */
    @GetMapping("/listByMobile")
    public List<Member> listByMobile(@RequestParam("mobile") String mobile) {
        return memberService.list(new QueryWrapper<Member>()
                .eq("mobile", mobile)
                .eq("delete_flag", false));
    }

    /**
     * 更新会员实体
     */
    @PostMapping("/updateById")
    public boolean updateById(@RequestBody Member member) {
        return memberService.updateById(member);
    }

    /**
     * 修改客户是否拥有店铺
     */
    @PostMapping("/updateHaveShop")
    public void updateHaveShop(@RequestParam("haveStore") Boolean haveStore,
                               @RequestParam("storeId") String storeId,
                               @RequestBody List<String> memberIds) {
        memberService.updateHaveShop(haveStore, storeId, memberIds);
    }

    /**
     * 根据地址ID获取地址信息
     */
    @GetMapping("/address/byId/{id}")
    public MemberAddress getAddressById(@PathVariable("id") String id) {
        return memberAddressService.getById(id);
    }

    /**
     * 获取客户默认收货地址
     */
    @GetMapping("/address/default")
    public MemberAddress getDefaultMemberAddress(@RequestParam("memberId") String memberId) {
        return memberAddressService.getOne(new QueryWrapper<MemberAddress>()
                .eq("member_id", memberId)
                .eq("is_default", true));
    }

    /**
     * 保存店员
     */
    @PostMapping("/clerk/save")
    public Clerk saveClerk(@RequestBody ClerkAddDTO clerkAddDTO) {
        return clerkService.saveClerk(clerkAddDTO);
    }

    /**
     * 查询店铺下全部店员
     */
    @GetMapping("/clerk/listByStoreId")
    public List<Clerk> listClerkByStoreId(@RequestParam("storeId") String storeId) {
        return clerkService.list(Wrappers.<Clerk>lambdaQuery().eq(Clerk::getStoreId, storeId));
    }

    /**
     * 删除所有店主店员记录
     */
    @GetMapping("/clerk/removeShopkeeper")
    public void removeShopkeeper() {
        clerkService.remove(Wrappers.<Clerk>lambdaQuery().eq(Clerk::getShopkeeper, true));
    }

    /**
     * 批量保存店员
     */
    @PostMapping("/clerk/saveBatch")
    public void saveBatch(@RequestBody List<Clerk> clerkList) {
        clerkService.saveBatch(clerkList);
    }

    /**
     * 客户积分统计
     */
    @GetMapping("/points/statistics")
    public MemberPointsStatisticsVO queryMemberPointsStatistics() {
        return memberPointsHistoryService.queryMemberPointsStatistics();
    }

    /**
     * 根据查询dto获取查询对象
     */
    @PostMapping("/connect/query")
    public Connect queryConnect(@RequestBody ConnectQueryDTO connectQueryDTO) {
        return connectService.queryConnect(connectQueryDTO);
    }

    /**
     * 查询店铺-物流公司设置（按物流公司id+店铺id）
     */
    @GetMapping("/storeLogistics/getOneByLogisticsAndStore")
    public StoreLogistics getOneByLogisticsAndStore(@RequestParam("logisticsId") String logisticsId,
                                                    @RequestParam("storeId") String storeId) {
        return storeLogisticsService.getOne(Wrappers.<StoreLogistics>lambdaQuery()
                .eq(StoreLogistics::getLogisticsId, logisticsId)
                .eq(StoreLogistics::getStoreId, storeId));
    }

    /**
     * 查询店铺内指定会员的足迹
     */
    @GetMapping("/footprint/listByStoreAndMember")
    public List<FootPrint> listFootprintsByStoreAndMember(@RequestParam("storeId") String storeId,
                                                          @RequestParam("memberId") String memberId) {
        return footprintService.list(Wrappers.<FootPrint>lambdaQuery()
                .eq(FootPrint::getStoreId, storeId)
                .eq(FootPrint::getMemberId, memberId));
    }

    /**
     * 商品评价数量统计
     */
    @PostMapping("/evaluation/getEvaluationCount")
    public long getEvaluationCount(@RequestBody EvaluationQueryParams evaluationQueryParams) {
        return memberEvaluationService.getEvaluationCount(evaluationQueryParams);
    }
}
