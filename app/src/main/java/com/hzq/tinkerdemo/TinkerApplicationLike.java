package com.hzq.tinkerdemo;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.hzq.tinkerdemo.log.MyLogImp;
import com.hzq.tinkerdemo.utils.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by hezhiqiang on 2018/10/12.
 * 使用DefaultLifeCycle注解生成Application（这种方式是Tinker官方推荐的）
 */

@DefaultLifeCycle(
        application = "com.hzq.tinkerdemo.MyApplication", // application类名。只能用字符串，这个MyApplication文件是不存在的，但可以在AndroidManifest.xml的application标签上使用（name）
        loaderClass = "com.tencent.tinker.loader.TinkerLoader", //loaderClassName, 我们这里使用默认即可!（可不写）
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false
)
public class TinkerApplicationLike extends DefaultApplicationLike {
    private Application mApplication;
    private Context mContext;
    private Tinker mTinker;

    //固定写法
    public TinkerApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    // 固定写法
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        mApplication = getApplication();
        mContext = getApplication();
        initTinker(base);
    }

    private void initTinker(Context base) {
        // tinker需要你开启MultiDex
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);
        //设置全局异常捕获
        TinkerManager.initFastCrashProtect();
        //开启升级重试功能（在安装Tinker之前设置）
        TinkerManager.setUpgradeRetryEnable(true);
        //设置Tinker日志输出类
        TinkerManager.setLogIml(new MyLogImp());
        //安装Tinker（在加载完multiDex之后，否则你需要将com.tencent.tinker.**手动放到main dex中）
        TinkerManager.installTinker(this);
        mTinker = Tinker.with(getApplication());

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
