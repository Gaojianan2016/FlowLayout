package com.gjn.flowlayoutlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gjn on 2018/5/23.
 */

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        int count = getChildCount();

        int x = getPaddingLeft();
        int maxL = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childW = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childH = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            maxL = Math.max(maxL, childH);
            if (x + childW > measureWidth) {
                width = measureWidth;
                height += maxL;
                x = getPaddingLeft();
                maxL = 0;
            }
            x += childW;
            //当子孩子还未超过可用宽度则先设置第一层的宽高
            if (width < measureWidth){
                width = x + getPaddingLeft() + getPaddingRight();
                height = maxL + getPaddingTop() + getPaddingBottom();
            }
        }
        setMeasuredDimension(
                (measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth : width,
                (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight : height
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int x = getPaddingLeft(), y = getPaddingRight();
        int maxL = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childW = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childH = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            maxL = Math.max(maxL, childH);
            if (x + childW > getMeasuredWidth()) {
                x = getPaddingLeft();
                y += maxL;
                maxL = 0;
            }
            int cl = x + params.leftMargin;
            int ct = y + params.topMargin;
            int cr = cl + child.getMeasuredWidth();
            int cb = ct + child.getMeasuredHeight();
            child.layout(cl, ct, cr, cb);
            x += childW;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
