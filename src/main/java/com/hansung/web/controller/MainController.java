package com.hansung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String homePage(Model model) throws Exception {
		model.addAttribute("page","Home");
		return "contents/home";
	}

	@GetMapping("/access-denied")
	public String noAuthorityPage(Model model) throws Exception {
		model.addAttribute("errors", "해당 페이지의 접근 권한이 없습니다.");
		return "errors";
	}
}