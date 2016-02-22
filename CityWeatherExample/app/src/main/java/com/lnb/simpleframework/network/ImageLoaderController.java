package com.lnb.simpleframework.network;

import android.content.Context;
import android.widget.ImageView;

/**
 * 图片请求入口访问控制器
 * 该类作为应用层所有图片请求的入口类
 */
public class ImageLoaderController {

    private static ImageLoaderController imageLoaderController;
    private ImageLoaderInterface mImageLoaderInterface;

    private ImageLoaderController(Context context) {
        mImageLoaderInterface = new UniversalImageLoader(context);
    }

    public static synchronized ImageLoaderController getInstance(Context context) {
        if (null == imageLoaderController) {
            imageLoaderController = new ImageLoaderController(
                    context.getApplicationContext());
        }
        return imageLoaderController;
    }

    /**
     * 加载小图标
     *
     * @param uri       图片地址
     * @param imageView ImageView
     * @param listener  加载监听
     */
    public void displayIcon(String uri, ImageView imageView,
                            ImageLoadListener listener) {
        mImageLoaderInterface.displayIcon(uri, imageView, listener);
    }

    /**
     * 加载大图片
     *
     * @param uri       图片地址
     * @param imageView ImageView
     * @param listener  加载监听
     */
    public void displayBigImage(String uri, ImageView imageView,
                                ImageLoadListener listener) {
        mImageLoaderInterface.displayBigImage(uri, imageView, listener);
    }

}
