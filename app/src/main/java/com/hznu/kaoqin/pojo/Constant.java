package com.hznu.kaoqin.pojo;

/**
 * Created by 代码咖啡 on 17/3/26
 * <p>
 * Email: wjnovember@icloud.com
 */

public class Constant {

    public static class Type {
        public static final int INTEGER = 0x1101;
        public static final int STRING = 0x1102;
        public static final int FLOAT = 0x1103;
        public static final int LONG = 0x1104;
        public static final int BOOLEAN = 0x1105;
    }

    public static class Net {
        public static final String DEFAULT_IP = "127.0.0.1";
        public static final String DEFAULT_PORT = "3306";
        public static final String KEY_IP = "key_ip";
        public static final String KEY_PORT = "key_port";
    }

    public static class Tag {
        public static final String NET = "tag_net";
    }
}
