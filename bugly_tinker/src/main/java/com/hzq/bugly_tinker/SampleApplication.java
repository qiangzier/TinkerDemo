package com.hzq.bugly_tinker;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by hezhiqiang on 2018/10/15.
 */

public class SampleApplication extends TinkerApplication {

    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.hzq.bugly_tinker.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
