package com.hansung.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansung.web.service.StockService;
import com.hansung.web.vo.ResultVO;
import com.hansung.web.vo.StockVo;

@Controller
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping("/stockView")
	public String productView(Model model) {
		model.addAttribute("page", "Stocks");
		return "contents/stockView";
	}
	
	@ResponseBody
	@GetMapping("stockView/stockList")
	public ResponseEntity<?> findStocks(ResultVO resultVo, @RequestParam("currentBranch") String currentBranch)
			throws Exception {
		Map<String, ArrayList<?>> contents = new HashMap<String, ArrayList<?>>();
		try {
			contents.put("contents", stockService.findStocks(currentBranch));
			resultVo.setResult(true);
			resultVo.setData(contents);
			return ResponseEntity.ok().body(resultVo);
		} catch (Exception e) {
			contents.put("contents", null);
			resultVo.setResult(true);
			resultVo.setData(contents);
			return ResponseEntity.ok().body(resultVo);
		}
	}

	@ResponseBody
	@PostMapping("stockView/searchProductCode")
	public ResponseEntity<?> searchSalesManagementByProductCode(@RequestBody HashMap<String, Object> param) {
		List<StockVo> result = stockService.searchStockByProductCode(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("stockView/searchProductName")
	public ResponseEntity<?> searchSalesManagementByProductName(@RequestBody HashMap<String, Object> param) {
		List<StockVo> result = stockService.searchStockByProductName(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("stockView/searchStockByDate")
	public ResponseEntity<?> searchSalesManagementByDate(@RequestBody HashMap<String, Object> param) {
		List<StockVo> result = stockService.searchStockByDate(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("stockView/autoCompleteProductCode")
	public ResponseEntity<?> autoCompleteSalesManagementByProductCode(@RequestBody HashMap<String, Object> param) {
		List<String> result = stockService.autoCompleteStockByProductCode(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("stockView/autoCompleteProductName")
	public ResponseEntity<?> autoCompleteSalesManagementByProductName(@RequestBody HashMap<String, Object> param) {
		List<String> result = stockService.autoCompleteStockByProductName(param);
		return ResponseEntity.ok().body(result);
	}
	
}