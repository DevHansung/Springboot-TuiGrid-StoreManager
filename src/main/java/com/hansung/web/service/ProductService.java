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
import com.hansung.web.mapper.ProductMapper;
import com.hansung.web.vo.ProductVo;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	public ArrayList<ProductVo> findProducts() {
		return productMapper.findProducts();
	}
	
	@Transactional
	public void insertProducts(List<ProductVo> productInsertList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<ProductVo> productInsertListConvert = mapper.convertValue(productInsertList, new TypeReference<List<ProductVo>>(){});
		try {
			productMapper.insertProducts(productInsertListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.BAD_REQUEST, "DB_insert_error");
		}
	}
	
	@Transactional
	public void deleteProducts(List<ProductVo> productDeleteList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<ProductVo> productDeleteListConvert = mapper.convertValue(productDeleteList, new TypeReference<List<ProductVo>>(){});
		try {
			productMapper.deleteProducts(productDeleteListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.BAD_REQUEST, "DB_delete_error");
		}
	}
	
	@Transactional
	public void updateProducts(List<ProductVo> productUpdateList) throws DbCrudException {
        ObjectMapper mapper = new ObjectMapper();
		List<ProductVo> productUpdateListConvert = mapper.convertValue(productUpdateList, new TypeReference<List<ProductVo>>(){});
		try {
			productMapper.updateProducts(productUpdateListConvert);
		} catch (Exception e) {
			throw new DbCrudException(HttpStatus.BAD_REQUEST, "DB_update_error");
		}
	}

	public List<ProductVo> searchProductsByProductCode(String ProductCode) {
		List<ProductVo> result = productMapper.searchProductsByProductCode(ProductCode);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 제품코드로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<ProductVo> searchProductsByProductName(String ProductName) {
		List<ProductVo> result = productMapper.searchProductsByProductName(ProductName);
		if (result.isEmpty()) {
			throw new BadRequestException("해당 상품명으로 조회되는 데이터가 없습니다.");
		}
		return result;
	}
	
	public List<ProductVo> searchProductsByDate(Map<String, Object> startAndEndDate) {
		List<ProductVo> result = productMapper.searchProductsByDate(startAndEndDate);
		if (result.isEmpty()) {
			throw new BadRequestException("해당일자로 조회되는 데이터가 없습니다.");
		}
		return result;
	}

	public List<String> autoCompleteProductByProductCode(String input) {
		return productMapper.autoCompleteProductByProductCode(input);
	}

	public List<String> autoCompleteProductByProductName(String input) {
		return productMapper.autoCompleteProductByProductName(input);
	}
}
