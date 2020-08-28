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
public class ProductVo {
	private int productSeq;
	private String productCode;
	private String productName;
	private String productType;
	private String productColor;
	private String productSize;
	private int productPurchasePrice;
	private int productSellingPrice;
	private Date ProductDate;
}
