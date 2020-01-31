package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

    private ArrayList<Integer> counter = new ArrayList<Integer>();



    public RVSAEmployeeDetails(FragmentActivity activity, ArrayList<EmployeeSAHotelForm> arrayListEmployee) {
        this.context = activity;
        this.arrayListSAEmployees = arrayListEmployee;


        for (int i = 0; i < arrayListSAEmployees.size(); i++) {
            counter.add(0);
        }
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

        ArrayList<EmployeeSAForm> EmployeeSaArrayList = arrayListSAEmployees.get(position).getEmployeeSAHotelForms();

        if(EmployeeSaArrayList != null && EmployeeSaArrayList.size() > 0) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            EmployeeSADetailRVAdapter employeeSADetailRVAdapter = new EmployeeSADetailRVAdapter(context, EmployeeSaArrayList);
            myHolder.rvEmplyoeeDetails.setHasFixedSize(true);
            myHolder.rvEmplyoeeDetails.setNestedScrollingEnabled(false);
            myHolder.rvEmplyoeeDetails.setLayoutManager(linearLayoutManager);
            myHolder.rvEmplyoeeDetails.setItemAnimator(new DefaultItemAnimator());
            myHolder.rvEmplyoeeDetails.setAdapter(employeeSADetailRVAdapter);
        }
        /*else
        {
            Toast.makeText(context, "Record not found", Toast.LENGTH_SHORT).show();
        }*/
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
        private ImageView ivArrow;
        private  RecyclerView rvEmplyoeeDetails;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mHotelName=itemView.findViewById(R.id.tv_hotel_name);
            ivArrow=itemView.findViewById(R.id.ivArrow);
            rvEmplyoeeDetails=itemView.findViewById(R.id.rv_emp_details);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter.get(getAdapterPosition()) % 2 == 0) {
                        rvEmplyoeeDetails.setVisibility(View.VISIBLE);
                        ivArrow.setImageDrawable(context.getDrawable(R.drawable.ic_up_arrow_16dp));
                    } else {
                        rvEmplyoeeDetails.setVisibility(View.GONE);
                        ivArrow.setImageDrawable(context.getDrawable(R.drawable.ic_down_arrow_16dp));
                    }

                    counter.set(getAdapterPosition(), counter.get(getAdapterPosition()) + 1);
                }
            });

        /*    circleImageView = (CircleImageView) itemView.findViewById(R.id.civ_emp_profile);
            mDesignation = (TextView) itemView.findViewById(R.id.tv_emp_designation);
            mName = (TextView) itemView.findViewById(R.id.tv_emp_name);
            status = (Switch) itemView.findViewById(R.id.switch_status);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_employee);*/
        }
    }
}
