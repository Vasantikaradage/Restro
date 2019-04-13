package com.restrosmart.restrohotel.Adapter;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Admin.ActivityEmpolyeeProfile;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Model.GetEmployeeDetails;
import com.restrosmart.restrohotel.R;

import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 10/9/2018.
 */

public class RVViewEmployee extends RecyclerView.Adapter<RVViewEmployee.MyHolder> {

    List<GetEmployeeDetails> viewEmployees;

    private Context context;

    private int emp_id, status1;

    private Sessionmanager sessionmanager;
    private String branchId, hotelId, status_value;

    public RVViewEmployee(FragmentActivity activity, List<GetEmployeeDetails> getEmployee) {

        this.context = activity;
        this.viewEmployees = getEmployee;
    }

    @NonNull
    @Override
    public RVViewEmployee.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_view_employee_list, parent, false);
        MyHolder vh = new MyHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVViewEmployee.MyHolder holder, final int position) {


        holder.mName.setText(viewEmployees.get(position).getEmpName());
        holder.mDesignation.setText(viewEmployees.get(position).getRole());
        status1 = (viewEmployees.get(position).getActiveStatus());

        sessionmanager = new Sessionmanager(context);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();

        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);

        if (status1 == 1) {
            holder.status.setChecked(true);
        } else {
            holder.status.setChecked(false);
        }

        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (holder.status.isChecked()) {
                    status_value = "1";
                } else {
                    status_value = "0";
                }
                emp_id = viewEmployees.get(position).getEmpId();

                ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                retrofit2.Call<JsonObject> call = apiService.updateStatus(emp_id,
                        status_value,
                        Integer.parseInt(branchId),
                        (Integer.parseInt(hotelId)));

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                        response.body();
                        String msg = String.valueOf(response.body());
                        Toast.makeText(context, "Status Updated Successfully..", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                    }
                });


            }
        });


        String path = viewEmployees.get(position).getEmpImg().toString();

        Picasso.with(context).load(path).into(holder.circleImageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityEmpolyeeProfile.class);
                intent.putExtra("empId", viewEmployees.get(position).getEmpId());
                intent.putExtra("empRole", viewEmployees.get(position).getRole());
                intent.putParcelableArrayListExtra("Emp_detail", (ArrayList<? extends Parcelable>) viewEmployees);
                context.startActivity(intent);


            }
        });

       /* holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ActivityAddNewEmployee.class);

                int empbranchId=viewEmployees.get(position).getBranch_Id();
                intent.putExtra("empId",viewEmployees.get(position).getEmpId()) ;
                intent.putExtra("empName",viewEmployees.get(position).getEmpName()) ;
                intent.putExtra("empEmail",viewEmployees.get(position).getEmpEmail()) ;
                intent.putExtra("empMobile",viewEmployees.get(position).getEmpMob()) ;
                intent.putExtra("empAddress",viewEmployees.get(position).getEmpAddress()) ;
                intent.putExtra("empUserName",viewEmployees.get(position).getUserName()) ;
                intent.putExtra("empDesId",viewEmployees.get(position).getRole_Id());
                intent.putExtra("empBranchId",viewEmployees.get(position).getBranch_Id());
                intent.putExtra("empAdhar",viewEmployees.get(position).getEmpAdharId());
                intent.putExtra("Password",viewEmployees.get(position).getPassword());
                intent.putExtra("ConPass",viewEmployees.get(position).getConPassword());
                context.startActivity(intent);

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return viewEmployees.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;

        TextView mName;
        TextView mDesignation;

        RelativeLayout relativeLayout;
        Switch status;
        ImageView imageView;


        public MyHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.civ_emp_profile);
            mDesignation = (TextView) itemView.findViewById(R.id.tv_emp_designation);
            mName = (TextView) itemView.findViewById(R.id.tv_emp_name);
            //imageView = (ImageView)itemView.findViewById(R.id.btn_edit);
            status = (Switch) itemView.findViewById(R.id.switch_status);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_employee);
        }
    }
}
