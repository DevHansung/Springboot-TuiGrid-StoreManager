package com.hansung.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansung.web.mapper.BoardReplyMapper;
import com.hansung.web.vo.BoardReplyVo;

@Service
public class BoardReplyService {

	@Autowired
	BoardReplyMapper boardReplyMapper;

	public List<BoardReplyVo> getReplyListByBoardNum(int boardNum) {
		return boardReplyMapper.getReplyListByBoardNum(boardNum);
	}

	public String getReplyByReplyWriter(int replyNum) {
		return boardReplyMapper.getReplyByReplyWriter(replyNum);
	}

	public void insertReply(Map<String, Object> param) {
		boardReplyMapper.insertReply(param);
	}

	public void deleteReplyByReplyNum(int replyNum) {
		boardReplyMapper.deleteReplyByReplyNum(replyNum);
	}
}
