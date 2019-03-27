package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.restrosmart.restro.Admin.ActivityEmpolyeeProfile;
import com.restrosmart.restro.Admin.AddNewEmployee;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Model.GetEmployeeDetails;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHREE on 10/9/2018.
 */

public class RVEmployeesAdapter extends RecyclerView.Adapter<RVEmployeesAdapter.ItemViewHolder> {

    private Context mContext;
    private List<GetEmployeeDetails> viewEmployees;
    private String status_value;
    private String emp_id;
    private String status1;

    public RVEmployeesAdapter(Context context, List<GetEmployeeDetails> getEmployee) {

        this.mContext = context;
        this.viewEmployees = getEmployee;
    }

    @NonNull
    @Override
    public RVEmployeesAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_employee_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVEmployeesAdapter.ItemViewHolder holder, final int position) {

        holder.mName.setText(viewEmployees.get(position).getEmpName());
        //  holder.mMobno.setText(viewEmployees.get(position).getEmpMob());
        holder.mDesignation.setText(viewEmployees.get(position).getRole());

        // emp_id = viewEmployees.get(position).getEmpName();
        status1 = String.valueOf(viewEmployees.get(position).getActiveStatus());


        switch (status1) {
            case "1":
                holder.status.setChecked(true);
                break;

            case "2":
                holder.status.setChecked(false);
                break;

            default:
                holder.status.setChecked(true);
        }


        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (holder.status.isChecked()) {
                    status_value = "1";
                } else {

                    status_value = "2";
                }
                emp_id = viewEmployees.get(position).getEmpName();

                ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                retrofit2.Call<List<GetEmployeeDetails>> call = apiService.updateStatus(emp_id, status_value);

                call.enqueue(new Callback<List<GetEmployeeDetails>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<GetEmployeeDetails>> call, Response<List<GetEmployeeDetails>> response) {
                        response.body();
                        String msg = String.valueOf(response.body());
                        //  Toast.makeText(context, "rdtytt"+details, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<GetEmployeeDetails>> call, Throwable t) {

                    }
                });
            }
        });

        String path = viewEmployees.get(position).getEmpImg().toString();

        Picasso.with(mContext).load(path).into(holder.circleImageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ActivityEmpolyeeProfile.class);

                intent.putExtra("empId", viewEmployees.get(position).getEmpId());

                intent.putParcelableArrayListExtra("Emp_detail", (ArrayList<? extends Parcelable>) viewEmployees);

                mContext.startActivity(intent);


            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddNewEmployee.class);
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return viewEmployees.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView mName, mDesignation;
        private RelativeLayout relativeLayout;
        private Switch status;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.civ_emp_profile);
            mDesignation = itemView.findViewById(R.id.tv_emp_designation);
            mName = itemView.findViewById(R.id.tv_emp_name);
            imageView = itemView.findViewById(R.id.btn_edit);
            status = itemView.findViewById(R.id.switch_status);
            relativeLayout = itemView.findViewById(R.id.relative_employee);
        }
    }
}
