package com.restrosmart.restrohotel.Admin;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import com.restrosmart.restrohotel.Adapter.CategoryViewPagerAdapterOffer;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.AddParentCategoryinfoModel;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
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

import static com.restrosmart.restrohotel.ConstantVariables.DASHBOARD_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_SUB;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_SUBMENU;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivitySelectMenu extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;
    private ProgressDialog progressDialog;
    private LinearLayout llNoCategoryData;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private int branchId, hotelId, mPcId = 1;


    private Sessionmanager sessionmanager;
    private ArrayList<UserCategory> arrayListUserCategory;


    private CategoryViewPagerAdapterOffer categoryViewPagerAdapterOffer;

    private ArrayList<ParentCategoryForm> mFragmentTitleList;
    private List<CategoryForm> fragmentCategoryModelArrayList;
    private List<AddParentCategoryinfoModel> addParentCategoryinfoModels;
    private ArrayList<ArrayList<CategoryForm>> arrayList;

    private ArrayList<MenuDisplayForm> arrayListMenu;
    private ArrayList<ToppingsForm> arrayListToppings;
    private ApiService apiService;
    private TextView tvToolBarTitle;
    private Toolbar mToolbar;
    private RequestQueue queue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        init();
        setUpToolBar();

        sessionmanager = new Sessionmanager(this);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
/*

        initRetrofitCallBackForCategory();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(DASHBOARD_CATEGORY, (service.getCategory(hotelId)));
*/

        initRetrofitCallBackForCategory();
        /* ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_SUBMENU, (service.GetAllCategorywithMenu()));*/
        //showProgressDialog();

    }

    private void setUpToolBar() {
        tvToolBarTitle.setText("Select Menu");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void initRetrofitCallBackForCategory() {
        StringRequest request = new StringRequest(Request.Method.GET, "https://api.myjson.com/bins/1ce12o", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                String str=response.toString();



      /*  mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                progressDialog.dismiss();
                JsonObject jsonObject = response.body();
             //   String jsonObject12 = "https://api.myjson.com/bins/1fw6qo";
              //  String category=jsonObject12.toString();

               String category = jsonObject.toString();
                switch (requestId) {
                    case PARENT_CATEGORY_WITH_SUBMENU:*/

                try {
                    JSONObject object = new JSONObject(str);
                    int status = object.getInt("status");
                    if (status == 1) {

                        JSONObject jsonObject1 = object.getJSONObject("AllMenu");

                        mFragmentTitleList.clear();
                        JSONArray jsonArray = jsonObject1.getJSONArray("MenuList");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            fragmentCategoryModelArrayList.clear();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            JSONArray jsonArray1 = jsonObject2.getJSONArray("Menu");

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


                                //menudisplay
                                JSONArray menuArray = jsonObject3.getJSONArray("submenu");

                                arrayListMenu=new ArrayList<>();
                                for (int im = 0; im < menuArray.length(); im++) {
                                    JSONObject menuObject = menuArray.getJSONObject(im);

                                    MenuDisplayForm menuForm = new MenuDisplayForm();
                                    menuForm.setMenu_Id(menuObject.getInt("Menu_Id"));

                                    menuForm.setCategory_Id(menuObject.getInt("Category_Id"));
                                    menuForm.setMenu_Name(menuObject.getString("Menu_Name"));
                                    menuForm.setMenu_Image_Name(menuObject.getString("Menu_Image_Name"));
                                    menuForm.setNon_Ac_Rate(menuObject.getInt("Non_Ac_Rate"));
                                    menuForm.setMenu_Test(menuObject.getInt("Menu_Test"));
                                    menuForm.setMenu_Descrip(menuObject.getString("Menu_Descrip"));
                                    menuForm.setHotel_Id(menuObject.getInt("Hotel_Id"));
                                    menuForm.setStatus(menuObject.getInt("Menu_Status"));

                                    JSONArray toppingArray = menuObject.getJSONArray("Topping");

                                    arrayListToppings = new ArrayList<>();
                                    // arrayListToppingAddMenu.clear();
                                    for (int it = 0; it < toppingArray.length(); it++) {

                                        JSONObject toppingsObject = toppingArray.getJSONObject(it);
                                        ToppingsForm toppingsForm = new ToppingsForm();
                                        toppingsForm.setToppingId(toppingsObject.getInt("Topping_Id"));
                                        toppingsForm.setToppingsName(toppingsObject.getString("Topping_Name"));
                                        toppingsForm.setToppingsPrice(toppingsObject.getInt("Topping_Price"));

                                        arrayListToppings.add(toppingsForm);
                                    }

                                    menuForm.setArrayListtoppings(arrayListToppings);
                                    arrayListMenu.add(menuForm);
                                }


                                categoryForm.setMenuDisplayFormArrayList(arrayListMenu);
                                fragmentCategoryModelArrayList.add(categoryForm);
                            }

                            arrayList.add(new ArrayList<CategoryForm>(fragmentCategoryModelArrayList));
                            AddParentCategoryinfoModel addParentCategoryinfoModel = new AddParentCategoryinfoModel();
                            addParentCategoryinfoModel.setFragment(new FragmentTabParentCategory());
                            addParentCategoryinfoModel.setCategoryForms(arrayList.get(i));
                            addParentCategoryinfoModels.add(addParentCategoryinfoModel);
                        }


                    } else {
                        progressDialog.dismiss();
                    }
                    setupViewPager(viewPager);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

                       // break;

                  /*  case DASHBOARD_CATEGORY:

                        try {
                            JSONObject jsonObject1 = new JSONObject(category);

                            int status = jsonObject1.getInt("status");
                            if (status == 1) {
                                arrayListUserCategory.clear();

                                JSONArray jsonArray = jsonObject1.getJSONArray("category");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                    UserCategory userCategory = new UserCategory();
                                    userCategory.setCategoryId(jsonObject2.getInt("Pc_Id"));
                                    userCategory.setCategoryImage(jsonObject2.getString("Image_Name"));
                                    userCategory.setCategoryName(jsonObject2.getString("Name"));
                                    arrayListUserCategory.add(userCategory);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

*/

            //}

            /*@Override
            public void notifyError(int requestId, Throwable error) {
                progressDialog.dismiss();
                Toast.makeText(ActivitySelectMenu.this, "error" + error,
                        Toast.LENGTH_SHORT).show();
            }
        };*/
   // }

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
                Toast.makeText(ActivitySelectMenu.this, "id" + mPcId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        categoryViewPagerAdapterOffer = new CategoryViewPagerAdapterOffer(getSupportFragmentManager(), mFragmentTitleList, addParentCategoryinfoModels);
        categoryViewPagerAdapterOffer.notifyDataSetChanged();
        viewPager.setAdapter(categoryViewPagerAdapterOffer);
    }


    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(getResources().getString(R.string.app_name));
        progressDialog.setMessage("Uploading Menu");
        progressDialog.show();
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        queue = Volley.newRequestQueue(this);


        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        llNoCategoryData = findViewById(R.id.llNoCategoryData);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        mFragmentTitleList = new ArrayList<>();
        fragmentCategoryModelArrayList = new ArrayList<>();
        addParentCategoryinfoModels = new ArrayList<>();
        arrayList = new ArrayList<>();
        arrayListUserCategory = new ArrayList<>();
        arrayListMenu = new ArrayList<>();
        arrayListToppings = new ArrayList<>();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
