package com.hansung.web.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hansung.web.vo.BoardVo;

@Repository
public interface BoardMapper {

	public int getBoardCount();

	public List<BoardVo> getBoardListPaging(Map<String, Object> paging);

	public BoardVo getBoardByBoardNum(int boardNum);

	public void insertBoard(BoardVo boardVo);

	public void updateBoard(BoardVo boardVo);

	public void deleteBoardByBoardNum(int boardNum);

	public void updateBoardViewCount(int boardNum);
}
