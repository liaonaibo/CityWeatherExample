package com.lnb.simpleframework.network;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.lnb.simpleframework.tools.Utility;

/**
 * 网络工具类
 *
 * 处理公共参数拼装、数据加解密等
 */
public class NetworkUtility {

    public static String getUrlParams(Context context,
                                      Map<String, String> params) {
        return buildParamsEncryptString(context,
                buildCommonParam(context, params));
    }

    /**
     * 构建请求参数，加密等
     *
     * @param rt
     * @param addParam
     * @return
     */
    public static String buildParamsEncryptString(Context context,
                                                  Map<String, String> paramMap) {

        String paramMapString = Utility.getHttpParamsStr(paramMap);
        //TODO:这里处理加密

        return paramMapString;
    }

    /**
     * 解密
     *
     * @param s
     * @return
     */
    public static String decodResponseData(Context context, String s) {
        //String decodedMsg = Utility.AESDecrpyt( AES_KEY, s);

        return s;
    }

    /**
     * 根据协议 组建不需要由用户提供的 req 参数
     */
    public static HashMap<String, String> buildCommonParam(Context context,
                                                           Map<String, String> addParam) {
        HashMap<String, String> paramsMap = new HashMap<String, String>();

        // 灵活控制参数
        if (addParam != null) {
            paramsMap.putAll(addParam);
        }

        // 聚合数据key
        paramsMap.put("key", "4104d2bf86449df8ebac791510732092");

        return paramsMap;
    }

}
