package cn.lili.modules.wechat.service;

import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.SearchVO;
import cn.lili.modules.wechat.entity.dos.WechatMPMessage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 微信小程序消息订阅 业务层
 * @author Chopper
 */
public interface WechatMPMessageService extends IService<WechatMPMessage> {

    /**
     * 初始化微信消息订阅模版
     */
    void init();

    /**
     * 微信小程序消息订阅分页查询
     *
     * @param entity 查询实体
     * @param searchVO 查询条件
     * @param pageVO 分页参数
     * @return 分页结果
     */
    IPage<WechatMPMessage> getByPage(WechatMPMessage entity, SearchVO searchVO, PageVO pageVO);
}
