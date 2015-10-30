package zabelnikov.ua.swipeablelayout.ui.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;

import ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutPercentageChangeListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;
import zabelnikov.ua.swipeablelayout.R;

public class ImageActivity extends AppCompatActivity {

    private SwipeableLayout swipeableLayout;
    private View colorFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        swipeableLayout = (SwipeableLayout) findViewById(R.id.swipeableLayout);
        colorFrame = findViewById(R.id.colorContainer);

        swipeableLayout.setOnLayoutPercentageChangeListener(new OnLayoutPercentageChangeListener() {
            private float lastAlpha = 1.0f;

            @Override
            public void percentageY(float percentage) {
                Log.d("Y", String.valueOf(1 - percentage / 3));
                AlphaAnimation alphaAnimation = new AlphaAnimation(lastAlpha, 1 - percentage / 3);
                alphaAnimation.setDuration(300);
                colorFrame.startAnimation(alphaAnimation);
                lastAlpha = 1 - percentage / 3;
            }
        });
        swipeableLayout.setOnSwipedListener(new OnLayoutSwipedListener() {
            @Override
            public void onLayoutSwiped() {
                ImageActivity.this.finish();
            }
        });
    }
}
