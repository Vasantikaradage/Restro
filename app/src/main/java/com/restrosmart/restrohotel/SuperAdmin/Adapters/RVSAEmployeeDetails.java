package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivitySAEmpolyeeProfile;
import com.restrosmart.restrohotel.SuperAdmin.Models.EmployeeSAForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.EmployeeSAHotelForm;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RVSAEmployeeDetails extends RecyclerView.Adapter<RVSAEmployeeDetails.MyHolder> {

    List<EmployeeSAHotelForm> arrayListSAEmployees;
    private Context context;
    private View view;
    private int emp_id, status1;
    // private Sessionmanager sessionmanager;
    //private String branchId, hotelId, status_value;
    //private Animator currentAnimator;
    // private int shortAnimationDuration;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private String status_value;


    public RVSAEmployeeDetails(FragmentActivity activity, ArrayList<EmployeeSAHotelForm> arrayListEmployee) {
        this.context = activity;
        this.arrayListSAEmployees = arrayListEmployee;
    }

    @NonNull
    @Override
    public RVSAEmployeeDetails.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_hotel_emp_list, viewGroup, false);
        MyHolder vh = new MyHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVSAEmployeeDetails.MyHolder myHolder, final int position) {
        myHolder.mHotelName.setText(arrayListSAEmployees.get(position).getHotelName());
      //  myHolder.mDesignation.setText(arrayListSAEmployees.get(position).getRole());
        //status1 = (arrayListSAEmployees.get(position).getActiveStatus());

        //     sessionmanager = new Sessionmanager(context);
        //  HashMap<String, String> name_info = sessionmanager.getHotelDetails();

        //  hotelId = name_info.get(HOTEL_ID);

       // String path = arrayListSAEmployees.get(position).getEmpImg();
       // Picasso.with(context).load(path).into(myHolder.circleImageView);

    /*    holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.circleImageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
            }
        });

        shortAnimationDuration = context.getResources().getInteger(
                android.R.integer.config_shortAnimTime);
*/
      /*  if (status1 == 1) {
            myHolder.status.setChecked(true);
        } else {
            myHolder.status.setChecked(false);
        }
*/
       /* myHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                emp_id = arrayListSAEmployees.get(position).getEmpId();
                if (myHolder.status.isChecked()) {
                    status_value = "1";
                    statusChange();
                } else {
                    status_value = "0";
                    statusChange();
                }
            }
        });*/

     /*   myHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Intent intent = new Intent(context, ActivitySAEmpolyeeProfile.class);
                intent.putExtra("empId", arrayListSAEmployees.get(position).getEmpId());
                intent.putExtra("empRole", arrayListSAEmployees.get(position).getRole());
                intent.putParcelableArrayListExtra("Emp_detail", (ArrayList<? extends Parcelable>) arrayListSAEmployees);
                context.startActivity(intent);*//*
            }
        });*/
    }

    private void statusChange() {

        retrofitCallBack();
        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, context);
       /* mRetrofitService.retrofitData(EMPLOYEE_STATUS, (apiService.updateStatus(emp_id,
                status_value,
                (Integer.parseInt(hotelId)))));*/
    }

    private void retrofitCallBack() {
    }

    @Override
    public int getItemCount() {
        return arrayListSAEmployees.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView mName,mHotelName;
        private TextView mDesignation;
        private RelativeLayout relativeLayout;
        private Switch status;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mHotelName=itemView.findViewById(R.id.tv_hotel_name);

        /*    circleImageView = (CircleImageView) itemView.findViewById(R.id.civ_emp_profile);
            mDesignation = (TextView) itemView.findViewById(R.id.tv_emp_designation);
            mName = (TextView) itemView.findViewById(R.id.tv_emp_name);
            status = (Switch) itemView.findViewById(R.id.switch_status);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_employee);*/
        }
    }
}
