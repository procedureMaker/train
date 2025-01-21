package com.example.train.member.service;

import com.example.train.member.mapper.MemberMapper;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper; //现在数据在Mapper，把接口（Mapper）注入进来，再返回
    public Integer count(){
        return memberMapper.count();
    }
}
