package com.lnb.simpleframework.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.Collection;

/**
 * universalimageloader 图片访问开源库的具体实现类,这个类仅在ImageLoaderController中调用
 * Created by liaonaibo on 16/2/14.
 */
public class UniversalImageLoader implements ImageLoaderInterface {

    /**
     * 图片请求
     */
    public ImageLoader mImageLoader;
    /**大图片请求配置*/
    private DisplayImageOptions optionsBig;
    /**小图片请求配置*/
    private DisplayImageOptions optionsIcon;

    public UniversalImageLoader(Context context) {
        mImageLoader = ImageLoader.getInstance(); // 实例化ImageLoader,每个布局里面都要实例化后再使用

        // File cacheDir =
        // StorageUtils.getOwnCacheDirectory(context,"imageloader/Cache");

        File cacheDir = context.getCacheDir();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3)
                        // 线程池数量
                .discCache(new UnlimitedDiscCache(cacheDir))
                        // 自定义缓存路径
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache()).build(); // 开始构建

        ImageLoader.getInstance().init(config); // 全局初始化此配置

        //TODO:here need something to do: add default image resource
//        optionsBig = getDisplayImageOptions(R.drawable.image_default);
//        optionsIcon = getDisplayImageOptions(R.drawable.icon_default);
    }

    /**
     * 图片请求配置
     *
     * @param default_drawable
     * @return
     */
    private DisplayImageOptions getDisplayImageOptions(int default_drawable) {

        // 显示图片的各种格式的设置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(default_drawable) // 没有图片资源时的默认图片
                .showImageOnFail(default_drawable) // 加载失败的图片
                .showImageOnLoading(default_drawable) // 加载图片是的图片
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true) // 启用内存缓存
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // 图片会缩放到目标大小
                .build();
        return options;
    }

    @Override
    public void displayIcon(String uri, ImageView imageView, final ImageLoadListener listener) {
        mImageLoader.displayImage(uri, imageView, optionsIcon, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                if (null != listener)
                    listener.onLoadingStarted(s, view);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                if (null != listener)
                    listener.onLoadingFailed(s, view);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (null != listener)
                    listener.onLoadingComplete(s, view, bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                if (null != listener)
                    listener.onLoadingCancelled(s, view);
            }
        });
    }

    @Override
    public void displayBigImage(String uri, ImageView imageView, final ImageLoadListener listener) {

        if (TextUtils.isEmpty(uri)) {
            return;
        }

        MemoryCacheAware<String, Bitmap> map = mImageLoader.getMemoryCache();
        boolean hasCache = false;
        if (null != map) {
            Collection<String> collection = map.keys();
            for (String str : collection) {
                if (str.contains(uri)) {
                    Bitmap b = map.get(str);
                    if (null == b) {
                        hasCache = false;
                    } else {
                        hasCache = true;
                        imageView.setImageBitmap(b);
                    }

                }
            }
        }
        if (!hasCache) {
            mImageLoader.displayImage(uri, imageView, optionsBig, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    if (null != listener)
                        listener.onLoadingStarted(s, view);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    if (null != listener)
                        listener.onLoadingFailed(s, view);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (null != listener)
                        listener.onLoadingComplete(s, view, bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    if (null != listener)
                        listener.onLoadingCancelled(s, view);
                }
            });
        }
    }
}
