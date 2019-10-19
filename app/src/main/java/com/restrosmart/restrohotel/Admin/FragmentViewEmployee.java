package com.restrosmart.restrohotel.Admin;

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
import com.restrosmart.restrohotel.Adapter.RVViewEmployee;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_ALL_EMPLOYEE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

/**
 * Created by SHREE on 10/8/2018.
 */
public class FragmentViewEmployee extends Fragment {
    private RecyclerView recyclerView;
    private RVViewEmployee adapterViewAllEmployee;
    private FrameLayout btnAddEmp;
    private View view;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    private int hotelId, roleId;
    private ArrayList<EmployeeForm> arrayListEmployee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_all_employees, null);
        init();
        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityNewAddEmployee.class);
                startActivity(intent);
            }
        });


        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        roleId =Integer.parseInt(name_info.get(ROLE_ID));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_ALL_EMPLOYEE, (service.getallEmployees(hotelId)));
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_employee);
        btnAddEmp = (FrameLayout) view.findViewById(R.id.btn_add_emp);
        arrayListEmployee = new ArrayList<>();
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

                        JSONArray jsonArray = object.getJSONArray("allemployee");
                        arrayListEmployee.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObjectEmp = jsonArray.getJSONObject(i);
                            int empRoleId=jsonObjectEmp.getInt("Role_Id");
                            if(roleId!= empRoleId) {
                                EmployeeForm employeeForm = new EmployeeForm();
                                employeeForm.setEmpId(jsonObjectEmp.getInt("Emp_Id"));
                                employeeForm.setEmpName(jsonObjectEmp.getString("Emp_Name"));
                                employeeForm.setEmpImg(jsonObjectEmp.getString("Emp_Img"));
                                employeeForm.setEmpEmail(jsonObjectEmp.getString("Emp_Email"));
                                employeeForm.setEmpAddress(jsonObjectEmp.getString("Emp_Address"));
                                employeeForm.setUserName(jsonObjectEmp.getString("User_Name"));
                                employeeForm.setHotelName(jsonObjectEmp.getString("Hotel_Name"));
                                employeeForm.setRole(jsonObjectEmp.getString("Role"));
                                employeeForm.setEmpMob(jsonObjectEmp.getString("Emp_Mob"));
                                employeeForm.setEmpAdharId(jsonObjectEmp.getString("Emp_Adhar_Id"));

                                employeeForm.setActiveStatus(jsonObjectEmp.getInt("Active_Status"));
                                employeeForm.setRole_Id(jsonObjectEmp.getInt("Role_Id"));
                                arrayListEmployee.add(employeeForm);
                            }
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

    private void getAdapter(ArrayList<EmployeeForm> getEmployee) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterViewAllEmployee = new RVViewEmployee(getActivity(), getEmployee);
        recyclerView.setAdapter(adapterViewAllEmployee);
    }
}
