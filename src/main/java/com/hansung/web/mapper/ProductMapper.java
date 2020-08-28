package com.hansung.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hansung.web.vo.ProductVo;

@Repository
public interface ProductMapper {
	ArrayList<ProductVo> findProducts();

	void insertProducts(List<ProductVo> list);

	void deleteProducts(List<ProductVo> list);

	void updateProducts(List<ProductVo> list);

	List<ProductVo> searchProductsByProductCode(String ProductCode);

	List<ProductVo> searchProductsByProductName(String ProductName);

	List<ProductVo> searchProductsByDate(Map<String, Object> startAndEndDate);

	List<String> autoCompleteProductByProductCode(String input);

	List<String> autoCompleteProductByProductName(String input);
}
