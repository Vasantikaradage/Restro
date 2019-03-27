package com.restrosmart.restro.User;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Adapter.RVFoodMenuAdapter;
import com.restrosmart.restro.Adapter.RVFoodSubCategoryAdapter;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.CategoryChangedListener;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.FoodCartModel;
import com.restrosmart.restro.Model.FoodMenuModel;
import com.restrosmart.restro.Model.FoodSubMenuModel;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.MovableFloatingActionButton;
import com.restrosmart.restro.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.FOOD_SUB_CATEGORY_MENU;
import static com.restrosmart.restro.ConstantVariables.WATER_BOTTLE_DETAIL;

public class FragmentFoodMenu extends Fragment {

    private View view;
    private MovableFloatingActionButton fabWaterBottle;
    private RecyclerView rvVegMenu, rvVegCategory;
    private RVFoodMenuAdapter rvFoodMenuAdapter;
    private RVFoodSubCategoryAdapter rvFoodSubCategoryAdapter;
    private ArrayList<FoodMenuModel> foodMenuModelArrayList;
    private ArrayList<FoodSubMenuModel> foodSubMenuModelArrayList;
    private Animation left_to_right_anim, right_to_left_anim;

    private int oldPosition = 0;
    private int categoryId, waterBottleId;
    private String waterBottleName, waterBottleImage;
    private float waterBottlePrice;

