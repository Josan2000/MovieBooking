package com.example.demo.model;


import lombok.Data;

@Data
public class Error {
	private String erCode;
	private String erMsg;
	public String getErCode() {
		return erCode;
	}
	public void setErCode(String erCode) {
		this.erCode = erCode;
	}
	public String getErMsg() {
		return erMsg;
	}
	public void setErMsg(String erMsg) {
		this.erMsg = erMsg;
	}
	
	

}