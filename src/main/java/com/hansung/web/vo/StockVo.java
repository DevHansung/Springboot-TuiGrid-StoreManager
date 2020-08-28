package com.hansung.web.vo;

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
public class StockVo {
	private int stockSeq;
	private String productCode;
	private String productName;
	private String productType;
	private String productColor;
	private String productSize;
	private int orderTotalQuantity;
	private int salesTotalQuantity;
	private int stockTotalQuantity;
	private int salesRate;
	private String stockBranch;

}
