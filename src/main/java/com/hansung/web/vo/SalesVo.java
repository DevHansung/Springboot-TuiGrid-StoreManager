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
public class SalesVo {
	private int salesSeq;
	private String productCode;
	private String productName;
	private String productType;
	private String productColor;
	private String productSize;
	private int productSellingPrice;
	private int salesQuantity;
	private int salesPrice;
	private String salesState;
	private String salesBranch;
	private Date salesDate;
}
