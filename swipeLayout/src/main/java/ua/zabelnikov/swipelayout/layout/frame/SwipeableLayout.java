package ua.zabelnikov.swipelayout.layout.frame;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import ua.zabelnikov.swipelayout.R;
import ua.zabelnikov.swipelayout.layout.SwipeGestureManager;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutPercentageChangeListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;

public class SwipeableLayout extends FrameLayout {

    private float swipeSpeed;
    private SwipeGestureManager swipeManager;
    private int swipeOrientationMode;

    public SwipeableLayout(Context context) {
        super(context);
    }

    public SwipeableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateAttributes(context, attrs);
        initSwipeManager();
    }

    public SwipeableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateAttributes(context, attrs);
        initSwipeManager();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateAttributes(context, attrs);
        initSwipeManager();
    }

    private void inflateAttributes(Context context, AttributeSet attributeSet) {

        swipeSpeed = 1.0f;
        swipeOrientationMode = SwipeGestureManager.OrientationMode.UP_BOTTOM;

        if (attributeSet != null) {
            TypedArray attributes = context.obtainStyledAttributes(attributeSet, R.styleable.SwipeableLayout);
            try {
                swipeSpeed = attributes.getFloat(R.styleable.SwipeableLayout_swipeSpeed, swipeSpeed);
                swipeOrientationMode = attributes.getInt(R.styleable.SwipeableLayout_swipeOrientation, swipeOrientationMode);
            } finally {
                attributes.recycle();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return swipeManager.onTouch(this, ev);
    }

    public void initSwipeManager() {
        SwipeGestureManager.Builder builder = new SwipeGestureManager.Builder();
        builder.setSwipeSpeed(swipeSpeed);
        builder.setOrientationMode(swipeOrientationMode);
        swipeManager = builder.create();
        this.setOnTouchListener(swipeManager);
    }

    public void addBlock(int orientationMode) {
        swipeManager.addBlock(orientationMode);
    }

    public void removeBlock(int orientationMode) {
        swipeManager.removeBlock(orientationMode);
    }

    public void setOnSwipedListener(OnLayoutSwipedListener onSwipedListener) {
        swipeManager.setOnSwipedListener(onSwipedListener);
    }

    public void setOnLayoutPercentageChangeListener(OnLayoutPercentageChangeListener onLayoutPercentageChangeListener) {
        swipeManager.setOnLayoutPercentageChangeListener(onLayoutPercentageChangeListener);
    }
}
