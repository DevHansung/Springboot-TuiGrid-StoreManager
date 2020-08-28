package com.hansung.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hansung.web.service.UserService;
import com.hansung.web.vo.UserVo;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/signin")
	public String signinPage(@RequestParam(value = "error", required = false) String error, Model model)
			throws Exception {
		if (error != null) {
			model.addAttribute("login_error", "회원정보가 일치하지 않거나 비활성화 된 계정입니다.");
		}
		return "contents/signin";
	}

	@GetMapping("/user/signinSuccess")
	public String signinSuccess(HttpServletRequest request, Authentication authentication, Model model) throws Exception {
		HttpSession session = request.getSession();
		Object securityContextObject = session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if (securityContextObject != null) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (!userDetails.getUsername().equals(null)) {
				UserVo userInfo = userService.findUserInfoByUsername(userDetails.getUsername());
				String branch = userInfo.getBranch();
				String name = userInfo.getName();
				session.setAttribute("branch", branch);
				session.setAttribute("name", name);
			}
		}
		return "redirect:/";
	}

	@GetMapping("/user/signup")
	public String signupPage(UserVo userVo) throws Exception {
		return "contents/signup";
	}

	@PostMapping("/user/signup")
	public String signup(@Valid UserVo userVo, BindingResult errors, Model model) {
		if (errors.hasErrors() || !userService.validateHandling(userVo).isEmpty()) {
			model.addAllAttributes(userService.validateHandling(userVo));
			return "contents/signup";
		}
		if (userService.insertUser(userVo) == true) {
			return "contents/signin";
		} else
			return "error";
	}
}
