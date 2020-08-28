package com.hansung.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansung.web.service.SalesService;
import com.hansung.web.vo.ResultVO;
import com.hansung.web.vo.SalesVo;

@Controller
public class SalesController {

	@Autowired
	private SalesService salesService;

	///// sales start /////
	@GetMapping("/sales")
	public String sales(Model model) {
		model.addAttribute("page", "Sales");
		return "contents/sales";
	}

	@ResponseBody
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("sales/insertSales")
	public ResponseEntity<?> insertSales(@RequestBody HashMap<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SalesVo> salesInsertList = (List<SalesVo>) param.get("createdRows");
		if (!salesInsertList.isEmpty()) {
			salesService.insertSales(salesInsertList);
		}
		result.put("result", true);
		return ResponseEntity.ok().body(result);
	}
	
	@ResponseBody
	@PostMapping("sales/searchProductCode")
	public ResponseEntity<?> searchProductByProductCode(@RequestBody HashMap<String, Object> param) {
		SalesVo result = salesService.searchProductByProductCode(param);
		return ResponseEntity.ok().body(result);
	}
	
	@ResponseBody
	@PostMapping("sales/searchProductName")
	public ResponseEntity<?> searchProductByProductName(@RequestBody HashMap<String, Object> param) {
		SalesVo result = salesService.searchProductByProductName(param);
		return ResponseEntity.ok().body(result);
	}
	
	@ResponseBody
	@PostMapping("sales/autoCompleteProductCode")
	public ResponseEntity<?> autoCompleteSalesByProductCode(@RequestBody HashMap<String, Object> param) {
		List<String> result = salesService.autoCompleteSalesByProductCode(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("sales/autoCompleteProductName")
	public ResponseEntity<?> autoCompleteSalesByProductName(@RequestBody HashMap<String, Object> param) {
		List<String> result = salesService.autoCompleteSalesByProductCode(param);
		return ResponseEntity.ok().body(result);
	}
	///// sales end /////

	///// salesManagement start/////
	@GetMapping("/salesManagement")
	public String salesManagement(Model model) {
		model.addAttribute("page", "SalesManagement");
		return "contents/salesManagement";
	}

	@ResponseBody
	@GetMapping("salesManagement/salesList")
	public ResponseEntity<?> findSales(ResultVO resultVo, @RequestParam("currentBranch") String currentBranch) {
		Map<String, ArrayList<?>> contents = new HashMap<String, ArrayList<?>>();
		try {
			contents.put("contents", salesService.findSales(currentBranch));
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
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("salesManagement/modifySales")
	public ResponseEntity<?> modifySales(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SalesVo> salesDeleteList = (List<SalesVo>) param.get("deletedRows");
		List<SalesVo> salesUpdateList = (List<SalesVo>) param.get("updatedRows");
		if (!salesDeleteList.isEmpty()) {
			salesService.deleteSales(salesDeleteList);
		}
		if (!salesUpdateList.isEmpty()) {
			salesService.updateSales(salesUpdateList);
		}
		result.put("result", true);
		result.put("data", true);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("salesManagement/searchProductCode")
	public ResponseEntity<?> searchSalesManagementByProductCode(@RequestBody HashMap<String, Object> param) {
		List<SalesVo> result = salesService.searchSalesManagementByProductCode(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("salesManagement/searchProductName")
	public ResponseEntity<?> searchSalesManagementByProductName(@RequestBody HashMap<String, Object> param) {
		List<SalesVo> result = salesService.searchSalesManagementByProductName(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("salesManagement/searchSalesDate")
	public ResponseEntity<?> searchSalesManagementByDate(@RequestBody HashMap<String, Object> param) {
		List<SalesVo> result = salesService.searchSalesManagementByDate(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("salesManagement/autoCompleteProductCode")
	public ResponseEntity<?> autoCompleteSalesManagementByProductCode(@RequestBody HashMap<String, Object> param) {
		List<String> result = salesService.autoCompleteSalesManagementByProductCode(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("salesManagement/autoCompleteProductName")
	public ResponseEntity<?> autoCompleteSalesManagementByProductName(@RequestBody HashMap<String, Object> param) {
		List<String> result = salesService.autoCompleteSalesManagementByProductName(param);
		return ResponseEntity.ok().body(result);
	}
	///// salesManagement end/////
}