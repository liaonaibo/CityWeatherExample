package com.lnb.simpleframework.adapter.binder;

/**
 * 数据类型枚举
 */
public enum DataType {

	DT_List_City_Weather("城市天气列表", 1001),
	DT_List_City_Search("城市搜索", 1002);

	private String desc;

	private int code;

	DataType(String desc, int code) {
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public int getCode() {
		return code;
	}

}
