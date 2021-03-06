package com.restrosmart.restrohotel.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.ToppingsViewPagerAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.Model.ParentToppingsInfoForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_TOPPINGS;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_TOPPINGS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public  class FragmentToppings extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  Sessionmanager sessionmanager;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  int hotelId,branchId;
   // private  ProgressDialog progressDialog;
    private  int mPcId=1;
    private FrameLayout flAddToppings;
    private  View dialoglayout;
    private  BottomSheetDialog dialog;
    private String imageName,mFinalImageName;
    private CircleImageView mCircleImageView;
    private EditText etvToppingName,etvToppingPrice;
    private TextView tvToppingTitle;

    private ArrayList<ParentCategoryForm> mFragmentTitleList;
    private List<ToppingsForm> fragmentToppingsArrayList;
    private List<ParentToppingsInfoForm> addParentCategoryinfos;
    private ArrayList<ArrayList<ToppingsForm>> arrayList;

    private  ToppingsViewPagerAdapter toppingsViewPagerAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_toppings, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sessionmanager.setTabposition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /* initRetrofitCallBackForToppings();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId,
                (branchId))));
        showProgressDialog();*/


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
                tvToppingTitle=dialoglayout.findViewById(R.id.tv_topping_title);
                tvToppingTitle.setVisibility(View.VISIBLE);
                Button cancelTopping=dialoglayout.findViewById(R.id.btnCancel);
                Button updateTopping=dialoglayout.findViewById(R.id.btnUpdate);
                mCircleImageView=dialoglayout.findViewById(R.id.img_toppings);
                FrameLayout frameLayoutImage=dialoglayout.findViewById(R.id.iv_select_image);
                frameLayoutImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent  imageIntent=new Intent(getActivity(),ActivityImageTopping.class);
                        imageIntent.putExtra("pcId", mPcId);
                        startActivityForResult(imageIntent, IMAGE_RESULT_OK);
                    }
                });

                saveTopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(etvToppingName.getText().toString().length()==0 || etvToppingPrice.getText().toString().length()==0){
                            alert();
                        }
                        else{


                            if(imageName==null)
                        {
                            mFinalImageName="null";
                        }
                        else
                        {
                            mFinalImageName=imageName.substring(imageName.lastIndexOf("/")+1);
                        }

                        saveToppingInformation();

                    }}


                    private void saveToppingInformation() {
                        initRetrofitCallBackForToppings();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(ADD_TOPPINGS, service.addToppings(etvToppingName.getText().toString(),
                                mFinalImageName,
                                Integer.parseInt(etvToppingPrice.getText().toString()),
                                hotelId,
                                mPcId));
                    }
                });


                cancelTopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }

    private void alert() {
        Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        initRetrofitCallBackForToppings();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId)));
        // showProgressDialog();
    }

//    private void showProgressDialog() {
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        //Without this user can hide loader by tapping outside screen
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setTitle(getContext().getResources().getString(R.string.app_name));
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
   // }

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
                         // progressDialog.dismiss();
                          if (status == 1) {
                              mFragmentTitleList.clear();
                              JSONArray jsonArray = object.getJSONArray("Topping_List");

                              for (int i = 0; i < jsonArray.length(); i++) {
                                  fragmentToppingsArrayList.clear();
                                  JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                  JSONArray jsonArray1 = jsonObject2.getJSONArray("Topping");

                                  ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
                                  parentCategoryForm.setPc_id(jsonObject2.getInt("Pc_Id"));
                                  parentCategoryForm.setName(jsonObject2.getString("Name").toString());
                                  mFragmentTitleList.add(parentCategoryForm);

                                  for (int in = 0; in < jsonArray1.length(); in++) {

                                      Log.d("", "jsonArray1.length()" + jsonArray1.length());
                                      JSONObject jsonObject3 = jsonArray1.getJSONObject(in);

                                      ToppingsForm toppingsForm = new ToppingsForm();
                                      toppingsForm.setToppingsName(jsonObject3.getString("Topping_Name"));
                                      toppingsForm.setToppingsPrice(jsonObject3.getInt("Topping_Price"));
                                      toppingsForm.setToppingId(jsonObject3.getInt("Topping_Id"));
                                      toppingsForm.setImage(jsonObject3.getString("Topping_Img").toString());

                                      toppingsForm.setPcId(jsonObject2.getInt("Pc_Id"));
                                      fragmentToppingsArrayList.add(toppingsForm);

                                  }

                                  arrayList.add(new ArrayList<ToppingsForm>(fragmentToppingsArrayList));
                                  ParentToppingsInfoForm parentToppingsInfoForm = new ParentToppingsInfoForm();
                                  parentToppingsInfoForm.setFragment(new FragmentTabParentToppings());
                                  parentToppingsInfoForm.setToppingsFoprms(arrayList.get(i));
                                  addParentCategoryinfos.add(parentToppingsInfoForm);
                              }


                          } else {

                          }
                          setupViewPager(viewPager);
                          //progressDialog.dismiss();
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
                              Intent intent = new Intent();
                              intent.setAction("Refresh_ToppingList");
                              getActivity().sendBroadcast(intent);
                              dialog.dismiss();

                          }
                          else {
                              Toast.makeText(getActivity(),object.getString("message"),Toast.LENGTH_LONG).show();
                              dialog.dismiss();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                      break;
              }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","requestId"+requestId);
                Log.d("","RetrofitError"+error);

            }
        };
    }

    private void setupViewPager(ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPcId = mFragmentTitleList.get(i).getPc_id();
                //Toast.makeText(getActivity(),"id"+mPcId,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        toppingsViewPagerAdapter = new ToppingsViewPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentTitleList, addParentCategoryinfos);
        toppingsViewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(toppingsViewPagerAdapter);
        viewPager.setCurrentItem(sessionmanager.getTabposition());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sessionmanager.setTabposition(0);
    }

    private void init() {
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout1);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager1);

        flAddToppings=getActivity().findViewById(R.id.ivAddToppings);

        mFragmentTitleList = new ArrayList<>();
        fragmentToppingsArrayList = new ArrayList<>();
        addParentCategoryinfos = new ArrayList<>();
        arrayList = new ArrayList<>();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            imageName = data.getStringExtra("image_name");
            Log.e("Result for image", imageName);

            Picasso.with(dialoglayout.getContext())
                    .load(imageName)
                    .resize(500, 500)
                    .into(mCircleImageView);
        }
    }
}
