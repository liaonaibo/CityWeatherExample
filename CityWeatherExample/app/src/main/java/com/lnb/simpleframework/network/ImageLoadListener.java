package com.lnb.simpleframework.network;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 图片加载回调
 */
public interface ImageLoadListener {

	void onLoadingStarted(String string, View view);

	void onLoadingFailed(String string, View view);

	void onLoadingComplete(String string, View view, Bitmap bitmap);

	void onLoadingCancelled(String s, View view);
}
