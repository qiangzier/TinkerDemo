package com.hzq.bugly_tinker;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Application application = SampleApplicationLike.getInstance().getApplication();

        TextView textView = findViewById(R.id.text);
//        textView.setText("测试差量包安装");
    }
}
