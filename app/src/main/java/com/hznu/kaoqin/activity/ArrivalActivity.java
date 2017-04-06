package com.hznu.kaoqin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hznu.kaoqin.MyApplication;
import com.hznu.kaoqin.R;
import com.hznu.kaoqin.adapters.ArrivalAdapter;
import com.hznu.kaoqin.bean.ArrivalResp;
import com.hznu.kaoqin.bean.BaseResponse;
import com.hznu.kaoqin.net.retrofit.RetrofitUtil;
import com.hznu.kaoqin.pojo.Constant;
import com.hznu.kaoqin.proxy.SPProxy;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class ArrivalActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.sp_course)
    Spinner spCourse;
    @BindView(R.id.sp_date)
    Spinner spDate;
    @BindView(R.id.rv_arrival)
    XRecyclerView rvArrival;
    @BindView(R.id.tv_no_arrival)
    TextView tvNoArrival;

    // 下拉框课程列表
    private List<String> mCourseList = new ArrayList<>();
    // 下拉框日期列表
    private List<String> mDateList = new ArrayList<>();
    // 到勤情况列表
    private List<ArrivalResp> mArrivalList = new ArrayList<>();

    private Context context = ArrivalActivity.this;

    private ArrayAdapter<String> mCourseNameAdapter;
    private ArrayAdapter<String> mDateAdapter;
    private ArrivalAdapter mArrivalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        ButterKnife.bind(this);

        // 初始化toolbar
        initToolbar();
        // 初始化课程列表
        initCourses();
        // 初始化监听事件
        initListener();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化课程名称列表
     */
    private void initCourses() {
        if (MyApplication.IS_OFFLINE) {
            mCourseList.clear();
            mCourseList.add("软件工程");
            mCourseList.add("软件过程管理");
            mCourseList.add("软件测试");
            inflateCourses();
        } else {
            String teacherId = SPProxy.getUserId(context);
            int token = SPProxy.getToken(context);
            RetrofitUtil.getCourseNames(context, teacherId, token, new Subscriber<BaseResponse<List<String>>>() {
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
                public void onNext(BaseResponse<List<String>> baseResponse) {
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
                            mCourseList.clear();
                            mCourseList.addAll(baseResponse.getResult());
                            inflateCourses();
                            spCourse.setSelection(0);
                            break;
                    }
                }
            });
        }
    }

    /**
     * 填充课程
     */
    private void inflateCourses() {
        if (mCourseNameAdapter == null) {
            mCourseNameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mCourseList);
            spCourse.setAdapter(mCourseNameAdapter);
        } else {
            mCourseNameAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        spCourse.setOnItemSelectedListener(this);
        spDate.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.sp_course:
                Log.d(Constant.Tag.NET, "click " + i);
                String courseName = mCourseList.get(i);
                Log.d(Constant.Tag.NET, "cours eis " + courseName);
                // 获取日期
                getDates(courseName);
                break;
            case R.id.sp_date:
                Log.d(Constant.Tag.NET, "trigger the date");
                String course = (String) spCourse.getSelectedItem();
                String arrivalDate = (String) spDate.getSelectedItem();
                getArrival(course, arrivalDate);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        ArrivalActivity.this.finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getDates(String courseName) {
        Log.d(Constant.Tag.NET, "getDates");
        if (MyApplication.IS_OFFLINE) {
            mDateList.clear();
            mDateList.add("2017-3-22");
            mDateList.add("2017-3-20");
            mDateList.add("2017-3-15");
            mDateList.add("2017-3-13");
            inflateDates();
        } else {
            String teacherId = SPProxy.getUserId(context);
            int token = SPProxy.getToken(context);
            RetrofitUtil.getInstance()
                    .getArrivalDate(context, teacherId, courseName, token, new Subscriber<BaseResponse<List<String>>>() {
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
                        public void onNext(BaseResponse<List<String>> baseResponse) {
                            int code = baseResponse.getCode();
                            Log.d(Constant.Tag.NET, "code is " + code);
                            switch (code) {
                                case Constant.Code.NO_USER:
                                    Toast.makeText(context, NO_USER_CHECK, Toast.LENGTH_SHORT).show();
                                    break;
                                case Constant.Code.INVALID_TOKEN:
                                    Toast.makeText(context, USER_STATUS_INVALID, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(context, LoginActivity.class));
                                    break;
                                case Constant.Code.SUCCESS:
                                    Log.d(Constant.Tag.NET, "SUCCESS");
                                    mDateList.clear();
                                    mDateList.addAll(baseResponse.getResult());
                                    inflateDates();
                                    break;
                                default:
                                    mDateList.clear();
                                    inflateDates();
                                    break;
                            }
                        }
                    });
        }
    }

    /**
     * 填充课程
     */
    private void inflateDates() {
        Log.d(Constant.Tag.NET, "infalteDates");
//        if (mDateAdapter == null) {
            mDateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mDateList);
            spDate.setAdapter(mDateAdapter);
//        } else {
//            mDateAdapter.notifyDataSetChanged();
//        }
//        spDate.setSelection(0);

        if (mDateList == null || mDateList.size() == 0) {
            Log.d(Constant.Tag.NET, "isEmpty");
            mArrivalList.clear();
            inflateArrival();
        }
    }

    /**
     * 获取到勤情况
     */
    private void getArrival(String courseName, String arrivalDate) {
        Log.d(Constant.Tag.NET, "getArrival");
        mArrivalList.clear();
        if (mDateList == null || mDateList.size() == 0) {
            rvArrival.setVisibility(View.GONE);
            tvNoArrival.setVisibility(View.VISIBLE);
            return;
        } else {
            rvArrival.setVisibility(View.VISIBLE);
            tvNoArrival.setVisibility(View.GONE);
        }

        if (MyApplication.IS_OFFLINE) {
            for (int i = 0; i < 10; i++) {
                ArrivalResp arrival = new ArrivalResp();
                arrival.setId("201235424" + i);
                arrival.setName("王毅然");
                arrival.setGender(i % 1);
                arrival.setDepartmentName("软件工程");
                arrival.setClassNumber(131);
                arrival.setCardNumber("378912348");
                arrival.setCourseId("1011");
                arrival.setArrivalDate(arrivalDate);
                arrival.setArrivalTime("12:05:00");
                mArrivalList.add(arrival);
            }
            inflateArrival();
            mArrivalAdapter.setOnItemClickListener(new ArrivalAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // TODO: 跳转
                }
            });
        } else {
            Log.d(Constant.Tag.NET, "list else");
            String teacherId = SPProxy.getUserId(context);
            int token = SPProxy.getToken(context);
            RetrofitUtil.getInstance()
                    .getArrival(context, teacherId, courseName, arrivalDate, token, new Subscriber<BaseResponse<List<ArrivalResp>>>() {
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
                        public void onNext(BaseResponse<List<ArrivalResp>> baseResponse) {
                            int code = baseResponse.getCode();
                            Log.d(Constant.Tag.NET, "--- code is " + code);
                            switch (code) {
                                case Constant.Code.NO_USER:
                                    Toast.makeText(context, NO_USER_CHECK, Toast.LENGTH_SHORT).show();
                                    break;
                                case Constant.Code.INVALID_TOKEN:
                                    Toast.makeText(context, USER_STATUS_INVALID, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(context, LoginActivity.class));
                                    break;
                                case Constant.Code.SUCCESS:
                                    Log.d(Constant.Tag.NET, "--- SUCCESS");
                                    mArrivalList.clear();
                                    mArrivalList.addAll(baseResponse.getResult());
                                    inflateArrival();
                                    break;
                                default:
                                    Log.d(Constant.Tag.NET, "default---");
                                    mArrivalList.clear();
                                    inflateArrival();
                                    break;
                                
                            }
                        }
                    });
        }
    }

    /**
     * 填充到勤信息
     */
    private void inflateArrival() {
        if (mArrivalAdapter == null) {
            mArrivalAdapter = new ArrivalAdapter(context, mArrivalList);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            rvArrival.setLayoutManager(manager);
            rvArrival.setAdapter(mArrivalAdapter);
            rvArrival.setPullRefreshEnabled(false);
            rvArrival.setLoadingMoreEnabled(false);
            Log.d(Constant.Tag.NET, "adapter null");
        } else {
            mArrivalAdapter.notifyDataSetChanged();
            Log.d(Constant.Tag.NET, "update adapter");
        }

        if (mArrivalList == null || mArrivalList.size() == 0) {
            rvArrival.setVisibility(View.GONE);
            tvNoArrival.setVisibility(View.VISIBLE);
        } else {
            rvArrival.setVisibility(View.VISIBLE);
            tvNoArrival.setVisibility(View.GONE);
        }
    }
}

