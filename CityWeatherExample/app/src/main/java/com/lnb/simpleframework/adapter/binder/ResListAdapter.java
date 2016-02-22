package com.lnb.simpleframework.adapter.binder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 数据适配器基类
 */
public abstract class ResListAdapter extends BaseAdapter {

	protected LayoutInflater mInflater;

	public Activity mContext;

	public List<BaseResource> data;
	
	private final HashMap<View, BaseResource> cacheListItems = new LinkedHashMap<View, BaseResource>(
			20, 0.75f, false) {

		private static final long serialVersionUID = 0x11102;

		@Override
		protected boolean removeEldestEntry(
				Entry<View, BaseResource> eldest) {
			return size() > 20;
		}
	};

	public ResListAdapter(Activity context, List<BaseResource> resList) {
		this.data = resList;
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	/**
	 * 点击事件数组
	 * 
	 * @return
	 */
	public abstract View.OnClickListener[] getOnClickListeners();

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 获取对象在当前集合的下标
	 * 
	 * @param resource
	 *            -> This object much be extend by {@link BaseResource}
	 * @return
	 */
	public int getPosition(BaseResource resource) {
		return data.indexOf(resource);
	}

	public void binderView(BaseResource item) {
		for (Entry<View, BaseResource> entry : cacheListItems.entrySet()) {
			BaseResource cacheItem = entry.getValue();
			if (cacheItem.getClass() != item.getClass()) {
				break;
			}
			if (cacheItem.equals(item)) {
				// update mList item : if need update other params..
				ViewBinder<BaseResource> binder = ResBinderFactory
						.getBinder(cacheItem);
				binder.bind(this, entry.getKey(), cacheItem);
			}
		}
	}

	public View getBinderView(BaseResource item) {
		for (Entry<View, BaseResource> entry : cacheListItems.entrySet()) {
			BaseResource cacheItem = entry.getValue();
			if (cacheItem.getClass() != item.getClass()) {
				break;
			}
			if (cacheItem.equals(item)) {
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseResource entity = (BaseResource) data.get(position);
		ViewBinder<BaseResource> binder = ResBinderFactory.getBinder(entity);
		convertView = binder.bind(this, convertView, entity);
		cacheListItems.put(convertView, entity);
		
		return convertView;
	}

}
