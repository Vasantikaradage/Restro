package com.restrosmart.restrohotel.Captain.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.RVFoodMenuAdapter;
import com.restrosmart.restrohotel.Captain.Adapters.RVFoodSubCategoryAdapter;
import com.restrosmart.restrohotel.Captain.Interfaces.CategoryChangedListener;
import com.restrosmart.restrohotel.Captain.Models.FoodMenuModel;
import com.restrosmart.restrohotel.Captain.Models.FoodSubMenuModel;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.LoadingDialog;
import com.restrosmart.restrohotel.Utils.MovableFloatingActionButton;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.FOOD_SUB_CATEGORY_MENU;
import static com.restrosmart.restrohotel.ConstantVariables.UNIQUE_KEY;
import static com.restrosmart.restrohotel.ConstantVariables.WATER_ADD_TO_CART;
import static com.restrosmart.restrohotel.ConstantVariables.WATER_BOTTLE_DETAIL;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.CUST_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.TABLE_NO;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.USER_ID;

public class FragmentFoodMenu extends Fragment {

    private View view;
    private MovableFloatingActionButton fabWaterBottle;
    private RecyclerView rvVegMenu, rvVegCategory;
    private RVFoodMenuAdapter rvFoodMenuAdapter;
    private RVFoodSubCategoryAdapter rvFoodSubCategoryAdapter;
    private ArrayList<FoodMenuModel> foodMenuModelArrayList;
    private ArrayList<ToppingsModel> toppingsModelArrayList;
    private ArrayList<FoodSubMenuModel> foodSubMenuModelArrayList;

    private int oldPosition = 0;
    private int categoryId;
    private String waterBottleId, waterBottleName, waterBottleImage;
    private float waterBottlePrice;

    private DecimalFormat df2;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private BottomSheetDialog bottomSheetDialog;
    private LoadingDialog loadingDialog;
    private ProgressDialog progressDialog;
    private TextView tvWBottleQty;

