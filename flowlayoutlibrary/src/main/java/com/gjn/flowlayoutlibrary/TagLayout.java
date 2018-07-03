package com.gjn.flowlayoutlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjn on 2018/5/24.
 */

public class TagLayout extends FlowLayout implements FlowAdapter.onDataChangeListener {
    private static final String TAG = "TagLayout";
    //所有viewList
    private List<View> viewList = new ArrayList<>();
    //需要设置textviewList
    private List<TextView> textViewList = new ArrayList<>();
    //选中的viewList
    private List<View> selectList = new ArrayList<>();
    //点击监听
    private onItemListener onItemListener;
    //选择监听
    private onSelectListener onSelectListener;
    //最大选择数量
    private int selectMax = -1;
    //适配器
    private FlowAdapter adapter;
    //是否限制点击
    boolean isLimitClick;

    public TagLayout(@NonNull Context context) {
        this(context, null);
    }

    public TagLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(FlowAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setonDataChangeListener(this);
        updateView();
    }

    private void updateView() {
        removeAllViews();
        viewList.clear();
        textViewList.clear();
        selectList.clear();
        if (adapter.getData() == null || adapter.getData().size() == 0) {
            Log.w(TAG, "adapter data is null.");
            return;
        }
        for (int i = 0; i < adapter.getCount(); i++) {
            //设置view和textview
            View view = adapter.onCreateView(this);
            TextView textView = adapter.onBindTextView(view, i, adapter.getItem(i));
            //设置事件
            addListener(i, view);
            //加入list
            viewList.add(view);
            textViewList.add(textView);
        }
        //绘制list
        createView();
        //绘制选中list
        selectView();
    }

    private void addListener(final int i, final View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIndex(i);
                if (onItemListener != null) {
                    onItemListener.onClick(i, view, adapter.getData().get(i));
                }
            }
        });
    }

    private void createView() {
        for (View view : viewList) {
            addView(view);
        }
    }

    private void selectView() {
        for (int i = 0; i < viewList.size(); i++) {
            View view = viewList.get(i);
            if (selectList.contains(view)){
                textViewList.get(i).setSelected(true);
            }else {
                textViewList.get(i).setSelected(false);
            }
        }
        if (onSelectListener != null) {
            List<Integer> selectIntegers = new ArrayList<>();
            List<View> selectViews = new ArrayList<>();
            List selectDatas = new ArrayList<>();

            for (int i = 0; i < textViewList.size(); i++) {
                if (textViewList.get(i).isSelected()) {
                    selectViews.add(viewList.get(i));
                    selectIntegers.add(i);
                    selectDatas.add(adapter.getItem(i));
                }
            }
            onSelectListener.onSelect(selectViews, selectIntegers, selectDatas);
        }
    }

    public void setSelectMax(int count) {
        if (count <= 0 || count > viewList.size()) {
            Log.w(TAG, "The number exceeds the set.");
            count = -1;
        }
        selectMax = count;
    }

    public boolean isLimitClick() {
        return isLimitClick;
    }

    public void setLimitClick(boolean limitClick) {
        isLimitClick = limitClick;
    }

    public void setOnSelectListener(TagLayout.onSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public void setOnItemListener(TagLayout.onItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void selectIndex(int index) {
        if (index > viewList.size() || index < 0) {
            Log.e(TAG, "index is error. size = " + viewList.size() + ",index = " + index);
            return;
        }
        View view = viewList.get(index);

        if (selectMax > 0) {
            if (selectList.contains(view)) {
                selectList.remove(view);
            }else {
                if (selectList.size() < selectMax) {
                    selectList.add(view);
                }else if (selectList.size() == selectMax) {
                    if (!isLimitClick) {
                        List<View> temp = new ArrayList<>();
                        temp.addAll(selectList);
                        temp.add(view);
                        temp.remove(0);
                        selectList.clear();
                        selectList.addAll(temp);
                    }
                }
            }
        } else {
            if (selectList.contains(view)) {
                selectList.remove(view);
            }else {
                selectList.add(view);
            }
        }
        selectView();
    }

    @Override
    public void notifyData() {
        Log.i(TAG, "data notify");
        updateView();
    }

    public interface onItemListener<T> {
        void onClick(int index, View view, T t);
    }

    public interface onSelectListener {
        void onSelect(List<View> selectViews, List<Integer> selectIntegers, List<Object> selectDatas);
    }
}
