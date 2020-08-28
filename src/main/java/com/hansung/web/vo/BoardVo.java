package com.hansung.web.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardVo {
	private int boardNum;
	private String boardTitle;
	private String boardText;
	private String boardWriter;
	private String boardBranch;
	private Date boardDate;
	private int boardCount;
}

