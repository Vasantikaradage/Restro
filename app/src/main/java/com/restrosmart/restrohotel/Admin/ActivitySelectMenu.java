package com.restrosmart.restrohotel.Admin;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
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

import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_SUBMENU;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivitySelectMenu extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;
    private ProgressDialog progressDialog;
    private LinearLayout llNoCategoryData;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private int hotelId;
    private int mPcId = 1;


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
    private int offerTypeId, offerId, winnerQty, buyQty, getQty;
    private ArrayList<MenuDisplayForm> menuDisplayFormArrayList;
    Intent intentDaily;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        init();
        setUpToolBar();
        intentDaily = getIntent();
        offerTypeId = intentDaily.getIntExtra("offerTypeId", 0);
        offerId = intentDaily.getIntExtra("offerId", 0);

        if (offerTypeId == 2) {
            winnerQty = intentDaily.getIntExtra("winnerQty", 0);
        } else if (offerTypeId == 1) {
            buyQty = intentDaily.getIntExtra("buyCnt", 0);
            getQty = intentDaily.getIntExtra("getCnt", 0);

        } else {

        }
        sessionmanager = new Sessionmanager(this);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        sessionmanager.deleteMenuCartList();

        initRetrofitCallBackForCategory();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_SUBMENU, (service.GetAllCategorywithMenu(hotelId)));
        showProgressDialog();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ok) {
            ArrayList<MenuDisplayForm> menuDisplayFormArrayList = sessionmanager.getAddToMenuCartList(ActivitySelectMenu.this);

            if (offerTypeId == 4) {
                if (menuDisplayFormArrayList == null) {
                    Toast.makeText(ActivitySelectMenu.this, "Please Select Menu...", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ActivitySelectMenu.this, ActivityMenuCart.class);
                    intent.putExtra("offerTypeId", offerTypeId);
                    intent.putExtra("offerId", offerId);
                    startActivity(intent);
                }
            } else if (offerTypeId == 2) {
                if (menuDisplayFormArrayList == null) {
                    Toast.makeText(ActivitySelectMenu.this, "Please Select Menu...", Toast.LENGTH_SHORT).show();
                } else if (menuDisplayFormArrayList.size() <= winnerQty) {
                    Intent intent = new Intent(ActivitySelectMenu.this, ActivityMenuCart.class);
                    intent.putExtra("offerTypeId", offerTypeId);
                    intent.putExtra("offerId", offerId);
                    intent.putExtra("winnerQty", winnerQty);
                    startActivity(intent);
                } else {
                    Toast.makeText(ActivitySelectMenu.this, "Selected Menu is more the winner people qty", Toast.LENGTH_SHORT).show();

                }
            } else if (buyQty != 0) {
                if (buyQty== menuDisplayFormArrayList.size()) {
                    Intent intent = getIntent();
                    intent.putExtra("menuListBuy", menuDisplayFormArrayList);
                    setResult(200, intent);
                    finish();
                } else {
                    Toast.makeText(ActivitySelectMenu.this, "Please select " + buyQty + " for this offer...", Toast.LENGTH_SHORT).show();

                }

            } else if (getQty != 0) {

                if (menuDisplayFormArrayList.size() == getQty) {
                    // menuDisplayFormArrayList = sessionmanager.getAddToMenuCartList(this);
                    Intent intent = getIntent();
                    intent.putExtra("menuListGet", menuDisplayFormArrayList);
                    setResult(300, intent);
                    finish();
                } else {
                    Toast.makeText(ActivitySelectMenu.this, "Please select " + getQty + " for this offer...", Toast.LENGTH_SHORT).show();

                    //  }
                }
            } else if ((buyQty == 0) && (getQty == 0)) {
                if (menuDisplayFormArrayList.size() == 1) {
                    Intent intent = new Intent(ActivitySelectMenu.this, ActivityMenuCart.class);
                    intent.putExtra("offerTypeId", offerTypeId);
                    intent.putExtra("offerId", offerId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ActivitySelectMenu.this, "Please select only one menu for this offer...", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(ActivitySelectMenu.this, "Please Select Menu...", Toast.LENGTH_SHORT).show();

            }
        }

            return super.onOptionsItemSelected(item);
        }

        private void setUpToolBar () {
            tvToolBarTitle.setText("Select Menu");
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }

        private void initRetrofitCallBackForCategory () {
            mResultCallBack = new IResult() {
                @Override
                public void notifySuccess(int requestId, retrofit2.Response<JsonObject> response) {
                    progressDialog.dismiss();
                    JsonObject jsonObject = response.body();
                    String category = jsonObject.toString();
                    switch (requestId) {
                        case PARENT_CATEGORY_WITH_SUBMENU:

                            try {
                                JSONObject object = new JSONObject(category);
                                int status = object.getInt("status");
                                if (status == 1) {

                                    mFragmentTitleList.clear();
                                    JSONArray jsonArray = object.getJSONArray("allmenu");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        JSONArray jsonArray1 = jsonObject2.getJSONArray("mainmenu");

                                        ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
                                        parentCategoryForm.setPc_id(jsonObject2.getInt("Pc_Id"));
                                        int pcId = jsonObject2.getInt("Pc_Id");
                                        parentCategoryForm.setName(jsonObject2.getString("Name").toString());
                                        mFragmentTitleList.add(parentCategoryForm);

                                        fragmentCategoryModelArrayList = new ArrayList<>();
                                        for (int in = 0; in < jsonArray1.length(); in++) {

                                            Log.d("", "jsonArray1.length()" + jsonArray1.length());
                                            JSONObject jsonObject3 = jsonArray1.getJSONObject(in);

                                            CategoryForm categoryForm = new CategoryForm();
                                            categoryForm.setCategory_id(jsonObject3.getInt("Category_Id"));
                                            categoryForm.setCategory_Name(jsonObject3.getString("Category_Name"));
                                            categoryForm.setC_Image_Name(jsonObject3.getString("C_Image_Name"));
                                            categoryForm.setPc_Id(jsonObject2.getInt("Pc_Id"));
                                            // categoryForm.setDefault_image(jsonObject2.getString("Image_Name"));


                                            //menudisplay
                                            JSONArray menuArray = jsonObject3.getJSONArray("submenu");

                                            arrayListMenu = new ArrayList<>();
                                            for (int im = 0; im < menuArray.length(); im++) {
                                                JSONObject menuObject = menuArray.getJSONObject(im);

                                                MenuDisplayForm menuForm = new MenuDisplayForm();
                                                menuForm.setMenu_Id(menuObject.getString("Menu_Id"));
                                                menuForm.setMenu_Name(menuObject.getString("Menu_Name"));
                                                menuForm.setNon_Ac_Rate(menuObject.getInt("Non_Ac_Rate"));
                                                menuForm.setPcId(jsonObject2.getInt("Pc_Id"));
                                                menuForm.setCategoryName(jsonObject3.getString("Category_Name"));
                                                menuForm.setMenu_Image_Name(menuObject.getString("Menu_Img"));
                                                menuForm.setError("");
                                                menuForm.setOffeerPrice("");
                                                menuForm.setSelected(false);
                                                arrayListMenu.add(menuForm);

                                            }

                                            categoryForm.setMenuDisplayFormArrayList(arrayListMenu);
                                            fragmentCategoryModelArrayList.add(categoryForm);

                                        }
                                        arrayList.add(new ArrayList<CategoryForm>(fragmentCategoryModelArrayList));
                                        AddParentCategoryinfoModel addParentCategoryinfoModel = new AddParentCategoryinfoModel();
                                        addParentCategoryinfoModel.setFragment(new FragmentTabParentCategory());
                                        addParentCategoryinfoModel.setWinnerQty(winnerQty);

                                        addParentCategoryinfoModel.setBuyQty(buyQty);
                                        addParentCategoryinfoModel.setGetQty(getQty);
                                        //  addParentCategoryinfoModel.setFragment(new FragmentTabLiquoreCateogory());
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
                }


                @Override
                public void notifyError(int requestId, Throwable error) {
                    progressDialog.dismiss();
                    Toast.makeText(ActivitySelectMenu.this, "error" + error,
                            Toast.LENGTH_SHORT).show();
                }
            };
        }

        private void setupViewPager (ViewPager viewPager){
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
            categoryViewPagerAdapterOffer = new CategoryViewPagerAdapterOffer(getSupportFragmentManager(), mFragmentTitleList, addParentCategoryinfoModels, winnerQty, buyQty, getQty, offerTypeId);
            categoryViewPagerAdapterOffer.notifyDataSetChanged();
            viewPager.setAdapter(categoryViewPagerAdapterOffer);
        }


        private void showProgressDialog () {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Without this user can hide loader by tapping outside screen
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle(getResources().getString(R.string.app_name));
            progressDialog.setMessage("Uploading Menu");
            progressDialog.show();
        }

        private void init () {
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
        public boolean onSupportNavigateUp () {
            finish();
            return true;
        }
    }
