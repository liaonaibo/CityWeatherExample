package com.lnb.simpleframework.ui.view;

import android.content.Intent;

import com.lnb.simpleframework.model.WeatherDetail;

/**
 * 天气详情View接口
 * Created by liaonaibo on 16/2/15.
 */
public interface IWeatherDetailView {
    /**显示详情数据*/
    void showDetail(WeatherDetail weatherDetail);
    /**数据加载中*/
    void onDataLoading();
    /**没有数据*/
    void requestDataFailByNoData();
    /**请求失败*/
    void requestDataFailByNetswork();
    /**城市ID*/
//    String getCityID();
//    /**获取城市名称*/
//    String getCityName();
    /**获取当前Activity的Intent*/
    Intent getDataIntent();
    /**设置标题*/
    void setTitle(String title);
}
