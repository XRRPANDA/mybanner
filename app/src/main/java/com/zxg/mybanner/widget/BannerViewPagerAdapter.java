package com.zxg.mybanner.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 自定义viewpager的适配器
 *
 * @author Administrator
 */
public class BannerViewPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private Context mContext;
    private BannerOnClickListener bannerOnClickListener;

    public void setBannerOnClickListener(BannerOnClickListener bannerOnClickListener) {
        this.bannerOnClickListener = bannerOnClickListener;
    }

    public BannerViewPagerAdapter(List<ImageView> views, Context context) {
        this.imageViews = views;
//		if(imageViews.size() < 3){
//			imageViews.addAll(views);
//		}
        this.mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
//		if(imageViews.size() == 0){
//			ImageView mImageView = new ImageView(mContext);
//			return mImageView;
//		}
        try {
            ImageView view = imageViews.get(position % imageViews.size());
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != bannerOnClickListener) {
                        Log.e("进入Adapter点击=", position + "");
                        bannerOnClickListener.imageClick(position % imageViews.size());
                    }
                    // 点击viewPager中的图片跳转页面
                    // intent = new Intent();
                    // HomePagerImage item = (HomePagerImage)
                    // mNewsImages.get(position % mNewsImages.size());
                    // if (item.advertType == 0) {
                    // intent.setClass(mContext, TopicListActivity.class);
                    // int folderId = item.target;
                    // HomeTopic topic = new HomeTopic(1, folderId, null, null,
                    // 0, null, null, null, 0, false);
                    // Log.d(TAG, "===========topic============  " +
                    // topic.toString());
                    // intent.putExtra("topic", topic);
                    // } else if (item.advertType == 1) {
                    // intent.setClass(mContext, ArticleActivity.class);
                    // PhoneArticle article = new PhoneArticle(item.target, 0,
                    // item.advertContent, item.docBrief, null, 0, false);
                    // intent.putExtra("article", article);
                    // intent.putExtra("from", "首页");
                    // } else {
                    // intent.setClass(mContext, WebViewActivity.class);
                    // intent.putExtra("url", item.advertContent);
                    // }
                    // mContext.startActivity(intent);
                }
            });
            ((ViewPager) container).addView(view, 0);
        } catch (Exception e) {

        }
        return imageViews.get(position % imageViews.size());
    }

    @Override
    public int getCount() {

        return Integer.MAX_VALUE;// 设置成最大值以便循环滑动
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
