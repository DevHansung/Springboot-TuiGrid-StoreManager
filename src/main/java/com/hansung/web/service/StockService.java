package com.hansung.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansung.web.exception.BadRequestException;
import com.hansung.web.mapper.StockMapper;
import com.hansung.web.vo.StockVo;

@Service
public class StockService {

	@Autowired
	private StockMapper stockMapper;

	public ArrayList<StockVo> findStocks(String currentBranch) {
		return stockMapper.findStocks(currentBranch);
	}
	
	public List<StockVo> searchStockByProductCode(Map<String, Object> param) {
		List<StockVo> result = stockMapper.searchStockByProductCode(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<StockVo> searchStockByProductName(Map<String, Object> param) {
		List<StockVo> result = stockMapper.searchStockByProductName(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품명으로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<StockVo> searchStockByDate(Map<String, Object> param) {
		List<StockVo> result = stockMapper.searchStockByDate(param);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 일자로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<String> autoCompleteStockByProductCode(Map<String, Object> param) {
		return stockMapper.autoCompleteStockByProductCode(param);
	}

	public List<String> autoCompleteStockByProductName(Map<String, Object> param) {
		return stockMapper.autoCompleteStockByProductName(param);
	}

}
