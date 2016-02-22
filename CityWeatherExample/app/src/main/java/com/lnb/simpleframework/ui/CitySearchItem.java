package com.lnb.simpleframework.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lnb.simpleframework.R;
import com.lnb.simpleframework.adapter.binder.BaseItem;
import com.lnb.simpleframework.adapter.binder.BaseResource;

/**
 * 搜索item
 * Created by liaonaibo on 16/2/16.
 */
public class CitySearchItem extends BaseItem{

    private EditText tv_city_name;

    public CitySearchItem(Context context) {
        super(context);
        init();
    }

    @Override
    protected void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_search, null);
        tv_city_name = (EditText) view.findViewById(R.id.item_search_et_search);
        addView(view);
    }
    /**设置数据*/
    public void setData(BaseResource baseResource){
        if (null != baseResource){
            setTag(baseResource);
        }
    }
    /**设置点击事件*/
    public void setOnClickListeners(OnClickListener[] listeners){

    }
}
