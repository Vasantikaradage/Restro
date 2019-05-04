package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterTableDisplay;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;
import static com.restrosmart.restrohotel.ConstantVariables.UPDATE_TABLE_STATUS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityTableInformation extends AppCompatActivity {
    private TextView txTitle;
    private Toolbar mTopToolbar;
    private RecyclerView mRecyclerView;
    private ArrayList<TableFormId> arrayListTableIds;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private int mHotelId, mBranchId, position;
    private int tableStatus;
    private AdapterTableDisplay adapterTableDisplay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_info);
        init();
        setUpToolBar();

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityTableInformation.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));

        Intent intent = getIntent();
        arrayListTableIds = intent.getExtras().getParcelableArrayList("tableIds");


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        adapterTableDisplay = new AdapterTableDisplay(this, arrayListTableIds, new StatusListener() {
            @Override
            public void statusListern(int position1, int status) {
                initRetrofitCallBack();
                tableStatus = status;
                position = position1;
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityTableInformation.this);
                mRetrofitService.retrofitData(UPDATE_TABLE_STATUS, service.TableStatus(tableStatus, arrayListTableIds.get(position).getTableId(), mHotelId,
                        mBranchId));


            }
        });

        mRecyclerView.setAdapter(adapterTableDisplay);


    }


    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId) {
                    case UPDATE_TABLE_STATUS:

                        JsonObject object = response.body();
                        String value = object.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(value);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityTableInformation.this, "Table Status Updated Successfully", Toast.LENGTH_LONG).show();
                                arrayListTableIds.get(position).setTableStatus(tableStatus);
                                adapterTableDisplay.refreshList(arrayListTableIds);
                            } else {
                                Toast.makeText(ActivityTableInformation.this, "Try Again..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }


    private void init() {
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        mRecyclerView = findViewById(R.id.recycler_table_details);
    }

    private void setUpToolBar() {
        setSupportActionBar(mTopToolbar);
        txTitle.setText("Table Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
