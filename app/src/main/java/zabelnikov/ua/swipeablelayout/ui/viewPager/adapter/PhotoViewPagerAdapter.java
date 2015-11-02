package zabelnikov.ua.swipeablelayout.ui.viewPager.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutPercentageChangeListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;
import zabelnikov.ua.swipeablelayout.R;

public class PhotoViewPagerAdapter extends PagerAdapter {

    public final List<String> items;
    private final Context context;
    private OnLayoutSwipedListener onLayoutSwipedListener;

    public PhotoViewPagerAdapter(Context context, List<String> photos) {
        this.context = context;
        items = photos;
    }

    public void setOnLayoutSwipedListener(OnLayoutSwipedListener onLayoutSwipedListener) {
        this.onLayoutSwipedListener = onLayoutSwipedListener;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        String link = items.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_pager_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        Picasso.with(context).load(link).fit().centerCrop().into(imageView);

        final View colorFrame = view.findViewById(R.id.colorContainer);
        SwipeableLayout swipeableLayout = (SwipeableLayout) view.findViewById(R.id.swipeableLayout);

        swipeableLayout.setOnLayoutPercentageChangeListener(new OnLayoutPercentageChangeListener() {
            private float lastAlpha = 1.0f;

            @Override
            public void percentageY(float percentage) {
                float alphaCorrector = percentage / 2;
                AlphaAnimation alphaAnimation = new AlphaAnimation(lastAlpha, 1 - alphaCorrector);
                alphaAnimation.setDuration(300);
                colorFrame.startAnimation(alphaAnimation);
                lastAlpha = 1 - alphaCorrector;
            }
        });
        swipeableLayout.setOnSwipedListener(new OnLayoutSwipedListener() {
            @Override
            public void onLayoutSwiped() {
                if (onLayoutSwipedListener != null) {
                    onLayoutSwipedListener.onLayoutSwiped();
                }
            }
        });


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

}
