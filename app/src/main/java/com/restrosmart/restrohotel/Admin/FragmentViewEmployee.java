package com.restrosmart.restrohotel.Admin;
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.restrosmart.restrohotel.Adapter.RVViewEmployee;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHREE on 10/8/2018.
 */
public class FragmentViewEmployee extends Fragment {
    private RecyclerView recyclerView;
    private RVViewEmployee adapterViewAllEmployee;
    private FrameLayout btnAddEmp;
    private View view;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_all_employees, null);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityNewAddEmployee.class);
                startActivity(intent);
            }
        });
        retrofitCallBack();
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_employee);
        btnAddEmp=(FrameLayout) view.findViewById(R.id.btn_add_emp);
    }

    @Override
    public void onResume() {
        super.onResume();
        retrofitCallBack();
    }

    private void retrofitCallBack() {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<EmployeeForm>> call= service.getallEmployees("1","1");

        call.enqueue(new Callback<List<EmployeeForm>>() {
            @Override
            public void onResponse(Call<List<EmployeeForm>> call, Response<List<EmployeeForm>> response) {
                List<EmployeeForm> getEmployee =  response.body();
                getData(getEmployee);
            }

            @Override
            public void onFailure(Call<List<EmployeeForm>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData(List<EmployeeForm> getEmployee) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterViewAllEmployee = new RVViewEmployee(getActivity(),getEmployee);
        recyclerView.setAdapter(adapterViewAllEmployee);
    }
}
