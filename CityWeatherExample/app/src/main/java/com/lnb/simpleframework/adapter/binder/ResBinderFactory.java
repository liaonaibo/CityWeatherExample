package com.lnb.simpleframework.adapter.binder;

import android.view.View;

import com.lnb.simpleframework.ui.CityItem;
import com.lnb.simpleframework.ui.CitySearchItem;

/**
 * 列表数据适配器工厂
 * <p/>
 * 根据传入枚举类型,返回对应Item对象
 */
public abstract class ResBinderFactory {

    public static ViewBinder<BaseResource> getBinder(BaseResource resource) {
        switch (resource.dataType) {
            case DT_List_City_Weather:
                // 返回城市天气列表Item
                return city_weather__list;
            case DT_List_City_Search:
                // 返回城市搜索列表Item
                return city_search_list;
            default:
                break;
        }
        return null;
    }

    /**
     * 城市列表item
     */
    private static ViewBinder<BaseResource> city_weather__list = new ViewBinder<BaseResource>() {

        @Override
        public View bind(ResListAdapter adapter, View view, BaseResource t) {
            CityItem item;
            if (view != null && view instanceof CityItem) {
                item = (CityItem) view;
            } else {
                item = new CityItem(adapter.mContext);
            }
            item.setData(t);
            item.setOnClickListeners(adapter.getOnClickListeners());
            return item;

        }
    };
    /**
     * 城市搜索
     */
    private static ViewBinder<BaseResource> city_search_list = new ViewBinder<BaseResource>() {

        @Override
        public View bind(ResListAdapter adapter, View view, BaseResource t) {
            CitySearchItem item;
            if (view != null && view instanceof CitySearchItem) {
                item = (CitySearchItem) view;
            } else {
                item = new CitySearchItem(adapter.mContext);
            }
            item.setData(t);
            item.setOnClickListeners(adapter.getOnClickListeners());
            return item;

        }
    };
}
