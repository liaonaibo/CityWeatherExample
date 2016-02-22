package com.lnb.simpleframework.network;

/**
 * 网络访问回调,提供其他模块使用
 * @author liaonaibo@domob.cn
 * @Data 2014年11月19日 上午11:25:01
 */
public interface NetworkRequestListener {
	public void response(String response);

	public void error(int errorCode, String errorLog);
}
