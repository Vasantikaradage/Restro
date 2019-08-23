package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVActiveTables;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.TableFormId;
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

import static com.restrosmart.restrohotel.ConstantVariables.ACTIVE_TABLE;
import static com.restrosmart.restrohotel.ConstantVariables.USER_RATING;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class fragmentDashboard extends Fragment {
    private View view;
   private RecyclerView rvTables,rvRating;
   private RetrofitService mRetrofitService;
   private IResult  mResultCallBack;
   private  int mHotelId,mBranchId;
   private ArrayList<TableFormId> arrayListTable;
   private  ArrayList<String> arrayListRating;
   private LinearLayout linearLayoutNoData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_dashboard, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        Sessionmanager sharedPreferanceManage = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));

        table();
        customerRating();
    }

    private void customerRating() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(USER_RATING, (service.activeTable(mBranchId,
                mHotelId)));
    }

    private void table() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(ACTIVE_TABLE, (service.activeTable(mBranchId,
                mHotelId)));
    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject object=response.body();
                String tableinfo=object.toString();

                try {
                    JSONObject jsonObject=new JSONObject(tableinfo);
                    int status=jsonObject.getInt("status");
                    if(status==1)
                    {
                        JSONArray jsonArray=jsonObject.getJSONArray("tableList");
                        arrayListTable.clear();
                        linearLayoutNoData.setVisibility(View.GONE);
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            TableFormId tableFormId=new TableFormId();
                            tableFormId.setTableId(jsonObject1.getInt("Table_Id"));
                            arrayListTable.add(tableFormId);
                        }
                        callAdapter();

                    }
                    else
                    {
                        linearLayoutNoData.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","requestId"+requestId);
                Log.d("","retrofitError"+error);

            }
        };
    }

    private void callAdapter() {

        if (arrayListTable != null && arrayListTable.size() > 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            rvTables.setLayoutManager(gridLayoutManager);
            RVActiveTables rvActivetables = new RVActiveTables(getActivity(), arrayListTable);
            rvTables.setAdapter(rvActivetables);
        }
        else
        {
            linearLayoutNoData.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        rvTables=view.findViewById(R.id.rv_tables);
      //  rvRating=view.findViewById(R.id.rv_customer_rating);
        arrayListTable=new ArrayList<>();
        arrayListRating=new ArrayList<>();
        linearLayoutNoData=view.findViewById(R.id.llNoTableData);


    }
}
