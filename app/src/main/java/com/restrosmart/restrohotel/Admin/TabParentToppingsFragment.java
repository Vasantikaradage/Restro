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

import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllCategory;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllToppings;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class TabParentToppingsFragment extends Fragment {
    private AdapterDisplayAllToppings adapterDisplayAllToppings;
    private RecyclerView recyclerView;
    private View view;
    private  Sessionmanager mSessionmanager;
    private  int mHotelId,mBranchId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_items, null);

        init();
        Bundle bundle = getArguments();
        final ArrayList<ToppingsForm>  toppingsForms = bundle.getParcelableArrayList("toppingObject");

        HashMap<String, String> name_info = mSessionmanager.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        adapterDisplayAllToppings = new AdapterDisplayAllToppings(getActivity(), toppingsForms);
        recyclerView.setAdapter(adapterDisplayAllToppings);
        adapterDisplayAllToppings.notifyDataSetChanged();

        return view;

    }


    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_item);
        mSessionmanager = new Sessionmanager(getActivity());
    }

    public static Fragment newInstance(ArrayList<ToppingsForm> toppingsForms, int position) {
        TabParentCategoryFragment fragment = new TabParentCategoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("toppingObject", toppingsForms);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }
}

