package com.hansung.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandling {
	@ExceptionHandler(Exception.class)
	public String globalException(Exception e, Model model) {
		model.addAttribute("error", "요청 작업 수행 중 문제가 발생 하였습니다.");
		System.out.println(e);
		return "error";
	}

	@ResponseBody
	@ExceptionHandler(DbCrudException.class)
	public ResponseEntity<?> dbCrudExceptionHandler(DbCrudException e) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", false);
		result.put("message", e.getMessage());
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestExceptionHandler(BadRequestException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
