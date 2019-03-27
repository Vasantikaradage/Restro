package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restro.Adapter.AdapterDisplayAllCategory;
import com.restrosmart.restro.Interfaces.Category;
import com.restrosmart.restro.Model.CategoryForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.Utils.Sessionmanager;

import java.util.ArrayList;


/**
 * Created by SHREE on 16/10/2018.
 */

public class TabParentCategoryFragment extends Fragment {
    int position;

    Category category;
    private  int brancId,hotelId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_items, null);

        Bundle bundle = getArguments();
        ArrayList<CategoryForm> categoryForms = bundle.getParcelableArrayList("menuobj");
        position=bundle.getInt("position");
       // category.categoryListern(position);

        Sessionmanager sessionmanager =new Sessionmanager(getActivity());

        for(int i=0;i<categoryForms.size();i++)
        {
            brancId=categoryForms.get(i).getBranch_Id();
            hotelId=categoryForms.get(i).getHotel_Id();

        }
        sessionmanager.setTabposition(position);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_item);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);


        AdapterDisplayAllCategory adapter_menu_item = new AdapterDisplayAllCategory(getActivity(), categoryForms);
        recyclerView.setAdapter(adapter_menu_item);
        return view;

    }



    @Override
    public void onResume() {
        super.onResume();
    }


    public static Fragment newInstance(ArrayList<CategoryForm> menu, int position) {

        TabParentCategoryFragment fragment = new TabParentCategoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("menuobj", menu);
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }



}



