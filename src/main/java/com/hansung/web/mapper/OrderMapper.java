package com.hansung.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hansung.web.vo.OrderVo;
import com.hansung.web.vo.ProductVo;

@Repository
public interface OrderMapper {
	///// order start/////
	void insertOrders(List<OrderVo> orderInsertList);

	ProductVo searchProductByProductCode(String productCode);

	ProductVo searchProductByProductName(String productName);

	List<String> autoCompleteOrderByProductCode(String input);

	List<String> autoCompleteOrderByProductName(String input);

	///// user orderManagement start/////
	ArrayList<OrderVo> findOrderManagement(String orderBranch);

	String getOrderStateByOrderSeq(int orderSeq);

	void deleteOrders(List<OrderVo> orderUpdateList);

	void updateOrders(List<OrderVo> orderUpdateList);

	List<OrderVo> searchOrderManagementByProductCode(Map<String, Object> param);

	List<OrderVo> searchOrderManagementByProductName(Map<String, Object> param);

	List<OrderVo> searchOrderManagementByDate(Map<String, Object> param);

	List<String> autoCompleteOrderManagementByProductCode(Map<String, Object> param);

	List<String> autoCompleteOrderManagementByProductName(Map<String, Object> param);

	///// admin orderView start/////
	ArrayList<OrderVo> findOrderView();

	List<OrderVo> searchOrderViewByProductCode(String ProductCode);

	List<OrderVo> searchOrderViewByProductName(String ProductName);

	List<OrderVo> searchOrderViewByOrderBranch(String orderBranch);

	List<OrderVo> searchOrderViewByDate(Map<String, Object> startAndEndDate);

	List<String> autoCompleteOrderViewByProductCode(String input);

	List<String> autoCompleteOrderViewByProductName(String input);

}
