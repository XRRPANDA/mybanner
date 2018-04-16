package com.zxg.mybanner.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义viewpager
 *
 * @author Administrator
 */
@SuppressLint("DrawAllocation")
public class BannerViewPager extends ViewPager {

    /**
     * 触摸时按下的点
     */
    PointF downP = new PointF();
    /**
     * 触摸时当前的点
     */
    PointF curP = new PointF();
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean boo = super.dispatchTouchEvent(ev);
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			xDistance = yDistance = 0f;
//			xLast = ev.getX();
//			yLast = ev.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			final float curX = ev.getX();
//			final float curY = ev.getY();
//
//			xDistance += Math.abs(curX - xLast);
//			yDistance += Math.abs(curY - yLast);
//			xLast = curX;
//			yLast = curY;
//			if (xDistance > yDistance) {
//				getParent().requestDisallowInterceptTouchEvent(true);// 这句话的作用
//																		// 告诉父view，我的单击事件我自行处理，不要阻碍我。
//			} else {
//				// boo = false; // 表示向下传递事件
//			}
//		}
        curP.x = ev.getX();
        curP.y = ev.getY();

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 记录按下时候的坐标
            // 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = ev.getX();
            downP.y = ev.getY();
            // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            if (Math.abs(downP.x - curP.x) < Math.abs(downP.y - curP.y)) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }

        return boo;
    }


    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(listener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//影响图片 宽高
//		int height = 0;
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//			int h = child.getMeasuredHeight();
//			if (h > height)
//				height = h;
//		}
//
//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
