# FlowLayout
自定义流式布局

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.Gaojianan2016:FlowLayout:1.0.6'
}
```

# 基础使用

```
package com.gjn.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gjn.flowlayoutlibrary.TagAdapter;
import com.gjn.flowlayoutlibrary.TagLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TagAdapter<String> adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TagLayout tl = findViewById(R.id.tl);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add("add " + adapter.getCount());
            }
        });

        adapter = new TagAdapter<String>(this, R.layout.tv_str, null) {
            @Override
            public TextView onBindTextView(View view, int i, String s) {
                TextView textView = (TextView) view;
                textView.setText(s);
                return textView;
            }
        };

        tl.setAdapter(adapter);

        list.add("Welcome to flowlayout");
        list.add("Hello Android");
        list.add("btn one");
        list.add("test all");

        adapter.add(list);

        tl.setOnItemListener(new TagLayout.onItemListener() {
            @Override
            public void onClick(int i, View view, Object o) {
                Log.e("TAG", "点击监听 " + i);
                Log.e("TAG", "==================2");
            }
        });
        //设置最大可选  超过数量范围将设置为-1 即可以无限选择
        tl.setSelectMax(2);

        tl.setOnSelectListener(new TagLayout.onSelectListener() {
            @Override
            public void onSelect(List<View> list, List<Integer> list1, List<Object> list2) {
                Log.e("TAG", "选择监听");
                Log.e("TAG", "当前选中项 ");
                for (Integer integer : list1) {
                    Log.e("TAG", "=>"+integer);
                }
                Log.e("TAG", "==================1");
            }
        });
    }
}

```
