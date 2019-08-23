package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterRVScratchcard;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.Model.ScratchCardForm;
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

import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.UPDATE_EMP_IMAGE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

public class ActivityAddScratchCard extends AppCompatActivity {

    private RecyclerView rvScratchCard;
    private ArrayList<ScratchCardForm> scratchCardFormArrayList;
    private TextView tvToolBarTitle,tvSelectMenu;
    private Toolbar mToolbar;
    private Spinner spParentcategory;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  int mHotelId,mBranchId;
    private  Sessionmanager sharedPreferanceManage;
    private  ArrayList<ParentCategoryForm> parentCategoryFormArrayList;

    private String date[] = {"17 Jun", "18 Jun", "19 Jun", "20 Jun"};
    private int count[] = {13, 5, 21, 50};

    private String messgae[] = {"Customer can get a ScratchCard and win 13 food Surprises offer ir valid till Jun 3-5", "Customer can get a ScratchCard and win 5  food Surprises offer ir valid till Jun 15-17",
            "Customer can get a ScratchCard and win 21 food Surprises offer ir valid till Jun 13-25", "Customer can get a ScratchCard and win 50 food Surprises offer ir valid till Jun 1-15"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scratchcard);

        init() ;
        setUpToolBar();

        for(int i=0; i<date.length; i++)
        {
            ScratchCardForm scratchCardForm=new ScratchCardForm();
            scratchCardForm.setCount(count[i]);
            scratchCardForm.setDate(date[i]);
            scratchCardForm.setMessage(messgae[i]);
            scratchCardFormArrayList.add(scratchCardForm);
        }


        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));

        ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
        parentCategoryForm.setPc_id(0);
        parentCategoryForm.setName("Select Category");
        parentCategoryFormArrayList.add(parentCategoryForm);



       /* initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddScratchCard.this);
        mRetrofitService.retrofitData(PARENT_CATEGORY, (service.getParentCategory(mHotelId,
                mBranchId)));*/



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvScratchCard.setHasFixedSize(true);
        rvScratchCard.setLayoutManager(linearLayoutManager);
        AdapterRVScratchcard adapterRVScratchcard = new AdapterRVScratchcard(getApplicationContext(), scratchCardFormArrayList);
        rvScratchCard.scrollToPosition(scratchCardFormArrayList.size() - 1);
        rvScratchCard.setAdapter(adapterRVScratchcard);

        tvSelectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ActivityAddScratchCard.this,ActivitySelectMenu.class);
                startActivity(intent);

            }
        });


    }

    private void initRetrofitCallBack() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                String objectInfo=jsonObject.toString();

                switch (requestId){
                    case PARENT_CATEGORY:
                    try {
                        JSONObject object=new JSONObject(objectInfo);
                        int status=object.getInt("status");
                        if(status==1){

                            JSONArray jsonArray=object.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
                                parentCategoryForm.setPc_id(jsonObject1.getInt("Pc_Id"));
                                parentCategoryForm.setName(jsonObject1.getString("Name"));
                                parentCategoryFormArrayList.add(parentCategoryForm);
                            }
                            ArrayAdapter<ParentCategoryForm> arrayAdapter=new ArrayAdapter<ParentCategoryForm>(ActivityAddScratchCard.this,R.layout.item_category,parentCategoryFormArrayList);
                           // spParentcategory.setAdapter(arrayAdapter);

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

    private void setUpToolBar() {
        tvToolBarTitle.setText("Scratch Card");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        rvScratchCard=findViewById(R.id.rv_scratch_card);
        scratchCardFormArrayList=new ArrayList<>();
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
      //  spParentcategory=findViewById(R.id.sp_parent_category);
        sharedPreferanceManage = new Sessionmanager(ActivityAddScratchCard.this);
        parentCategoryFormArrayList=new ArrayList<>();
        tvSelectMenu=findViewById(R.id.tv_select_menu);
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
