package cn.lili.modules.im.service;

import cn.lili.modules.im.entity.dos.ImTalk;
import cn.lili.modules.im.entity.dto.IMTalkQueryParams;
import cn.lili.modules.im.entity.vo.ImTalkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * 聊天 业务层
 *
 * @author Chopper
 */
public interface ImTalkService extends IService<ImTalk> {

    /**
     * 获取与某人的聊天框
     *
     * @param userId1
     * @return
     */
    ImTalk getTalkByUser(String userId1);

    /**
     * 获取与某人的聊天
     * @param userId
     * @return
     */
    ImTalkVO getTalkByUserId(String userId);

    /**
     * 置顶消息
     *
     * @param id
     * @param top
     */
    void top(String id, Boolean top);

    /**
     * 禁用（前端不做展示）聊天
     *
     * @param id
     */
    void disable(String id);

    /**
     * 获取用户聊天列表
     * @return 用户聊天列表
     */
    List<ImTalkVO> getUserTalkList(IMTalkQueryParams imTalkQueryParams);

    /**
     * 获取商家聊天列表
     * @return 商家聊天列表
     */
    List<ImTalkVO> getStoreTalkList(IMTalkQueryParams imTalkQueryParams);

    /**
     * 更新会话最后一条消息信息。
     *
     * @param talkId          会话ID
     * @param lastTalkMessage 最后消息内容
     * @param lastTalkTime    最后消息时间
     * @param lastMessageType 最后消息类型
     * @return 是否更新成功
     */
    boolean updateLastTalkMessage(String talkId, String lastTalkMessage, Date lastTalkTime, String lastMessageType);
}
