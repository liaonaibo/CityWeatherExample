package com.lnb.simpleframework.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lnb.simpleframework.adapter.binder.BaseResource;

import org.json.JSONObject;

import java.util.List;

/**
 * 天气详情对象
 * Created by liaonaibo on 16/2/14.
 */
public class WeatherDetail extends BaseResource{

    /**今日天气*/
    public Today today;
    /**未来几天天气*/
    public List<Future> future;

    /**
     * 今日天气
     */
    public class Today{
        /**城市 : "天津"*/
        public String city;
        /**更新时间: "2014年03月21日"*/
        public String date_y;
         /**星期几: "星期五"*/
        public String week;
        /**温度 :  8℃~20℃ */
        public String temperature;
        /**今日天气 : "晴转霾"*/
        public String weather;
        /**穿衣指数*/
        public String dressing_index;
        /**穿衣建议*/
        public String dressing_advice;
        /**紫外线强度*/
        public String uv_index;
        /**舒适度指数*/
        public String comfort_index;
    }

    /**
     * 未来天气
     */
    public class Future{
        /**星期几: "星期五"*/
        public String week;
        /**温度 :  8℃~20℃ */
        public String temperature;
        /**今日天气 : "晴转霾"*/
        public String weather;
        /**时间: "20140804"*/
        public String date;
    }

    public static WeatherDetail parser(String json){
        try {
            JSONObject obj = new JSONObject(json);
            String data = obj.optString("result");
            if (!TextUtils.isEmpty(data)) {
                return new Gson().fromJson(data,WeatherDetail.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
