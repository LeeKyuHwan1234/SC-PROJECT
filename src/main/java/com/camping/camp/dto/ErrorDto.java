package com.camping.camp.dto;

import lombok.Data;

@Data
public class ErrorDto {
	private String enumber;
	private String etitle;
	private String econtent;
	private String ewriter;
	private String ewrite_date;
	private String estatus;
}
