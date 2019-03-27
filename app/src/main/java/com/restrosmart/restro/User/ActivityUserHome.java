package com.restrosmart.restro.User;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Adapter.OfferBannerImageAdapter;
import com.restrosmart.restro.Adapter.RVCategoryAdapter;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.OfferBannerModel;
import com.restrosmart.restro.Model.UserCategory;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.DASHBOARD_CATEGORY;

/**
 * Created by SHREE on 09/10/2018.
 */

public class ActivityUserHome extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView tvTableNo;
    private RecyclerView rvCategory;
    private RVCategoryAdapter mRvCategoryAdapter;
    private ArrayList<UserCategory> userCategoryArrayList;

    //private LinearLayout llVeg, llNonVeg, llLiquors;
    private ViewPager viewPagerOffers;
    private CirclePageIndicator indicator;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private String[] IMAGES = {"https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg", "https://carwad.net/sites/default/files/junk-food-images-132191-1421046.jpg",
            "https://d2isrguaavypvq.cloudfront.net/images/restaurants/rst_896656.jpg"};
    private String[] NAMES = {"Crispy Burger", "Veg Cheese Burger", "Pizza"};
    private String[] PRICE = {"100", "150", "250"};
    private ArrayList<OfferBannerModel> offerBannerModelArrayList = new ArrayList<>();

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    private int hotelId, branchId, tableNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        init();
        mToolbar.setTitle("Hotel Name");
        setSupportActionBar(mToolbar);

        setOfferBanner();

       /* Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String barcodeData = getIntent().getStringExtra("data");
            show(barcodeData);
        }*/

        getCategories(1, 1);
    }

    private void show(String barcode) {
        try {
            JSONObject jsonObject = new JSONObject(barcode);

            hotelId = jsonObject.getInt("Hotel_Id");
            branchId = jsonObject.getInt("Branch_Id");
            tableNo = jsonObject.getInt("Table_No");

           /* Toast.makeText(this, String.valueOf(hotelId), Toast.LENGTH_LONG).show();
            Toast.makeText(this, String.valueOf(branchId), Toast.LENGTH_LONG).show();
            Toast.makeText(this, String.valueOf(tableNo), Toast.LENGTH_LONG).show();*/

            getCategories(hotelId, branchId);
            tvTableNo.setText(String.valueOf(tableNo));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.llVeg:
                Intent intentVeg = new Intent(ActivityUserHome.this, ActivityHotelMenu.class);
                intentVeg.putExtra("menuId", 1);
                startActivity(intentVeg);
                break;
            case R.id.llNonVeg:
                Intent intentNonVeg = new Intent(ActivityUserHome.this, ActivityHotelMenu.class);
                intentNonVeg.putExtra("menuId", 2);
                startActivity(intentNonVeg);
                break;
            case R.id.llLiquors:
                Intent intentLiquors = new Intent(ActivityUserHome.this, ActivityHotelMenu.class);
                intentLiquors.putExtra("menuId", 3);
                startActivity(intentLiquors);
                break;*/
        }
    }

    public void getCategories(int hotelId, int branchId) {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityUserHome.this);
        mRetrofitService.retrofitData(DASHBOARD_CATEGORY, (service.getCategory(hotelId, branchId)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String value = jsonObject.toString();

                try {
                    JSONObject jsonObject1 = new JSONObject(value);

                    int status = jsonObject1.getInt("status");
                    if (status == 1) {
                        userCategoryArrayList.clear();

                        JSONArray jsonArray = jsonObject1.getJSONArray("category");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            UserCategory userCategory = new UserCategory();
                            userCategory.setCategoryId(jsonObject2.getInt("Pc_Id"));
                            userCategory.setCategoryImage(jsonObject2.getString("Image_Name"));
                            userCategory.setCategoryName(jsonObject2.getString("Name"));
                            userCategoryArrayList.add(userCategory);
                        }

                        if (userCategoryArrayList.size() == 1) {
                            GridLayoutManager layoutManager = new GridLayoutManager(ActivityUserHome.this, 1, LinearLayoutManager.VERTICAL, false);
                            rvCategory.setLayoutManager(layoutManager);
                        } else if (userCategoryArrayList.size() == 2 || userCategoryArrayList.size() > 3) {
                            GridLayoutManager layoutManager = new GridLayoutManager(ActivityUserHome.this, 2, LinearLayoutManager.VERTICAL, false);
                            rvCategory.setLayoutManager(layoutManager);
                        } else {
                            GridLayoutManager layoutManager = new GridLayoutManager(ActivityUserHome.this, 3, LinearLayoutManager.VERTICAL, false);
                            rvCategory.setLayoutManager(layoutManager);
                        }

                        rvCategory.setNestedScrollingEnabled(true);
                        mRvCategoryAdapter = new RVCategoryAdapter(ActivityUserHome.this, userCategoryArrayList);
                        rvCategory.setAdapter(mRvCategoryAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.v("Retrofit RequestId", String.valueOf(requestId));
                Log.v("Retrofit Error", String.valueOf(error));
                //Toast.makeText(ActivityUserHome.this, "fail", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void setOfferBanner() {

        for (int i = 0; i < IMAGES.length; i++) {
            OfferBannerModel offerBannerModel = new OfferBannerModel();
            offerBannerModel.setImageUrl(IMAGES[i]);
            offerBannerModel.setOfferName(NAMES[i]);
            offerBannerModel.setOfferPrice(PRICE[i]);
            offerBannerModelArrayList.add(offerBannerModel);
        }

        viewPagerOffers.setAdapter(new OfferBannerImageAdapter(ActivityUserHome.this, offerBannerModelArrayList));
        indicator.setViewPager(viewPagerOffers);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(4 * density);

        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPagerOffers.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void init() {

        userCategoryArrayList = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbarTransparent);
        viewPagerOffers = findViewById(R.id.viewPagerOffers);
        indicator = findViewById(R.id.indicator);

        tvTableNo = findViewById(R.id.tvTableNo);
        rvCategory = findViewById(R.id.rvCategory);
        /*llVeg = findViewById(R.id.llVeg);
        llNonVeg = findViewById(R.id.llNonVeg);
        llLiquors = findViewById(R.id.llLiquors);*/
    }

    /*private void setSliderViews() {

        for (int i = 0; i < no_of_imgs; i++) {
            SliderView sliderView = new SliderView(ActivityUserHome.this);
            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg");
                    break;
                case 1:
                    sliderView.setImageUrl("https://carwad.net/sites/default/files/junk-food-images-132191-1421046.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("https://image.shutterstock.com/image-photo/italian-pizza-olives-paprika-tomato-260nw-1043887507.jpg");
                    break;
                case 3:
                    sliderView.setImageUrl("https://www.worldofbuzz.com/wp-content/uploads/2018/02/pork-belly-featured-image.jpg");
                    break;
                case 4:
                    sliderView.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlktJiuiGJ0sjpqQBVuDF7uzR_vmGZa0vQft51Umc71uYsinLGmQ");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            // sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(ActivityUserHome.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }*/
}
