package com.lnb.simpleframework.adapter.binder;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * 列表类型的UI的Item的基类,提供初始化、设置点击、设置数据方法
 */
public abstract class BaseItem extends LinearLayout {
	public BaseItem(Context context) {
		super(context);
	}

	/**初始化*/
	protected abstract void init() ;
	/**设置点击事件*/
	public void setOnClickListeners(OnClickListener[] listeners){};
	/**设置数据*/
	public void setData(BaseResource baseResource){}
}
