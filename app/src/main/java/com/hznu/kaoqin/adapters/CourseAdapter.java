package com.hznu.kaoqin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hznu.kaoqin.R;
import com.hznu.kaoqin.bean.CourseResp;
import com.hznu.kaoqin.pojo.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 代码咖啡 on 17/3/27
 * <p>
 * Email: wjnovember@icloud.com
 */

public class CourseAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private List<CourseResp> mCourseList = new ArrayList<>();

    private OnItemClickListener mListener;

    public CourseAdapter(Context context, List<CourseResp> courseList) {
        mContext = context;

        if (courseList != null) {
            mCourseList = courseList;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_courses, parent, false);
        holder = new MyViewHolder(view);
        view.setTag(holder);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CourseResp courseResp = mCourseList.get(position);
        ((MyViewHolder)holder).initData(courseResp);
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        MyViewHolder holder = (MyViewHolder) view.getTag();
        if (mListener != null) {
            mListener.onItemClick(view, holder.getAdapterPosition());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.et_course_name)
        TextView etCourseName;
        @BindView(R.id.et_week)
        TextView etWeek;
        @BindView(R.id.et_day_in_a_week)
        TextView etDayInAWeek;
        @BindView(R.id.et_class)
        TextView etClass;
        @BindView(R.id.et_classroom)
        TextView etClassroom;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void initData(CourseResp courseResp) {
            if (courseResp == null) {
                return;
            }

            String name = courseResp.getName();
            name = TextUtils.isEmpty(name) ? "课程名找不到了" : name;

            int startWeek = courseResp.getStartWeek();
            startWeek = startWeek == 0 ? 1 : startWeek;
            int endWeek = courseResp.getEndWeek();
            endWeek = endWeek == 0 ? 16 : endWeek;
            String weeks = startWeek + "~" + endWeek + "周";

            int day = courseResp.getDayInWeek();
            day = (day - 1) % 7;
            String dayInWeek = Constant.Calendar.DAY_IN_A_WEEK[day];

            int startClass = courseResp.getStartClass();
            startClass = startClass == 0 ? 1 : startClass;
            int endClass = courseResp.getEndClass();
            endClass = endClass == 0 ? 12 : endClass;
            String classes = startClass + "~" + endClass + "节";

            String classroom = courseResp.getClassroom();
            classroom = TextUtils.isDigitsOnly(classroom) ? "教室找不到了" : classroom;

            etCourseName.setText(name);
            etWeek.setText(weeks);
            etDayInAWeek.setText(dayInWeek);
            etClass.setText(classes);
            etClassroom.setText(classroom);
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);
    }
}
