package com.restrosmart.restro.Admin;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Adapter.CategoryViewPagerAdapter;
import com.restrosmart.restro.Adapter.RecyclerViewImageAdapter;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.Category;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.AddImage;
import com.restrosmart.restro.Model.AddParentCategoryinfo;
import com.restrosmart.restro.Model.CategoryForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.IMAGE_LIST;
import static com.restrosmart.restro.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restro.ConstantVariables.PARENT_CATEGORY;
import static com.restrosmart.restro.ConstantVariables.SAVE_CATEGORY;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.ROLE_ID;

public class MenuItems extends Fragment {
    TabLayout tabLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    RetrofitService mRetrofitService;
    String image_result;
    MyReceiver myReceiver;

    ImageView btnCategory;
    int tabPosition, btnId;
    ArrayList<AddImage> arrayListImage = new ArrayList<AddImage>();
    private IntentFilter intentFilter;
    private IResult mResultCallBack;
    private String branchId, hotelId, mImageName, image_name, categoryName, emp_role, image, mFinalImageName;
    private EditText etxCategoryNme;
    private RecyclerView image_recyclerview;
    private View dialoglayout;
    private CircleImageView circleImageView1;
    private ArrayList<String> mFragmentTitleList = new ArrayList<>();
    private List<CategoryForm> fragmentCategoryModelArrayList = new ArrayList<CategoryForm>();
    private List<AddParentCategoryinfo> addParentCategoryinfos = new ArrayList<AddParentCategoryinfo>();
    private ArrayList<ArrayList<CategoryForm>> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_tab_menu, null);

        intentFilter = new IntentFilter("Add_Category");

       myReceiver = new MyReceiver();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }


    @Override
    public void onStart() {

        super.onStart();

    }


    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    //Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
    //  getContext().registerReceiver(myReceiver,intentFilter);
    // init();


    @Override
    public void onPause() {
        super.onPause();
        //getContext().unregisterReceiver(myReceiver);
    }


    private void update() {
//        Sessionmanager sharedPreferanceManage1 = new Sessionmanager(getActivity());
//        image = sharedPreferanceManage1.getImage();
        Picasso.with(dialoglayout.getContext())
                .load(image)
                .resize(500, 500)
                .into(circleImageView1);
    }

    private void init() {

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        btnCategory = (ImageView) getActivity().findViewById(R.id.image_add);


        Sessionmanager sharedPreferanceManage = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        emp_role = name_info.get(ROLE_ID);
        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);


        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PARENT_CATEGORY, (service.GetAllCategory(Integer.parseInt(hotelId),
                Integer.parseInt(branchId))));


        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnId = v.getId();

                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                dialoglayout = li.inflate(R.layout.activity_add_category, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialoglayout);

                final AlertDialog dialog = builder.create();

                FrameLayout btnCamara = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);


               /* Picasso.with(dialoglayout.getContext())
                        .load(R.drawable.foodimg2)
                        .resize(500, 500)
                        .into(circleImageView1);*/




                etxCategoryNme=dialoglayout.findViewById(R.id.etx_category_name);
                Button btnCancel = dialoglayout.findViewById(R.id.btnCancel);
                Button btnSave = dialoglayout.findViewById(R.id.btnSave);
                TextView txTitle=dialoglayout.findViewById(R.id.tx_add_cat);
                txTitle.setVisibility(View.VISIBLE);


                circleImageView1=(CircleImageView)dialoglayout.findViewById(R.id.img_category);

                Picasso.with(dialoglayout.getContext())
                        .load(R.drawable.foodimg2)
                        .resize(500, 500)
                        .into(circleImageView1);

                btnCamara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityCategoryGallery.class);
                        startActivityForResult(intent,IMAGE_RESULT_OK);
                    }
                });


              /*  Picasso.with(dialoglayout.getContext())
                        .load(image_result)
                        .resize(500, 500)
                        .into(circleImageView1);*/


                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String    selImage = image_result;
                        int start = selImage.indexOf("t/");
                        String suffix = selImage.substring(start + 1);
                        int start1 = suffix.indexOf("/");
                        mFinalImageName = suffix.substring(start1 + 1);
                        getRetrofitDataSave();

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }


                });

                dialog.show();


            }

            private void getRetrofitDataSave() {

                initRetrofitCallBackForCategory();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());

                mRetrofitService.retrofitData(SAVE_CATEGORY, service.addCategory(etxCategoryNme.getText().toString(),
                        mFinalImageName,
                        Integer.parseInt(hotelId),
                        Integer.parseInt(branchId),
                        tabPosition));

            }

            private void initRetrofitCallBackForCategory() {
                mResultCallBack = new IResult() {
                    @Override
                    public void notifySuccess(int requestId, Response<JsonObject> response) {

                        switch (requestId) {

                            case IMAGE_LIST:

                                JsonObject jsonObject1 = response.body();
                                String imageValue = jsonObject1.toString();
                                try {
                                    JSONObject object = new JSONObject(imageValue);
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object1 = jsonArray.getJSONObject(i);
                                        AddImage addImage = new AddImage();
                                        addImage.setImage(object1.getString("image").toString());
                                        arrayListImage.add(addImage);
                                    }
                                    getImage(arrayListImage);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;

                                    /*case EDIT_CATEGORY:

                                        JsonObject jsonObject2 = response.body();
                                        String valueinfo = jsonObject2.toString();
                                        try {
                                            JSONObject object = new JSONObject(valueinfo);
                                            int status = object.getInt("status");
                                            if (status == 1) {
                                                Toast.makeText(getActivity(), "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent();
                                                intent.setAction("Update_Category");
                                                  sendBroadcast(intent);


                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        break;*/

                            case SAVE_CATEGORY:
                                JsonObject jsonObject3 = response.body();
                                String value = jsonObject3.toString();

                                try {
                                    JSONObject object = new JSONObject(value);
                                    JSONObject object1=object.getJSONObject("data");
                                    Toast.makeText(getActivity(), "Category added successfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    intent.setAction("Add_Category");
                                    sendBroadcast(intent);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                break;


                        }
                    }

                    @Override
                    public void notifyError(int requestId, Throwable error) {
                        Toast.makeText(getActivity(), "error" + error,
                                Toast.LENGTH_SHORT).show();
                    }
                };
            }


        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/){
            image_result=data.getStringExtra("image_name");
            Log.e("Result",image_result);

            Picasso.with(dialoglayout.getContext())
                    .load(image_result)
                    .resize(500, 500)
                    .into(circleImageView1);
        }
    }

    private void sendBroadcast(Intent intent) {
    }

    private void getImage(final ArrayList<AddImage> images) {


        image_recyclerview.setHasFixedSize(true);
        int no_of_col = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        RecyclerViewImageAdapter adapter = new RecyclerViewImageAdapter(getActivity(), images, mImageName, new Category() {

            @Override
            public void categoryListern(int position) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                image_name = images.get(position).getImage();
                //  mImageName = image_name.substring(image_name.lastIndexOf('/') + 1);
                int start = image_name.indexOf("t/");
                String suffix = image_name.substring(start + 1);
                int start1 = suffix.indexOf("/");
                mImageName = suffix.substring(start1 + 1);
            }
        });

        image_recyclerview.setLayoutManager(gridLayoutManager);
        image_recyclerview.setAdapter(adapter);
    }

    private void initRetrofitCallback() {

        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();

                String value = jsonObject.toString();
                try {

                    JSONObject object = new JSONObject(value);
                    int status = object.getInt("status");
                    if (status == 1) {
                        JSONObject jsonObject1 = object.getJSONObject("AllMenu");

                        mFragmentTitleList.clear();
                        JSONArray jsonArray = jsonObject1.getJSONArray("MenuList");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            fragmentCategoryModelArrayList.clear();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            JSONArray jsonArray1 = jsonObject2.getJSONArray("Menu");
                            String pcName;

                            if (jsonArray1.length() != 0) {

                                pcName = jsonObject2.getString("Name").toString();

                            } else {
                                pcName = jsonObject2.getString("Name").toString();
                            }
                            mFragmentTitleList.add(pcName);


                            for (int in = 0; in < jsonArray1.length(); in++) {

                                Log.d("", "jsonArray1.length()" + jsonArray1.length());
                                JSONObject jsonObject3 = jsonArray1.getJSONObject(in);

                                CategoryForm categoryForm = new CategoryForm();
                                categoryForm.setCategory_id(jsonObject3.getInt("Category_Id"));
                                categoryForm.setCategory_Name(jsonObject3.getString("Category_Name"));
                                categoryForm.setC_Image_Name(jsonObject3.getString("C_Image_Name"));
                                // categoryForm.setBranch_Id(jsonObject3.getInt("Branch_Id"));
                                //categoryForm.setHotel_Id(Integer.parseInt(hotelId));
                                fragmentCategoryModelArrayList.add(categoryForm);

                            }

                            arrayList.add(new ArrayList<CategoryForm>(fragmentCategoryModelArrayList));

                            AddParentCategoryinfo addParentCategoryinfo = new AddParentCategoryinfo();
                            addParentCategoryinfo.setFragment(new TabParentCategoryFragment());
                            addParentCategoryinfo.setCategoryForms(arrayList.get(i));
                            addParentCategoryinfos.add(addParentCategoryinfo);


                        }
                    } else {

                    }

                    setupViewPager(viewPager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Toast.makeText(getActivity(), "error" + error,
                        Toast.LENGTH_SHORT).show();

                Log.d("", "vasanti error" + error);

            }


        };
    }

    private void setupViewPager(ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        CategoryViewPagerAdapter categoryViewPagerAdapter = new CategoryViewPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentTitleList, addParentCategoryinfos, new Category() {
            @Override
            public void categoryListern(int position) {
                tabPosition = position;
                //  pcId= position;

            }
        });

        categoryViewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(categoryViewPagerAdapter);
        viewPager.setCurrentItem(0);

        // viewPager.setOffscreenPageLimit(1);


    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
             init();

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PARENT_CATEGORY,(service.GetAllCategory(1,1)));


            Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_SHORT).show();


        }
    }
}








