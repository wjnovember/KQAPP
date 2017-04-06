package com.hznu.kaoqin.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hznu.kaoqin.MyApplication;
import com.hznu.kaoqin.R;
import com.hznu.kaoqin.adapters.CourseAdapter;
import com.hznu.kaoqin.bean.BaseResponse;
import com.hznu.kaoqin.bean.CourseResp;
import com.hznu.kaoqin.bean.TeacherCoursesResp;
import com.hznu.kaoqin.net.Net;
import com.hznu.kaoqin.net.retrofit.RetrofitUtil;
import com.hznu.kaoqin.pojo.Constant;
import com.hznu.kaoqin.proxy.SPProxy;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rv_course)
    RecyclerView rvCourse;
    @BindView(R.id.tv_no_course)
    TextView tvNoCourse;
    @BindView(R.id.fab_kaoqin)
    FloatingActionButton fabKaoqin;

    private TextView tvUsername;

    private long mExitTime = 0;     // 两次点击的起始时间

    private List<CourseResp> mCourseList = new ArrayList<>();

    private CourseAdapter mAdapter;


    
    private Context context = HomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        // 初始化资源
        initRes();
        // 初始化Intent传值
        initIntent();
        // 初始化标题栏
        initToolbar();
        // 初始化抽屉式布局及导航栏
        initDrawerNav();
        // 初始化列表
        initList();
    }

    /**
     * 初始化Intent传值
     */
    private void initIntent() {
        if (tvUsername == null) {
            // 获取headerlayout中的tvUsername
            ConstraintLayout navLayout = (ConstraintLayout) navView.inflateHeaderView(R.layout.nav_header_home);
            tvUsername = (TextView) navLayout.findViewById(R.id.tv_username);
        }

        Intent intent = getIntent();
        String username = "用户名丢失";
        if (intent != null) {
            username = intent.getStringExtra(Constant.Key.USER_NAME);

        }
        if (!MyApplication.IS_OFFLINE) {
            tvUsername.setText(username);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化抽屉式布局及导航栏
     */
    private void initDrawerNav() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 初始化列表
     */
    private void initList() {
        // 根据是否在离线模式下使用模拟数据
        if (MyApplication.IS_OFFLINE) {
            tvNoCourse.setVisibility(View.GONE);
            initData();
            inflateItems();
            mAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(HomeActivity.this, CourseDetailActivity.class));
                }
            });
        } else {
            String teacherId = SPProxy.getUserId(context);
            int token = SPProxy.getToken(context);
            RetrofitUtil.getCourses(context, teacherId, token, new Subscriber<BaseResponse>() {
                        @Override
                        public void onCompleted() {
                            unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            unsubscribe();
                            Toast.makeText(context, CONNECT_FAILED, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            int code = baseResponse.getCode();
                            switch (code) {
                                case Constant.Code.NO_USER:
                                    Toast.makeText(context, NO_USER_CHECK, Toast.LENGTH_SHORT).show();
                                    break;
                                case Constant.Code.INVALID_TOKEN:
                                    Toast.makeText(context, USER_STATUS_INVALID, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(context, LoginActivity.class));
                                    break;
                                case Constant.Code.SUCCESS:
                                    TeacherCoursesResp tc = (TeacherCoursesResp) baseResponse.getResult();
                                    mCourseList.clear();
                                    mCourseList.addAll(tc.getCourses());
                                    if (mCourseList == null || mCourseList.size() == 0) {
                                        rvCourse.setVisibility(View.GONE);
                                        tvNoCourse.setVisibility(View.VISIBLE);
                                    } else {
                                        rvCourse.setVisibility(View.VISIBLE);
                                        tvNoCourse.setVisibility(View.GONE);
                                        inflateItems();
                                        mAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                CourseResp courseResp = mCourseList.get(position);
                                                String courseId = courseResp.getId();
                                                Intent intent = new Intent(context, CourseDetailActivity.class);
                                                intent.putExtra(Constant.Key.COURSE_ID, courseId);
                                                context.startActivity(intent);
                                            }
                                        });
                                    }
                                    break;
                            }
                        }
                    });
        }
    }

    /**
     * 离线模式下使用虚拟数据
     */
    private void initData() {
        mCourseList.clear();
        for (int i = 0; i < 20; i++) {
            CourseResp courseResp = new CourseResp();
            courseResp.setId("1011");
            courseResp.setName("软件过程管理");
            courseResp.setStartWeek(1);
            courseResp.setEndWeek(16);
            courseResp.setDayInWeek(3);
            courseResp.setStartClass(3);
            courseResp.setEndClass(5);
            courseResp.setTeacherId("2013213121");
            courseResp.setClassroom("33-309");
            mCourseList.add(courseResp);
        }
    }

    /**
     * 填充子项
     */
    private void inflateItems() {
        if (mAdapter == null) {
            mAdapter = new CourseAdapter(context, mCourseList);
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rvCourse.setLayoutManager(manager);
        rvCourse.setAdapter(mAdapter);
        fabKaoqin.attachToRecyclerView(rvCourse);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.nav_logout:
                new AlertDialog.Builder(context)
                        .setMessage(WHETHER_TO_LOGOUT)
                        .setPositiveButton(CONFIRM, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 退出登录
                                logout();
                            }
                        })
                        .setNegativeButton(NOT_SURE, null)
                        .show();
                break;
            case R.id.nav_ip_settings:
                startActivity(new Intent(context, IPSettingsActivity.class));
                break;
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * 退出登录
     */
    private void logout() {
        // 根据是否启动离线模式判断是否要网络请求
        if (MyApplication.IS_OFFLINE) {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        } else {
            String id = SPProxy.getUserId(context);
            int token = SPProxy.getToken(context);
            RetrofitUtil.logout(context, id, token, new Subscriber<BaseResponse>() {
                        @Override
                        public void onCompleted() {
                            unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            unsubscribe();
                            Toast.makeText(context, LOGOUT_FAILED, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();
                        }
                    });
        }
    }

    /**
     * 返回键按键事件监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果点击home返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 返回到桌面
            backToDesktop();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回桌面
     */
    public void backToDesktop() {
        // 如果两次点击时间间隔大于2秒
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            // 提示再次点击，返回桌面
            Toast.makeText(this, BACK_TO_DESKTOP, Toast.LENGTH_LONG).show();
            // 时间标记从当前时间重新开始
            mExitTime = System.currentTimeMillis();
        } else {
            // 返回桌面的意图
            Intent mIntent = new Intent(Intent.ACTION_MAIN);
            mIntent.addCategory(Intent.CATEGORY_HOME);
            startActivity(mIntent);
        }
    }

    @OnClick({R.id.fab_kaoqin})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_kaoqin:
                startActivity(new Intent(context, ArrivalActivity.class));
                break;
        }
    }
}