    private HashMap<String, String> hotelDetails, userDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_menu, container, false);

        init();

        categoryId = getArguments().getInt("categoryId");

        userDetails = mSessionmanager.getCustDetails();
        hotelDetails = mSessionmanager.getHotelDetails();

        loadingDialog.showLoadingDialog();
        getCategoryMenus();
        getWaterBottleDetail();

        fabWaterBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiag();
            }
        });

        return view;
    }

    private void getWaterBottleDetail() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(WATER_BOTTLE_DETAIL, (service.getWaterBottle(Integer.parseInt(hotelDetails.get(HOTEL_ID)), UNIQUE_KEY)));
    }

    private void getCategoryMenus() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(FOOD_SUB_CATEGORY_MENU, (service.getSubCategoryMenu(Integer.parseInt(hotelDetails.get(HOTEL_ID)), categoryId, UNIQUE_KEY)));
    }

    private void WaterAddToCart() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(WATER_ADD_TO_CART, (service.addToCart(mSessionmanager.getOrderID(),
                Integer.parseInt(hotelDetails.get(TABLE_NO)),
                Integer.parseInt(userDetails.get(CUST_ID)),
                Integer.parseInt(hotelDetails.get(HOTEL_ID)),
                waterBottleId,
                waterBottleName,
                String.valueOf(waterBottlePrice),
                Integer.parseInt(tvWBottleQty.getText().toString()),
                "",0,"", "","", 0, 0, 1, 0, UNIQUE_KEY)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String responseValue = jsonObject.toString();

                switch (requestId) {
                    case WATER_BOTTLE_DETAIL:
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseValue);

                            int status = jsonObject1.getInt("status");
                            String msg = jsonObject1.getString("message");

                            if (status == 1) {
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("Water_bottle_details");

                                waterBottleId = jsonObject2.getString("Water_Id");
                                waterBottleName = jsonObject2.getString("Water_Name");
                                waterBottleImage = jsonObject2.getString("Water_Img");
                                waterBottlePrice = Float.parseFloat(df2.format(Float.parseFloat(jsonObject2.getString("Water_Price"))));

                                mSessionmanager.setWaterBottleDetail(waterBottleId, waterBottleName, waterBottleImage, waterBottlePrice);
                            } else {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case WATER_ADD_TO_CART:
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseValue);

                            int status = jsonObject1.getInt("status");
                            String msg = jsonObject1.getString("message");

                            progressDialog.dismiss();
                            if (status == 1) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                tvWBottleQty.setText("1");

                                mSessionmanager.saveCartCount();
                                mSessionmanager.saveOrderID(jsonObject1.getInt("Order_Id"));
                                Intent intent = new Intent("com.restrosmart.restro.addmenu");
                                getContext().sendBroadcast(intent);
                            } else {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case FOOD_SUB_CATEGORY_MENU:
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseValue);

                            int status = jsonObject1.getInt("status");
                            String msg = jsonObject1.getString("message");

                            if (status == 1) {
                                JSONArray jsonArray = jsonObject1.getJSONArray("Menulist");

                                foodSubMenuModelArrayList.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    foodMenuModelArrayList = new ArrayList<>();

                                    JSONObject subMenuJsonObject = jsonArray.getJSONObject(i);

                                    int subMenuId = subMenuJsonObject.getInt("Submenu_Id");
                                    String subMenuName = subMenuJsonObject.getString("Submenu_Name");
                                    JSONArray menuJsonArray = subMenuJsonObject.getJSONArray("Menu");

                                    for (int j = 0; j < menuJsonArray.length(); j++) {
                                        toppingsModelArrayList = new ArrayList<>();

                                        JSONObject menuJsonObject = menuJsonArray.getJSONObject(j);

                                        int menuId = menuJsonObject.getInt("Menu_Id");
                                        String menuName = menuJsonObject.getString("Menu_Name");
                                        String menuDesc = menuJsonObject.getString("Menu_Descrip");
                                        String menuImage = menuJsonObject.getString("Menu_Image_Name");
                                        float menuPrice = Float.parseFloat(df2.format(Float.parseFloat(menuJsonObject.getString("Non_Ac_Rate"))));
                                        int menuTaste = menuJsonObject.getInt("Menu_Test");

                                        JSONArray toppingsJsonArray = menuJsonObject.getJSONArray("Topping");
                                        for (int k = 0; k < toppingsJsonArray.length(); k++) {
                                            JSONObject toppingsJsonObject = toppingsJsonArray.getJSONObject(k);

                                            ToppingsModel toppingsModel = new ToppingsModel();
                                            toppingsModel.setToppingsId(toppingsJsonObject.getInt("Topping_Id"));
                                            toppingsModel.setToppingsName(toppingsJsonObject.getString("Topping_Name"));
                                            toppingsModel.setToppingsPrice(Float.parseFloat(df2.format(Float.parseFloat(toppingsJsonObject.getString("Topping_Price")))));

                                            toppingsModelArrayList.add(toppingsModel);
                                        }

                                        FoodMenuModel foodMenuModel = new FoodMenuModel();
                                        foodMenuModel.setMenuId(menuId);
                                        foodMenuModel.setMenuName(menuName);
                                        foodMenuModel.setMenuDesc(menuDesc);
                                        foodMenuModel.setMenuImage(menuImage);
                                        foodMenuModel.setMenuPrice(menuPrice);
                                        foodMenuModel.setMenuTaste(menuTaste);
                                        foodMenuModel.setToppingsModelArrayList(toppingsModelArrayList);

                                        foodMenuModelArrayList.add(foodMenuModel);
                                    }

                                    FoodSubMenuModel foodSubMenuModel = new FoodSubMenuModel();

                                    foodSubMenuModel.setSubmenuId(subMenuId);
                                    foodSubMenuModel.setSubmenuName(subMenuName);
                                    foodSubMenuModel.setArrayList(foodMenuModelArrayList);
                                    foodSubMenuModelArrayList.add(foodSubMenuModel);
                                }

                                rvVegMenu.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvFoodMenuAdapter = new RVFoodMenuAdapter(getContext(), foodSubMenuModelArrayList.get(0).getArrayList());
                                rvVegMenu.setAdapter(rvFoodMenuAdapter);

                                rvVegCategory.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvFoodSubCategoryAdapter = new RVFoodSubCategoryAdapter(getContext(), foodSubMenuModelArrayList, new CategoryChangedListener() {
                                    @Override
                                    public void categoryChanged(int selectedPosition) {

                                        if (oldPosition > selectedPosition) {
                                            rvVegCategory.getLayoutManager().scrollToPosition(selectedPosition - 1);
                                        } else {
                                            rvVegCategory.getLayoutManager().scrollToPosition(selectedPosition + 1);
                                        }
                                        rvFoodMenuAdapter.refreshList(foodSubMenuModelArrayList.get(selectedPosition).getArrayList());

                                        oldPosition = selectedPosition;
                                    }
                                });
                                rvVegCategory.setAdapter(rvFoodSubCategoryAdapter);
                            } else {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                }

                loadingDialog.dismissLoadingDialog();
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.v("Retrofit RequestId", String.valueOf(requestId));
                Log.v("Retrofit Error", String.valueOf(error));
            }
        };
    }

    private void showDiag() {

        View view1 = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.circular_dialog, null);
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(bottomSheetDialog, true, null);
            }
        });

        bottomSheetDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    revealShow(bottomSheetDialog, false, bottomSheetDialog);
                    return true;
                }
                return false;
            }
        });

        FrameLayout flClose = bottomSheetDialog.findViewById(R.id.flClose);
        Button btnAddWBottle = bottomSheetDialog.findViewById(R.id.btnAddWBottle);
        TextView tvMinusWBottleQty = bottomSheetDialog.findViewById(R.id.tvMinusWBottleQty);
        tvWBottleQty = bottomSheetDialog.findViewById(R.id.tvWBottleQty);
        TextView tvAddWBottleQty = bottomSheetDialog.findViewById(R.id.tvAddWBottleQty);

        flClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(bottomSheetDialog, false, bottomSheetDialog);
            }
        });

        btnAddWBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Adding to cart...");
                progressDialog.show();
                WaterAddToCart();
            }
        });

        tvAddWBottleQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvWBottleQty.setText(String.valueOf(Integer.parseInt(tvWBottleQty.getText().toString()) + 1));
            }
        });

        tvMinusWBottleQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(tvWBottleQty.getText().toString()) == 1) {
                    tvWBottleQty.setText(String.valueOf(1));
                } else {
                    tvWBottleQty.setText(String.valueOf(Integer.parseInt(tvWBottleQty.getText().toString()) - 1));
                }
            }
        });

        ((View) view1.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        bottomSheetDialog.show();
    }

    private void revealShow(BottomSheetDialog dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, 1200);

        int cx = (int) (fabWaterBottle.getX() + (fabWaterBottle.getWidth() / 2));
        int cy = (int) (fabWaterBottle.getY()) + fabWaterBottle.getHeight() + 56;


        if (b) {
            Animator revealAnimator = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
            }
            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(900);
            revealAnimator.start();

        } else {
            Animator anim = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
            }
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);
                }
            });
            anim.setDuration(900);
            anim.start();
        }
    }

    private void init() {
        df2 = new DecimalFormat(".##");
        mSessionmanager = new Sessionmanager(getContext());
        loadingDialog = new LoadingDialog(getContext());

        foodSubMenuModelArrayList = new ArrayList<>();

        rvVegMenu = view.findViewById(R.id.rvVegMenu);
        rvVegCategory = view.findViewById(R.id.rvVegCategory);
        fabWaterBottle = view.findViewById(R.id.fabWaterBottle);
    }
}
