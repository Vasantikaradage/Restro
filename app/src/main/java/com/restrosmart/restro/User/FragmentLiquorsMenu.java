package com.restrosmart.restro.User;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restro.Adapter.RVLiquorBrandsAdapter;
import com.restrosmart.restro.Model.LiquorBrandsModel;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class FragmentLiquorsMenu extends Fragment {

    private View view;
    private RecyclerView rvLiquorBrands;
    private RVLiquorBrandsAdapter rvLiquorBrandsAdapter;

    private ArrayList<String> brandName;
    private int[] ints = {R.drawable.bottle1, R.drawable.bottle5, R.drawable.bottle3,
            R.drawable.bottle4, R.drawable.bottle2, R.drawable.bottle6,
            R.drawable.bottle7, R.drawable.bottle8, R.drawable.bottle9,
            R.drawable.bottle10, R.drawable.bottle11, R.drawable.bottle12,
            R.drawable.bottle13, R.drawable.bottle14, R.drawable.bottle15,
            R.drawable.bottle16, R.drawable.bottle17, R.drawable.bottle18,
            R.drawable.bottle19, R.drawable.bottle20, R.drawable.bottle21,
            R.drawable.bottle22};

    private ArrayList<LiquorBrandsModel> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_liquors_menu, container, false);

        init();

        for (int i = 0; i < brandName.size(); i++) {

            LiquorBrandsModel liquorBrandsModel = new LiquorBrandsModel();
            liquorBrandsModel.setBrandsName(brandName.get(i));
            liquorBrandsModel.setArrayList(ints);
            arrayList.add(liquorBrandsModel);
        }

        rvLiquorBrands.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //rvLiquorBrands.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
        rvLiquorBrandsAdapter = new RVLiquorBrandsAdapter(getContext(), arrayList);
        rvLiquorBrands.setAdapter(rvLiquorBrandsAdapter);

        return view;
    }

    private void init() {
        brandName = new ArrayList<>();
        brandName.add("Wine");
        brandName.add("Wiskey");
        brandName.add("Beer");
        brandName.add("Vodka");
        brandName.add("Rum");
        brandName.add("Gin");

        rvLiquorBrands = view.findViewById(R.id.rvLiquorBrands);
    }
}
