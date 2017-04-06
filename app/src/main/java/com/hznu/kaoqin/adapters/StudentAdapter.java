package com.hznu.kaoqin.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hznu.kaoqin.R;
import com.hznu.kaoqin.bean.StudentResp;
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

public class StudentAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private List<StudentResp> mStudentList = new ArrayList<>();

    private OnItemClickListener mListener;

    public StudentAdapter(Context context, List<StudentResp> studentList) {
        mContext = context;

        if (studentList != null) {
            mStudentList = studentList;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_students, parent, false);
        holder = new MyViewHolder(view);
        view.setTag(holder);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StudentResp studentResp = mStudentList.get(position);
        ((MyViewHolder) holder).initData(studentResp);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
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

        @BindView(R.id.tv_student_name)
        TextView tvStudentName;
        @BindView(R.id.img_gender)
        ImageView imgGender;
        @BindView(R.id.tv_profession)
        TextView tvProfession;
        @BindView(R.id.tv_class)
        TextView tvClass;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void initData(StudentResp studentResp) {
            if (studentResp == null) {
                return;
            }

            String studentName = studentResp.getName();
            if (TextUtils.isEmpty(studentName)) {
                studentName = "学生姓名找不到了...";
            }
            tvStudentName.setText(studentName);

            int gender = studentResp.getGender();
            if (gender == Constant.Gender.MALE) {
                imgGender.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_male));
            } else {
                imgGender.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_female));
            }

            String profession = studentResp.getDepartmentName();
            if (TextUtils.isEmpty(profession)) {
                profession = "专业找不到了...";
            }
            tvProfession.setText(profession);

            int clazz = studentResp.getClassNumber();
            tvClass.setText(clazz + "");


        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);
    }
}
