package com.hznu.kaoqin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hznu.kaoqin.R;

/**
 * Created by 代码咖啡 on 17/3/29
 * <p>
 * Email: wjnovember@icloud.com
 */

public class BaseActivity extends AppCompatActivity {

    protected String LOGOUT_FAILED;
    protected String CONNECT_FAILED;
    protected String NO_USER_CHECK;
    protected String USER_STATUS_INVALID;
    protected String INVALID_IP;
    protected String INVALID_PORT;
    protected String LACK_OF_ACCOUNT_PWD;
    protected String BACK_TO_DESKTOP;
    protected String WHETHER_TO_LOGOUT;
    protected String CONFIRM;
    protected String NOT_SURE;
    protected String INVALID_ACCOUNT_PWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化资源
        initRes();
    }

    /**
     * 初始化资源
     */
    protected void initRes() {
        LOGOUT_FAILED = getString(R.string.logout_failed);
        CONNECT_FAILED = getString(R.string.connect_failed);
        NO_USER_CHECK = getString(R.string.no_user_check);
        USER_STATUS_INVALID = getString(R.string.user_status_invalid);
        INVALID_IP = getString(R.string.invalid_ip);
        INVALID_PORT = getString(R.string.invalid_port);
        LACK_OF_ACCOUNT_PWD = getString(R.string.lack_of_account_pwd);
        BACK_TO_DESKTOP = getString(R.string.back_to_desktop);
        WHETHER_TO_LOGOUT = getString(R.string.wether_to_logout);
        CONFIRM = getString(R.string.confirm);
        NOT_SURE = getString(R.string.not_sure);
        INVALID_ACCOUNT_PWD = getString(R.string.invalid_account_pwd);
    }
}
