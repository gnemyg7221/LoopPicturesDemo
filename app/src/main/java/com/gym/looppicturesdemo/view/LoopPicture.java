package com.gym.looppicturesdemo.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gym.looppicturesdemo.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;


/**
 * 轮播器：Created by gym on 2016/10/30.
 * 图片的循环滚动原理：
 * 
 */

public class LoopPicture extends FrameLayout {
    private Context context;
    private DisplayImageOptions options;
    private ImageLoader mImageLoader;
    private String[] imageTitle; //展示图片的标题
    private List<ImageView> images;       //展示的图片
    private List<ImageView> imagesDots;     //展示图片位置的小圆点
    private int delayTime; //轮播图延迟换图的时间

    private boolean isAutoPlay;
    private int currentItem;
    public Handler handler = new Handler();
    private ViewPager viewPager;
    private int imageLength;
    private LinearLayout dotLayout;
    private int titleLength;
    private TextView titleTV;
    private boolean hasTitle;

    public LoopPicture(Context context) {
        this(context, null);
    }

    public LoopPicture(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopPicture(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        //使用第三方库universal-imageloader 加载图片缓存
        initImageLoader(context);

        //初始化数据
        initData();
    }

    /**
     * 使用第三方库universal-imageloader 加载图片缓存
     */
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 初始化轮播器中的数据
     */
    private void initData() {
        images = new ArrayList<ImageView>();
        imagesDots = new ArrayList<ImageView>();
        delayTime = 2000;
    }

    /**
     * 提供接口：
     * 使用轮播器时设置图片(图片id)
     */
    public void setImageRes(int[] imagesRes, boolean hasTitle) {
        this.hasTitle = hasTitle;
        initView();
        initImgFromRes(imagesRes);
        startLoopPicture();
    }

    /**
     * 提供接口：
     * 使用轮播器时设置图片(图片URl)
     */
    public void setImageUrl(String[] imageUrl, boolean hasTitle){
        this.hasTitle = hasTitle;
        initView();
        initImgFromUrl(imageUrl);
        startLoopPicture();
    }

    /**
     * 提供接口：
     * 使用轮播器时设置图片的标题
     */
    public void setImageTitle(String[] imageTitle){
        this.imageTitle = imageTitle;
    }

    /**
     * 初始化轮播器布局
     */
    private void initView() {
        images.clear();
        View view = LayoutInflater.from(context).inflate(R.layout.loop_pictures, this, true);
        viewPager = (ViewPager)view.findViewById(R.id.vp);
        dotLayout = (LinearLayout) view.findViewById(R.id.ll_dot);
        titleTV = (TextView) view.findViewById(R.id.tv_title);
        dotLayout.removeAllViews();
    }



    /**
     * 初始化轮播器的图片(图片URL)
     * 使用第三方类库universal-imageloader 加载图片缓存
     */
    private void initImgFromUrl(String[] imageUrl) {
        imageLength = imageUrl.length;
        for(int i = 0;i<imageLength;i++){
            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            dotView.setImageResource(R.drawable.dot_blur);
            dotLayout.addView(dotView, params);
            imagesDots.add(dotView);
        }
        imagesDots.get(0).setImageResource(R.drawable.dot_focus);

        //universal-imageloader 加载图片缓存
        for(int i = 0; i <= imageLength + 1; i++){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(R.drawable.loading);
            if(i == 0){
                mImageLoader.displayImage(imageUrl[imageLength - 1], imageView);
            } else if(i == imageLength + 1){
                mImageLoader.displayImage(imageUrl[0], imageView);
            } else {
                mImageLoader.displayImage(imageUrl[i - 1], imageView);
            }
            images.add(imageView);
        }
    }

    /**
     * 初始化轮播器的图片(图片id)
     */
    private void initImgFromRes(int[] imagesRes) {
        imageLength = imagesRes.length;
        for(int i = 0; i < imageLength; i++){
            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            dotView.setImageResource(R.drawable.dot_blur);
            dotLayout.addView(dotView, params);
            imagesDots.add(dotView);
        }
        //设置第一张轮播图的小圆点为选中状态
        imagesDots.get(0).setImageResource(R.drawable.dot_focus);

        for(int i = 0;i<= imageLength+1 ; i++){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(R.drawable.loading);
            if(i == 0){
                imageView.setImageResource(imagesRes[imageLength - 1]);
            } else if (i == imageLength + 1){
                imageView.setImageResource(imagesRes[0]);
            } else {
                imageView.setImageResource(imagesRes[i - 1]);
            }
            images.add(imageView);
        }
    }

    /**
     * 设置ViewPager,开启轮播器
     */
    private void startLoopPicture(){
        viewPager.setAdapter(new LoopPicturesAdapter());
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        currentItem = 1;
        viewPager.addOnPageChangeListener(new LoopPicturesListener());

        //开启轮播器
        isAutoPlay = true;
        handler.postDelayed(task, 2000);
    }


    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (imageLength + 1) + 1;
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, 3000);
                }
            } else {
                handler.postDelayed(task, 5000);
            }
        }
    };


    class LoopPicturesAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = images.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    class LoopPicturesListener implements OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for(int i = 0; i<imagesDots.size();i++){
                if(i == position - 1){
                    imagesDots.get(i).setImageResource(R.drawable.dot_focus);
                    if(hasTitle) {
                        titleTV.setText(imageTitle[i]);
                    }
                } else {
                    imagesDots.get(i).setImageResource(R.drawable.dot_blur);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state){
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(imageLength, false);
                    } else if (viewPager.getCurrentItem() == imageLength + 1) {
                        viewPager.setCurrentItem(1, false);
                    }
                    currentItem = viewPager.getCurrentItem();
                    isAutoPlay = true;
                    break;
            }

        }
    }
}
