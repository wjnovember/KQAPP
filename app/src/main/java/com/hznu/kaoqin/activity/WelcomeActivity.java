package com.hznu.kaoqin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hznu.kaoqin.MyApplication;
import com.hznu.kaoqin.R;
import com.hznu.kaoqin.bean.BaseResponse;
import com.hznu.kaoqin.bean.TeacherResp;
import com.hznu.kaoqin.net.Net;
import com.hznu.kaoqin.net.retrofit.RetrofitUtil;
import com.hznu.kaoqin.pojo.Constant;
import com.hznu.kaoqin.proxy.SPProxy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    @BindView(R.id.tv_offline)
    TextView tvOffline;
    private String id = null;
    private int token = 0;
    
    private Context context = WelcomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        // 获取id和token
        id = SPProxy.getUserId(context);
        token = SPProxy.getToken(context);

        // 如果不存在id或token，则跳转登录界面
        if (TextUtils.isEmpty(id) || token == 0) {
            delayJump(Constant.Time.DEPLAY_JUMP, LoginActivity.class);
        } else { // 否则访问网络，查看是否在线
            judgeUserStatus();
        }
    }

    /**
     * 判断用户是否在线
     */
    private void judgeUserStatus() {
        RetrofitUtil.getInstance()
                .isOnline(context, id, token, new Subscriber<BaseResponse<TeacherResp>>() {
                    @Override
                    public void onCompleted() {
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, CONNECT_FAILED, Toast.LENGTH_SHORT).show();
                        unsubscribe();
                        btnRefresh.setVisibility(View.VISIBLE);
                        tvOffline.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onNext(BaseResponse<TeacherResp> baseResponse) {
                        // 获取接口代码判断当前账户是否在线
                        int code = baseResponse.getCode();
                        switch (code) {
                            case Constant.Code.SUCCESS: // 在线
                                TeacherResp teacherResp = baseResponse.getResult();
                                String id = teacherResp.getId();
                                String name = teacherResp.getName();
                                Log.d(Constant.Tag.NET, "teacher is " + id);
                                delayJump(Constant.Time.DEPLAY_JUMP, HomeActivity.class, name);
                                break;
                            default: // 不在线，跳转登录界面
                                delayJump(Constant.Time.DEPLAY_JUMP, LoginActivity.class);
                                break;
                        }
                    }
                });
    }

    private void delayJump(int seconds, Class cls) {
        delayJump(seconds, cls, null);
    }

    /**
     * 延迟跳转
     *
     * @param seconds
     */
    private void delayJump(int seconds, final Class cls, final String userName) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, cls);
                if (!TextUtils.isEmpty(userName)) {
                    intent.putExtra(Constant.Key.USER_NAME, userName);
                }
                startActivity(intent);
                finish();
            }
        }, seconds * 1000);
    }

    @OnClick({R.id.btn_refresh, R.id.tv_offline})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh:
                judgeUserStatus();
                break;
            case R.id.tv_offline:
                MyApplication.IS_OFFLINE = true;
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                break;
        }
    }
}
