package zabelnikov.ua.swipeablelayout.ui.viewPager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;
import zabelnikov.ua.swipeablelayout.R;
import zabelnikov.ua.swipeablelayout.ui.viewPager.adapter.PhotoViewPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity implements OnLayoutSwipedListener {

    private PhotoViewPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initPagerAdapter();

        viewPager.setAdapter(pagerAdapter);
    }

    public void initPagerAdapter() {

        List<String> linkList = new ArrayList<>();

        linkList.add("http://www.vetprofessionals.com/catprofessional/images/home-cat.jpg");
        linkList.add("http://s.hswstatic.com/gif/whiskers-sam.jpg");
        linkList.add("http://bolen-kot.net.ru/wp-content/uploads/2015/09/123.jpg");
        linkList.add("https://upload.wikimedia.org/wikipedia/en/b/b7/George,_a_perfect_example_of_a_tuxedo_cat.jpg");

        pagerAdapter = new PhotoViewPagerAdapter(this, linkList);

        pagerAdapter.setOnLayoutSwipedListener(this);
    }

    @Override
    public void onLayoutSwiped() {
        finish();
    }
}
