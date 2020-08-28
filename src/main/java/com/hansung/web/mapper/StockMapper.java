package com.hansung.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hansung.web.vo.StockVo;

@Repository
public interface StockMapper {
	ArrayList<StockVo> findStocks(String currentBranch);

	int getStockTotalQuantityByproductCodeAndBranch(Map<String, Object> param);

	List<StockVo> searchStockByProductCode(Map<String, Object> param);

	List<StockVo> searchStockByProductName(Map<String, Object> param);

	List<StockVo> searchStockByDate(Map<String, Object> param);

	List<String> autoCompleteStockByProductCode(Map<String, Object> param);

	List<String> autoCompleteStockByProductName(Map<String, Object> param);
}
