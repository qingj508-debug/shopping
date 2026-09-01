package cn.lili.modules.wxchannels.service;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.wxchannels.entity.dos.WxChannelCategory;
import cn.lili.modules.wxchannels.entity.dto.WxChannelCategorySubmitDTO;
import cn.lili.modules.wxchannels.entity.dto.WxChannelsCategoryDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 视频号小店-类目申请 服务接口
 *
 * 职责：
 * - 提供视频号类目申请记录的分页查询能力；
 * - 提供批量提审（提交类目资质材料）能力。
 *
 * 约定：
 * - 分页默认按创建时间倒序；
 * - status 支持 APPROVED、PENDING、REJECTED 等状态过滤；
 * - 提审入参需遵循 WxChannelCategorySubmitDTO 定义，服务层负责最大批量等业务校验。
 */
public interface WxChannelCategoryService extends IService<WxChannelCategory> {

    /**
     * 分页查询视频号类目申请
     *
     * @param page   分页参数（页码、页大小、排序）
     * @param status 状态过滤（APPROVED/PENDING/REJECTED），可为空表示全部
     * @return 类目申请分页结果
     */
    IPage<WxChannelCategory> page(PageVO page, String status);

    /**
     * 批量提交类目资质材料进行提审
     *
     * @param dto 批量提审请求（包含条目与资质材料）
     */
    void submit(WxChannelCategorySubmitDTO dto);

    /**
     * 获取微信视频号全量三级类目，并缓存结果
     *
     * 说明：
     * - 对接微信「获取商品类目」接口，获取所有三级类目及其资质信息；
     * - 由于官方返回包约 2MB，且有频率限制，仅在缓存失效或主动刷新时调用；
     * - 默认缓存 1 天，期间重复调用直接命中缓存。
     *
     * @param forceRefresh 是否强制刷新（true 则忽略缓存，直接拉取并覆盖缓存）
     * @return 微信官方三级类目列表
     */
    java.util.List<WxChannelsCategoryDTO> listAllThirdCategory(boolean forceRefresh);
}
