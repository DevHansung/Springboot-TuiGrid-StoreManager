package com.hansung.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.web.exception.BadRequestException;
import com.hansung.web.exception.DbCrudException;
import com.hansung.web.mapper.OrderMapper;
import com.hansung.web.vo.OrderVo;
import com.hansung.web.vo.ProductVo;
import com.hansung.web.vo.SalesVo;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;

	///// order start/////
	@Transactional
	public void insertOrders(List<OrderVo> orderInsertList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<OrderVo> orderInsertListConvert = mapper.convertValue(orderInsertList, new TypeReference<List<OrderVo>>(){});
		try {
			orderMapper.insertOrders(orderInsertListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.NOT_FOUND, "DB_insert_error");
		}
	}

	public ProductVo searchProductByProductCode(String productCode) {
		ProductVo result = orderMapper.searchProductByProductCode(productCode);
		if (result == null) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public ProductVo searchProductByProductName(String productName) {
		ProductVo result = orderMapper.searchProductByProductName(productName);
		if (result == null) {
			throw new BadRequestException("해당 제품명으로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<String> autoCompleteOrderByProductCode(String input) {
		return orderMapper.autoCompleteOrderByProductCode(input);
	}

	public List<String> autoCompleteOrderByProductName(String input) {
		return orderMapper.autoCompleteOrderByProductName(input);
	}

	///// user orderManagement start/////
	public ArrayList<OrderVo> findOrderManagement(String orderBranch) {
		return orderMapper.findOrderManagement(orderBranch);
	}
	
	@Transactional
	public void deleteOrders(List<SalesVo> orderDeleteList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<OrderVo> orderDeleteListConvert = mapper.convertValue(orderDeleteList, new TypeReference<List<OrderVo>>(){});
		for (int i = 0; i < orderDeleteListConvert.size(); i++) {
			String getOrderState = orderMapper.getOrderStateByOrderSeq(orderDeleteListConvert.get(i).getOrderSeq());
			if(getOrderState.equals("수령")) {
				//orderDeleteListConvert.remove(i);
				throw new DbCrudException(HttpStatus.NOT_FOUND, 
						orderDeleteListConvert.get(i).getOrderSeq() + " SEQ의 주문상태가 '수령'이므로 해당 주문 정보의 삭제가 불가합니다.");
			}
		} try {
			orderMapper.deleteOrders(orderDeleteListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.NOT_FOUND, "DB_delete_error");
		}
	}
	
	@Transactional
	public void updateOrders(List<SalesVo> orderUpdateList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<OrderVo>orderUpdateListConvert = mapper.convertValue(orderUpdateList, new TypeReference<List<OrderVo>>(){});
		for (int i = 0; i < orderUpdateListConvert.size(); i++) {
			String getOrderState = orderMapper.getOrderStateByOrderSeq(orderUpdateListConvert.get(i).getOrderSeq());
			if(getOrderState.equals("수령")) {
				//orderUpdateListConvert.remove(i);
				throw new DbCrudException(HttpStatus.NOT_FOUND, 
					orderUpdateListConvert.get(i).getOrderSeq() + " SEQ의 주문상태가 '수령'이므로 주문상태 값 변경이 불가합니다.");
			}
		} try {
			orderMapper.updateOrders(orderUpdateListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.NOT_FOUND, "DB_update_error");
		}
	}

	public List<OrderVo> searchOrderManagementByProductCode(Map<String, Object> param) {
		List<OrderVo> result = orderMapper.searchOrderManagementByProductCode(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<OrderVo> searchOrderManagementByProductName(Map<String, Object> param) {
		List<OrderVo> result = orderMapper.searchOrderManagementByProductName(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품명으로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<OrderVo> searchOrderManagementByDate(Map<String, Object> param) {
		List<OrderVo> result = orderMapper.searchOrderManagementByDate(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 일자로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<String> autoCompleteOrderManagementByProductCode(Map<String, Object> param) {
		return orderMapper.autoCompleteOrderManagementByProductCode(param);
	}

	public List<String> autoCompleteOrderManagementByProductName(Map<String, Object> param) {
		return orderMapper.autoCompleteOrderManagementByProductName(param);
	}

	///// admin orderView start/////
	public ArrayList<OrderVo> findOrderView() {
		return orderMapper.findOrderView();
	}

	public List<OrderVo> searchOrderViewByProductCode(String ProductCode) {
		List<OrderVo> result = orderMapper.searchOrderViewByProductCode(ProductCode);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<OrderVo> searchOrderViewByProductName(String ProductName) {
		List<OrderVo> result = orderMapper.searchOrderViewByProductName(ProductName);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품명으로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<OrderVo> searchOrderViewByOrderBranch(String orderBranch) {
		List<OrderVo> result = orderMapper.searchOrderViewByOrderBranch(orderBranch);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 지점으로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<OrderVo> searchOrderViewByDate(Map<String, Object> startAndEndDate) {
		List<OrderVo> result = orderMapper.searchOrderViewByDate(startAndEndDate);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 일자로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<String> autoCompleteOrderViewByProductCode(String input) {
		return orderMapper.autoCompleteOrderViewByProductCode(input);
	}

	public List<String> autoCompleteOrderViewByProductName(String input) {
		return orderMapper.autoCompleteOrderViewByProductName(input);
	}
}
