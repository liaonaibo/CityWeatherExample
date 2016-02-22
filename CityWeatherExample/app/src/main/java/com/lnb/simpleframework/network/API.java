package com.lnb.simpleframework.network;

/**
 * 网络请求接口地址
 */
public class API {
	/**正式地址*/
    public static String base_url = "http://v.juhe.cn/weather/";
	/**线下地址*/
	//public static String base_url = "http://192.168.1.1:9181";
	/** 获取天气列表 */
	public static String getWeatherList = base_url + "citys";
	/** 获取天气详情 */
	public static String getWeatherDetailByIp = base_url + "ip";
	/** 获取天气详情 */
	public static String getWeatherDetailByCityIndex = base_url + "index";
}
