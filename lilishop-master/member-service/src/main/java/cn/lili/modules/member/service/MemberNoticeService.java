package cn.lili.modules.member.service;

import cn.lili.modules.member.entity.dos.MemberNotice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 客户站内信业务层
 *
 * @author Chopper
 * @since 2020/11/17 3:44 下午
 */
public interface MemberNoticeService extends IService<MemberNotice> {

    /**
     * 批量标记站内信已读
     *
     * @param ids 站内信ID列表
     * @return 是否更新成功
     */
    boolean read(List<String> ids);

    /**
     * 标记指定会员的全部站内信已读
     *
     * @param memberId 会员ID
     * @return 是否更新成功
     */
    boolean readAll(String memberId);

    /**
     * 删除指定会员的全部站内信
     *
     * @param memberId 会员ID
     * @return 是否删除成功
     */
    boolean removeAll(String memberId);
}
