package com.example.train.member.mapper;

import com.example.train.member.domain.Member;
import com.example.train.member.domain.MemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    long countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(Long id);

    /**
     *
     * @param record 空值不更新
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    /**
     *
     * @param record 空值更新
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}