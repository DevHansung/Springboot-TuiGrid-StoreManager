package com.hansung.web.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.hansung.web.mapper.SalesMapper;
import com.hansung.web.mapper.StockMapper;
import com.hansung.web.vo.SalesVo;

@Service
public class SalesService {
	@Autowired
	private SalesMapper salesMapper;

	@Autowired
	private StockMapper stockMapper;
	
	///// sales start/////
	@Transactional
	public void insertSales(List<SalesVo> salesInsertList) {
        ObjectMapper mapper = new ObjectMapper();
		List<SalesVo> salesInsertListConvert = mapper.convertValue(salesInsertList, new TypeReference<List<SalesVo>>(){});
		for (int i = 0; i < salesInsertListConvert.size(); i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("productCode" , salesInsertListConvert.get(i).getProductCode());
			param.put("currentBranch" , salesInsertListConvert.get(i).getSalesBranch());
			int getStockTotalQuantity = stockMapper.getStockTotalQuantityByproductCodeAndBranch(param);
			if(getStockTotalQuantity < salesInsertListConvert.get(i).getSalesQuantity()) {
				//salesInsertListConvert.remove(i);
				throw new DbCrudException(HttpStatus.NOT_FOUND, salesInsertListConvert.get(i).getSalesSeq() + "SEQ "
					+ "항목의 판매수량이 재고 수량보다" + (salesInsertListConvert.get(i).getSalesQuantity() - getStockTotalQuantity) + "개 많습니다\n"
					+ salesInsertListConvert.get(i).getProductCode() + "의 재고수량: " + getStockTotalQuantity);
			}
		} try {
			salesMapper.insertSales(salesInsertListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.NOT_FOUND, "DB_insert_error");
		}
	}

	public SalesVo searchProductByProductCode(Map<String, Object> param) {
		SalesVo result = salesMapper.searchProductByProductCode(param);
		if (result == null) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public SalesVo searchProductByProductName(Map<String, Object> param) {
		SalesVo result = salesMapper.searchProductByProductName(param);
		if (result == null) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}
	
	public List<String> autoCompleteSalesByProductCode(Map<String, Object> param) {
		return salesMapper.autoCompleteSalesByProductCode(param);
	}
	
	public List<String> autoCompleteSalesByProductName(Map<String, Object>param) {
		return salesMapper.autoCompleteSalesByProductName(param);
	}

	///// salesManagement start/////
	public ArrayList<SalesVo> findSales(String salesBranch) {
		return salesMapper.findSales(salesBranch);
	}
	
	@Transactional
	public void deleteSales(List<SalesVo> salesDeleteList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<SalesVo> salesDeleteListConvert = mapper.convertValue(salesDeleteList, new TypeReference<List<SalesVo>>(){});
		try {
			salesMapper.deleteSales(salesDeleteListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.NOT_FOUND, "DB_delete_error");
		}
	}
	
	@Transactional
	public void updateSales(List<SalesVo> salesUpdateList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<SalesVo> salesUpdateListConvert = mapper.convertValue(salesUpdateList, new TypeReference<List<SalesVo>>(){});	
		for (int i = 0; i < salesUpdateListConvert.size(); i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("productCode" , salesUpdateListConvert.get(i).getProductCode());
			param.put("currentBranch" , salesUpdateListConvert.get(i).getSalesBranch());
			int stockOldQuantity = stockMapper.getStockTotalQuantityByproductCodeAndBranch(param) 
					+ salesMapper.getSalesQuantityBySalesSeq(salesUpdateListConvert.get(i).getSalesSeq());
			if(stockOldQuantity < salesUpdateListConvert.get(i).getSalesQuantity()) {
				//salesUpdateListConvert.remove(i);
				throw new DbCrudException(HttpStatus.NOT_FOUND, salesUpdateListConvert.get(i).getSalesSeq() + "SEQ "
					+ "항목의 판매수량이 재고 수량보다 " + (salesUpdateListConvert.get(i).getSalesQuantity() - stockOldQuantity) + "개 많습니다\n"
					+ salesUpdateListConvert.get(i).getProductCode() + "의 재고수량: " + stockOldQuantity);
			}				
		} try {
			salesMapper.updateSales(salesUpdateListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.NOT_FOUND, "DB_update_error");
		}
	}

	public List<SalesVo> searchSalesManagementByProductCode(Map<String, Object> param) {
		List<SalesVo> result = salesMapper.searchSalesManagementByProductCode(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<SalesVo> searchSalesManagementByProductName(Map<String, Object> param) {
		List<SalesVo> result = salesMapper.searchSalesManagementByProductName(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<SalesVo> searchSalesManagementByDate(Map<String, Object> param) {
		List<SalesVo> result = salesMapper.searchSalesManagementByDate(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<String> autoCompleteSalesManagementByProductCode(Map<String, Object> param) {
		return salesMapper.autoCompleteSalesManagementByProductCode(param);
	}

	public List<String> autoCompleteSalesManagementByProductName(Map<String, Object> param) {
		return salesMapper.autoCompleteSalesManagementByProductName(param);
	}

}
