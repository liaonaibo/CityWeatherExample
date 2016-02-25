package com.lnb.simpleframework.ui.adapter;

import android.app.Activity;
import android.view.View;

import com.lnb.simpleframework.adapter.binder.BaseResource;
import com.lnb.simpleframework.adapter.binder.ResListAdapter;
import com.lnb.simpleframework.presenter.CityListPresenter;

import java.util.List;

/**
 * 城市数据列表数据适配器
 * 提供跳转
 * Created by liaonaibo on 16/2/15.
 */
public class CityAdapter extends ResListAdapter {
    /**城市列表主持者*/
    private CityListPresenter mCityListPresenter;

    public CityAdapter(Activity context, List<BaseResource> resList) {
        super(context, resList);
    }

    public CityAdapter(Activity context, CityListPresenter cityListPresenter,List<BaseResource> resList) {
        this(context, resList);
        mCityListPresenter = cityListPresenter;
    }

    @Override
    public View.OnClickListener[] getOnClickListeners() {
        return mCityListPresenter.getOnClickListeners();
    }

}
