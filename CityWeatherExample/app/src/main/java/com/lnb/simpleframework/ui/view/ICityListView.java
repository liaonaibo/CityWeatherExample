package com.lnb.simpleframework.ui.view;

import com.lnb.simpleframework.adapter.binder.BaseResource;

import java.util.List;

/**
 * 城市列表View接口
 * Created by liaonaibo on 16/2/15.
 */
public interface ICityListView {
    /**显示城市列表*/
    void showCityList(List<BaseResource> list);
    /**数据加载中*/
    void onDataLoading();
    /**没有数据*/
    void requestDataFailByNoData();
    /**请求失败*/
    void requestDataFailByNetswork();
}
