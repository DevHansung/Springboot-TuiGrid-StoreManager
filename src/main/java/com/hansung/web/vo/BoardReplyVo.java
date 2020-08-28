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
public class BoardReplyVo {
	private int replyNum;
	private String replyTitle;
	private String replyText;
	private String replyWriter;
	private Date replyDate;
}

