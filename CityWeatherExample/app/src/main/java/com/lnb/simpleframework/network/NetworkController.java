package com.lnb.simpleframework.network;

import java.util.Map;

import android.content.Context;

/**
 * 网络访问控制器
 * 
 * @param <T>
 */
public class NetworkController implements NetworkInterface {

	private static NetworkController mVolleyContrller;

	private NetworkInterface mNetworkInterface;

	private NetworkController(Context context) {
		// 多态方便以后更换网络框架
		mNetworkInterface = new VolleyHttpClient(context);
	}

	public static synchronized NetworkController getInstance(Context context) {
		if (null == mVolleyContrller) {
			// 单例,持有应用级别上下文，可以防止持有activity的上下文导致内存泄露
			mVolleyContrller = new NetworkController(
					context.getApplicationContext());
		}
		return mVolleyContrller;
	}


	@Override
	public void requestCityList(Map<String, String> params, NetworkRequestListener callBack) {
		mNetworkInterface.requestCityList(params, callBack);
	}

	@Override
	public void requestCityWeatherDetailByIP(Map<String, String> params, NetworkRequestListener callBack) {
		mNetworkInterface.requestCityWeatherDetailByIP(params, callBack);
	}

	@Override
	public void requestCityWeatherDetailByCityID(Map<String, String> params, NetworkRequestListener callBack) {
		mNetworkInterface.requestCityWeatherDetailByCityID(params,callBack);
	}

}
