package cn.lili.controller.buyer.member;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.security.context.UserContext;
import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.member.entity.dos.MemberPointsHistory;
import cn.lili.modules.member.entity.vo.MemberPointsHistoryVO;
import cn.lili.modules.member.service.MemberPointsHistoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 买家端,客户积分历史接口
 *
 * @author Bulbasaur
 * @since 2020-02-25 14:10:16
 */
@RestController
@Tag(name = "买家端,客户积分历史接口")
@RequestMapping("/buyer/member/memberPointsHistory")
public class PointsHistoryBuyerController {
    @Autowired
    private MemberPointsHistoryService memberPointsHistoryService;

    @Operation(summary = "分页获取")
    @GetMapping("/getByPage")
    public ResultMessage<IPage<MemberPointsHistory>> getByPage(PageVO page) {
        return ResultUtil.data(memberPointsHistoryService.pageByMemberId(UserContext.getCurrentUser().getId(), page));
    }

    @Operation(summary = "获取客户积分VO")
    @GetMapping("/getMemberPointsHistoryVO")
    public ResultMessage<MemberPointsHistoryVO> getMemberPointsHistoryVO() {
        return ResultUtil.data(memberPointsHistoryService.getMemberPointsHistoryVO(UserContext.getCurrentUser().getId()));
    }


}
