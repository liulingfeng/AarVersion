package com.lxs.core;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.zibin.luban.Luban;

/**
 * @author liuxiaoshuai
 * @date 2020-01-16
 * @desc
 * @email liulingfeng@mistong.com
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        Luban.with(this);
    }
}
