package com.lnb.simpleframework.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lnb.simpleframework.R;
import com.lnb.simpleframework.model.City;

import com.lnb.simpleframework.adapter.binder.BaseItem;
import com.lnb.simpleframework.adapter.binder.BaseResource;

/**
 * 城市列表item
 * Created by liaonaibo on 16/2/16.
 */
public class CityItem extends BaseItem{

    private TextView tv_city_name;

    public CityItem(Context context) {
        super(context);
        init();
    }

    @Override
    protected void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_city, null);
        tv_city_name = (TextView) view.findViewById(R.id.item_city_tv_cityname);
        addView(view);
    }
    /**设置数据*/
    public void setData(BaseResource baseResource){
        if (baseResource instanceof City){
            City city = (City) baseResource;
            tv_city_name.setText(city.city +"(" + city.district+")");
            setTag(baseResource);
        }
    }
    /**设置点击事件*/
    public void setOnClickListeners(OnClickListener[] listeners){
        this.setOnClickListener(listeners[0]);
    };
}
