package com.restrosmart.restrohotel.Captain.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Activities.ActivityHotelMenu;
import com.restrosmart.restrohotel.Captain.Adapters.RVParcelOrderAdapter;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParcelOrders extends Fragment implements View.OnClickListener {

    private View view;
    private RecyclerView rvParcelOrders;
    private LinearLayout llNoData;
    private FloatingActionButton fabAddMenu;

    private ArrayList<OrderModel> orderModelArrayList;
    private ArrayList<UserCategory> userCategoryArrayList;

    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_parcel_orders, container, false);

        init();

        orderModelArrayList = getArguments().getParcelableArrayList("arrayListParcelOrder");
        userCategoryArrayList = getArguments().getParcelableArrayList("arrayListUserCategory");

        if (orderModelArrayList != null && orderModelArrayList.size() > 0) {
            RVParcelOrderAdapter rvParcelOrderAdapter = new RVParcelOrderAdapter(getContext(), orderModelArrayList);
            rvParcelOrders.setHasFixedSize(true);
            rvParcelOrders.setNestedScrollingEnabled(false);
            rvParcelOrders.setLayoutManager(new GridLayoutManager(getContext(), 1));
            rvParcelOrders.setItemAnimator(new DefaultItemAnimator());
            rvParcelOrders.setAdapter(rvParcelOrderAdapter);

            rvParcelOrders.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        } else {
            rvParcelOrders.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }

        fabAddMenu.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAddMenu) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.user_register_dialog, null);
            }
            dialogBuilder.setView(dialogView);

            alertDialog = dialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();

            EditText edtName = dialogView.findViewById(R.id.edtName);
            EditText edtMobileNo = dialogView.findViewById(R.id.edtMobileNo);
            Button btnCancelOrder = dialogView.findViewById(R.id.btnCancelOrder);
            Button btnConfirmOrder = dialogView.findViewById(R.id.btnConfirmOrder);

            btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();

                    Intent intent = new Intent(getContext(), ActivityHotelMenu.class);
                    intent.putExtra("categoryPos", 0);
                    intent.putExtra("categoryId", userCategoryArrayList.get(0).getCategoryId());
                    intent.putExtra("categoryName", userCategoryArrayList.get(0).getCategoryName());
                    intent.putParcelableArrayListExtra("arrayList", userCategoryArrayList);
                    getContext().startActivity(intent);
                }
            });

            btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        }
    }

    private void init() {
        rvParcelOrders = view.findViewById(R.id.rvParcelOrders);
        llNoData = view.findViewById(R.id.llNoData);
        fabAddMenu = view.findViewById(R.id.fabAddMenu);
    }
}
