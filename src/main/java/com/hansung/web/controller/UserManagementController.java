package com.hansung.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansung.web.service.UserManagementService;
import com.hansung.web.vo.UserVo;


@Controller
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;
	
	@GetMapping("/userManagement")
	public String getListPage(Model model, @RequestParam("currentPage") int currentPage) {
		int count = userManagementService.geUserCount();
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
		List<UserVo> userList = null;
		userList = userManagementService.getUserListPaging(displayPost, postNum);
		model.addAttribute("userList", userList);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("page", "UserManagement");
		return "contents/userManagement";
	}
	
	@ResponseBody
	@PostMapping("userManagement/changeEnabled")
	public ResponseEntity<?> changeEnabled(@RequestBody HashMap<String, Object> param) throws Exception {
		String userId = (String) param.get("userId");
		if(userId != null) {
			userManagementService.changeEnabled(param);	
		}
		return ResponseEntity.ok().body(null);
	}
	
	@ResponseBody
	@PostMapping("userManagement/changeRole")
	public ResponseEntity<?> changeRole(@RequestBody HashMap<String, Object> param) throws Exception {
		String userId = (String) param.get("userId");
		if(userId != null) {
			userManagementService.changeRole(param);	
		}
		return ResponseEntity.ok().body(null);
	}
}
