package com.restrosmart.restrohotel.Captain.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.RVEditToppingsAdapter;
import com.restrosmart.restrohotel.Captain.Interfaces.ToppingsListener;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.REMOVE_MENU_CART;
import static com.restrosmart.restrohotel.ConstantVariables.SAVE_MENU_CART;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityEditCartMenu extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView tvMenuName, tvMenuPrice, tvMenuQty, tvMinusQty, tvAddQty, tvToppingsLabel, tvRemoveMenu, tvCancel, tvSave;
    private RecyclerView rvToppings;

    private String toppingsList;
    private int OrderDetailId;
    private ArrayList<ToppingsModel> toppingsModelArrayList, allToppingsModelArrayList;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private ProgressDialog progressDialog;
    private HashMap<String, String> hotelDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart_menu);

        init();
        setupToolbar();

        hotelDetails = mSessionmanager.getHotelDetails();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            OrderDetailId = bundle.getInt("OrderDetailId");

            tvMenuName.setText(bundle.getString("MenuName"));
            tvMenuQty.setText(String.valueOf(bundle.getInt("MenuQty")));
            tvMenuPrice.setText(getResources().getString(R.string.currency) + String.valueOf(bundle.getFloat("MenuPrice")));
            toppingsModelArrayList = bundle.getParcelableArrayList("ToppingsList");
            allToppingsModelArrayList = bundle.getParcelableArrayList("AllToppingsList");

            if (allToppingsModelArrayList != null && allToppingsModelArrayList.size() > 0) {
                RVEditToppingsAdapter rvEditToppingsAdapter = new RVEditToppingsAdapter(this, toppingsModelArrayList, allToppingsModelArrayList, new ToppingsListener() {
                    @Override
                    public void AddRemoveToppings(ArrayList<ToppingsModel> arrayList) {
                        if (arrayList != null && arrayList.size() > 0) {
                            toppingsList = getToppingList(arrayList);
                            //Toast.makeText(ActivityEditCartMenu.this, toppingsList, Toast.LENGTH_SHORT).show();
                        } else {
                            toppingsList = "";
                        }
                    }
                });
                rvToppings.setHasFixedSize(true);
                rvToppings.setNestedScrollingEnabled(false);
                rvToppings.setLayoutManager(new GridLayoutManager(this, 1));
                rvToppings.setItemAnimator(new DefaultItemAnimator());
                rvToppings.setAdapter(rvEditToppingsAdapter);

                tvToppingsLabel.setVisibility(View.VISIBLE);
                rvToppings.setVisibility(View.VISIBLE);
            } else {
                tvToppingsLabel.setVisibility(View.GONE);
                rvToppings.setVisibility(View.GONE);
            }
        }

        tvRemoveMenu.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvAddQty.setOnClickListener(this);
        tvMinusQty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRemoveMenu:
                alertDialogRemove();
                break;

            case R.id.tvCancel:
                finish();
                break;

            case R.id.tvSave:
                alertDialogSave();
                break;

            case R.id.tvAddQty:
                tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) + 1));
                break;

            case R.id.tvMinusQty:
                if (Integer.parseInt(tvMenuQty.getText().toString()) == 1) {
                    tvMenuQty.setText(String.valueOf(1));
                } else {
                    tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) - 1));
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void alertDialogRemove() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage("Are you sure you want to remove this menu from cart?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        progressDialog = new ProgressDialog(ActivityEditCartMenu.this);
                        progressDialog.setMessage("Removing menu from cart...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        removeMenuCart();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void alertDialogSave() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage("Are you sure you want to save menu cart?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        progressDialog = new ProgressDialog(ActivityEditCartMenu.this);
                        progressDialog.setMessage("Updating menu from cart...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        saveMenuCart();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void removeMenuCart() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(REMOVE_MENU_CART, (service.removeCartMenu(Integer.parseInt(hotelDetails.get(HOTEL_ID)), Integer.parseInt(hotelDetails.get(BRANCH_ID)), OrderDetailId)));
    }

    private void saveMenuCart() {
        if (toppingsList == null) {
            toppingsList = "";
        }

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(SAVE_MENU_CART, (service.saveCartMenu(Integer.parseInt(hotelDetails.get(HOTEL_ID)), Integer.parseInt(hotelDetails.get(BRANCH_ID)), OrderDetailId,
                tvMenuQty.getText().toString(), toppingsList)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                JsonObject jsonObjectCat = response.body();
                String responseValue = jsonObjectCat.toString();

                switch (requestId) {
                    case REMOVE_MENU_CART:

                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);

                            int status = jsonObject.getInt("status");

                            progressDialog.dismiss();
                            if (status == 1) {
                                Intent intent = new Intent("com.restrosmart.restro.refreshcart");
                                sendBroadcast(intent);
                                Toast.makeText(ActivityEditCartMenu.this, "Menu removed", Toast.LENGTH_SHORT).show();
                                mSessionmanager.deleteCartCount();
                                finish();
                            } else {
                                Toast.makeText(ActivityEditCartMenu.this, getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case SAVE_MENU_CART:

                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);

                            int status = jsonObject.getInt("status");

                            progressDialog.dismiss();
                            if (status == 1) {
                                Intent intent = new Intent("com.restrosmart.restro.refreshcart");
                                sendBroadcast(intent);
                                Toast.makeText(ActivityEditCartMenu.this, "Menu updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityEditCartMenu.this, getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.v("Retrofit RequestId", String.valueOf(requestId));
                Log.v("Retrofit Error", String.valueOf(error));
            }
        };
    }

    private String getToppingList(ArrayList<ToppingsModel> toppingsArrayList) {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < toppingsArrayList.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Topping_Id", toppingsArrayList.get(i).getToppingsId());
                //jsonObject.put("Topping_Name", arrayListToppings.get.getStudentName());
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return jsonArray.toString();
    }

    private void setupToolbar() {
        mToolbar.setTitle("Edit Menu");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mSessionmanager = new Sessionmanager(this);

        mToolbar = findViewById(R.id.edtMenuToolbar);
        tvMenuName = findViewById(R.id.tvMenuName);
        tvMenuPrice = findViewById(R.id.tvMenuPrice);
        tvMenuQty = findViewById(R.id.tvMenuQty);
        tvMinusQty = findViewById(R.id.tvMinusQty);
        tvAddQty = findViewById(R.id.tvAddQty);
        tvToppingsLabel = findViewById(R.id.tvToppingsLabel);
        rvToppings = findViewById(R.id.rvToppings);
        tvRemoveMenu = findViewById(R.id.tvRemoveMenu);
        tvCancel = findViewById(R.id.tvCancel);
        tvSave = findViewById(R.id.tvSave);
    }
}
