package com.restrosmart.restrohotel.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.CategoryViewPagerAdapter;
import com.restrosmart.restrohotel.Adapter.ToppingsViewPagerAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.Category;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.AddParentCategoryinfo;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.Model.ParentToppingsInfo;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_TOPPINGS;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_SUB;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_TOPPINGS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public  class FragmentToppings extends Fragment {

    private  View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  Toolbar toolbar;
    private  Sessionmanager sessionmanager;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  int hotelId,branchId;
    private  ProgressDialog progressDialog;
    private  int mPcId;
    private FrameLayout flAddToppings;
    private  View dialoglayout;
    private  BottomSheetDialog dialog;
    private EditText etvToppingName,etvToppingPrice;

    private ArrayList<ParentCategoryForm> mFragmentTitleList;
    private List<ToppingsForm> fragmentToppingsArrayList;
    private List<ParentToppingsInfo> addParentCategoryinfos;
    private ArrayList<ArrayList<ToppingsForm>> arrayList;

    private  ToppingsViewPagerAdapter toppingsViewPagerAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toppings, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        branchId = Integer.parseInt(name_info.get(BRANCH_ID));

        initRetrofitCallBackForToppings();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId,
                (branchId))));
        showProgressDialog();


        flAddToppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialoglayout = li.inflate(R.layout.dialog_add_toppings, null);
                dialog= new BottomSheetDialog(getActivity());
                dialog.setContentView(dialoglayout);
                etvToppingName=dialoglayout.findViewById(R.id.etv_topping_name);
                etvToppingPrice=dialoglayout.findViewById(R.id.etv_topping_price);
                Button saveTopping=dialoglayout.findViewById(R.id.btnSave);
                Button cancelTopping=dialoglayout.findViewById(R.id.btnCancel);
                Button updateTopping=dialoglayout.findViewById(R.id.btnUpdate);

                saveTopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initRetrofitCallBackForToppings();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());

                        mRetrofitService.retrofitData(ADD_TOPPINGS, service.addToppings(etvToppingName.getText().toString(),
                                Integer.parseInt(etvToppingPrice.getText().toString()),
                                hotelId,
                                branchId,
                                1));
                    }
                });
                dialog.show();

                cancelTopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });




        /*tabLayout.addTab(tabLayout.newTab().setText("Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Veg"));
       // ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);

        Pager adapter = new Pager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1) {
                   // toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),
                                R.color.colorPrimaryDark));
                    }

                } else if (tab.getPosition() == 2) {

                 //   toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_purple));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor((ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)));
                    }

                } else {
                  //  toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),
                                R.color.colorPrimaryDark));
                    }
                }


                // int position =  tabLayout.getSelectedTabPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                //toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(),
                   //     R.color.colorPrimary));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Adding onTabSelectedListener to swipe views
        //tabLayout.addOnTabSelectedListener((TabLayout.BaseOnTabSelectedListener) getActivity());
*/

    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(getContext().getResources().getString(R.string.app_name));
        progressDialog.setMessage("Uploading Toppings");
        progressDialog.show();
    }

    private void initRetrofitCallBackForToppings() {
        
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
              switch (requestId)
              {
                  case PARENT_CATEGORY_WITH_TOPPINGS:
                      JsonObject jsonObject = response.body();
                      String mParentSubcategory = jsonObject.toString();
                      try {
                          JSONObject object = new JSONObject(mParentSubcategory);
                          int status = object.getInt("status");
                          if (status == 1) {
                              JSONObject jsonObject1 = object.getJSONObject("AllMenu");

                              mFragmentTitleList.clear();
                              JSONArray jsonArray = jsonObject1.getJSONArray("MenuList");

                              for (int i = 0; i < jsonArray.length(); i++) {
                                  fragmentToppingsArrayList.clear();
                                  JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                  JSONArray jsonArray1 = jsonObject2.getJSONArray("Menu");
                                  //  String pcName = jsonObject2.getString("Name").toString();
                                  ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
                                  parentCategoryForm.setPc_id(jsonObject2.getInt("Pc_Id"));
                                  parentCategoryForm.setName(jsonObject2.getString("Name").toString());
                                  mFragmentTitleList.add(parentCategoryForm);

                                  for (int in = 0; in < jsonArray1.length(); in++) {

                                      Log.d("", "jsonArray1.length()" + jsonArray1.length());
                                      JSONObject jsonObject3 = jsonArray1.getJSONObject(in);

                                      ToppingsForm toppingsForm = new ToppingsForm();
                                      toppingsForm.setToppingsName(jsonObject3.getString("Topping_Name"));
                                      toppingsForm.setToppingsPrice(jsonObject3.getInt("Toppingf_Price"));
                                      toppingsForm.setPcId(jsonObject2.getInt("Pc_Id"));
                                      fragmentToppingsArrayList.add(toppingsForm);

                                  }

                                  arrayList.add(new ArrayList<ToppingsForm>(fragmentToppingsArrayList));
                                  ParentToppingsInfo parentToppingsInfo = new ParentToppingsInfo();
                                  parentToppingsInfo.setFragment(new TabParentToppingsFragment());
                                  parentToppingsInfo.setToppingsFoprms(arrayList.get(i));
                                  addParentCategoryinfos.add(parentToppingsInfo);
                              }


                          } else {

                          }
                          setupViewPager(viewPager);
                          progressDialog.dismiss();
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                      break;
                  case ADD_TOPPINGS:
                      JsonObject toppingObject = response.body();
                      String mToppingAdd = toppingObject.toString();
                      try {
                          JSONObject object=new JSONObject(mToppingAdd);
                          int status=object.getInt("status");
                          if(status==1)
                          {
                              Toast.makeText(getActivity(),"Topping Added Successfully",Toast.LENGTH_LONG).show();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                      break;
              }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void setupViewPager(ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        toppingsViewPagerAdapter = new ToppingsViewPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentTitleList, addParentCategoryinfos, new Category() {
            @Override
            public void categoryListern(int pcId) {
                mPcId = pcId;

            }
        });
        toppingsViewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(toppingsViewPagerAdapter);
    }


    private void init() {
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        flAddToppings=view.findViewById(R.id.ivAddToppings);

        mFragmentTitleList = new ArrayList<>();
        fragmentToppingsArrayList = new ArrayList<>();
        addParentCategoryinfos = new ArrayList<>();
        arrayList = new ArrayList<>();
    }
}
