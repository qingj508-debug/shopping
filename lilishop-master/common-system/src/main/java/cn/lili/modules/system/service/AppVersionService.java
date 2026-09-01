package cn.lili.modules.system.service;

import cn.lili.common.vo.PageVO;
import cn.lili.modules.system.entity.dos.AppVersion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * app版本业务层
 *
 * @author Chopper
 * @since 2020/11/17 8:02 下午
 */
public interface AppVersionService extends IService<AppVersion> {

    /**
     * 获取当前最新的APP版本
     * 获取用户的APP类型，返回最新的数据的版本号
     *
     * @param appType APP版本类型
     * @return 最新的APP版本号
     */
    AppVersion getAppVersion(String appType);

    /**
     * 检测APP版本信息
     *
     * @param appVersion app版本
     * @return 是否可添加
     */
    boolean checkAppVersion(AppVersion appVersion);

    /**
     * app版本分页查询
     *
     * @param type APP类型（可选）
     * @param pageVO 分页参数
     * @return 分页结果
     */
    IPage<AppVersion> pageByType(String type, PageVO pageVO);
}
