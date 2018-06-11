package com.gjn.flowlayoutlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by gjn on 2018/5/29.
 */

public abstract class TagAdapter<T> extends FlowAdapter<T> {

    public TagAdapter(Context context, int layoutId, List<T> data) {
        super(context, layoutId, data);
    }

    @Override
    public View onCreateView(FlowLayout layout) {
        return LayoutInflater.from(context).inflate(LayoutId, layout, false);
    }
}
