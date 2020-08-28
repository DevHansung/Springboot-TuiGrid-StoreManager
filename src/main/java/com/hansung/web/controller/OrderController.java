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

import com.hansung.web.service.OrderService;
import com.hansung.web.vo.OrderVo;
import com.hansung.web.vo.ProductVo;
import com.hansung.web.vo.ResultVO;
import com.hansung.web.vo.SalesVo;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	///// order start /////
	@GetMapping("/order")
	public String order(Model model) throws Exception {
		model.addAttribute("page", "Order");
		return "contents/order";
	}

	@ResponseBody
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("order/insertOrder")
	public ResponseEntity<?> insertOrders(@RequestBody HashMap<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<OrderVo> orderInsertList = (List<OrderVo>) param.get("createdRows");
		if (!orderInsertList.isEmpty()) {
			orderService.insertOrders(orderInsertList);
		}
		result.put("result", true);
		return ResponseEntity.ok().body(result);

	}

	@ResponseBody
	@PostMapping("order/searchProductCode")
	public ResponseEntity<?> searchProductByproductCode(@RequestBody HashMap<String, Object> param) {
		String productCode = (String) param.get("productCode");
		ProductVo result = orderService.searchProductByProductCode(productCode);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("order/searchProductName")
	public ResponseEntity<?> searchProductByproductName(@RequestBody HashMap<String, Object> param) {
		String productName = (String) param.get("productName");
		ProductVo result = orderService.searchProductByProductName(productName);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("order/autoCompleteProductCode")
	public ResponseEntity<?> autoCompleteOrderByProductCode(@RequestBody HashMap<String, Object> param)
			throws Exception {
		String input = (String) param.get("input");
		List<String> result = orderService.autoCompleteOrderByProductCode(input);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("order/autoCompleteProductName")
	public ResponseEntity<?> autoCompleteOrderByProductName(@RequestBody HashMap<String, Object> param)
			throws Exception {
		String input = (String) param.get("input");
		List<String> result = orderService.autoCompleteOrderByProductName(input);
		return ResponseEntity.ok().body(result);
	}
	///// order end /////

	///// user orderManagement start /////
	@GetMapping("/orderManagement")
	public String orderManagement(Model model) {
		model.addAttribute("page", "OrderManagement");
		return "contents/orderManagement";
	}

	@ResponseBody
	@GetMapping("orderManagement/orderList")
	public ResponseEntity<?> findOrders(ResultVO resultVo, @RequestParam("currentBranch") String currentBranch) {
		Map<String, ArrayList<?>> contents = new HashMap<String, ArrayList<?>>();
		try {
			contents.put("contents", orderService.findOrderManagement(currentBranch));
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
	@PostMapping("orderManagement/modifyOrders")
	public ResponseEntity<?> modifyOrders(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SalesVo> orderDeleteList = (List<SalesVo>) param.get("deletedRows");
		List<SalesVo> orderUpdateList = (List<SalesVo>) param.get("updatedRows");
		if (!orderDeleteList.isEmpty()) {
			orderService.deleteOrders(orderDeleteList);
		}
		if (!orderUpdateList.isEmpty()) {
			orderService.updateOrders(orderUpdateList);
		}
		result.put("result", true);
		result.put("data", true);
		return ResponseEntity.ok().body(result);

	}

	@ResponseBody
	@PostMapping("orderManagement/searchProductCode")
	public ResponseEntity<?> searchOrdersByProductCode(@RequestBody Map<String, Object> param) {
		List<OrderVo> result = orderService.searchOrderManagementByProductCode(param);
		return ResponseEntity.ok().body(result);

	}

	@ResponseBody
	@PostMapping("orderManagement/searchProductName")
	public ResponseEntity<?> searchOrdersByProductName(@RequestBody HashMap<String, Object> param) {
		List<OrderVo> result = orderService.searchOrderManagementByProductName(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("orderManagement/searchOrderDate")
	public ResponseEntity<?> searchOrdersByOrderDate(@RequestBody HashMap<String, Object> param) {
		List<OrderVo> result = orderService.searchOrderManagementByDate(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("orderManagement/autoCompleteProductCode")
	public ResponseEntity<?> autoCompleteOrderManagementByProductCode(@RequestBody HashMap<String, Object> param) {
		List<String> result = orderService.autoCompleteOrderManagementByProductCode(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("orderManagement/autoCompleteProductName")
	public ResponseEntity<?> autoCompleteOrderManagementByProductName(@RequestBody HashMap<String, Object> param) {
		String input = (String) param.get("input");
		List<String> result = orderService.autoCompleteOrderViewByProductName(input);
		return ResponseEntity.ok().body(result);
	}
	///// orderManagement end /////

	///// admin orderView start /////
	@GetMapping("/orderView")
	public String AdminOrderManagement(Model model) {
		model.addAttribute("page", "AdminOrderManagement");
		return "contents/orderView";
	}

	@ResponseBody
	@GetMapping("orderView/orderList")
	public ResponseEntity<?> findOrderView(ResultVO resultVo) {
		Map<String, ArrayList<?>> contents = new HashMap<String, ArrayList<?>>();
		try {
			contents.put("contents", orderService.findOrderView());
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
	@PostMapping("orderView/searchProductCode")
	public ResponseEntity<?> searchOrderViewByProductCode(@RequestBody HashMap<String, Object> param) throws Exception {
		String productCode = (String) param.get("productCode");
		List<OrderVo> result = orderService.searchOrderViewByProductCode(productCode);
		return ResponseEntity.ok().body(result);

	}

	@ResponseBody
	@PostMapping("orderView/searchProductName")
	public ResponseEntity<?> searchOrderViewByProductName(@RequestBody HashMap<String, Object> param) {
		String productName = (String) param.get("productName");
		List<OrderVo> result = orderService.searchOrderViewByProductName(productName);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("orderView/searchOrderBranch")
	public ResponseEntity<?> searchOrderViewByOrderBranch(@RequestBody HashMap<String, Object> param) {
		String currentBranch = (String) param.get("currentBranch");
		List<OrderVo> result = orderService.searchOrderViewByOrderBranch(currentBranch);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("orderView/searchOrderDate")
	public ResponseEntity<?> searchOrderViewByOrderDate(@RequestBody HashMap<String, Object> param) {
		List<OrderVo> result = orderService.searchOrderViewByDate(param);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("orderView/autoCompleteProductCode")
	public ResponseEntity<?> autoCompleteOrderViewByProductCode(@RequestBody HashMap<String, Object> param)
			throws Exception {
		String input = (String) param.get("input");
		List<String> result = orderService.autoCompleteOrderViewByProductCode(input);
		return ResponseEntity.ok().body(result);
	}

	@ResponseBody
	@PostMapping("orderView/autoCompleteProductName")
	public ResponseEntity<?> autoCompleteOrderViewByProductName(@RequestBody HashMap<String, Object> param)
			throws Exception {
		String input = (String) param.get("input");
		List<String> result = orderService.autoCompleteOrderViewByProductName(input);
		return ResponseEntity.ok().body(result);
	}
	///// orderView end /////
}