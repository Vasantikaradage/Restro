package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.restrosmart.restrohotel.Adapter.AdapterParcelOrder;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentTabTakeAwayOrders extends Fragment {
    private ArrayList<OrderModel> arraylistParcelOrder;
    private Sessionmanager sessionmanager;
    private int hotelId;
    private RecyclerView rvParcel;
    private ArrayList<String> orderIdsArrayList;
    private ArrayList<String> arrayList;
    private LinearLayout llNodata;
    private SpinKitView skloding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //arrayList=getArguments().getStringArrayList("arrayListId");
        arraylistParcelOrder = getArguments().getParcelableArrayList("OrderArrayListTakeAway");
        View view = inflater.inflate(R.layout.tab_parcel_orders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        callViewAdapter();
      /*  if (arraylistParcelOrder.size() != 0 && arraylistParcelOrder != null) {
            skloding.setVisibility(View.GONE);
            llNodata.setVisibility(View.GONE);

        } else {
            skloding.setVisibility(View.GONE);
            llNodata.setVisibility(View.VISIBLE);
        }
*/

    }

    private void callViewAdapter() {

        if (arraylistParcelOrder != null && arraylistParcelOrder.size() > 0) {

            for (int i = 0; i < arraylistParcelOrder.size(); i++) {
                arrayList.add("Order " + (i + 1));
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvParcel.setLayoutManager(linearLayoutManager);
            AdapterParcelOrder adapterParcelOrder = new AdapterParcelOrder(getContext(), arraylistParcelOrder);
            rvParcel.setAdapter(adapterParcelOrder);
        } else {
            //No data
        }
    }

    private void init() {
        rvParcel = getActivity().findViewById(R.id.recycler_parcel_order);

        arrayList = new ArrayList<>();
      //  skloding = getActivity().findViewById(R.id.skLoading);
      //  llNodata = getActivity().findViewById(R.id.llNoOrderData);
    }
}
