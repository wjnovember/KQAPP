package com.hznu.kaoqin.net;

import android.content.Context;

import com.hznu.kaoqin.pojo.Constant;
import com.hznu.kaoqin.proxy.SPProxy;

/**
 * Created by 代码咖啡 on 17/3/27
 * <p>
 * Email: wjnovember@icloud.com
 */

public class Net {

    /**
     * 获取基url
     * @param context
     * @return
     */
    public static String getBaseUrl(Context context) {
        String url = "http://";
        url += SPProxy.getIp(context) + ":" + SPProxy.getPort(context) + "/" + Constant.Net.API_NAME + "/";
        return url;
    }

    /**
     * 获取登录接口url
     * @param context
     * @return
     */
    public static String getLoginUrl(Context context) {
        return getBaseUrl(context) + Constant.Net.API_LOGIN + "/";
    }
}
