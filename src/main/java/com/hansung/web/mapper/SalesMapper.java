package com.hansung.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hansung.web.vo.SalesVo;

@Repository
public interface SalesMapper {
	void insertSales(List<SalesVo> salesInsertList);

	SalesVo searchProductByProductCode(Map<String, Object> param);

	SalesVo searchProductByProductName(Map<String, Object> param);

	List<String> autoCompleteSalesByProductCode(Map<String, Object> param);

	List<String> autoCompleteSalesByProductName(Map<String, Object> param);

	///// salesManagement start/////
	ArrayList<SalesVo> findSales(String salesBranch);

	int getSalesQuantityBySalesSeq(int salesSeq);

	void deleteSales(List<SalesVo> salesDeleteList);

	void updateSales(List<SalesVo> salesUpdateList);

	List<SalesVo> searchSalesManagementByProductCode(Map<String, Object> param);

	List<SalesVo> searchSalesManagementByProductName(Map<String, Object> param);

	List<SalesVo> searchSalesManagementByDate(Map<String, Object> param);

	List<String> autoCompleteSalesManagementByProductCode(Map<String, Object> param);

	List<String> autoCompleteSalesManagementByProductName(Map<String, Object> param);
}
