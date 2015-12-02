package ua.zabelnikov.swipelayout.layout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

import ua.zabelnikov.swipelayout.layout.listener.LayoutShiftListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutPercentageChangeListener;
import ua.zabelnikov.swipelayout.layout.listener.OnLayoutSwipedListener;

public class SwipeGestureManager implements View.OnTouchListener {

    //Listeners
    private OnLayoutSwipedListener onSwipedListener;
    private OnLayoutPercentageChangeListener onLayoutPercentageChangeListener;
    private LayoutShiftListener layoutShiftListener;
    //Listeners

    // Configs
    private float swipeSpeed;
    private int orientationMode;
    private Set<Integer> blocks;
    //Configs

    private float firstXPosition;
    private float firstYPosition;

    private int lastXPosition;
    private int lastYPosition;

    private int currentXPosition;
    private int currentYPosition;

    private int shiftX;
    private int shiftY;

    private int lastMotion = -1;

    private final Context context;
    private final GestureDetector gestureDetector;

    private SwipeGestureManager(Context context, float startX, float startY, float swipeSpeed, int orientationMode) {
        this.context = context;
        firstXPosition = startX;
        firstYPosition = startY;
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
                status = swipeByY(view, event) && swipeByX(view, event);
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
                currentYPosition = (int) view.getY();
                lastMotion = MotionEvent.ACTION_DOWN;
            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {

                shiftY = y - lastYPosition;

                view.setY(currentYPosition + (shiftY * swipeSpeed));

                if (onLayoutPercentageChangeListener != null) {
                    onLayoutPercentageChangeListener.percentageY(dif > 1 ? 1.0f : dif);
                }

                triggerPositionChangeListener(shiftX, shiftY, true);
                lastMotion = MotionEvent.ACTION_MOVE;
            } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                triggerPositionChangeListener(shiftX, shiftY, false);
                lastMotion = MotionEvent.ACTION_UP;
                if (dif > 1.0) {
                    triggerSwipeListener();
                }
                rollback(view, "y", view.getY(), firstYPosition);
            } else if (event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
                return false;
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
                currentXPosition = (int) view.getX();
                lastMotion = MotionEvent.ACTION_DOWN;
            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {

                shiftX = x - lastXPosition;
                view.setX(currentXPosition + (shiftX * swipeSpeed));

                if (onLayoutPercentageChangeListener != null) {
                    onLayoutPercentageChangeListener.percentageX(dif > 1 ? 1.0f : dif);
                }

                triggerPositionChangeListener(shiftX, shiftY, true);
                lastMotion = MotionEvent.ACTION_MOVE;
            } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                triggerPositionChangeListener(shiftX, shiftY, false);
                lastMotion = MotionEvent.ACTION_UP;
                if (dif > 1.0) {
                    triggerSwipeListener();
                }
                rollback(view, "x", view.getX(), firstXPosition);
            } else if (event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
               return false;
            }
        }
        Log.d("Motion", String.valueOf(event.getActionMasked()));
        return true;
    }

    private void rollback(View view, String axis, float fromPosition, float startPosition) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, axis, fromPosition, startPosition);
        animator.setDuration(300);
        animator.start();
    }

    class FlingGestureDetector extends GestureDetector.SimpleOnGestureListener {

        float sensitivity = 500;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityY) > sensitivity && !blocks.contains(OrientationMode.UP_BOTTOM)) {
                triggerSwipeListener();
                return true;
            } else if (Math.abs(velocityX) > sensitivity && !blocks.contains(OrientationMode.LEFT_RIGHT)) {
                triggerSwipeListener();
                return true;
            } else {
                return false;
            }
        }
    }

    public void addBlock(int orientationMode) {
        blocks.add(orientationMode);
    }

    public void setBlockSet(Set<Integer> blockSet) {
        this.blocks.addAll(blockSet);
    }

    public void removeBlock(int orientationMode) {
        blocks.remove(orientationMode);
    }

    public void setSwipeSpeed(int swipeSpeed) {
        this.swipeSpeed = swipeSpeed;
    }

    public void setOrientationMode(int orientationMode) {
        this.orientationMode = orientationMode;
    }

    public void setOnSwipedListener(OnLayoutSwipedListener onSwipedListener) {
        this.onSwipedListener = onSwipedListener;
    }

    public void setOnLayoutPercentageChangeListener(OnLayoutPercentageChangeListener onLayoutPercentageChangeListener) {
        this.onLayoutPercentageChangeListener = onLayoutPercentageChangeListener;
    }

    public void setLayoutShiftListener(LayoutShiftListener layoutShiftListener) {
        this.layoutShiftListener = layoutShiftListener;
    }

    private void triggerSwipeListener() {
        if (onSwipedListener != null) {
            onSwipedListener.onLayoutSwiped();
        }
    }

    private void triggerPositionChangeListener(float positionX, float positionY, boolean state) {
        if (layoutShiftListener != null && lastMotion != MotionEvent.ACTION_UP) {
            layoutShiftListener.onLayoutShifted(positionX, positionY, state);
        }
    }

    public static class Builder {

        private final Context context;
        private float mSwipeSpeed;
        private int mOrientationMode;
        private float startX;
        private float startY;

        public Builder(Context context) {
            this.context = context;
        }

        public void setSwipeSpeed(float mSwipeSpeed) {
            this.mSwipeSpeed = mSwipeSpeed;
        }

        public void setOrientationMode(int orientationMode) {
            this.mOrientationMode = orientationMode;
        }

        public void setStartCoordinates(float startX, float startY) {
            this.startX = startX;
            this.startY = startY;
        }

        public void setStartX(int startX) {
            this.startX = startX;
        }

        public SwipeGestureManager create() {
            return new SwipeGestureManager(context, startX, startY, mSwipeSpeed, mOrientationMode);
        }
    }

    public abstract class OrientationMode {
        public static final int LEFT_RIGHT = 0;
        public static final int UP_BOTTOM = 1;
        public static final int BOTH = 2;
        public static final int NONE = 3;
    }
}
