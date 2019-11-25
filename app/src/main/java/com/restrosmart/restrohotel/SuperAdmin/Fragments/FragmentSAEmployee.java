package com.restrosmart.restrohotel.SuperAdmin.Fragments;

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
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Admin.ActivityNewAddEmployee;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivityAddAdmin;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVSAEmployeeDetails;
import com.restrosmart.restrohotel.SuperAdmin.Models.EmployeeSAForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.EmployeeSAHotelForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_ALL_EMPLOYEE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class FragmentSAEmployee extends Fragment {
    private View view;
    private  RecyclerView recyclerView;
    private FrameLayout btnAddEmp;
    private ArrayList<EmployeeSAForm> arrayListEmployee;
    private  ArrayList<EmployeeSAHotelForm>  employeeSAHotelFormArrayList;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  RVSAEmployeeDetails rvsaEmployeeDetails;
    private Sessionmanager sessionmanager;
    private HashMap<String, String> superAdminInfo;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sa_employees, null);
        init();
        superAdminInfo = sessionmanager.getSuperAdminDetails();
        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAddAdmin.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_SA_ALL_EMPLOYEE, (service.getSAEmployees(Integer.parseInt(superAdminInfo.get(EMP_ID)))));
    }

    private void retrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                JsonObject jsonObject = response.body();
                String empInfo = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(empInfo);
                    int status = object.getInt("status");
                    if (status == 1) {

                        JSONArray jsonArray = object.getJSONArray("allhotels");
                        employeeSAHotelFormArrayList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject hotelObject=jsonArray.getJSONObject(i);
                            EmployeeSAHotelForm employeeSAHotelForm=new EmployeeSAHotelForm();
                            employeeSAHotelForm.setHotelName(hotelObject.getString("Hotel_Name"));


                            JSONArray employeeArray=hotelObject.getJSONArray("Employees");
                            arrayListEmployee=new ArrayList<>();
                            for (int j=0;j<employeeArray.length(); j++) {

                                JSONObject jsonObjectEmp = employeeArray.getJSONObject(j);
                                int empRoleId = jsonObjectEmp.getInt("Role_Id");
                                EmployeeSAForm employeeSAForm = new EmployeeSAForm();
                                employeeSAForm.setEmpId(jsonObjectEmp.getInt("Emp_Id"));
                                employeeSAForm.setEmpName(jsonObjectEmp.getString("Emp_Name"));
                                employeeSAForm.setEmpImg(jsonObjectEmp.getString("Emp_Img"));
                                employeeSAForm.setEmpEmail(jsonObjectEmp.getString("Emp_Email"));
                                employeeSAForm.setEmpAddress(jsonObjectEmp.getString("Emp_Address"));
                                employeeSAForm.setUserName(jsonObjectEmp.getString("User_Name"));
                                employeeSAForm.setHotelName(jsonObjectEmp.getString("Hotel_Name"));
                                employeeSAForm.setRole(jsonObjectEmp.getString("Role"));
                                employeeSAForm.setEmpMob(jsonObjectEmp.getString("Emp_Mob"));
                                employeeSAForm.setEmpAdharId(jsonObjectEmp.getString("Emp_Adhar_Id"));

                                employeeSAForm.setActiveStatus(jsonObjectEmp.getInt("Active_Status"));
                                employeeSAForm.setRole_Id(jsonObjectEmp.getInt("Role_Id"));
                                arrayListEmployee.add(employeeSAForm);
                            }
                            employeeSAHotelForm.setEmployeeSAHotelForms(arrayListEmployee);
                            employeeSAHotelFormArrayList.add(employeeSAHotelForm);

                        }
                        getAdapter(arrayListEmployee);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "RequestId" + requestId);
                Log.d("", "RetrofotError" + error);
            }
        };
    }

    private void getAdapter(ArrayList<EmployeeSAForm> arrayListEmployee) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        rvsaEmployeeDetails = new RVSAEmployeeDetails(getActivity(), employeeSAHotelFormArrayList);
        recyclerView.setAdapter(rvsaEmployeeDetails);

    }

    private void init() {
        sessionmanager=new Sessionmanager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_employee);
        btnAddEmp = (FrameLayout) view.findViewById(R.id.btn_add_emp);
        arrayListEmployee = new ArrayList<>();
        employeeSAHotelFormArrayList=new ArrayList<>();
    }

}
