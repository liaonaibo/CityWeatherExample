package com.lnb.simpleframework.adapter.binder;

import android.view.View;

/**
 * 数据工厂泛型接口类,主要实现返回不同类型Item对象
 * @param <T>
 */
public interface ViewBinder<T> {

    View bind(ResListAdapter adapter, View view, T t);
}
