package com.hansung.web.vo;

import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResultVO {

	private boolean result;
	private Map<String, ArrayList<?>> data;
}
