package com.restrosmart.restrohotel.Captain.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.RVLiquorBrandsAdapter;
import com.restrosmart.restrohotel.Captain.Models.FlavourUnitModel;
import com.restrosmart.restrohotel.Captain.Models.FlavoursModel;
import com.restrosmart.restrohotel.Captain.Models.LiquorBrandsModel;
import com.restrosmart.restrohotel.Captain.Models.SpecificLiqourBrandModel;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.LoadingDialog;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.LIQOUR_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.UNIQUE_KEY;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentLiquorsMenu extends Fragment {

    private View view;
    private RecyclerView rvLiquorBrands;
    private RVLiquorBrandsAdapter rvLiquorBrandsAdapter;

    private int categoryId;
    private DecimalFormat df2;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private ArrayList<LiquorBrandsModel> liquorBrandsModelArrayList;
    private ArrayList<SpecificLiqourBrandModel> specificLiqourBrandModelArrayList;
    private ArrayList<FlavoursModel> flavoursModelArrayList;
    private ArrayList<ToppingsModel> toppingsModelArrayList;
    private ArrayList<FlavourUnitModel> flavourUnitModelArrayList;

    private HashMap<String, String> hotelDetails;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_liquors_menu, container, false);

        init();

        categoryId = getArguments().getInt("categoryId");
        hotelDetails = mSessionmanager.getHotelDetails();
        loadingDialog.showLoadingDialog();
        getLiqourCategory();

        return view;
    }

    private void getLiqourCategory() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(LIQOUR_CATEGORY, (service.getLiqourCategory(Integer.parseInt(hotelDetails.get(HOTEL_ID)), categoryId, UNIQUE_KEY)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {

                    case LIQOUR_CATEGORY:

                        JsonObject jsonObjectCat = response.body();
                        String responseValue = jsonObjectCat.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);

                            int status = jsonObject.getInt("status");
                            String msg = jsonObject.getString("message");

                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                liquorBrandsModelArrayList.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    specificLiqourBrandModelArrayList = new ArrayList<>();

                                    JSONObject specificBrandJsonObject = jsonArray.getJSONObject(i);

                                    int brandsId = specificBrandJsonObject.getInt("Category_Id");
                                    String brandsName = specificBrandJsonObject.getString("Category_Name");
                                    JSONArray brandsJsonArray = specificBrandJsonObject.getJSONArray("menu");

                                    for (int j = 0; j < brandsJsonArray.length(); j++) {

                                        flavoursModelArrayList = new ArrayList<>();
                                        toppingsModelArrayList = new ArrayList<>();

                                        JSONObject specificBrandsJsonObject = brandsJsonArray.getJSONObject(j);

                                        int specificBrandsId = specificBrandsJsonObject.getInt("Menu_Id");
                                        String specificBrandsName = specificBrandsJsonObject.getString("Menu_Name");
                                        String specificBrandsImg = specificBrandsJsonObject.getString("Menu_Image_Name");
                                        JSONArray brandsFlavourJsonArray = specificBrandsJsonObject.getJSONArray("flavours");
                                        JSONArray liquorToppingsJsonArray = specificBrandsJsonObject.getJSONArray("Topping");

                                        for (int k = 0; k < brandsFlavourJsonArray.length(); k++) {

                                            flavourUnitModelArrayList = new ArrayList<>();
                                            JSONObject flavoursJsonObject = brandsFlavourJsonArray.getJSONObject(k);

                                            int flavourId = flavoursJsonObject.getInt("Flavour_Id");
                                            String flavourName = flavoursJsonObject.getString("Flavour_Name");
                                            String flavourImg = flavoursJsonObject.getString("F_Image_Name");
                                            JSONArray flavourUnitJsonArray = flavoursJsonObject.getJSONArray("unit");

                                            for (int l = 0; l < flavourUnitJsonArray.length(); l++) {

                                                JSONObject flavourUnitJsonObject = flavourUnitJsonArray.getJSONObject(l);

                                                FlavourUnitModel flavourUnitModel = new FlavourUnitModel();
                                                flavourUnitModel.setUnitName(flavourUnitJsonObject.getString("Unit_Name"));
                                                flavourUnitModel.setUnitPrice(Float.parseFloat(df2.format(Float.parseFloat(flavourUnitJsonObject.getString("Unit_Cost")))));

                                                flavourUnitModelArrayList.add(flavourUnitModel);
                                            }

                                            FlavoursModel flavoursModel = new FlavoursModel();
                                            flavoursModel.setFlavourId(flavourId);
                                            flavoursModel.setFlavourName(flavourName);
                                            flavoursModel.setFlavourImg(flavourImg);
                                            flavoursModel.setFlavourUnitModelArrayList(flavourUnitModelArrayList);

                                            flavoursModelArrayList.add(flavoursModel);
                                        }

                                        for (int m = 0; m < liquorToppingsJsonArray.length(); m++) {
                                            JSONObject toppingsJsonObject = liquorToppingsJsonArray.getJSONObject(m);

                                            ToppingsModel toppingsModel = new ToppingsModel();
                                            toppingsModel.setToppingsId(toppingsJsonObject.getInt("Topping_Id"));
                                            toppingsModel.setToppingsName(toppingsJsonObject.getString("Topping_Name"));
                                            toppingsModel.setToppingsPrice(Float.parseFloat(df2.format(Float.parseFloat(toppingsJsonObject.getString("Topping_Price")))));

                                            toppingsModelArrayList.add(toppingsModel);
                                        }

                                        SpecificLiqourBrandModel specificLiqourBrandModel = new SpecificLiqourBrandModel();
                                        specificLiqourBrandModel.setMenuId(specificBrandsId);
                                        specificLiqourBrandModel.setMenuName(specificBrandsName);
                                        specificLiqourBrandModel.setMenuImage(specificBrandsImg);
                                        specificLiqourBrandModel.setFlavoursModelArrayList(flavoursModelArrayList);
                                        specificLiqourBrandModel.setToppingsModelArrayList(toppingsModelArrayList);

                                        specificLiqourBrandModelArrayList.add(specificLiqourBrandModel);
                                    }

                                    LiquorBrandsModel liquorBrandsModel = new LiquorBrandsModel();

                                    liquorBrandsModel.setBrandId(brandsId);
                                    liquorBrandsModel.setBrandName(brandsName);
                                    liquorBrandsModel.setArrayList(specificLiqourBrandModelArrayList);

                                    liquorBrandsModelArrayList.add(liquorBrandsModel);
                                }


                                rvLiquorBrands.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                //rvLiquorBrands.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
                                rvLiquorBrandsAdapter = new RVLiquorBrandsAdapter(getContext(), categoryId, liquorBrandsModelArrayList);
                                rvLiquorBrands.setAdapter(rvLiquorBrandsAdapter);
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

    private void init() {
        df2 = new DecimalFormat(".##");
        mSessionmanager = new Sessionmanager(getContext());
        loadingDialog = new LoadingDialog(getContext());

        liquorBrandsModelArrayList = new ArrayList<>();

        rvLiquorBrands = view.findViewById(R.id.rvLiquorBrands);
    }
}
