package cn.lili.modules.member.serviceimpl;

import cn.lili.modules.member.entity.dos.MemberGroup;
import cn.lili.modules.member.mapper.MemberGroupMapper;
import cn.lili.modules.member.service.MemberGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MemberGroupServiceImpl extends ServiceImpl<MemberGroupMapper, MemberGroup> implements MemberGroupService {
}
