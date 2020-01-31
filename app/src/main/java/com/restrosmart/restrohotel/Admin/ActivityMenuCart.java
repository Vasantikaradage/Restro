package com.restrosmart.restrohotel.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.content.IntentCompat;
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
import com.restrosmart.restrohotel.Adapter.AdapterMenuCartDetails;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.MenuListerner;
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
import static com.restrosmart.restrohotel.ConstantVariables.APPLY_RUSH_HOUR;
import static com.restrosmart.restrohotel.ConstantVariables.APPLY_SCRATCHCARD;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityMenuCart extends AppCompatActivity {
    private Sessionmanager sessionmanager;
    private ArrayList<MenuDisplayForm> menuDisplayFormArrayList;
    private RecyclerView rvMenuCart;
    private Button btnSubmit;
    private JSONArray jsonArray;


    private AdapterMenuCartDetails adapterMenuCartDetails;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private int offerTypeId, offerId, winnerQty, buyQty, getQty;
    private int hotelId;
    private TextView txTitle, tvPrice, tvOfferPrice, tvQty;
    private Toolbar mTopToolbar;
    private Intent intent;
    int orgQty = 0, totQty;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cart);

        init();

        setUpToolBar();


        intent = getIntent();
        offerTypeId = intent.getIntExtra("offerTypeId", 0);
        offerId = intent.getIntExtra("offerId", 0);


        String offer = String.valueOf(offerId);
        Toast.makeText(this, offer, Toast.LENGTH_SHORT).show();

        if (offerTypeId == 2) {
            winnerQty = intent.getIntExtra("winnerQty", 0);
            tvQty.setVisibility(View.VISIBLE);
            tvOfferPrice.setVisibility(View.GONE);
            tvPrice.setVisibility(View.GONE);

        } else if (offerTypeId == 1) {
            buyQty = intent.getIntExtra("buyQty", 0);

            if (buyQty != 0) {
                tvQty.setVisibility(View.GONE);
                tvOfferPrice.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);
            } else {
                tvPrice.setVisibility(View.VISIBLE);
            }
        } else {
            tvQty.setVisibility(View.GONE);
            tvOfferPrice.setVisibility(View.VISIBLE);
            tvPrice.setVisibility(View.VISIBLE);

        }


        menuDisplayFormArrayList = sessionmanager.getAddToMenuCartList(ActivityMenuCart.this);
        HashMap<String, String> stringHashMap = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(stringHashMap.get(HOTEL_ID));

        if (menuDisplayFormArrayList.size() > 0 && menuDisplayFormArrayList != null) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvMenuCart.setLayoutManager(linearLayoutManager);
            rvMenuCart.setHasFixedSize(true);
            rvMenuCart.getLayoutManager().setMeasurementCacheEnabled(false);
            adapterMenuCartDetails = new AdapterMenuCartDetails(ActivityMenuCart.this, offerTypeId, menuDisplayFormArrayList, new MenuListerner() {
                @Override
                public void getMenuListerner(int position, String offerPrice) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("menuId", menuDisplayFormArrayList.get(position).getMenu_Id());
                        object.put("menuName", menuDisplayFormArrayList.get(position).getMenu_Name());
                        object.put("menuPrice", menuDisplayFormArrayList.get(position).getNon_Ac_Rate());
                        object.put("offerPrice", offerPrice);
                        object.put("pcId", menuDisplayFormArrayList.get(position).getPcId());
                        object.put("flavourName", menuDisplayFormArrayList.get(position).getCategoryName());
                        //  Toast.makeText(ActivityMenuCart.this, offerPrice, Toast.LENGTH_SHORT).show();
                        jsonArray.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            });
            rvMenuCart.setAdapter(adapterMenuCartDetails);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean flag = true;
                ArrayList<MenuDisplayForm> pojoArrayList = adapterMenuCartDetails.getArrayList();

                for (int i = 0; i < pojoArrayList.size(); i++) {

                    if (pojoArrayList.get(i).getOffeerPrice().isEmpty() || pojoArrayList.get(i).getOffeerPrice().equals("0")) {
                        pojoArrayList.get(i).setError("Please enter price");
                        flag = false;
                    } else if (Integer.parseInt(pojoArrayList.get(i).getOffeerPrice()) >= pojoArrayList.get(i).getNon_Ac_Rate()) {
                        Toast.makeText(ActivityMenuCart.this, "Offer price should be less than original price", Toast.LENGTH_SHORT).show();
                        flag = false;
                        //   pojoArrayList.get(i).setError("");
                    }
                }
                adapterMenuCartDetails.setArrayList(pojoArrayList);


                if (flag) {

                    if (offerTypeId == 4) {

                        try {
                            for (int i = 0; i < pojoArrayList.size(); i++) {
                                JSONObject object = new JSONObject();
                                object.put("menuId", pojoArrayList.get(i).getMenu_Id());
                                object.put("menuName", pojoArrayList.get(i).getMenu_Name());
                                object.put("menuPrice", pojoArrayList.get(i).getNon_Ac_Rate());
                                object.put("offerPrice", pojoArrayList.get(i).getOffeerPrice());
                                object.put("pcId", pojoArrayList.get(i).getPcId());
                                object.put("flavourName", pojoArrayList.get(i).getCategoryName());
                                //  Toast.makeText(ActivityMenuCart.this,offerPrice, Toast.LENGTH_SHORT).show();
                                jsonArray.put(object);
                            }
                            //Toast.makeText(ActivityMenuCart.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenuCart.this);
                        builder
                                .setTitle("Apply Offer")
                                .setMessage("Are you sure you want to apply this Offer ?")
                                /* .setIcon(R.drawable.ic_gr_promocode)*/
                                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        initRetrofitCallBack();
                                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenuCart.this);
                                        mRetrofitService.retrofitData(APPLY_RUSH_HOUR, (service.applyRushHours(hotelId, offerId, jsonArray.toString())));

                                    }
                                });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                    } else if (offerTypeId == 2) {

                        for (int i = 0; i < pojoArrayList.size(); i++) {

                            int qty = Integer.parseInt(pojoArrayList.get(i).getOffeerPrice());
                            totQty = orgQty + qty;
                            orgQty = qty;
                        }
                        if (totQty <= winnerQty) {
                            try {
                                for (int i = 0; i < pojoArrayList.size(); i++) {
                                    JSONObject object = new JSONObject();
                                    object.put("menuId", pojoArrayList.get(i).getMenu_Id());
                                    object.put("menuName", pojoArrayList.get(i).getMenu_Name());
                                    object.put("menuImg", pojoArrayList.get(i).getMenu_Image_Name());
                                    object.put("offerqtycount", pojoArrayList.get(i).getOffeerPrice());
                                    object.put("pcId", pojoArrayList.get(i).getPcId());
                                    object.put("flavourName", pojoArrayList.get(i).getCategoryName());
                                    //  Toast.makeText(ActivityMenuCart.this,offerPrice, Toast.LENGTH_SHORT).show();
                                    jsonArray.put(object);
                                }
                            } catch (Exception e) {

                            }
                            //  Toast.makeText(ActivityMenuCart.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenuCart.this);
                            builder
                                    .setTitle("Apply Offer")
                                    .setMessage("Are you sure you want to apply this Offer ?")
                                    /* .setIcon(R.drawable.ic_gr_promocode)*/
                                    .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            initRetrofitCallBack();
                                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenuCart.this);
                                            mRetrofitService.retrofitData(APPLY_SCRATCHCARD, (service.applyScratchcard(hotelId, offerId, jsonArray.toString())));
                                        }
                                    });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();

                        } else {
                            // pojoArrayList.clear();
                            Toast.makeText(ActivityMenuCart.this, "Selected menu Count should be same as winner people quantity", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (flag == false && offerTypeId == 1 && buyQty == 0 && getQty == 0) {
                    try {
                        for (int i = 0; i < pojoArrayList.size(); i++) {
                            JSONObject object = new JSONObject();
                            object.put("menuId", pojoArrayList.get(i).getMenu_Id());
                            object.put("menuName", pojoArrayList.get(i).getMenu_Name());
                            object.put("menuPrice", pojoArrayList.get(i).getNon_Ac_Rate());
                            object.put("pcId", pojoArrayList.get(i).getPcId());
                            object.put("menuImg", pojoArrayList.get(i).getMenu_Image_Name());
                            object.put("buyGet", 0);
                            object.put("flavourName", pojoArrayList.get(i).getCategoryName());
                            jsonArray.put(object);
                        }
                        Toast.makeText(ActivityMenuCart.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenuCart.this);
                    builder
                            .setTitle("Apply Offer")
                            .setMessage("Are you sure you want to apply this Offer ?")
                            /* .setIcon(R.drawable.ic_gr_promocode)*/
                            .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    initRetrofitCallBack();
                                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenuCart.this);
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
                }
            }
        });
    }

    private void setUpToolBar() {
        setSupportActionBar(mTopToolbar);
        txTitle.setText("Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String strJsonResp = jsonObject.toString();

                switch (requestId) {

                    case APPLY_RUSH_HOUR:

                        try {
                            JSONObject object = new JSONObject(strJsonResp);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenuCart.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                sessionmanager.deleteMenuCartList();

                                Intent intent = new Intent(ActivityMenuCart.this, ActivityDisplayRushHours.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ActivityMenuCart.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case APPLY_SCRATCHCARD:
                        try {
                            JSONObject object = new JSONObject(strJsonResp);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenuCart.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                sessionmanager.deleteMenuCartList();

                                Intent intent = new Intent(ActivityMenuCart.this, ActivityDisplayScratchCard.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ActivityMenuCart.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case APPLY_DAILYOFFER:
                        try {
                            JSONObject object = new JSONObject(strJsonResp);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenuCart.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                sessionmanager.deleteMenuCartList();

                                Intent intent = new Intent(ActivityMenuCart.this, ActivityDisplayDailyOffer.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ActivityMenuCart.this, object.getString("message"), Toast.LENGTH_SHORT).show();

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

    private void init() {
        sessionmanager = new Sessionmanager(ActivityMenuCart.this);
        rvMenuCart = findViewById(R.id.rv_menu_cart_recycler);
        btnSubmit = findViewById(R.id.btn_send);
        jsonArray = new JSONArray();
        mTopToolbar = findViewById(R.id.toolbar);
        txTitle = mTopToolbar.findViewById(R.id.tx_title);
        tvPrice = findViewById(R.id.tv_menu_price);
        tvOfferPrice = findViewById(R.id.menuOfferPrice);
        tvQty = findViewById(R.id.menuqty);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
