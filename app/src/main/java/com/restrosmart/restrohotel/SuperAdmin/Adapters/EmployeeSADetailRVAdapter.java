package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivitySaAdminProfile;
import com.restrosmart.restrohotel.SuperAdmin.Models.EmployeeSAForm;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public  class EmployeeSADetailRVAdapter  extends RecyclerView.Adapter<EmployeeSADetailRVAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<EmployeeSAForm> employeeSAFormArrayList;

    public EmployeeSADetailRVAdapter(Context context, ArrayList<EmployeeSAForm> employeeSAFormArrayList) {
        this.mContext=context;
        this.employeeSAFormArrayList=employeeSAFormArrayList;
    }

    @NonNull
    @Override
    public EmployeeSADetailRVAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.emp_sa_list_item, viewGroup, false);
        ItemViewHolder vh = new ItemViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeSADetailRVAdapter.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvEmpName.setText(employeeSAFormArrayList.get(i).getEmpName());
        itemViewHolder.tvEmpEmail.setText(employeeSAFormArrayList.get(i).getEmpEmail());
        itemViewHolder.tvEmpPhone.setText(employeeSAFormArrayList.get(i).getEmpMob());
        Picasso.with(mContext).load(employeeSAFormArrayList.get(i).getEmpImg()).into(itemViewHolder.mCircularImageView);

        if(employeeSAFormArrayList.get(i).getActiveStatus()==1)
        {
            itemViewHolder.mSwitch.setChecked(true);
        }
        else
        {
            itemViewHolder.mSwitch.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return employeeSAFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEmpName, tvEmpEmail,tvEmpPhone;
        private Switch mSwitch;
        private CircleImageView mCircularImageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmpName=itemView.findViewById(R.id.tv_emp_name);
            tvEmpEmail =itemView.findViewById(R.id.tv_emp_email);
            tvEmpPhone=itemView.findViewById(R.id.tv_emp_phone);
            mSwitch=itemView.findViewById(R.id.switch_status);
            mCircularImageView=itemView.findViewById(R.id.civ_emp_profile);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, ActivitySaAdminProfile.class);
                    intent.putExtra("employeeForm",employeeSAFormArrayList.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
