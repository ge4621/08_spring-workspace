package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

@Repository
public class BoardDao {
	
	public int selectListCount(SqlSessionTemplate sqlsession) {
		return sqlsession.selectOne("boardMapper.selectListCount");
	}
	
	public ArrayList<Board> selectList(SqlSessionTemplate sqlsession,PageInfo pi){
		
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit(); 
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		return (ArrayList)sqlsession.selectList("boardMapper.selectList", null , rowBounds);
	}
	
	public int insertBoard(SqlSessionTemplate sqlsession,Board b) {
		return sqlsession.insert("boardMapper.insertBoard", b);
	}
	
	public int increaseCount(SqlSessionTemplate sqlsession,int boardNo) {
		return sqlsession.update("boardMapper.increaseCount", boardNo);
	}
	
	public Board selectBoard(SqlSessionTemplate sqlsession,int boardNo) {
		return sqlsession.selectOne("boardMapper.selectBoard", boardNo);
	}

}
