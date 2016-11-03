# LoopPicturesDemo 图片轮播器
##介绍

    平常编程发现轮播图使用的地方很多，避免重复造轮子，决定抽取出来。
    
    一个图片轮播器，使用universal-image-loader来加载图片缓存。
    
    图片轮循（图片轮播从最后一张重新回到第一张）的原理并非是现在最常用的将 ViewPagerAdapter 中的count数目
    设置成无限大，而是：
    
    在添加轮播器的图片时，在最后一张图片后面重复添加第一张图片，用来当作缓冲图片！所以在最后一张图片展示完后，继续滚动会显示准
    备好的缓冲图片，此时检测到位置再立即 setCurrentItem 将图片设置成第一张，借此完成图片的轮询原理。
    
##效果展示
![](https://github.com/gnemyg7221/LoopPicturesDemo/raw/master/showPicture/1.png)  ![](https://github.com/gnemyg7221/LoopPicturesDemo/raw/master/showPicture/2.png)  

##用法介绍
###1.在布局中添加控件
```Java
<com.gym.looppicturesdemo.view.LoopPicture
android:id="@+id/loopPicture"
android:layout_width="match_parent"
android:layout_height="250dp"/>
```
###2.给图片轮播器提供图片资源或题目
  提供的3个接口：
* public void setImageRes(int[] imagesRes, boolean hasTitle)
* public void setImageUrl(String[] imageUrl, boolean hasTitle)
* public void setImageTitle(String[] imageTitle)

####(1)方法一： 设置图片资源id。
  若图片上需要呈现题目，boolean值传入true，相应的将imageTitle数组传入setImageTitle方法中
```Java
        loopPicture = (LoopPicture)this.findViewById(R.id.loopPicture);

        String[] imagesRes = new String[] {
         "http://pic.sc.chinaz.com/files/pic/pic9/201604/apic20400.jpg",
         "http://pics.sc.chinaz.com/files/pic/pic9/201602/apic19022.jpg",
         "http://pics.sc.chinaz.com/files/pic/pic9/201603/fpic430.jpg",
         "http://pics.sc.chinaz.com/files/pic/pic9/201605/apic20631.jpg"};
        loopPicture.setImageUrl(imagesRes, true);

        String[] titles = {"111111111111111111","2222222222222222",
                "3333333333333","4444444444444","5555555555"};
        loopPicture.setImageTitle(titles);

```
####(2)方法二： 设置图片资源URL。
  若图片上需要呈现题目，boolean值传入true，相应的将imageTitle数组传入setImageTitle方法中
```Java
        loopPicture = (LoopPicture)this.findViewById(R.id.loopPicture);

        int[] imagesRes = { R.drawable.a, R.drawable.b, R.drawable.c,
                R.drawable.d, R.drawable.e };
        loopPicture.setImageUrl(imagesRes, true);

        String[] titles = {"111111111111111111","2222222222222222",
                "3333333333333","4444444444444","5555555555"};
        loopPicture.setImageTitle(titles);

```
##依赖的第三方库：universal-image-loader
```
    /*图片缓存*/
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.4'
```
##学习于
https://github.com/iKrelve/Kanner 学习了这位coder的代码，受益良多
