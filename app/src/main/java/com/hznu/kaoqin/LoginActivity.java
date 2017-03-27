package com.hznu.kaoqin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hznu.kaoqin.activity.HomeActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.img_avatar)
    RoundedImageView imgAvatar;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_login, R.id.tv_set_ip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_set_ip:
                startActivity(new Intent(LoginActivity.this, IPSettingsActivity.class));
                break;
            case R.id.btn_login:
                // 登录操作，添加圆形进度条
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                break;
        }
    }
}