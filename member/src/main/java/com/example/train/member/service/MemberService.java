package com.example.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.example.train.common.exception.BusinessException;
import com.example.train.common.exception.BusinessExceptionEnum;
import com.example.train.member.domain.Member;
import com.example.train.member.domain.MemberExample;
import com.example.train.member.mapper.MemberMapper;
import com.example.train.member.req.MemberRegisterReq;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper; //现在数据在Mapper，把接口（Mapper）注入进来，再返回

    public Integer count() {
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(MemberRegisterReq memberRegisterReq) {
        String mobile  = memberRegisterReq.getMobile();
        MemberExample memberExample = new MemberExample();
        //其实是一个for循环，返回等于mobile的集合
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);

        //不为空，返回ID或者提示信息
        if (CollUtil.isNotEmpty(list)){
//            return list.get(0).getId();
//            throw new RuntimeException("手机号已注册");
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(System.currentTimeMillis());
//        member.setId(1L);
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
}
