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
import com.hznu.kaoqin.bean.ArrivalResp;
import com.hznu.kaoqin.pojo.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 代码咖啡 on 17/3/29
 * <p>
 * Email: wjnovember@icloud.com
 */

public class ArrivalAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private List<ArrivalResp> mArrivalList = new ArrayList<>();

    private OnItemClickListener mListener;

    public ArrivalAdapter(Context context, List<ArrivalResp> arrivalList) {
        mContext = context;

        if (arrivalList != null) {
            mArrivalList = arrivalList;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_arrivals, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setTag(holder);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArrivalResp arrivalResp = mArrivalList.get(position);
        ((MyViewHolder) holder).initData(arrivalResp);
    }

    @Override
    public int getItemCount() {
        return mArrivalList.size();
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

        @BindView(R.id.tv_st_name)
        TextView tvStName;
        @BindView(R.id.img_gender)
        ImageView imgGender;
        @BindView(R.id.tv_st_id)
        TextView tvStId;
        @BindView(R.id.tv_st_profession)
        TextView tvStProfession;
        @BindView(R.id.tv_st_class)
        TextView tvStClass;
        @BindView(R.id.tv_arrival_time)
        TextView tvArrivalTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void initData(ArrivalResp arrivalResp) {
            if (arrivalResp == null) {
                return;
            }

            String stName = arrivalResp.getName();
            if (TextUtils.isEmpty(stName)) {
                stName = "学生姓名找不到了...";
            }
            tvStName.setText(stName);

            int gender = arrivalResp.getGender();
            if (gender == Constant.Gender.MALE) {
                imgGender.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_male));
            } else {
                imgGender.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_female));
            }

            String stId = arrivalResp.getId();
            if (TextUtils.isEmpty(stId)) {
                stId = "学号丢失了...";
            }
            tvStId.setText(stId);

            String profession = arrivalResp.getDepartmentName();
            if (TextUtils.isEmpty(profession)) {
                profession = "专业不名...";
            }
            tvStProfession.setText(profession);

            int classNumber = arrivalResp.getClassNumber();
            tvStClass.setText(classNumber + "班");

            String arrivalTime = arrivalResp.getArrivalTime();
            if (TextUtils.isEmpty(arrivalTime)) {
                arrivalTime = "时间不名...";
            } else {
                arrivalTime = arrivalTime.substring(0, arrivalTime.lastIndexOf(":"));
            }
            tvArrivalTime.setText(arrivalTime);

        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);
    }
}
