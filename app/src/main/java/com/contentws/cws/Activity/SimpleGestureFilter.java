package com.contentws.cws.Activity;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class SimpleGestureFilter extends SimpleOnGestureListener {

    public static final int SWIPE_UP = 1;
    public static final int SWIPE_DOWN = 2;
    public static final int SWIPE_LEFT = 3;
    public static final int SWIPE_RIGHT = 4;

    public static final int MODE_TRANSPARENT = 0;
    public static final int MODE_SOLID = 1;
    public static final int MODE_DYNAMIC = 2;

    private static final int ACTION_FAKE = -13;
    private int swipeMinDistance = 50;
    private int swipeMaxDistance = 350;
    private int swipeMinVelocity = 50;

    private int mode = MODE_DYNAMIC;
    private boolean running = true;
    private boolean tapIndicator = false;

    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;

    public SimpleGestureFilter(Activity context, SimpleGestureListener sgl) {
        this.context = context;
        this.detector = new GestureDetector(context, this);
        this.listener = sgl;
    }

    public void onTouchEvent(MotionEvent event) {
        if (!this.running) {
            return;
        }

        boolean result = this.detector.onTouchEvent(event);

        if (this.mode == MODE_SOLID) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        } else if (this.mode == MODE_DYNAMIC) {
            if (event.getAction() == ACTION_FAKE) {
                event.setAction(MotionEvent.ACTION_UP);
            } else if (result) {
                event.setAction(MotionEvent.ACTION_CANCEL);
            } else if (this.tapIndicator) {
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }
        }
    }

    public void setMode(int m) {
        this.mode = m;
    }

    public int getMode() {
        return this.mode;
    }

    public void setEnabled(boolean status) {
        this.running = status;
    }

    public void setSwipeMaxDistance(int distance) {
        this.swipeMaxDistance = distance;
    }

    public void setSwipeMinDistance(int distance) {
        this.swipeMinDistance = distance;
    }

    public int getSwipeMinVelocity() {
        return this.swipeMinVelocity;
    }

    public boolean onFling(MotionEvent startEvent, MotionEvent endEvent, float velocityX, float velocityY) {
        String str = "";
        if (endEvent.getX() > startEvent.getX() && endEvent.getY() <= startEvent.getY()) {
            float slope = (Math.abs(endEvent.getY() - startEvent.getY()))/Math.abs((endEvent.getX() - startEvent.getX()));
            if (slope > 1) {
                this.listener.onSwipe(SWIPE_UP);
                str = "1 UP";
            } else {
                this.listener.onSwipe(SWIPE_RIGHT);
                str = "1 RIGHT";
            }
        } else if (endEvent.getX() > startEvent.getX() && endEvent.getY() > startEvent.getY()) {
            float slope = (Math.abs(endEvent.getY() - startEvent.getY()))/Math.abs((endEvent.getX() - startEvent.getX()));
            if (slope > 1) {
                this.listener.onSwipe(SWIPE_RIGHT);
                str = "4 RIGHT";
            } else {
                this.listener.onSwipe(SWIPE_DOWN);
                str = "4 DOWN";
            }
        } else if (endEvent.getX() <= startEvent.getX() && endEvent.getY() <= startEvent.getY()) {
            float slope = (Math.abs(endEvent.getY() - startEvent.getY()))/Math.abs((endEvent.getX() - startEvent.getX()));
            if (slope > 1) {
                this.listener.onSwipe(SWIPE_UP);
                str = "2 UP";
            } else {
                this.listener.onSwipe(SWIPE_LEFT);
                str = "2 LEFT";
            }
        } else if (endEvent.getX() <= startEvent.getX() && endEvent.getY() > startEvent.getY()) {
            float slope = (Math.abs(endEvent.getY() - startEvent.getY()))/Math.abs((endEvent.getX() - startEvent.getX()));
            if (slope > 1) {
                this.listener.onSwipe(SWIPE_DOWN);
                str = "3 DOWN";
            } else {
                this.listener.onSwipe(SWIPE_LEFT);
                str = "3 LEFT";
            }
        }

        System.out.println(str);
        return true;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        this.tapIndicator = true;
        return false;
    }

    public boolean onDoubleTap(MotionEvent arg) {
        this.listener.onDoubleTap();
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent arg) {
        return true;
    }

    public boolean onSingleTapConfirmed(MotionEvent arg) {
        if (this.mode == MODE_DYNAMIC) {
            arg.setAction(ACTION_FAKE);
            this.context.dispatchTouchEvent(arg);
        }

        return false;
    }

    static interface SimpleGestureListener{
        void onSwipe(int direction);
        void onDoubleTap();
    }
}
