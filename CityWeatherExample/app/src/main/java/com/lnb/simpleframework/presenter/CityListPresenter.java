package com.lnb.simpleframework.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.lnb.simpleframework.adapter.binder.DataType;
import com.lnb.simpleframework.model.City;
import com.lnb.simpleframework.ui.WeatherDetailActivity;
import com.lnb.simpleframework.adapter.binder.BaseResource;
import com.lnb.simpleframework.network.NetworkController;
import com.lnb.simpleframework.network.NetworkRequestListener;
import com.lnb.simpleframework.ui.view.ICityListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市列表UI主持者
 * 作为View与Model交互的中间纽带，处理与用户交互的负责逻辑
 * Created by liaonaibo on 16/2/15.
 */
public class CityListPresenter {
    private Context mContext;
    private ICityListView mICityListView;
    private boolean isLoading;
    private List<BaseResource> mData;
    public CityListPresenter(Context context,ICityListView iCityListView){
        this.mContext = context;
        this.mICityListView = iCityListView;
        this.mData = new ArrayList<BaseResource>();
    }

    public void getData(){
        if (isLoading){
            return;
        }
        isLoading = true;
        //通知UI数据加载中
        mICityListView.onDataLoading();
        NetworkController.getInstance(mContext).requestCityList(null, new NetworkRequestListener() {
            @Override
            public void response(String response) {
                isLoading = false;

                if(!TextUtils.isEmpty(response)){
                    List<City> list = City.parserList(response);
                    if(null != list && list.size() > 0){
                        mData.addAll(list);
                        //显示数据到UI上
                        mICityListView.showCityList(mData);
                    }else{
                        //没有数据了
                        mICityListView.requestDataFailByNoData();
                    }
                }

            }

            @Override
            public void error(int errorCode, String errorLog) {
                isLoading = false;
                //通知UI网络异常
                mICityListView.requestDataFailByNetswork();
            }
        });
    }

    /**
     * 跳转到天气详情界面
     * @param view
     */
    public void jumpToWeatherDetailActivity(View view) {
        Object object = view.getTag();

        if(null != object){
            City city = (City) object;
            WeatherDetailActivity.start(mContext, city);
        }
    }

    /**
     * 获取点击事件数组
     * @return
     */
    public View.OnClickListener[] getOnClickListeners() {
        View.OnClickListener[]  listeners = new View.OnClickListener[1];
        listeners[0] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToWeatherDetailActivity(v);
            }
        };
        return listeners;
    }

    /**
     * 搜索城市
     * @param cityName
     */
    public void searchCity(String cityName) {
        City city = new City();
        city.dataType = DataType.DT_List_City_Search;
        city.city = cityName;
        WeatherDetailActivity.start(mContext, city);
    }
}
