package com.restrosmart.restro.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.restrosmart.restro.R;

public class FragmentOrderStatus extends Fragment implements View.OnClickListener {

    private View view;
    private Spinner spOrders;
    private ImageView ivAccepted, ivPreparing, ivReady, ivServed;
    private LinearLayout llAddMenu, llPayNow;

    private AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_status, container, false);

        init();
        //setupToolbar();

        // Initializing a String Array
        String[] plants = new String[]{"Select Order", "Order 1", "Order 2", "Order 3", "Order 4", "Order 5"};

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, plants);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spOrders.setAdapter(spinnerArrayAdapter);

        llAddMenu.setOnClickListener(this);
        llPayNow.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        ((ActivityCartBill) getActivity()).getSupportActionBar().setTitle("Order Status");
        super.onResume();
    }

   /* private void setupToolbar() {
        mToolbar.setTitle("Order Status");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llAddMenu:
                getActivity().finish();
                break;

            case R.id.llPayNow:
                Intent intent = new Intent(getContext(), ActivityBillPayment.class);
                getContext().startActivity(intent);
                break;
        }
    }

    private void init() {

        spOrders = view.findViewById(R.id.spOrders);
        ivAccepted = view.findViewById(R.id.ivAccepted);
        ivPreparing = view.findViewById(R.id.ivPreparing);
        ivReady = view.findViewById(R.id.ivReady);
        ivServed = view.findViewById(R.id.ivServed);
        llAddMenu = view.findViewById(R.id.llAddMenu);
        llPayNow = view.findViewById(R.id.llPayNow);
    }
}
