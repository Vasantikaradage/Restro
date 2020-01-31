package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVAdminAssignTable;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.SelectTableListerner;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.Model.TableForm;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ALLOCATE_TABLE_CAPTAIN;
import static com.restrosmart.restrohotel.ConstantVariables.ASSIGN_TABLE_UPDATE;
import static com.restrosmart.restrohotel.ConstantVariables.GET_ALL_EMPLOYEE;
import static com.restrosmart.restrohotel.ConstantVariables.TABLE_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityAssignTable extends AppCompatActivity {

    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    private int hotelId, branchId;
    private Spinner spEmployee, spArea;
    private RecyclerView rvTableDetails;
    private Button btnSave, btnCancel, btnUpdate;
    private RetrofitService mRetrofitService;
    private Toolbar mToolbar;
    private TextView tvTitle;
    private int areaId = 0, employeeId;
    private RVAdminAssignTable rvAdminAssignTable;

    private ArrayList<EmployeeForm> employeeFormArrayList;
    private ArrayList<TableFormId> selectedTableArrayList,editedTableArrayList;
    private ArrayList<TableForm> areaArrayList;
    private ArrayList<TableFormId> arrayListtTableId;

    private LinearLayout linearLayoutNoData;
    private  int editAreaId,editEmpId,position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_table);

        init();
        setUpToolBar();
        rvTableDetails.setVisibility(View.GONE);

        HashMap<String, String> stringHashMap = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(stringHashMap.get(HOTEL_ID));


        Intent intent=getIntent();
        getEmployeeDetails();
        getAreaDetails();
        editAreaId=intent.getIntExtra("editAreaId",0);
        editEmpId=intent.getIntExtra("editEmpId",0);


        if(editEmpId!=0 && editAreaId!=0)
        {
            editedTableArrayList=intent.getParcelableArrayListExtra("tables");
            getEmployeeDetails();
            getAreaDetails();

            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tableList = getTableList();
                    retrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAssignTable.this);
                    mRetrofitService.retrofitData(ASSIGN_TABLE_UPDATE, service.assignTableUpdate(hotelId,editAreaId,editEmpId,tableList));

                }
            });


        }
        else
        {
            btnUpdate.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);
        }

        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                employeeId = employeeFormArrayList.get(i).getEmpId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaId = areaArrayList.get(i).getAreaId();
                SetRvToAdapter(areaArrayList.get(i).getArrayTableFormIds());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValid()) {
                    String tableList = getTableList();
                    retrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAssignTable.this);
                    mRetrofitService.retrofitData(ALLOCATE_TABLE_CAPTAIN, (service.allocateTable(tableList, employeeId, areaId, hotelId
                    )));
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //getTableList();

    }

    private boolean isValid() {
        if (employeeId == 0) {
            Toast.makeText(ActivityAssignTable.this, "Please Select Employee..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (areaId == 0) {
            Toast.makeText(ActivityAssignTable.this, "Please Select Area..", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setUpToolBar() {
        tvTitle.setText("Assign Table To Employee");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void getEmployeeDetails() {
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAssignTable.this);
        mRetrofitService.retrofitData(GET_ALL_EMPLOYEE, (service.getallEmployees(hotelId)));
    }

    private void getAreaDetails() {
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAssignTable.this);
        mRetrofitService.retrofitData(TABLE_DETAILS, service.tableDisplay(hotelId));
    }

    private void retrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();
                switch (requestId) {
                    case GET_ALL_EMPLOYEE:

                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                linearLayoutNoData.setVisibility(View.GONE);

                                JSONArray jsonArray = object.getJSONArray("allemployee");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject empObject = jsonArray.getJSONObject(i);

                                    String empRole = empObject.getString("Role");
                                    int activeStatus = empObject.getInt("Active_Status");

                                    if (empRole.equals("Captain") && (activeStatus == 1)) {
                                        EmployeeForm employeeForm = new EmployeeForm();
                                        employeeForm.setRole_Id(empObject.getInt("Role_Id"));
                                        employeeForm.setEmpName(empObject.getString("Emp_Name"));
                                        employeeForm.setEmpId(empObject.getInt("Emp_Id"));
                                        employeeFormArrayList.add(employeeForm);
                                    }
                                    ArrayAdapter<EmployeeForm> employeeFormArrayAdapter = new ArrayAdapter<EmployeeForm>(ActivityAssignTable.this, android.R.layout.simple_spinner_item, employeeFormArrayList);
                                    employeeFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spEmployee.setAdapter(employeeFormArrayAdapter);
                                    spArea.setSelection(0);


                                    if (editEmpId != 0) {
                                        for (position = 0; position < employeeFormArrayList.size(); position++)
                                            if (employeeFormArrayList.get(position).getEmpId() == editEmpId)
                                                break;

                                    }
                                    spEmployee.setSelection(position);
                                }
                            } else {
                                linearLayoutNoData.setVisibility(View.VISIBLE);
                                rvTableDetails.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case TABLE_DETAILS:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = object.getJSONArray("table");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject areaObject = jsonArray.getJSONObject(i);
                                    int areaStatus = areaObject.getInt("Area_Status");
                                    if (areaStatus == 1) {
                                        TableForm tableForm = new TableForm();
                                        tableForm.setAreaId(areaObject.getInt("Area_Id"));
                                        tableForm.setAreaName(areaObject.getString("Area_Name"));
                                        JSONArray array = areaObject.getJSONArray("tableList");

                                        // if(areaId==areaObject.getInt("Area_Id")) {
                                        arrayListtTableId = new ArrayList<>();
                                        for (int in = 0; in < array.length(); in++) {

                                            JSONObject jsonObject2 = array.getJSONObject(in);
                                            TableFormId tableFormId = new TableFormId();
                                            tableFormId.setTableId(jsonObject2.getInt("Table_Id"));
                                            tableFormId.setTableStatus(jsonObject2.getInt("Table_Status"));
                                            tableFormId.setTableNo(jsonObject2.getInt("Table_No"));
                                            tableFormId.setSelected(false);
                                            arrayListtTableId.add(tableFormId);
                                        }
                                        // }
                                        tableForm.setArrayTableFormIds(arrayListtTableId);
                                        areaArrayList.add(tableForm);
                                    }
                                    ArrayAdapter<TableForm> employeeFormArrayAdapter = new ArrayAdapter<TableForm>(ActivityAssignTable.this, android.R.layout.simple_spinner_item, areaArrayList);
                                    employeeFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spArea.setAdapter(employeeFormArrayAdapter);
                                    spArea.setSelection(0);

                                    if (editAreaId != 0) {
                                        for (position = 0; position < areaArrayList.size(); position++)
                                            if (areaArrayList.get(position).getAreaId() == editAreaId)
                                                break;

                                    }
                                    spArea.setSelection(position);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case ALLOCATE_TABLE_CAPTAIN:
                        try {
                            JSONObject object1 = new JSONObject(objectInfo);
                            int status = object1.getInt("status");
                            String msg = object1.getString("message");
                            if (status == 1) {
                                Toast.makeText(ActivityAssignTable.this, msg, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityAssignTable.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case ASSIGN_TABLE_UPDATE:

                        try {
                            JSONObject object1 = new JSONObject(objectInfo);
                            int status = object1.getInt("status");
                            String msg = object1.getString("message");
                            if (status == 1) {
                                Toast.makeText(ActivityAssignTable.this, msg, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityAssignTable.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }

    private void SetRvToAdapter(ArrayList<TableFormId> arrayTableFormIds) {

        if (arrayTableFormIds.size() != 0) {
            rvTableDetails.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
            rvTableDetails.setLayoutManager(gridLayoutManager);
            rvTableDetails.setHasFixedSize(true);
            rvTableDetails.getLayoutManager().setMeasurementCacheEnabled(false);
            rvAdminAssignTable = new RVAdminAssignTable(this, arrayTableFormIds, editedTableArrayList,new SelectTableListerner() {

                @Override
                public void tableListerner(ArrayList<TableFormId> arrayList) {

                    if (arrayList != null && arrayList.size() != 0) {
                        selectedTableArrayList.clear();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).isSelected()) {
                                selectedTableArrayList.add(arrayList.get(i));
                            }
                        }
                    }

                }
            });
            rvTableDetails.setAdapter(rvAdminAssignTable);


            //  getTableList();
        } else {
            rvTableDetails.setVisibility(View.GONE);
        }


    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        tvTitle = mToolbar.findViewById(R.id.tx_title);
        sessionmanager = new Sessionmanager(this);
        spEmployee = findViewById(R.id.sp_employee);
        spArea = findViewById(R.id.sp_area);
        rvTableDetails = findViewById(R.id.rv_table_details);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        linearLayoutNoData = findViewById(R.id.llNoTableData);

        employeeFormArrayList = new ArrayList<>();
        areaArrayList = new ArrayList<>();
        arrayListtTableId = new ArrayList<>();
        selectedTableArrayList = new ArrayList<>();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private String getTableList() {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedTableArrayList.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Table_Id", selectedTableArrayList.get(i).getTableId());
                jsonObject.put("Table_No",selectedTableArrayList.get(i).getTableNo());
                //jsonObject.put("Topping_Name", arrayListToppings.get(i).getStudentName());
                //    Toast.makeText(this, "id" + selectedTableArrayList.get(i).getTableId(), Toast.LENGTH_LONG).show();
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }
}
