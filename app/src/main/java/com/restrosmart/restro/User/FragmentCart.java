package com.restrosmart.restro.User;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restro.Adapter.FoodCartRVAdapter;
import com.restrosmart.restro.Interfaces.AddRemoveItemCartListener;
import com.restrosmart.restro.Model.FoodCartModel;
import com.restrosmart.restro.R;
import com.restrosmart.restro.Utils.Sessionmanager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.restrosmart.restro.Utils.Sessionmanager.WATER_BOTTLE_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.WATER_BOTTLE_IMAGE;
import static com.restrosmart.restro.Utils.Sessionmanager.WATER_BOTTLE_NAME;
import static com.restrosmart.restro.Utils.Sessionmanager.WATER_BOTTLE_PRICE;

public class FragmentCart extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tvSubTotal;
    private RecyclerView cartRecyclerView;
    private FoodCartRVAdapter foodCartRVAdapter;
    private ArrayList<FoodCartModel> foodCartModelArrayList;
    private LinearLayout llPlaceOrder, llCartEmpty;
    private NestedScrollView nestedCart;
    private Button btnAddWaterBottle;
    private AlertDialog alertDialog;

    private Sessionmanager sessionmanager;

    private String waterBottleId, waterBottleName, waterBottleImage, waterBottlePrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        init();

        foodCartModelArrayList = sessionmanager.getAddToFoodCartList(getContext());

        if (foodCartModelArrayList != null && foodCartModelArrayList.size() != 0) {

            HashMap<String, String> hashMap = sessionmanager.getWaterBottleDetail();
            waterBottleId = hashMap.get(WATER_BOTTLE_ID);
            waterBottleName = hashMap.get(WATER_BOTTLE_NAME);
            waterBottleImage = hashMap.get(WATER_BOTTLE_IMAGE);
            waterBottlePrice = hashMap.get(WATER_BOTTLE_PRICE);

            tvSubTotal.setText(String.valueOf(sessionmanager.getFoodSubTotal(getContext())));

            foodCartRVAdapter = new FoodCartRVAdapter(getContext(), foodCartModelArrayList, new AddRemoveItemCartListener() {
                @Override
                public void addRemovedItem() {
                    tvSubTotal.setText(String.valueOf(sessionmanager.getFoodSubTotal(getContext())));
                    foodCartModelArrayList = sessionmanager.getAddToFoodCartList(getContext());
                    if (foodCartModelArrayList != null && foodCartModelArrayList.size() != 0) {
                        nestedCart.setVisibility(View.VISIBLE);
                        llPlaceOrder.setVisibility(View.VISIBLE);
                        llCartEmpty.setVisibility(View.GONE);
                    } else {
                        nestedCart.setVisibility(View.GONE);
                        llPlaceOrder.setVisibility(View.GONE);
                        llCartEmpty.setVisibility(View.VISIBLE);
                    }
                }
            });
            cartRecyclerView.setHasFixedSize(true);
            cartRecyclerView.setNestedScrollingEnabled(false);
            cartRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            cartRecyclerView.setItemAnimator(new DefaultItemAnimator());
            cartRecyclerView.setAdapter(foodCartRVAdapter);

            nestedCart.setVisibility(View.VISIBLE);
            llPlaceOrder.setVisibility(View.VISIBLE);
            llCartEmpty.setVisibility(View.GONE);
        } else {
            nestedCart.setVisibility(View.GONE);
            llPlaceOrder.setVisibility(View.GONE);
            llCartEmpty.setVisibility(View.VISIBLE);
        }

        llPlaceOrder.setOnClickListener(this);
        btnAddWaterBottle.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llPlaceOrder:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = null;
                if (layoutInflater != null) {
                    dialogView = layoutInflater.inflate(R.layout.place_order_dialog, null);
                }
                dialogBuilder.setView(dialogView);

                alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                Button btnCancelOrder = dialogView.findViewById(R.id.btnCancelOrder);
                Button btnConfirmOrder = dialogView.findViewById(R.id.btnConfirmOrder);

                btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        /*Intent intent = new Intent(FragmentCart.this, FragmentOrderStatus.class);
                        startActivity(intent);*/

                        ActivityCartBill activity = (ActivityCartBill) getActivity();
                        activity.LoadFragment(2);
                        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.add(R.id.flContainer, new FragmentOrderStatus());
                        ft.commit();*/
                        //getActivity().getActionBar().setTitle("Order Status");
                    }
                });

                btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                break;

            case R.id.btnAddWaterBottle:

                FoodCartModel foodCartModel = new FoodCartModel();
                foodCartModel.setMenuId(Integer.parseInt(waterBottleId));
                foodCartModel.setMenuName(waterBottleName);
                foodCartModel.setMenuImage(waterBottleImage);
                foodCartModel.setMenuPrice(Float.parseFloat(waterBottlePrice));
                foodCartModel.setMenuQtyPrice(Float.parseFloat(waterBottlePrice));
                foodCartModel.setMenuQty(1);
                sessionmanager.addToFoodCart(getContext(), foodCartModel);

                foodCartRVAdapter.qtyRefreshList();
                tvSubTotal.setText(String.valueOf(sessionmanager.getFoodSubTotal(getContext())));
                break;
        }
    }

    @Override
    public void onResume() {
        ((ActivityCartBill) getActivity()).getSupportActionBar().setTitle("Cart");
        super.onResume();
    }

    private void init() {

        sessionmanager = new Sessionmanager(getContext());
        foodCartModelArrayList = new ArrayList<>();
        tvSubTotal = view.findViewById(R.id.tvSubTotal);
        cartRecyclerView = view.findViewById(R.id.rvCart);
        nestedCart = view.findViewById(R.id.nestedCart);
        llPlaceOrder = view.findViewById(R.id.llPlaceOrder);
        llCartEmpty = view.findViewById(R.id.llCartEmpty);
        btnAddWaterBottle = view.findViewById(R.id.btnAddWaterBottle);
    }
}
