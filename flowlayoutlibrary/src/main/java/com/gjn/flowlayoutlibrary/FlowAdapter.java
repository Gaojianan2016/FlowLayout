package com.gjn.flowlayoutlibrary;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjn on 2018/5/24.
 */

public abstract class FlowAdapter<T> {
    protected Context context;
    protected int LayoutId;
    protected List<T> data;
    protected onDataChangeListener dataChangeListener;

    public FlowAdapter(Context context, int layoutId, List<T> data) {
        this.context = context;
        LayoutId = layoutId;
        this.data = data == null ? new ArrayList<T>(): data;
    }

    public void setData(List<T> data) {
        if (data == null) {
            this.data.clear();
            notifyData();
            return;
        }
        this.data.clear();
        add(data);
    }

    private void notifyData() {
        if (dataChangeListener != null) {
            dataChangeListener.notifyData();
        }
    }

    public List<T> getData() {
        return data;
    }

    public T getItem(int pos){
        return data.get(pos);
    }

    public void add(List<T> data){
        this.data.addAll(data);
        notifyData();
    }

    public void add(T t){
        data.add(t);
        notifyData();
    }

    public void add(int pos, T t){
        data.add(pos, t);
        notifyData();
    }

    public void remove(int pos){
        data.remove(pos);
        notifyData();
    }

    public void remove(T t){
        data.remove(t);
        notifyData();
    }

    public void change(int pos, T t){
        data.set(pos, t);
        notifyData();
    }

    public boolean isCanClick(int pos, T t){
        return true;
    }

    public int getCount(){
        return data.size();
    }

    public void setonDataChangeListener(onDataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
    }

    public abstract View onCreateView(FlowLayout tagLayout);

    public abstract TextView onBindTextView(View view, int i, T t);

    public interface onDataChangeListener {
        void notifyData();
    }
}
