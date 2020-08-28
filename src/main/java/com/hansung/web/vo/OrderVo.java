package com.hansung.web.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderVo {
	private int orderSeq;
	private String productCode;
	private String productName;
	private String productType;
	private String productColor;
	private String productSize;
	private int productSellingPrice;
	private int productPurchasePrice;
	private int orderQuantity;
	private int orderPrice;
	private String orderState;
	private String orderBranch;
	private Date orderDate;
}
