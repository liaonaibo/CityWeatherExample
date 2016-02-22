package com.lnb.simpleframework.network;

import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import cn.ykt.networktool.NetworkError;
import cn.ykt.networktool.ParseError;
import cn.ykt.networktool.Request;
import cn.ykt.networktool.RequestQueue;
import cn.ykt.networktool.Response;
import cn.ykt.networktool.Response.Listener;
import cn.ykt.networktool.ServerError;
import cn.ykt.networktool.TimeoutError;
import cn.ykt.networktool.VolleyError;
import cn.ykt.networktool.toolbox.StringRequest;
import cn.ykt.networktool.toolbox.Volley;

/**
 * Google 的Volley网络访问框架
 *
 */
public class VolleyHttpClient implements NetworkInterface {
	/** 请求队列 */
	private RequestQueue queue;
	private Context mContext;

	public VolleyHttpClient(Context context) {
		queue = Volley.newRequestQueue(context);
		this.mContext = context;
	}

	/**
	 * put the request into RequestQueue
	 * 
	 * @param
	 */
	private void addQueue(final String url, final NetworkRequestListener callBack) {
		StringRequest request = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {

						try {
							//TODO:这里统一设置解密
//							JSONObject obj = new JSONObject(arg0);
//							String data = obj.optString("msg");
//							data = NetworkUtility.decodResponseData(mContext, data);
							if (null != callBack) {
								callBack.response(arg0);
							}
						} catch (Exception e) {
							if (null != callBack) {
								callBack.error(0, e.getMessage());
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (null != callBack) {
							callBack.error(0, parserNetError(url, arg0));
						}
					}
				});
		queue.add(request);
	}

	private String parserNetError(String url, VolleyError volleyerror) {

		if (volleyerror instanceof ParseError) {
			ParseError error = (ParseError) volleyerror;
			if (!TextUtils.isEmpty(error.getMessage())) {
				return error.getMessage();
			} else {
				return "获取数据失败";
			}
		} else if (volleyerror instanceof ServerError) {
			return "服务器错误";
		} else if (volleyerror instanceof NetworkError) {
			return "网络不给力";
		} else if (volleyerror instanceof TimeoutError) {
			return "连接超时";
		} else {
			return "连接异常";
		}
	}

	@Override
	public void requestCityList(Map<String, String> params, NetworkRequestListener callBack) {
		String url=API.getWeatherList+"?"+NetworkUtility.getUrlParams(mContext, params);
		addQueue(url, callBack);
	}

	@Override
	public void requestCityWeatherDetailByIP(Map<String, String> params, NetworkRequestListener callBack) {
		String url=API.getWeatherDetailByIp+"?"+NetworkUtility.getUrlParams(mContext, params);
		addQueue(url, callBack);
	}

	@Override
	public void requestCityWeatherDetailByCityID(Map<String, String> params, NetworkRequestListener callBack) {
		String url=API.getWeatherDetailByCityIndex+"?"+NetworkUtility.getUrlParams(mContext, params);
		addQueue(url, callBack);
	}
}
