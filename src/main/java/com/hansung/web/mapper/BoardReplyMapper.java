package com.hansung.web.mapper;

import java.util.List;
import java.util.Map;

import com.hansung.web.vo.BoardReplyVo;

public interface BoardReplyMapper {

	List<BoardReplyVo> getReplyListByBoardNum(int boardNum);

	String getReplyByReplyWriter(int replyNum);

	void insertReply(Map<String, Object> param);

	void deleteReplyByReplyNum(int replyNum);
}
