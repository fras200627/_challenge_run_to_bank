package com.challenge.accounts.jpa;

public class RequestFilterParams {

	private String key;
	private RequestFilterPredicatesEnum filterOperationEnum;
	private String arg1;
	private String arg2;
	   
	public RequestFilterParams(String key,
							   RequestFilterPredicatesEnum filterOperationEnum,
							   String arg1,
							   String arg2) {
		super();
		this.key = key;
		this.filterOperationEnum = filterOperationEnum;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public RequestFilterPredicatesEnum getFilterOperationEnum() {
		return filterOperationEnum;
	}
	public void setFilterOperationEnum(RequestFilterPredicatesEnum filterOperationEnum) {
		this.filterOperationEnum = filterOperationEnum;
	}
	public String getArg1() {
		return arg1;
	}
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	public String getArg2() {
		return arg2;
	}
	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}
}
