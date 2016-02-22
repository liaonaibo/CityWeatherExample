package com.lnb.simpleframework.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lnb.simpleframework.adapter.binder.BaseResource;
import com.lnb.simpleframework.adapter.binder.DataType;

import org.json.JSONObject;

import java.util.List;

/**
 * 城市对象
 * Created by liaonaibo on 16/2/14.
 */
public class City extends BaseResource{
    /**城市ID*/
    public int id;
    /**省份名称*/
    public String province;
    /**城市*/
    public String city;
    /**城市/区名称*/
    public String district;

    public City(){
        this.dataType = DataType.DT_List_City_Weather;
    }

    public static List<City> parserList(String json){
        try {
            JSONObject obj = new JSONObject(json);
            String data = obj.optString("result");
            if (!TextUtils.isEmpty(data)) {
                List<City> list = new Gson().fromJson(data,
                        new TypeToken<List<City>>() {
                        }.getType());
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
