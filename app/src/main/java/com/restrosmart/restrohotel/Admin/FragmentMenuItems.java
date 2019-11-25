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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.CategoryViewPagerAdapter;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.AddParentCategoryinfoModel;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.LoadingDialog;
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

import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_SUB;
import static com.restrosmart.restrohotel.ConstantVariables.SAVE_CATEGORY;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentMenuItems extends Fragment {
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SpinKitView skLoading;

    private LinearLayout llNoCategoryData;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private String imageName=null;
    private String  mFinalImageName;
    private int branchId, hotelId, mPcId = 1, btnId;

    private ImageView btnCategory;
    private EditText etxCategoryNme;
    private View dialoglayout;
    private BottomSheetDialog dialog;
    private CircleImageView mCircleImageView;
    private Sessionmanager sessionmanager;

    private CategoryViewPagerAdapter categoryViewPagerAdapter;

    private ArrayList<ParentCategoryForm> mFragmentTitleList;
    private List<CategoryForm> fragmentCategoryModelArrayList;
    private List<AddParentCategoryinfoModel> addParentCategoryinfoModels;
    private ArrayList<ArrayList<CategoryForm>> arrayList;
    private  ApiService apiService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_tab_menu, null);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));


        initRetrofitCallBackForCategory();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_SUB, (service.GetAllCategory(hotelId)));
       // showProgressDialog();


        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnId = v.getId();

                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                dialoglayout = li.inflate(R.layout.activity_add_category, null);
                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(dialoglayout);

                FrameLayout btnCamara = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);
                etxCategoryNme = dialoglayout.findViewById(R.id.etx_category_name);
                Button btnCancel = dialoglayout.findViewById(R.id.btnCancel);
                Button btnSave = dialoglayout.findViewById(R.id.btnSave);
                TextView txTitle = dialoglayout.findViewById(R.id.tx_add_cat);
                txTitle.setVisibility(View.VISIBLE);

                mCircleImageView = (CircleImageView) dialoglayout.findViewById(R.id.img_category);
                Picasso.with(dialoglayout.getContext())
                        .load(R.drawable.ic_steak)
                        .resize(500, 500)
                        .into(mCircleImageView);


                btnCamara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityCategoryGallery.class);
                        intent.putExtra("Pc_Id", mPcId);
                        startActivityForResult(intent, IMAGE_RESULT_OK);
                    }
                });


                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (etxCategoryNme.getText().toString().length() == 0) {
                            alert();
                        } else {

                            if (imageName == null) {
                                mFinalImageName = "null";
                            } else {
                           /* int start = imageName.indexOf("t/");
                            String suffix = imageName.substring(start + 1);
                            int start1 = suffix.indexOf("/");
                            mFinalImageName = suffix.substring(start1 + 1);*/
                                mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                            }
                            skLoading.setVisibility(View.VISIBLE);
                            saveCategoryInformation();
                        }
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

            private void saveCategoryInformation() {
                initRetrofitCallBackForCategory();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(SAVE_CATEGORY, service.addCategory(etxCategoryNme.getText().toString(),
                        mFinalImageName,
                        hotelId,
                        mPcId));

               // showProgressDialog();
            }
        });
    }

    private void alert() {
        Toast.makeText(getActivity(),"Please enter the category name",Toast.LENGTH_LONG).show();
    }



    @Override
    public void onResume() {
        super.onResume();
       /* initRetrofitCallBackForCategory();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_SUB, (service.GetAllCategory(hotelId,
                branchId)));*/

    }


    private void init() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);
        llNoCategoryData = getActivity().findViewById(R.id.llNoCategoryData);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        btnCategory = (ImageView) getActivity().findViewById(R.id.image_add);

        mFragmentTitleList = new ArrayList<>();
        fragmentCategoryModelArrayList = new ArrayList<>();
        addParentCategoryinfoModels = new ArrayList<>();
        arrayList = new ArrayList<>();

        skLoading = getActivity().findViewById(R.id.skLoading);
    }

    private void initRetrofitCallBackForCategory() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId) {
                    case PARENT_CATEGORY_WITH_SUB:
                        JsonObject jsonObject = response.body();
                        String mParentSubcategory = jsonObject.toString();
                        try {
                            JSONObject object = new JSONObject(mParentSubcategory);
                            int status = object.getInt("status");
                            if (status == 1) {
                                llNoCategoryData.setVisibility(View.GONE);

                                JSONObject jsonObject1 = object.getJSONObject("AllMenu");

                                mFragmentTitleList.clear();
                                JSONArray jsonArray = jsonObject1.getJSONArray("MenuList");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    fragmentCategoryModelArrayList.clear();
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

                                        CategoryForm categoryForm = new CategoryForm();
                                        categoryForm.setCategory_id(jsonObject3.getInt("Category_Id"));
                                        categoryForm.setCategory_Name(jsonObject3.getString("Category_Name"));
                                        categoryForm.setC_Image_Name(jsonObject3.getString("C_Image_Name"));
                                        categoryForm.setPc_Id(jsonObject2.getInt("Pc_Id"));
                                        categoryForm.setDefault_image(jsonObject2.getString("Default_Img"));
                                        fragmentCategoryModelArrayList.add(categoryForm);

                                    }

                                    arrayList.add(new ArrayList<CategoryForm>(fragmentCategoryModelArrayList));
                                    AddParentCategoryinfoModel addParentCategoryinfoModel = new AddParentCategoryinfoModel();
                                    addParentCategoryinfoModel.setFragment(new FragmentTabParentCategory());
                                    addParentCategoryinfoModel.setCategoryForms(arrayList.get(i));
                                    addParentCategoryinfoModels.add(addParentCategoryinfoModel);
                                }


                            } else {

                                llNoCategoryData.setVisibility(View.VISIBLE);
                            }
                           skLoading.setVisibility(View.GONE);
                            setupViewPager(viewPager);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case SAVE_CATEGORY:
                        JsonObject jsonObject3 = response.body();
                        String saveCategory = jsonObject3.toString();

                        try {
                            JSONObject object = new JSONObject(saveCategory);
                            int status=object.getInt("status");
                            if(status==1) {
                                Toast.makeText(getActivity(), "Category added successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.setAction("Refresh_CategoryList");
                                getActivity().sendBroadcast(intent);
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                            }
                            skLoading.setVisibility(View.GONE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            imageName = data.getStringExtra("image_name");

            Picasso.with(dialoglayout.getContext())
                    .load(imageName)
                    .resize(500, 500)
                    .into(mCircleImageView);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPcId = mFragmentTitleList.get(i).getPc_id();
                Toast.makeText(getActivity(),"id"+mPcId,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        categoryViewPagerAdapter = new CategoryViewPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentTitleList, addParentCategoryinfoModels);
        categoryViewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(categoryViewPagerAdapter);

    }
}








