package com.hznu.kaoqin.utils;

/**
 * Created by 代码咖啡 on 17/3/26
 * <p>
 * Email: wjnovember@icloud.com
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.hznu.kaoqin.pojo.Constant;

public class SPUtil {

    private static SharedPreferences mInstance;
    private static SharedPreferences.Editor editor;

    private static final String FILE_PREFERENCES = "carInspection";

    /**
     * 存入数据
     * @param context
     * @param key
     * @param value
     */
    public static void save(Context context, String key, Object value) {
        if (null == mInstance) {
            mInstance = context.getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);
        }
        if (null == editor) {
            editor = mInstance.edit();
        }

        // 判断value的类型，并存入value
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        }  else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }

        editor.commit();
        editor = null;
        mInstance = null;
    }

    /**
     * 从sharedpreferences里面获取数据
     * @param context
     * @param key
     * @param valueType
     * @return
     */
    public static Object get(Context context, String key, int valueType) {
        Object result = null;
        mInstance = context.getSharedPreferences(FILE_PREFERENCES, Activity.MODE_WORLD_READABLE);
        // 判断要获取的数据的类型
        switch (valueType) {
            case Constant.Type.INTEGER:
                result = mInstance.getInt(key, 0);
                break;
            case Constant.Type.STRING:
                result = mInstance.getString(key, "");
                break;
            case Constant.Type.FLOAT:
                result = mInstance.getFloat(key, 0);
                break;
            case Constant.Type.LONG:
                result = mInstance.getLong(key, 0);
                break;
            case Constant.Type.BOOLEAN:
                result = mInstance.getBoolean(key, false);
                break;
        }
        mInstance = null;
        return result;
    }
}