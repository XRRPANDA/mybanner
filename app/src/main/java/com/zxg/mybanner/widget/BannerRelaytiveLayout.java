package com.zxg.mybanner.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zxg.mybanner.R;
import com.zxg.mybanner.bean.AdvicesBackBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * banner轮播图
 */
public class BannerRelaytiveLayout extends RelativeLayout {
    private String TAG = "MyRelaytiveLayout";
    private View vp;
    private RelativeLayout pagerguide;
    private BannerViewPager viewPager;
    private LinearLayout viewIndicator;
    /**
     * 每张图的标题
     */
    private TextView title;
    /**
     * 默认title的size，单位dp
     */
    private final float default_title_text_size = 16;
    /**
     * 实际值
     */
    private float real_title_text_size ;
    /**
     * 默认title的文字颜色,单位sp
     */
    private final int  default_title_text_color = Color.parseColor("#000000");
    /**
     * 实际值
     */
    private int  real_title_text_color;
    /**
     * 选中小圆点
     */
    private ImageView dot;
    /**
     * 圆点width，默认5dp
     */
    private final int default_dot_width = 5;
    private  float real_dot_width;
    private List<ImageView> mNewsDots;
    private List<ImageView> views;
    private BannerViewPagerAdapter myAdapter;
    private Context mContext;
    /**
     * true为暂停状态，当手动移动图片的时候设置为true
     */
    private boolean isPause;
    /**
     * 刚刚切换完图片
     */
    private boolean isUp;
    /**
     * 是否停止轮播，默认false,退出设置为true
     */
    private boolean isStop = false;
    private List<AdvicesBackBean.ContentBean> mNewsImages;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private BannerOnClickListener bannerOnClickListener;

    private String url1 = "assets://huabo1_20170724.png";
    private String url2 = "assets://huabo2_20170724.png";
//    private String url3 = "assets://huabo1_20170724.png";
    /**
     * 重新设置过轮播图片的时间
     */
    private long resetTime;
    private Thread thread = null;
    /**
     * 图片切换
     */
    private final int handleChange = 10001;
    /**
     * 图片切换的时间频率
     */
    private int intervalTime = 5000;

