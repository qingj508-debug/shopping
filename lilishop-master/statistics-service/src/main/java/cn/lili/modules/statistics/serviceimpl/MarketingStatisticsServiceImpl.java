package cn.lili.modules.statistics.serviceimpl;

import cn.lili.feign.CouponClient;
import cn.lili.feign.PromotionStatsClient;
import cn.lili.modules.promotion.entity.dos.Coupon;
import cn.lili.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.lili.modules.statistics.entity.vo.MarketingOverviewVO;
import cn.lili.modules.statistics.service.MarketingStatisticsService;
import cn.lili.modules.statistics.service.OrderStatisticsService;
import cn.lili.modules.statistics.util.StatisticsDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 营销概况统计业务层实现
 *
 * @author Bulbasaur
 * @since 2026/07/17
 */
@Service
public class MarketingStatisticsServiceImpl implements MarketingStatisticsService {

    @Autowired
    private PromotionStatsClient promotionStatsClient;
    @Autowired
    private CouponClient couponClient;
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Override
    public MarketingOverviewVO overview(StatisticsQueryParam statisticsQueryParam) {
        Date now = new Date();
        MarketingOverviewVO vo = new MarketingOverviewVO();
        vo.setCouponActiveNum(promotionStatsClient.countActive("COUPON", now.getTime()));
        vo.setSeckillActiveNum(promotionStatsClient.countActive("SECKILL", now.getTime()));
        vo.setPintuanActiveNum(promotionStatsClient.countActive("PINTUAN", now.getTime()));
        vo.setFullDiscountActiveNum(promotionStatsClient.countActive("FULL_DISCOUNT", now.getTime()));
        vo.setKanjiaActiveNum(promotionStatsClient.countActive("KANJIA", now.getTime()));
        vo.setPointsGoodsActiveNum(promotionStatsClient.countActive("POINTS_GOODS", now.getTime()));

        List<Coupon> coupons = couponClient.list();
        long publish = 0L, received = 0L, used = 0L;
        if (coupons != null) {
            for (Coupon c : coupons) {
                publish += c.getPublishNum() == null ? 0 : c.getPublishNum();
                received += c.getReceivedNum() == null ? 0 : c.getReceivedNum();
                used += c.getUsedNum() == null ? 0 : c.getUsedNum();
            }
        }
        vo.setCouponPublishNum(publish);
        vo.setCouponReceivedNum(received);
        vo.setCouponUsedNum(used);

        Date[] dates = StatisticsDateUtil.getDateArray(statisticsQueryParam);
        Double discount = orderStatisticsService.getDiscountPrice(dates);
        vo.setDiscountAmount(discount == null ? 0D : discount);
        return vo;
    }
}
