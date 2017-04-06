package com.hznu.kaoqin.pojo;

import retrofit2.http.PUT;

/**
 * Created by 代码咖啡 on 17/3/26
 * <p>
 * Email: wjnovember@icloud.com
 */

public class Constant {

    /**
     * 数据类型
     */
    public static class Type {
        public static final int INTEGER = 0x1101;
        public static final int STRING = 0x1102;
        public static final int FLOAT = 0x1103;
        public static final int LONG = 0x1104;
        public static final int BOOLEAN = 0x1105;
    }

    /**
     * 键值对的KEY
     */
    public static class Key {
        public static final String IP = "key_ip";
        public static final String PORT = "key_port";
        public static final String USER_ID = "key_user_id";
        public static final String TOKEN = "key_token";
        public static final String ACCOUNT = "key_account";
        public static final String PWD = "key_pwd";
        public static final String USER_NAME = "key_user_name";
        public static final String COURSE_ID = "key_course_id";
    }

    /**
     * 网络接口相关
     */
    public static class Net {
        public static final String DEFAULT_IP = "127.0.0.1";
        public static final String DEFAULT_PORT = "80";

        public static final String API_NAME = "kqapi";
        public static final String API_LOGIN = "login.php";

        public static final int READ_TIME_OUT = 6;
        public static final int CONNECT_TIME_OUT = 5;
    }

    /**
     * 标签
     */
    public static class Tag {
        public static final String NET = "tag_net";
    }

    /**
     * 网络接口返回的代码
     */
    public static class Code {

        public static final int SUCCESS = 600;

        public static final int CONNECT_FAILED = 410;
        public static final int NO_USER = 411;
        public static final int ACCESS_DENIED = 412;
        public static final int LONG_TIME_NO_LOGIN = 413;
        public static final int NOT_ONLINE = 414;
        public static final int INVALID_TOKEN = 415;
        public static final int INVALID_PARAM = 416;
        public static final int CACHE_FAILED = 417;
        public static final int NO_COURSE = 418;
        public static final int NO_STUDENT = 419;
    }

    /**
     * 日期相关
     */
    public static class Calendar {
        public static final String[] DAY_IN_A_WEEK = new String[] {
                "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
        };
    }

    /**
     * 时间相关
     */
    public static class Time {
        public static final int DEPLAY_JUMP = 2;
    }

    /**
     * 性别相关
     */
    public static class Gender {
        public static final int MALE = 0;
        public static final int FEMALE = 1;
    }
}
