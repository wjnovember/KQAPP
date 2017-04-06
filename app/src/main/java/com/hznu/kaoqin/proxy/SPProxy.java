package com.hznu.kaoqin.proxy;

import android.content.Context;
import android.text.TextUtils;

import com.hznu.kaoqin.pojo.Constant;
import com.hznu.kaoqin.utils.SPUtil;

/**
 * Created by 代码咖啡 on 17/3/27
 * <p>
 * Email: wjnovember@icloud.com
 */

public class SPProxy {

    /**
     * 获取IP地址
     * @param context
     * @return
     */
    public static String getIp(Context context) {
        String ip = (String) SPUtil.get(context, Constant.Key.IP, Constant.Type.STRING);
        if (TextUtils.isEmpty(ip)) {
            ip = Constant.Net.DEFAULT_IP;
        }
        return ip;
    }

    /**
     * 保存IP地址
     * @param context
     * @param ip
     */
    public static void saveIp(Context context, String ip) {
        SPUtil.save(context, Constant.Key.IP, ip);
    }

    /**
     * 获取端口号
     * @param context
     * @return
     */
    public static String getPort(Context context) {
        String port = (String) SPUtil.get(context, Constant.Key.PORT, Constant.Type.STRING);

        if (TextUtils.isEmpty(port)) {
            port = Constant.Net.DEFAULT_PORT;
        }
        return port;
    }

    /**
     * 保存端口号
     * @param context
     * @param port
     */
    public static void savePort(Context context, String port) {
        SPUtil.save(context, Constant.Key.PORT, port);
    }

    /**
     * 存放用户id
     * @param context
     * @param id
     */
    public static void saveUserId(Context context, String id) {
        SPUtil.save(context, Constant.Key.USER_ID, id);
    }

    /**
     * 获取用户id
     * @param context
     * @return
     */
    public static String getUserId(Context context) {
        String userId = (String) SPUtil.get(context, Constant.Key.USER_ID, Constant.Type.STRING);
        return userId;
    }

    /**
     * 保存token
     * @param context
     * @param token
     */
    public static void saveToken(Context context, int token) {
        SPUtil.save(context, Constant.Key.TOKEN, token);
    }

    /**
     * 获取token
     * @param context
     * @return
     */
    public static int getToken(Context context) {
        int token = (int) SPUtil.get(context, Constant.Key.TOKEN, Constant.Type.INTEGER);
        return token;
    }

    /**
     * 保存账号
     * @param context
     * @param account
     */
    public static void saveAccount(Context context, String account) {
        SPUtil.save(context, Constant.Key.ACCOUNT, account);
    }

    /**
     * 获取账号
     * @param context
     * @return
     */
    public static String getAccount(Context context) {
        String account = (String) SPUtil.get(context, Constant.Key.ACCOUNT, Constant.Type.STRING);
        return account;
    }

    /**
     * 保存密码
     * @param context
     * @param pwd
     */
    public static void savePwd(Context context, String pwd) {
        SPUtil.save(context, Constant.Key.PWD, pwd);
    }

    /**
     * 获取密码
     * @param context
     */
    public static String getPwd(Context context) {
        String pwd = (String) SPUtil.get(context, Constant.Key.PWD, Constant.Type.STRING);
        return pwd;
    }


}
