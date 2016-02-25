package com.lnb.simpleframework.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.lnb.simpleframework.adapter.binder.DataType;
import com.lnb.simpleframework.model.City;
import com.lnb.simpleframework.model.WeatherDetail;
import com.lnb.simpleframework.network.NetworkController;
import com.lnb.simpleframework.network.NetworkRequestListener;
import com.lnb.simpleframework.ui.view.IWeatherDetailView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 天气详情UI主持者
 * 作为View与Model交互的中间纽带，处理与用户交互的负责逻辑
 * Created by liaonaibo on 16/2/15.
 */
public class WeatherDetailPresenter {
    private Context mContext;
    private IWeatherDetailView mIWeatherDetailView;
    private boolean isLoading;

    public WeatherDetailPresenter(Context context, IWeatherDetailView iWeatherDetailView) {
        this.mContext = context;
        this.mIWeatherDetailView = iWeatherDetailView;
    }

    public void loadData() {
        if (isLoading) {
            return;
        }

        City city = getData();
        if (null == city)
            return;
        String title = city.city + ((!TextUtils.isEmpty(city.district)) ? "(" + city.district + ")" : "");
        mIWeatherDetailView.setTitle(title);
        isLoading = true;
        //通知UI数据加载中
        mIWeatherDetailView.onDataLoading();

        Map<String, String> params = new HashMap<String, String>();
        if (DataType.DT_List_City_Search == city.dataType) {
            params.put("cityname", "" + city.city);
        } else {
            params.put("cityname", "" + city.id);
        }
        params.put("format", "2");

        NetworkController.getInstance(mContext).requestCityWeatherDetailByCityID(params, new NetworkRequestListener() {
            @Override
            public void response(String response) {
                isLoading = false;

                if (!TextUtils.isEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int resultcode = jsonObject.optInt("resultcode");
                        if (200 != resultcode) {
                            Toast.makeText(mContext, jsonObject.optString("reason"), Toast.LENGTH_SHORT).show();
                            mIWeatherDetailView.requestDataFailByNoData();
                        } else {
                            WeatherDetail weatherDetail = WeatherDetail.parser(response);
                            if (null != weatherDetail) {
                                //显示数据到UI上
                                mIWeatherDetailView.showDetail(weatherDetail);
                            } else {
                                //没有数据了
                                mIWeatherDetailView.requestDataFailByNoData();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void error(int errorCode, String errorLog) {
                isLoading = false;
                //通知UI网络异常
                mIWeatherDetailView.requestDataFailByNetswork();
            }
        });
    }

    private City getData() {

        Intent intent = mIWeatherDetailView.getDataIntent();

        if (null == intent) {
            return null;
        }
        Bundle bundle = intent.getExtras();

        if (null == bundle) {
            return null;
        }
        Object object = bundle.getSerializable("data");
        if (null != object && object instanceof City) {
            return (City) object;
        }
        return null;
    }
}
