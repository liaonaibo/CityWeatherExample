package com.lnb.simpleframework.network;

import java.util.Map;

/**
 * 网络访问接口
 *
 */
public interface NetworkInterface {
	/**
	 * 请求城市列表
	 * 
	 * @param params
	 *            Map<String, String>
	 * 
	 * @param callBack
	 *            {@link NetworkRequestListener}
	 */
	public void requestCityList(Map<String, String> params, NetworkRequestListener callBack);
	/**
	 * 通过IP请求天气详情
	 *
	 * @param params
	 *            Map<String, String>
	 *
	 * @param callBack
	 *            {@link NetworkRequestListener}
	 */
	public void requestCityWeatherDetailByIP(Map<String, String> params, NetworkRequestListener callBack);
	/**
	 * 通过城市ID请求天气详情
	 *
	 * @param params
	 *            Map<String, String>
	 *
	 * @param callBack
	 *            {@link NetworkRequestListener}
	 */
	public void requestCityWeatherDetailByCityID(Map<String, String> params, NetworkRequestListener callBack);
}
