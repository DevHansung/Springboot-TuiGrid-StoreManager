package com.hansung.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansung.web.service.BoardReplyService;
import com.hansung.web.service.BoardService;
import com.hansung.web.vo.BoardReplyVo;
import com.hansung.web.vo.BoardVo;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardReplyService boardReplyService;

	@GetMapping("/boardView")
	public String getBoardList(Model model, @RequestParam("currentPage") int currentPage) {

		int count = boardService.getBoardCount();
		int postNum = 10;
		int pageNum = (int) Math.ceil((double) count / postNum);
		int displayPost = (currentPage - 1) * postNum;
		int pageNum_cnt = 10;
		int endPageNum = (int) (Math.ceil((double) currentPage / (double) pageNum_cnt) * pageNum_cnt);
		int startPageNum = endPageNum - (pageNum_cnt - 1);
		int endPageNum_tmp = (int) (Math.ceil((double) count / (double) pageNum_cnt));
		if (endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}
		boolean prev = startPageNum == 1 ? false : true;
		boolean next = endPageNum * pageNum_cnt >= count ? false : true;
		List<BoardVo> boardList = null;
		boardList = boardService.getBoardListPaging(displayPost, postNum);
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("page", "BoardView");
		return "contents/boardView";
	}

	@GetMapping("/boardDetail")
	public String getBoard(@RequestParam("currentPage") int cutrentPage, @RequestParam("boardNum") int boardNum,
			Model model) {
		BoardVo board = boardService.getBoardByBoardNum(boardNum);
		boardService.updateBoardViewCount(boardNum); // 게시판 글 조회수 counter
		model.addAttribute("board", board);
		model.addAttribute("currentPage", cutrentPage);
		model.addAttribute("page", "boardDetail");
		return "contents/BoardDetail";
	}

	@GetMapping("/writeBoard")
	public String boardInsert(@RequestParam("currentPage") int currentPage, BoardVo boardVo, Model model) throws Exception {
		model.addAttribute("page", "BoardWrite");
		model.addAttribute("currentPage", currentPage);
		return "contents/boardWrite";
	}

	@PostMapping("/writeBoard")
	public String insertBoard(@Valid BoardVo boardVo, BindingResult bindingResult) {
		boardService.insertBoard(boardVo);
		return "redirect:/boardView?currentPage=1";
	}

	@GetMapping("/updateBoard")
	public String boardUpdate(@RequestParam("currentPage") int currentPage, @RequestParam("boardNum") int boardNum,
			Model model) throws Exception {
		model.addAttribute("page", "BoardUpdate");
		model.addAttribute("boardVo", boardService.getBoardByBoardNum(boardNum));
		model.addAttribute("currentPage", currentPage);
		return "contents/boardUpdate";
	}

	@PostMapping("/updateBoard")
	public String updateBoard(@Valid BoardVo boardVo, @RequestParam("currentPage") int currentPage,
			@RequestParam("boardNum") int boardNum) throws Exception {
		boardService.updateBoard(boardVo);
		return "redirect:/boardDetail?boardNum=" + boardNum + "&currentPage=" + currentPage;
	}

	@DeleteMapping("/boardDetail/deleteBoard")
	@ResponseBody
	public ResponseEntity<?> deleteBoardByBoardNum(@RequestBody HashMap<String, Object> param,
			HttpServletRequest request) {
		String result = "error";
		if (param.get("boardWriter").equals(request.getSession().getAttribute("name"))) {
			boardService.deleteBoardByBoardNum(Integer.parseInt(param.get("boardNum").toString()));
			result = "success";
		}
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/boardDetail/replyList")
	@ResponseBody
	public ResponseEntity<?> getReplyList(@RequestParam("boardNum") int boardNum) throws Exception {
		List<BoardReplyVo> replyList = boardReplyService.getReplyListByBoardNum(boardNum);
		return ResponseEntity.ok().body(replyList);
	}

	@PostMapping("/boardDetail/writeReply")
	@ResponseBody
	public ResponseEntity<?> insertReply(@RequestBody HashMap<String, Object> param) throws Exception {
		boardReplyService.insertReply(param);
		return ResponseEntity.ok().body("success");
	}

	@DeleteMapping("/boardDetail/deleteReply")
	@ResponseBody
	public ResponseEntity<?> deleteReply(@RequestBody HashMap<String, Integer> param, HttpServletRequest request) {
		int replyNum = param.get("replyNum");
		String result = "error";
		String boardWriter = boardReplyService.getReplyByReplyWriter(replyNum);
		if (boardWriter.equals(request.getSession().getAttribute("name"))) {
			boardReplyService.deleteReplyByReplyNum(replyNum);
			result = "success";
		}
		return ResponseEntity.ok().body(result);
	}

}
