package com.hzq.tinkerdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

/**
 * Created by hezhiqiang on 2018/10/11.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txt = findViewById(R.id.txt);
        txt.setText("test tinker success");

        TextView txt1 = findViewById(R.id.txt1);
        txt1.setText("测试修复资源");


    }

    public void install_patch(View view) {
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
//        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), getCacheDir().getAbsolutePath() + "/patch_signed_7zip.apk");
    }

    public void uninstall_patch(View view) {
        ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
        Tinker.with(getApplicationContext()).cleanPatch();
    }
}
