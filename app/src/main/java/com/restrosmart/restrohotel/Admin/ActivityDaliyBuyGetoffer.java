package com.restrosmart.restrohotel.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVDailyOfferBuyGetList;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
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
import static com.restrosmart.restrohotel.ConstantVariables.APPLY_DAILYOFFER;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityDaliyBuyGetoffer  extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private  TextView tvBuyCount,tvGetCount,tvToolBarTitle;
    private  Sessionmanager mSessionManager;
    private Button btnBuyselectMenu,btnGetSelectMenu,submit;
    private RecyclerView rvBuyMenu,rvGetMenu;
    private  int offerId,offerTypeId,hotelId;
    private   Intent intent;
    private ArrayList<MenuDisplayForm> arrayListBuyMenu,arrayListGetMenu;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  Sessionmanager sessionmanager;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_buy_get_offer);

        init();
        setUpToolBar();

        intent=getIntent();
        offerId=intent.getIntExtra("offerId",0);
        offerTypeId=intent.getIntExtra("offerTypeId",0);

        HashMap<String, String> stringHashMap = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(stringHashMap.get(HOTEL_ID));

        btnGetSelectMenu.setOnClickListener(ActivityDaliyBuyGetoffer.this);
        btnBuyselectMenu.setOnClickListener(ActivityDaliyBuyGetoffer.this);
        submit.setOnClickListener(ActivityDaliyBuyGetoffer.this);

        tvGetCount.setText("Buy Menu Count "+String.valueOf(intent.getIntExtra("getCnt",0)) );
        tvBuyCount.setText("Get Menu Count "+String.valueOf(intent.getIntExtra("buyCnt",0) ));
    }

    private void setUpToolBar() {
        tvToolBarTitle.setText("Daily Offer");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mSessionManager = new Sessionmanager(this);
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        tvBuyCount=findViewById(R.id.tv_buy_cnt);
        tvGetCount=findViewById(R.id.tv_get_cnt);
        btnBuyselectMenu=findViewById(R.id.btn_buy_select_menu);
        btnGetSelectMenu=findViewById(R.id.btn_get_select_menu);
        rvBuyMenu=findViewById(R.id.rv_buy_menu);
        rvGetMenu=findViewById(R.id.rv_get_menu);
        submit=findViewById(R.id.btn_menu_submit);
        arrayListBuyMenu=new ArrayList<>();
        arrayListGetMenu=new ArrayList<>();
        sessionmanager=new Sessionmanager(this);
        jsonArray=new JSONArray();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_buy_select_menu:
                Intent intentBuy=new Intent(ActivityDaliyBuyGetoffer.this,ActivitySelectMenu.class);
                intentBuy.putExtra("buyCnt",intent.getIntExtra("buyCnt",0));
                intentBuy.putExtra("offerTypeId", offerTypeId);
                intentBuy.putExtra("offerId", offerId);
                startActivityForResult(intentBuy,200);
                break;

            case R.id.btn_get_select_menu:
                Intent intentGet=new Intent(ActivityDaliyBuyGetoffer.this,ActivitySelectMenu.class);
                intentGet.putExtra("getCnt",intent.getIntExtra("getCnt",0));
                intentGet.putExtra("offerTypeId", offerTypeId);
                intentGet.putExtra("offerId", offerId);
                startActivityForResult(intentGet,300);
                break;

            case R.id.btn_menu_submit:

                if(arrayListBuyMenu!=null && arrayListBuyMenu.size()>0 && arrayListGetMenu!=null && arrayListGetMenu.size()>0) {
                    JSONObject object = new JSONObject();
                    try {
                        for (int i = 0; i < arrayListBuyMenu.size(); i++) {
                            JSONObject objectbuy = new JSONObject();
                            objectbuy.put("menuId", arrayListBuyMenu.get(i).getMenu_Id());
                            objectbuy.put("menuName", arrayListBuyMenu.get(i).getMenu_Name());
                            objectbuy.put("menuPrice", arrayListBuyMenu.get(i).getNon_Ac_Rate());
                            objectbuy.put("pcId", arrayListBuyMenu.get(i).getPcId());
                            objectbuy.put("menuImg", arrayListBuyMenu.get(i).getMenu_Image_Name());
                            objectbuy.put("buyGet", 1);
                            objectbuy.put("flavourName", arrayListBuyMenu.get(i).getCategoryName());
                            jsonArray.put(objectbuy);
                        }

                        for (int i = 0; i < arrayListGetMenu.size(); i++) {
                            JSONObject object1 = new JSONObject();
                            object1.put("menuId", arrayListGetMenu.get(i).getMenu_Id());
                            object1.put("menuName", arrayListGetMenu.get(i).getMenu_Name());
                            object1.put("menuPrice", arrayListGetMenu.get(i).getNon_Ac_Rate());
                            object1.put("pcId", arrayListGetMenu.get(i).getPcId());
                            object1.put("menuImg", arrayListGetMenu.get(i).getMenu_Image_Name());
                            object1.put("buyGet", 2);
                            object1.put("flavourName", arrayListGetMenu.get(i).getCategoryName());
                            jsonArray.put(object1);
                        }
                    } catch (Exception e) {

                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDaliyBuyGetoffer.this);
                    builder
                            .setTitle("Apply Offer")
                            .setMessage("Are you sure you want to apply this Offer ?")
                            /* .setIcon(R.drawable.ic_gr_promocode)*/
                            .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    initRetrofitCallBack();
                                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityDaliyBuyGetoffer.this);
                                    mRetrofitService.retrofitData(APPLY_DAILYOFFER, (service.applyDailyOffer(hotelId, offerId, jsonArray.toString())));
                                }
                            });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }else
                {
                    Toast.makeText(this, "Please select  buy and get menus....", Toast.LENGTH_SHORT).show();
                }

        }



    }

    private void initRetrofitCallBack() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                String objectResponce=jsonObject.toString();
                try {
                    JSONObject  object=new JSONObject(objectResponce);
                    int status=object.getInt("status");
                    if(status==1)
                    {
                        Toast.makeText(ActivityDaliyBuyGetoffer.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(ActivityDaliyBuyGetoffer.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    finish();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 200) {
            //image_result = data.getStringExtra("image_name");
            arrayListBuyMenu = data.getParcelableArrayListExtra("menuListBuy");

            if (arrayListBuyMenu.size() > 0 && arrayListBuyMenu != null) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                rvBuyMenu.setHasFixedSize(true);
                rvBuyMenu.setLayoutManager(linearLayoutManager);
                RVDailyOfferBuyGetList rvDailyOfferBuyGetList = new RVDailyOfferBuyGetList(getBaseContext(), arrayListBuyMenu);
                rvBuyMenu.setAdapter(rvDailyOfferBuyGetList);
            }
        }else if(resultCode == 300) {
            arrayListGetMenu = data.getParcelableArrayListExtra("menuListGet");

            if (arrayListGetMenu.size() > 0 && arrayListGetMenu != null) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                rvGetMenu.setHasFixedSize(true);
                rvGetMenu.setLayoutManager(linearLayoutManager);
                RVDailyOfferBuyGetList rvDailyOfferBuyGetList = new RVDailyOfferBuyGetList(getBaseContext(), arrayListGetMenu);
                rvGetMenu.setAdapter(rvDailyOfferBuyGetList);
            }
        }

        }

}
