package me.zhangls.loadinglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;


//https://github.com/lzyzsd/LoadingLayout
//https://github.com/lsjwzh/MaskEverywhere

/**
 * Created by zhangls on 2016/3/30.
 */
public class OverlayLayout extends FrameLayout {

    protected View mTargetView;

    public OverlayLayout(Context context) {
        super(context);
    }

    public OverlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverlayLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * use LoadingLayout itself to wrap and replace the targetView.
     * Note: if you attachTo targetView on 'onCreate' method,targetView may be not layout complete.
     *
     * @param targetView
     * @return
     */
    public void attachTo(final View targetView) {
        if (targetView == null) {
            throw new IllegalArgumentException();
        }
        mTargetView = targetView;

        ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        this.setLayoutParams(layoutParams);
        //XML创建OverlayLayout
        if (targetView.getParent() != null && targetView.getParent() instanceof ViewGroup) {
            //1.获取目标View的基Parent
            ViewGroup targetViewParent = (ViewGroup) targetView.getParent();
            //2.记住原targetView在基Parent的索引
            int targetViewPosInParent = targetViewParent.indexOfChild(targetView);
            //3.从基Parent中移除targetView
            targetViewParent.removeView(targetView);
            //4.给当前的父View添加targetView
            addView(targetView);
            //5.把当前的父View添加到基Parent
            targetViewParent.addView(this, targetViewPosInParent);
            //JAVA创建OverlayLayout
        } else {
            ViewUtil.addGlobalLayoutListenerOnce(targetView, new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Log.d("ViewUtil", "onGlobalLayout");
                    if (targetView.getParent() == null) {
                        return;
                    }
                    //和上面一样
                    ViewGroup targetViewParent = (ViewGroup) targetView.getParent();
                    int targetViewPosInParent = targetViewParent.indexOfChild(targetView);
                    targetViewParent.removeView(targetView);
                    targetViewParent.addView(OverlayLayout.this, targetViewPosInParent);
                    addView(targetView);
                }
            });
        }
    }

}
