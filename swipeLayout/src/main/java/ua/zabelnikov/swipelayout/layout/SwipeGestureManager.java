package ua.zabelnikov.swipelayout.layout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.ArraySet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

import ua.zabelnikov.swipelayout.layout.listener.OnLayoutPercentageChangeListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;

public class SwipeGestureManager implements View.OnTouchListener {

    //Listeners
    private OnLayoutSwipedListener onSwipedListener;
    private OnLayoutPercentageChangeListener onLayoutPercentageChangeListener;
    //Listeners

    // Configs
    private final float swipeSpeed;
    private final int orientationMode;
    private final Set<Integer> blocks;
    //Configs

    private int lastYPosition;
    private int lastXPosition;
    private int layoutPosition;

    private final Context context;
    private final GestureDetector gestureDetector;

    private SwipeGestureManager(Context context, float swipeSpeed, int orientationMode) {
        this.context = context;
        this.swipeSpeed = swipeSpeed;
        this.orientationMode = orientationMode;
        this.blocks = new HashSet<>();
        gestureDetector = new GestureDetector(context, new FlingGestureDetector());
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean status;
        switch (orientationMode) {
            case OrientationMode.LEFT_RIGHT:
                status = swipeByX(view, event);
                break;
            case OrientationMode.UP_BOTTOM:
                status = swipeByY(view, event);
                break;
            case OrientationMode.BOTH:
                status = swipeByX(view, event) && swipeByY(view, event);
                break;
            default:
                status = false;
        }
        return status;
    }

    private boolean swipeByY(View view, MotionEvent event) {
        if (!blocks.contains(OrientationMode.UP_BOTTOM)) {
            if (gestureDetector.onTouchEvent(event)) {
                return true;
            }

            final int y = (int) event.getRawY();
            int height = view.getHeight();
            float dif = Math.abs(view.getY()) / (height / 4);

            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                lastYPosition = y;
                layoutPosition = (int) view.getY();

            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {

                int diffY = y - lastYPosition;

                view.setY(layoutPosition + (diffY * swipeSpeed));

                if (onLayoutPercentageChangeListener != null) {
                    onLayoutPercentageChangeListener.percentageY(dif > 1 ? 1.0f : dif);
                }

            } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {

                if (dif > 1.0) {
                    triggerSwipeListener();
                }
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "y", view.getY(), 0);
                animator.setDuration(300);
                animator.start();
            }
        }
        return true;
    }

    private boolean swipeByX(View view, MotionEvent event) {
        if (!blocks.contains(OrientationMode.LEFT_RIGHT)) {
            if (gestureDetector.onTouchEvent(event)) {
                return true;
            }

            final int x = (int) event.getRawX();
            int width = view.getWidth();
            float dif = Math.abs(view.getX()) / (width / 4);

            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                lastXPosition = x;
                layoutPosition = (int) view.getX();

            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {

                int diffX = x - lastXPosition;
                view.setX(layoutPosition + (diffX * swipeSpeed));

                if (onLayoutPercentageChangeListener != null) {
                    onLayoutPercentageChangeListener.percentageX(dif > 1 ? 1.0f : dif);
                }

            } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {

                if (dif > 1.0) {
                    triggerSwipeListener();
                }
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "x", view.getX(), 0);
                animator.setDuration(300);
                animator.start();
            }
        }
        return true;
    }

    class FlingGestureDetector extends GestureDetector.SimpleOnGestureListener {

        float sensitivity = 500;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityY) > sensitivity && !blocks.contains(OrientationMode.UP_BOTTOM)) {
                onSwipedListener.onLayoutSwiped();
                return true;
            } else if (Math.abs(velocityX) > sensitivity && !blocks.contains(OrientationMode.LEFT_RIGHT)) {
                onSwipedListener.onLayoutSwiped();
                return true;
            } else {
                return false;
            }
        }
    }

    public void addBlock(int orientationMode) {
        blocks.add(orientationMode);
    }

    public void removeBlock(int orientationMode) {
        blocks.remove(orientationMode);
    }

    public void setOnSwipedListener(OnLayoutSwipedListener onSwipedListener) {
        this.onSwipedListener = onSwipedListener;
    }

    public void setOnLayoutPercentageChangeListener(OnLayoutPercentageChangeListener onLayoutPercentageChangeListener) {
        this.onLayoutPercentageChangeListener = onLayoutPercentageChangeListener;
    }

    private void triggerSwipeListener() {
        if (onSwipedListener != null) {
            onSwipedListener.onLayoutSwiped();
        }
    }

    public static class Builder {

        private final Context context;
        private float mSwipeSpeed;
        private int mOrientationMode;

        public Builder(Context context) {
            this.context = context;
        }

        public void setSwipeSpeed(float mSwipeSpeed) {
            this.mSwipeSpeed = mSwipeSpeed;
        }

        public void setOrientationMode(int orientationMode) {
            this.mOrientationMode = orientationMode;
        }

        public SwipeGestureManager create() {
            return new SwipeGestureManager(context, mSwipeSpeed, mOrientationMode);
        }
    }

    public abstract class OrientationMode {
        public static final int LEFT_RIGHT = 0;
        public static final int UP_BOTTOM = 1;
        public static final int BOTH = 2;
        public static final int NONE = 3;
    }
}
