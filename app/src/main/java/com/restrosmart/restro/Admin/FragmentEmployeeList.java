package com.restrosmart.restro.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restro.Adapter.RVEmployeesAdapter;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Model.GetEmployeeDetails;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHREE on 10/8/2018.
 */

public class FragmentEmployeeList extends Fragment {

    private View view;
    private RecyclerView rvEmployees;
    private RVEmployeesAdapter adapterViewAllEmployee;
    private TextView btnAddEmp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_employee_list, container, false);

        init();

        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewEmployee.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getEmployeesList();
    }

    private void getEmployeesList() {

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<GetEmployeeDetails>> call = service.getallEmployees("1", "1");

        call.enqueue(new Callback<List<GetEmployeeDetails>>() {
            @Override
            public void onResponse(Call<List<GetEmployeeDetails>> call, Response<List<GetEmployeeDetails>> response) {
                List<GetEmployeeDetails> getEmployee = response.body();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvEmployees.setHasFixedSize(true);
                rvEmployees.setLayoutManager(linearLayoutManager);
                adapterViewAllEmployee = new RVEmployeesAdapter(getContext(), getEmployee);
                rvEmployees.setAdapter(adapterViewAllEmployee);
            }

            @Override
            public void onFailure(Call<List<GetEmployeeDetails>> call, Throwable throwable) {
                Log.v("Retrofit Error", String.valueOf(throwable));
            }
        });
    }

    private void init() {
        rvEmployees = view.findViewById(R.id.rvEmployees);
        btnAddEmp = view.findViewById(R.id.btnAddEmp);
    }
}
