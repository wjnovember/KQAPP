package com.hznu.kaoqin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hznu.kaoqin.MyApplication;
import com.hznu.kaoqin.R;
import com.hznu.kaoqin.adapters.StudentAdapter;
import com.hznu.kaoqin.bean.BaseResponse;
import com.hznu.kaoqin.bean.CourseResp;
import com.hznu.kaoqin.bean.StudentResp;
import com.hznu.kaoqin.bean.StudentsCourseResp;
import com.hznu.kaoqin.net.Net;
import com.hznu.kaoqin.net.retrofit.RetrofitUtil;
import com.hznu.kaoqin.pojo.Constant;
import com.hznu.kaoqin.proxy.SPProxy;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class CourseDetailActivity extends BaseActivity {

    @BindView(R.id.tv_course_name)
    TextView tvCourseName;
    @BindView(R.id.tv_course_round)
    TextView tvCourseRound;
    @BindView(R.id.tv_classroom)
    TextView tvClassroom;
    @BindView(R.id.tv_weeks)
    TextView tvWeeks;
    @BindView(R.id.tv_day_in_week)
    TextView tvDayInWeek;
    @BindView(R.id.tv_classes)
    TextView tvClasses;
    @BindView(R.id.rv_students)
    XRecyclerView rvStudents;
    @BindView(R.id.tv_no_student)
    TextView tvNoStudent;
    @BindView(R.id.app_bar)
    Toolbar toolBar;

    private String mCourseId;
    private CourseResp mCourseResp;
    private List<StudentResp> mStudentList = new ArrayList<>();

    private StudentAdapter mAdapter;

    private Context context = CourseDetailActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);

        // 获取资源
        initRes();
        // 初始化标题栏
        initToolbar();
        // 初始化intent
        initIntent();
        // 初始化数据
        initData();
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        CourseDetailActivity.this.finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 初始化intent
     */
    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mCourseId = intent.getStringExtra(Constant.Key.COURSE_ID);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (MyApplication.IS_OFFLINE) {
            mCourseResp = new CourseResp();
            mCourseResp.setId("1011");
            mCourseResp.setName("软件工程");
            mCourseResp.setStartWeek(1);
            mCourseResp.setEndWeek(16);
            mCourseResp.setDayInWeek(3);
            mCourseResp.setStartClass(1);
            mCourseResp.setEndWeek(3);
            mCourseResp.setTeacherId("2013213121");
            mCourseResp.setClassroom("33-309");

            for (int i = 0; i < 17; i++) {
                StudentResp student = new StudentResp();
                student.setId("2013425313");
                student.setName("王毅然");
                student.setGender(0);
                student.setDepartmentName("软件工程");
                student.setClassNumber(131);
                student.setCardNumber("32485937");
                student.setCourseId("1011");
                mStudentList.add(student);
            }

            inflateItems();
            inflateCourse();
            mAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 跳转学生信息详情
                }
            });
        } else {
            String teacherId = SPProxy.getUserId(context);
            int token = SPProxy.getToken(context);
            RetrofitUtil.getInstance()
                    .getStudentsByCourse(context, mCourseId, teacherId, token, new Subscriber<BaseResponse>() {
                        @Override
                        public void onCompleted() {
                            unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            unsubscribe();
                            Log.e(Constant.Tag.NET, "error is " + e.getMessage());
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
                                    StudentsCourseResp sc = (StudentsCourseResp) baseResponse.getResult();
                                    mCourseResp = sc.getCourse();
                                    mStudentList.clear();
                                    mStudentList.addAll(sc.getStudents());

                                    if (mCourseResp != null) {
                                        inflateCourse();
                                    }
                                    if (mStudentList == null || mStudentList.size() == 0) {
                                        rvStudents.setVisibility(View.GONE);
                                        tvNoStudent.setVisibility(View.VISIBLE);
                                    } else {
                                        rvStudents.setVisibility(View.VISIBLE);
                                        tvNoStudent.setVisibility(View.GONE);
                                        inflateItems();
                                        mAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
//                                                StudentResp studentResp = mStudentList.get(position);
//                                                String courseId = studentResp.getId();
//                                                Intent intent = new Intent(context, CourseDetailActivity.class);
//                                                intent.putExtra(Constant.Key.COURSE_ID, courseId);
//                                                context.startActivity(intent);
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
     * 填充列表
     */
    private void inflateItems() {
        if (mAdapter == null) {
            mAdapter = new StudentAdapter(context, mStudentList);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            rvStudents.setLayoutManager(manager);
            rvStudents.setAdapter(mAdapter);
            rvStudents.setPullRefreshEnabled(false);
            rvStudents.setLoadingMoreEnabled(false);
        }
    }

    /**
     * 填充课程信息
     */
    private void inflateCourse() {
        tvCourseRound.setText(mCourseResp.getName().substring(0, 1));
        tvCourseName.setText(mCourseResp.getName());
        tvClassroom.setText(mCourseResp.getClassroom());
        tvWeeks.setText(mCourseResp.getStartWeek() + "~" + mCourseResp.getEndWeek() + "周");
        int i = mCourseResp.getDayInWeek();
        tvDayInWeek.setText(Constant.Calendar.DAY_IN_A_WEEK[i]);
        tvClasses.setText(mCourseResp.getStartClass() + "~" + mCourseResp.getEndClass() + "节");
    }
}