    public void setBannerOnClickListener(BannerOnClickListener bannerOnClickListener) {
        this.bannerOnClickListener = bannerOnClickListener;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case handleChange:
                    int totalcount = views.size();
                    int currentItem = viewPager.getCurrentItem();
                    int toItem = currentItem + 1 == totalcount ? 0 : currentItem + 1;

                    viewPager.setCurrentItem(toItem, true);

                    // // 每五秒钟发送一个message，用于切换viewPager中的图片，第二种方法
                    // this.sendEmptyMessageDelayed(1, intervalTime);

            }
        }
    };

    /**
     * 实例化图片加载实例
     * @param context
     */
    private void initImageLoader(Context context) {
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration createDefault = ImageLoaderConfiguration.createDefault(context);
        imageLoader.init(createDefault);


        options = new DisplayImageOptions.Builder().
                //设置图片在下载期间显示的图片
                        showImageOnLoading(R.mipmap.banner_loading)
                //设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.banner_empty)
                //设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.banner_fail)
                //缓存
                .cacheInMemory(true)
                //硬盘缓存
                .cacheOnDisk(true).build();
    }
    public BannerRelaytiveLayout(Context context) {
        this(context, null);
    }

    public BannerRelaytiveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerRelaytiveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        real_title_text_size = (float) sp2px(context, default_title_text_size);
        real_dot_width = (float) dip2px(context, default_dot_width);
        real_title_text_color = default_title_text_color;

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BannerLayoutView, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initWithContext(context);
    }
    /**
     * 获取xml中设置的属性值
     *
     * @param attributes
     */
    protected void initByAttributes(TypedArray attributes) {
        real_title_text_color = attributes.getColor(R.styleable.BannerLayoutView_banner_text_color, default_title_text_color);
        real_title_text_size = attributes.getDimension(R.styleable.BannerLayoutView_banner_text_size, real_title_text_size);
        real_dot_width = attributes.getDimension(R.styleable.BannerLayoutView_banner_dot_width, real_dot_width);
    }

    /**
     *  初始化数据，占位轮播，以免出现空白
     * @param context
     */
    private void initWithContext(Context context) {
        initImageLoader(context);
        vp = View.inflate(context, R.layout.index_photos, null);
        pagerguide = (RelativeLayout) vp.findViewById(R.id.pagerguide);
        viewPager = (BannerViewPager) vp.findViewById(R.id.vp_pagerxlist);
        viewIndicator = (LinearLayout) vp.findViewById(R.id.vpindicator);
        title = (TextView) vp.findViewById(R.id.img_title);
        title.setTextColor(real_title_text_color);
        title.setTextSize(real_title_text_size);
        views = new ArrayList<ImageView>();
        mNewsDots = new ArrayList<ImageView>();
        viewPager.setCurrentItem(views.size() * 100);
        viewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isPause = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isUp = true;
                        isPause = false;
                        break;
                    default:
                        isPause = false;
                        break;
                }
                return false;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                setImageBackground(arg0 % views.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            private void setImageBackground(int selectItems) {
                for (int i = 0; i < mNewsImages.size(); i++) {
                    if (i == selectItems) {
                        mNewsDots.get(i).setBackgroundResource(R.mipmap.banner_point_selected);
                        title.setText(mNewsImages.get(i).getName());
                    } else {
                        mNewsDots.get(i).setBackgroundResource(R.mipmap.banner_point_unselect);
                    }
                }
            }
        });
        this.addView(vp);
        List<AdvicesBackBean.ContentBean> mlist = new ArrayList<>();
        AdvicesBackBean.ContentBean mContentBean = new AdvicesBackBean.ContentBean();
        mContentBean.setName("");
        mContentBean.setUrl(url1);
        mlist.add(mContentBean);
        AdvicesBackBean.ContentBean mContentBean2 = new AdvicesBackBean.ContentBean();
        mContentBean2.setName("");
        mContentBean2.setUrl(url2);
        mlist.add(mContentBean2);
        //当图片小于等于2次的时候，手动滑动会出现空白，自动增加以去除bug
        while (mlist.size() <= 2){
            mlist.addAll(mlist);
        }
        try {
            updateImages(mlist);
        } catch (Exception e) {
            Log.d("轮播显示错误=",e.toString());
        }
    }
    /**
     * 更新图片
     * @param newsImages
     * @throws Exception
     */
    public void updateImages(List<AdvicesBackBean.ContentBean> newsImages) throws Exception {
        this.mNewsImages = newsImages;
        Log.d(TAG, "ImageViews请空前----------->" + views.size());
        views.clear();
        mNewsDots.clear();
        viewIndicator.removeAllViews();

        while (newsImages.size() <= 2){
            newsImages.addAll(newsImages);
        }

        for (int i = 0; i < mNewsImages.size(); i++) {
            ImageView temptView = new ImageView(mContext);
            Log.d(TAG, "homePagerImages.get(" + i + ")----------->" + mNewsImages.get(i));
            String imageurl = mNewsImages.get(i).getUrl();
            imageLoader.displayImage(imageurl, temptView, options, new AnimateFirstDisplayListener());
            //设置大小
            temptView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            temptView.setScaleType(ImageView.ScaleType.FIT_XY);
            temptView.setClickable(true);
            temptView.setEnabled(true);
            // 将homepagerimage对象传给自定义viewpager的adapter，点击后跳转用
            temptView.setTag(mNewsImages.get(i));
            views.add(temptView);
            dot = new ImageView(mContext);// 设置小圆点imageview的参数
            dot.setLayoutParams(new LayoutParams((int)real_dot_width, (int)real_dot_width));
            mNewsDots.add(dot);
            if (i == 0) {
                // 默认进入程序后第一张图片被选中;
                title.setText(mNewsImages.get(i).getName());
                mNewsDots.get(i).setBackgroundResource(R.mipmap.banner_point_selected);
            } else {
                mNewsDots.get(i).setBackgroundResource(R.mipmap.banner_point_unselect);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;

            viewIndicator.addView(dot, layoutParams);
        }

        // myAdapter.notifyDataSetChanged();
        myAdapter = new BannerViewPagerAdapter(views, mContext);
        viewPager.setAdapter(myAdapter);
        viewPager.setCurrentItem(newsImages.size() * 100);
        myAdapter.setBannerOnClickListener(bannerOnClickListener);
        if (null == thread) {
            // activity启动两秒钟后，发送一个message，用来将viewPager中的图片切换到下一个
            if (System.currentTimeMillis() - resetTime > intervalTime) {
                thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (!isStop) {
                            if (!isPause) {
                                if (isUp) {
                                    try {
                                        thread.sleep(intervalTime);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    isUp = false;
                                }
                                // 注意这里是先切换再等待
                                mHandler.sendEmptyMessage(handleChange);
                                try {
                                    thread.sleep(intervalTime);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                // 发送一个message，用来将viewPager中的图片切换到下一个
                // mHandler.sendEmptyMessageDelayed(handleChange, intervalTime);
                thread.start();
                resetTime = System.currentTimeMillis();
            }
        }

    }

    /**
     * 加载完图片的监听 用于添加动画
     *
     * @author Administrator
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        thread = null;
    }
    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率px(像素) 转成dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float sp2px(Context context, float sp){
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
