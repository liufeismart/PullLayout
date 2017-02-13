package com.liufeismart.pulllayout.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by liufeismart on 17/2/10.
 */

public class PullLayout extends FrameLayout {
    private ImageView iv_bg;//放背景图
    private FrameLayout layout ;//放提供的控件的

    //state 的状态
    private static final int STATE_NORMAL = 0;
    private static final int STATE_TOP = 1;
    private static final int STATE_BOTTOM = 2;
    private static final int STATE_PULL = 3;
    private int state = STATE_NORMAL;
    //移动的上下限
    private float translate_min = -300f;
    private float translate_max = 300f;
    //记录
    private float mPrevY;
    private float translationY;
    private boolean childInTouchEvent = true;//子控件不要处理TouchEvent
    ObjectAnimator animator;//回弹动画

    public PullLayout(Context context) {
        super(context);
        init(context);
    }


    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void setTranslateMax(int max) {
        translate_max = max;
    }

    public void setTranslateMin(int min) {
        translate_min = min;
    }

    public void setChildInTouchEvent(boolean touch) {
        childInTouchEvent = touch;
    }

    private void init(Context context) {
        iv_bg = new ImageView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        this.addView(iv_bg, params);
        iv_bg.setScaleType(ImageView.ScaleType.FIT_XY);
        layout = new FrameLayout(context);
        layout.setBackground(null);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(layout, params2);
    }

    public void setImageBackground(Drawable drawable) {
        iv_bg.setImageDrawable(drawable);
    }

    public void setImageBackgroundResource(int drawableId) {
        iv_bg.setImageResource(drawableId);
    }

    public ImageView getImageBackground() {
        return iv_bg;
    }

    public void setImageHeight(int height) {
        ViewGroup.LayoutParams params = iv_bg.getLayoutParams();
        params.height = height;
        iv_bg.setLayoutParams(params);
    }

    public void setLayoutHeight(int height) {
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = height;
        layout.setLayoutParams(params);
    }


    public void setLayout(View layoutView) {
        this.layout.addView(layoutView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }



    private void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(!childInTouchEvent) {
            onTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int a=0;
                break;
            case MotionEvent.ACTION_MOVE:
                int b=0;
                break;
        }
        switch(state) {
            case STATE_TOP:
//                if(!childInTouchEvent) {
//                    onTouchEvent(event);
//                }
                break;
            default:
                return true;

        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                translationY = layout.getTranslationY();
                setPrevY(event.getY());
                if(animator!=null && animator.isRunning()) {
                    animator.end();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float mMoveY = event.getY() - mPrevY;
                float tempY = translationY + mMoveY;
                if(tempY>translate_min && tempY< translate_max) {
                    layout.setTranslationY(tempY);
                    setState(STATE_PULL);
                }
                else if(tempY <= translate_min) {

                    layout.setTranslationY(translate_min);
                    setState(STATE_TOP);

                }
                else if(tempY >= translate_max) {
                    layout.setTranslationY(translate_max);
                    setState(STATE_BOTTOM);
                }
                //
//                setPrevY( event.getY());
                break;
            case MotionEvent.ACTION_UP:
                touchUP();

                break;
        }
        return true;
    }

    private void touchUP() {
        switch (state) {
            case STATE_TOP:

                break;
            default:

                animator =  ObjectAnimator.ofFloat(layout,"translationY", layout.getTranslationY(),0)
                        .setDuration(600);

                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        setState(STATE_PULL);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        setState(STATE_NORMAL);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animator.start();

                break;
        }
    }

    private void setPrevY(float y) {
        mPrevY = y;
    }
}
