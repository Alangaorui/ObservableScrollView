package com.grant.observablescrollview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by grant on 2018/4/27 0027.
 */

public class ObservableScrollView extends ScrollView {

    /**
     * 回调接口监听事件
     */
    private OnObservableScrollViewListener mOnObservableScrollViewListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 添加回调接口，便于把滑动事件的数据向外抛
     */
    public interface OnObservableScrollViewListener {
        void onObservableScrollViewListener(int l, int t, int oldl, int oldt);
    }

    /**
     * 注册回调接口监听事件
     *
     * @param onObservableScrollViewListener
     */
    public void setOnObservableScrollViewListener(OnObservableScrollViewListener onObservableScrollViewListener) {
        this.mOnObservableScrollViewListener = onObservableScrollViewListener;
    }

    /**
     * 滑动监听
     * @param l Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t Current vertical scroll origin. 当前滑动的y轴距离
     * @param oldl Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param oldt Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnObservableScrollViewListener != null) {
            //将监听到的数据向外抛
            mOnObservableScrollViewListener.onObservableScrollViewListener(l, t, oldl, oldt);
        }
    }

}
