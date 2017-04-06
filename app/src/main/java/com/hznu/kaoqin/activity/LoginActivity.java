package com.hznu.kaoqin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hznu.kaoqin.MyApplication;
import com.hznu.kaoqin.R;
import com.hznu.kaoqin.bean.BaseResponse;
import com.hznu.kaoqin.bean.LoginResp;
import com.hznu.kaoqin.net.Net;
import com.hznu.kaoqin.pojo.Constant;
import com.hznu.kaoqin.proxy.SPProxy;
import com.hznu.kaoqin.net.retrofit.RetrofitUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.img_avatar)
    RoundedImageView imgAvatar;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // 初始化用户信息
        initUserInfo();

    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        String account = SPProxy.getAccount(context);
        String pwd = SPProxy.getPwd(context);

        etAccount.setText(account);
        etPwd.setText(pwd);

        etAccount.setSelection(account.length());
    }

    @OnClick({R.id.btn_login, R.id.tv_set_ip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_set_ip:
                startActivity(new Intent(context, IPSettingsActivity.class));
                break;
            case R.id.btn_login:
                String account = etAccount.getText().toString();
                String pwd = etPwd.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(this, LACK_OF_ACCOUNT_PWD, Toast.LENGTH_SHORT).show();
                    return;
                }

                SPProxy.saveAccount(context, account);
                SPProxy.savePwd(context, pwd);

                // 根据是否启用离线模式，判断是否网络请求
                if (MyApplication.IS_OFFLINE) {
                    startActivity(new Intent(context, HomeActivity.class));
                } else {
                    RetrofitUtil.getInstance()
                            .login(context, account, pwd, new Subscriber<BaseResponse<LoginResp>>() {
                                @Override
                                public void onCompleted() {
                                    unsubscribe();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(context, CONNECT_FAILED, Toast.LENGTH_SHORT).show();
                                    unsubscribe();
                                }

                                @Override
                                public void onNext(BaseResponse<LoginResp> baseResponse) {
                                    int code = baseResponse.getCode();
                                    Log.i(Constant.Tag.NET, "code is " + code);
                                    Log.i(Constant.Tag.NET, "6");

                                    switch (code) {
                                        case Constant.Code.SUCCESS:
                                            Log.i(Constant.Tag.NET, "7");
                                            LoginResp loginRes = (LoginResp) baseResponse.getResult();
                                            Log.i(Constant.Tag.NET, "8");
                                            String id = loginRes.getId();
                                            int token = loginRes.getToken();
                                            String name = loginRes.getName();
                                            Log.i(Constant.Tag.NET, "id -- " + id + "; token -- " + token);
                                            SPProxy.saveUserId(context, id);
                                            SPProxy.saveToken(context, token);

                                            Intent intent = new Intent(context, HomeActivity.class);
                                            intent.putExtra(Constant.Key.USER_NAME, name);
                                            // 登录操作，添加圆形进度条
                                            startActivity(intent);
                                            break;
                                        default:
                                            Toast.makeText(context, INVALID_ACCOUNT_PWD, Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            });
                }

                break;
        }
    }
}