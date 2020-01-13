package com.lxs.pay;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lxs.common.StringUtils;

/**
 * @author liuxiaoshuai
 * @date 2019-12-20
 * @desc
 * @email liulingfeng@mistong.com
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView tv = findViewById(R.id.tv);
        tv.setText(StringUtils.getStr());
    }
}
