package zabelnikov.ua.swipeablelayout.ui.singleItem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout;
import ua.zabelnikov.swipelayout.layout.listener.LayoutShiftListener;
import zabelnikov.ua.swipeablelayout.R;

public class SingleItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        SwipeableLayout swipeableLayout = (SwipeableLayout) findViewById(R.id.swipeableLayout);

        swipeableLayout.addBlock(1);

        swipeableLayout.setLayoutShiftListener(new LayoutShiftListener() {
            @Override
            public void onLayoutShifted(float positionX, float positionY, boolean isTouched) {
                Log.d("positionX", String.valueOf(positionX));
                Log.d("positionY", String.valueOf(positionY));
                Log.d("isTouched", String.valueOf(isTouched));
            }
        });
    }
}
