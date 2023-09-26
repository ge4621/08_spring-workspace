package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository //dao만 repository 나머지는 service는 service ,controller은 controller
public class MemberDao {

	public Member loginMember(SqlSessionTemplate sqlSession,Member m) {
		return sqlSession.selectOne("memberMapper.loginMember", m);
	
	}
	
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}
}
