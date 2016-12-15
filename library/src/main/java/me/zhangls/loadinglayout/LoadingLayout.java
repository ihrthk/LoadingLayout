package me.zhangls.loadinglayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zhangls on 2016/11/27.
 */

public class LoadingLayout extends OverlayLayout {

    //加载中，空页面，出错页面和内容页面


    /**
     * 空数据View
     */
    private View mEmptyView;
    /**
     * 错误View
     */
    private View mError;
    /**
     * 加载View
     */
    private View mLoadingView;


    public LoadingLayout(Context context) {
        super(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public LoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        try {
            mTargetView = getChildAt(0);

            int loading = typedArray.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.loading_layout);
            int error = typedArray.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.error_layout);
            int empty = typedArray.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.empty_layout);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            mLoadingView = inflater.inflate(loading, null);
            addView(mLoadingView);
            mError = inflater.inflate(error, null);
            addView(mError);
            mEmptyView = inflater.inflate(empty, null);
            addView(mEmptyView);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * create a LoadingLayout to wrap and replace the targetView.
     * Note: if you attachTo targetView on 'onCreate' method,targetView may be not layout complete.
     *
     * @param contentView
     * @return
     */
    public static LoadingLayout wrap(final View contentView) {
        if (contentView == null) {
            throw new IllegalArgumentException();
        }

        final LoadingLayout loadingLayout = new LoadingLayout(contentView.getContext());
        loadingLayout.attachTo(contentView);
        return loadingLayout;
    }


    public void setLoading(View view) {
        if (mTargetView == null) {
            throw new IllegalArgumentException();
        }

        if (mLoadingView != null) {
            removeView(mLoadingView);
        }

        view = mLoadingView;
        addView(view);
    }


    public void setContent(View view) {
        attachTo(view);
    }

    public void setEmpty(View view) {
        if (mTargetView == null) {
            throw new IllegalArgumentException();
        }

        if (mEmptyView != null) {
            removeView(mEmptyView);
        }
        view = mEmptyView;
        addView(view);
    }

    public void setError(View view) {
        if (mTargetView == null) {
            throw new IllegalArgumentException();
        }


        if (mError != null) {
            removeView(mError);
        }
        mError = view;
        addView(view);
    }


    public void showLoading() {

        mLoadingView.setVisibility(VISIBLE);
    }

    public void showContent() {

    }

    public void showEmpty() {

    }

    public void showError() {

    }
}
