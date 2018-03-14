在之前的开发中，指示器都是跟viewpager配合着使用，这样如果想在切换时实现indicator的平滑滚动，只需要配合使用onPageScrolled就可以了。因为onPageScrolled会不断回调，这样就可以在这里去实时刷新UI。

而如果indicator不是配合ViewPager使用呢？就像下面这个页面，就没有配合ViewPager一起使用。下面使用的是RecyclerView，当点击上面的indicator，下面的RecyclerView会滑到相应的位置。这个时候，如何实现指示器indicator在切换tab的过程中平滑滑动呢？





直接上代码：

自定义View的类文件

package ctrip.android.tour.component;

import android.widget.LinearLayout;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet
import android.widget.Scroller;

/**
 * @author Zhenhua on 2017/6/6 19:39.
 * @email zhshan@ctrip.com
 */

public class ScrollableIndicator extends LinearLayout {
    public ScrollableIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    private Scroller scroller;

    public void smoothScrollTo(int destX) {
        int finalX = scroller.getFinalX();
        int deltaX = destX - finalX;
        scroller.startScroll(finalX, 0, deltaX, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }
}

可以看到代码逻辑非常简单，其实中间还是遇到了一些坑，不过这里就不详细介绍了，只是讲解如何使用。

接下来是xml文件：

			<ctrip.android.tour.component.ScrollableIndicator
                            android:layout_width="match_parent"
                            android:layout_height="2dp"
                            android:id="@+id/indicator"
                            android:background="@drawable/cttour_around_travel_border_bottom"
                            android:orientation="vertical">
                            <View
                                android:layout_width="10dp"
                                android:layout_height="match_parent"
                                android:background="#009fde"
                                android:id="@+id/indicator_content"/>
                        </ctrip.android.tour.component.ScrollableIndicator>

里面的View就是可滑动的indicator。


那么如何使用呢？

1、这个是指定了indicatorView的宽度为屏幕宽度，并且其中的可滑动indicator宽度 = 屏幕宽度 / 选项个数

int screeWidth = CommonUtil.getScreenWidth(context);
        final int size = tabItems.size();
        int indicator_width = screeWidth / size;

        indicatorView.getLayoutParams().width = indicator_width;
        indicatorView.setLayoutParams(new LinearLayout.LayoutParams(indicator_width, LinearLayout.LayoutParams.MATCH_PARENT));
2、这个是指定滑到第几个tab，注意“负号”
//Animation of indicator
        int screeWidth = CommonUtil.getScreenWidth(context);
        int part = screeWidth / total;
        indicator.smoothScrollTo(-index * part);

okay了，这样可平滑滑动的indicator就脱离开Viewpager了。
如需要demo，或有任何问题可以邮件联系我。邮件地址为： zhshan@ctrip.com



~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~华丽丽的分割线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



由于最近很多小伙伴给我发邮件表示，希望我能把这个控件封装一下，趁着不忙的时候，我就做了个封装。可以说是封装得非常彻底，你只需要在xml中引入控件：

<com.ctrip.indicatorapplication.indicator.IndicatorView
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="2dp" />
然后，在java代码中初始化一下，传入的数字为tab的总个数。
final IndicatorView indicatorView = findViewById(R.id.indicator);
        indicatorView.init(5);
然后在需要滑动到哪个tab时，只需要调用以下方法，并传入这个tab的index。
indicatorView.smoothSlideTo(index);

使用起来非常简便。点击这里下载demo。

