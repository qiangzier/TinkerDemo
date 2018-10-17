package com.hzq.tinkerdemo.utils;

import android.widget.Toast;

import com.hzq.tinkerdemo.crash.TinkerUncaughtExceptionHandler;
import com.hzq.tinkerdemo.reporter.SampleLoadReporter;
import com.hzq.tinkerdemo.reporter.SamplePatchListener;
import com.hzq.tinkerdemo.reporter.SamplePatchReporter;
import com.hzq.tinkerdemo.reporter.SampleTinkerReport;
import com.hzq.tinkerdemo.service.SampleResultService;
import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;

/**
 * Created by hezhiqiang on 2018/10/12.
 */

public class TinkerManager {
    private static final String TAG = "Tinker.TinkerManager";

    private static ApplicationLike applicationLike;
    private static TinkerUncaughtExceptionHandler uncaughtExceptionHandler;
    private static boolean isInstalled = false;

    public static void setTinkerApplicationLike(ApplicationLike appLike) {
        applicationLike = appLike;
    }

    public static ApplicationLike getTinkerApplicationLike() {
        return applicationLike;
    }

    /**
     * 初始化全局异常铺或
     */
    public static void initFastCrashProtect() {
        if(uncaughtExceptionHandler == null) {
            uncaughtExceptionHandler = new TinkerUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
    }

    /**
     * 开启或关闭补丁重试功能
     * @param enable
     */
    public static void setUpgradeRetryEnable(boolean enable) {
        UpgradePatchRetry.getInstance(applicationLike.getApplication()).setRetryEnable(enable);
    }

    /**
     * 默认安装Tinker（使用默认的report类：DefaultLoadReporter,DefaultPatchReporter、DefaultPatchListener、DefaultTinkerResultService）
     * 如果你不需要监听app打补丁的情况（如：当打补丁失败是上传失败信息），则使用该方法
     * @param appLike
     */
    public static void installDefaultTinker(ApplicationLike appLike) {
        if(isInstalled) {
            TinkerLog.w(TAG,"install tinker,but has installed, ignore");
            return;
        }

        TinkerInstaller.install(appLike);
        isInstalled = true;
    }

    /**
     * 自定义安装Tinker（使用你自定义的reporter类：SampleLoadReporter、SamplePatchReporter、SamplePatchListener、SampleResultService）
     * @param appLike
     */
    public static void installTinker(final ApplicationLike appLike) {
        if(isInstalled) {
            TinkerLog.w(TAG,"install tinker,but has installed, ignore");
            return;
        }

        //or you can just use DefaultLoadReporter
        LoadReporter loadReporter = new SampleLoadReporter(appLike.getApplication());

        PatchReporter patchReporter = new SamplePatchReporter(appLike.getApplication());

        PatchListener patchListener = new SamplePatchListener(appLike.getApplication());

        SampleTinkerReport.setReporter(new SampleTinkerReport.Reporter() {
            @Override
            public void onReport(int key) {

            }

            @Override
            public void onReport(String message) {
                Toast.makeText(appLike.getApplication(), message, Toast.LENGTH_SHORT).show();
            }
        });

        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(appLike,loadReporter,patchReporter,patchListener, SampleResultService.class,upgradePatchProcessor);

        isInstalled = true;
    }

    /**
     * 设置日志
     * @param imp
     */
    public static void setLogIml(TinkerLog.TinkerLogImp imp) {
        //设置Tinker日志输出类
        TinkerInstaller.setLogIml(imp);
    }
}