    private DecimalFormat df2;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager sessionmanager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_menu, container, false);

        init();

        categoryId = getArguments().getInt("categoryId");

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
        mRetrofitService.retrofitData(WATER_BOTTLE_DETAIL, (service.getWaterBottle(1, 1)));
    }

    private void getCategoryMenus() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(FOOD_SUB_CATEGORY_MENU, (service.getSubCategoryMenu(1, 1, categoryId)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {

                    case WATER_BOTTLE_DETAIL:

                        JsonObject jsonObject = response.body();
                        String value = jsonObject.toString();

                        try {
                            JSONObject jsonObject1 = new JSONObject(value);

                            int status = jsonObject1.getInt("status");
                            if (status == 1) {
                                waterBottleId = jsonObject1.getInt("Menu_Id");
                                waterBottleName = jsonObject1.getString("Menu_Name");
                                waterBottleImage = jsonObject1.getString("Menu_Image_Name");
                                waterBottlePrice = Float.parseFloat(df2.format(Float.parseFloat(jsonObject1.getString("Non_Ac_Rate"))));

                                Log.e("data", String.valueOf(waterBottleId));
                                Log.e("data", waterBottleName);
                                Log.e("data", waterBottleImage);
                                Log.e("data", String.valueOf(waterBottlePrice));
                                sessionmanager.setWaterBottleDetail(waterBottleId, waterBottleName, waterBottleImage, waterBottlePrice);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case FOOD_SUB_CATEGORY_MENU:

                        JsonObject jsonObjectCat = response.body();
                        String responseValue = jsonObjectCat.toString();

                        try {
                            JSONObject jsonObject1 = new JSONObject(responseValue);

                            int status = jsonObject1.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject1.getJSONArray("submenu");

                                foodSubMenuModelArrayList.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    foodMenuModelArrayList = new ArrayList<>();

                                    JSONObject subMenuJsonObject = jsonArray.getJSONObject(i);

                                    int subMenuId = subMenuJsonObject.getInt("Submenu_Id");
                                    String subMenuName = subMenuJsonObject.getString("Submenu_Name");
                                    JSONArray menuJsonArray = subMenuJsonObject.getJSONArray("Menu");

                                    for (int j = 0; j < menuJsonArray.length(); j++) {
                                        JSONObject menuJsonObject = menuJsonArray.getJSONObject(j);

                                        int menuId = menuJsonObject.getInt("Menu_Id");
                                        String menuName = menuJsonObject.getString("Menu_Name");
                                        String menuDesc = menuJsonObject.getString("Menu_Descrip");
                                        String menuImage = menuJsonObject.getString("Menu_Image_Name");
                                        float menuPrice = Float.parseFloat(df2.format(Float.parseFloat(menuJsonObject.getString("Non_Ac_Rate"))));

                                        FoodMenuModel foodMenuModel = new FoodMenuModel();
                                        foodMenuModel.setMenuId(menuId);
                                        foodMenuModel.setMenuName(menuName);
                                        foodMenuModel.setMenuDesc(menuDesc);
                                        foodMenuModel.setMenuImage(menuImage);
                                        foodMenuModel.setMenuPrice(menuPrice);

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

    private void showDiag() {

        View view1 = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.circular_dialog, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view1);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialog, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    revealShow(dialog, false, dialog);
                    return true;
                }
                return false;
            }
        });

        FrameLayout flClose = dialog.findViewById(R.id.flClose);
        final Button btnAddWBottle = dialog.findViewById(R.id.btnAddWBottle);
        final CardView cvQty = dialog.findViewById(R.id.cvQty);
        TextView tvMinusWBottleQty = dialog.findViewById(R.id.tvMinusWBottleQty);
        final TextView tvWBottleQty = dialog.findViewById(R.id.tvWBottleQty);
        TextView tvAddWBottleQty = dialog.findViewById(R.id.tvAddWBottleQty);
        TextView tvGoCart = dialog.findViewById(R.id.tvGoCart);

        flClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(dialog, false, dialog);
            }
        });

        tvGoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(dialog, false, dialog);
                Intent intent = new Intent(getContext(), ActivityCartBill.class);
                getContext().startActivity(intent);
            }
        });

        btnAddWBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodCartModel foodCartModel = new FoodCartModel();
                foodCartModel.setMenuId(waterBottleId);
                foodCartModel.setMenuName(waterBottleName);
                foodCartModel.setMenuImage(waterBottleImage);
                foodCartModel.setMenuPrice(waterBottlePrice);
                foodCartModel.setMenuQtyPrice(waterBottlePrice);
                foodCartModel.setMenuQty(1);
                sessionmanager.addToFoodCart(getContext(), foodCartModel);

                btnAddWBottle.startAnimation(left_to_right_anim);
                cvQty.startAnimation(right_to_left_anim);
                cvQty.setVisibility(View.VISIBLE);
                btnAddWBottle.clearAnimation();
                btnAddWBottle.setVisibility(View.GONE);

                Intent intent = new Intent("com.restrosmart.restro.addmenu");
                intent.putExtra("menuname", "Water Bottle");
                getContext().sendBroadcast(intent);
            }
        });

        tvAddWBottleQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionmanager.updateQtyFoodCart(getContext(), 1, 1);
                tvWBottleQty.setText(String.valueOf(Integer.parseInt(tvWBottleQty.getText().toString()) + 1));
            }
        });

        tvMinusWBottleQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(tvWBottleQty.getText().toString()) == 1) {
                    tvWBottleQty.setText(String.valueOf(1));
                    cvQty.clearAnimation();
                    cvQty.setVisibility(View.GONE);
                    btnAddWBottle.setVisibility(View.VISIBLE);

                    sessionmanager.removeMenuFoodCart(getContext(), 1);

                    Intent intent = new Intent("com.restrosmart.restro.addmenu");
                    intent.putExtra("menuname", "Water Bottle");
                    getContext().sendBroadcast(intent);
                } else {
                    sessionmanager.updateQtyFoodCart(getContext(), 0, 1);
                    tvWBottleQty.setText(String.valueOf(Integer.parseInt(tvWBottleQty.getText().toString()) - 1));
                }
            }
        });

        ((View) view1.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        dialog.show();
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
        sessionmanager = new Sessionmanager(getContext());

        foodSubMenuModelArrayList = new ArrayList<>();

        rvVegMenu = view.findViewById(R.id.rvVegMenu);
        rvVegCategory = view.findViewById(R.id.rvVegCategory);
        fabWaterBottle = view.findViewById(R.id.fabWaterBottle);

        left_to_right_anim = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right_anim);
        right_to_left_anim = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left_anim);
    }
}
