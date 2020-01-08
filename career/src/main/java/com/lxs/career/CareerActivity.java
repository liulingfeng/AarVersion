package com.lxs.career;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lxs.common.StringUtils;

/**
 * @author liuxiaoshuai
 * @date 2019-12-25
 * @desc
 * @email liulingfeng@mistong.com
 */
public class CareerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career);
        TextView tv = findViewById(R.id.tv);
        tv.setText("去你麻辣隔壁的");
        ImageView iv = findViewById(R.id.iv);
        Glide.with(this).load("https://bbs.ewt360.com/uc_server/avatar.php?uid=5176227&size=small").into(iv);
    }
}
