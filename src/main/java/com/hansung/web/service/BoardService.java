package com.hansung.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansung.web.mapper.BoardMapper;
import com.hansung.web.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	BoardMapper boardMapper;

	public int getBoardCount() {
		return boardMapper.getBoardCount();
	}

	public List<BoardVo> getBoardListPaging(int displayPost, int postNum) {
		Map<String, Object> paging = new HashMap<String, Object>();
		paging.put("displayPost", displayPost);
		paging.put("postNum", postNum);
		return boardMapper.getBoardListPaging(paging);
	}

	public BoardVo getBoardByBoardNum(int boardNum) {
		return boardMapper.getBoardByBoardNum(boardNum);
	}

	public void insertBoard(BoardVo boardVo) {
		boardMapper.insertBoard(boardVo);
	}

	public void updateBoard(BoardVo boardVo) {
		boardMapper.updateBoard(boardVo);
	}

	public void deleteBoardByBoardNum(int boardNum) {
		boardMapper.deleteBoardByBoardNum(boardNum);
	}

	public void updateBoardViewCount(int boardNum) {
		boardMapper.updateBoardViewCount(boardNum);
	}

}
