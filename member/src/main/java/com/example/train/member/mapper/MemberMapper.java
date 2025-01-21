package com.example.train.member.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    /**
     * 查询member这张表的条数
     * @return
     */
    Integer count();
}
