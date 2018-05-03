package com.grant.observablescrollview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ObservableScrollView.OnObservableScrollViewListener, OnBannerListener {
    private ObservableScrollView mObservableScrollView;
    private TextView mTextView;
    private LinearLayout mHeaderContent;
    private TextView mTitle;

    private int mHeight;

    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置透明状态栏
        WindowTitleStatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_main);

        //初始化控件
        mObservableScrollView = (ObservableScrollView) findViewById(R.id.sv_main_content);
        mTextView = (TextView) findViewById(R.id.tv_main_topContent);
        mHeaderContent = (LinearLayout) findViewById(R.id.ll_header_content);
        mTitle = (TextView) findViewById(R.id.tv_header_title);
        initView();
        //获取标题栏高度
        ViewTreeObserver viewTreeObserver = mTextView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = mTextView.getHeight() - mHeaderContent.getHeight();//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                mObservableScrollView.setOnObservableScrollViewListener(MainActivity.this);
            }
        });
    }


    private void initView() {
        banner = (Banner) findViewById(R.id.banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyImageLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();


    }

    /**
     * 获取ObservableScrollView的滑动数据
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        if (t <= 0) {//在最顶部
            //顶部图处于最顶部，标题栏透明
            mHeaderContent.setBackgroundColor(Color.argb(0, 48, 63, 159));
            mTitle.setVisibility(View.GONE);
        } else if (t > 0 && t < mHeight) {//滑动过程中
            //滑动过程中，渐变
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
            mHeaderContent.setBackgroundColor(Color.argb((int) alpha, 48, 63, 159));
            mTitle.setVisibility(View.VISIBLE);
        } else {//滑动到最顶部
            //过顶部图区域，标题栏定色
            mHeaderContent.setBackgroundColor(Color.BLUE);
            mTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第"+position+"张轮播图");
    }
}
