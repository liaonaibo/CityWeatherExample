package com.lnb.simpleframework.network;

import android.widget.ImageView;

/**
 * 图片访问接口
 * 用于规范图片请求派生类的请求函数
 */
public interface ImageLoaderInterface {
	/**
	 * 加载小图片
	 *
	 * @param uri
	 *            图片地址
	 * @param imageView
	 *            ImageView
	 * @param listener
	 *            加载监听
	 */
	public void displayIcon(String uri, ImageView imageView,
							ImageLoadListener listener);

	/**
	 * 加载大图片
	 *
	 * @param uri
	 *            图片地址
	 * @param imageView
	 *            ImageView
	 * @param listener
	 *            加载监听
	 */
	public void displayBigImage(String uri, ImageView imageView,
								ImageLoadListener listener) ;
}
