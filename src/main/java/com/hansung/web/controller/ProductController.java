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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansung.web.service.ProductService;
import com.hansung.web.vo.ProductVo;
import com.hansung.web.vo.ResultVO;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/productView")
	public String productView(Model model) {
		model.addAttribute("page", "ProductCheck");
		return "contents/productView";
	}

	@GetMapping("/productManagement")
	public String productManagement(Model model) {
		model.addAttribute("page", "ProductManagement");
		return "contents/productManagement";
	}

	@ResponseBody
	@GetMapping("productManagement/productList")
	public ResultVO findProductList(ResultVO result) {
		Map<String, ArrayList<?>> contents = new HashMap<String, ArrayList<?>>();
		contents.put("contents", productService.findProducts());
		result.setResult(true);
		result.setData(contents);
		return result;
	}

	@ResponseBody
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("productManagement/productList")
	public ResponseEntity<?> modifyProduct(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProductVo> productInsertList = (List<ProductVo>) param.get("createdRows");
		List<ProductVo> productUpdateList = (List<ProductVo>) param.get("updatedRows");
		List<ProductVo> productDeleteList = (List<ProductVo>) param.get("deletedRows");
		if (!productInsertList.isEmpty()) {
			productService.insertProducts(productInsertList);
		}
		if (!productDeleteList.isEmpty()) {
			productService.deleteProducts(productDeleteList);
		}
		if (!productUpdateList.isEmpty()) {
			productService.updateProducts(productUpdateList);
		}
		result.put("result", true);
		result.put("data", true);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("productManagement/searchProductCode")
	public ResponseEntity<?> searchProductsByProductCode(@RequestBody HashMap<String, Object> requestData) {
		String productCode = (String) requestData.get("productCode");
		List<ProductVo> result = productService.searchProductsByProductCode(productCode);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("productManagement/searchProductName")
	public ResponseEntity<?> searchProductsByProductName(@RequestBody HashMap<String, Object> requestData) {
		String productName = (String) requestData.get("productName");
		List<ProductVo> result = productService.searchProductsByProductName(productName);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("productManagement/searchProductDate")
	public ResponseEntity<?> searchProductsByDate(@RequestBody HashMap<String, Object> startAndEndDate) {
		List<ProductVo> result = productService.searchProductsByDate(startAndEndDate);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("productManagement/autoCompleteProductCode")
	public ResponseEntity<?> autoCompleteProductByProductCode(@RequestBody HashMap<String, Object> param) {
		String input = (String) param.get("input");
		List<String> result = productService.autoCompleteProductByProductCode(input);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("productManagement/autoCompleteProductName")
	public ResponseEntity<?> autoCompleteProductByProductName(@RequestBody HashMap<String, Object> param) {
		String input = (String) param.get("input");
		List<String> result = productService.autoCompleteProductByProductName(input);
		return ResponseEntity.ok().body(result);
	}
}