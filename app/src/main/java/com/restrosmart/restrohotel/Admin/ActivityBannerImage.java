package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVVImageAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.ImageForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_LIST;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public  class ActivityBannerImage extends AppCompatActivity {
    private Toolbar mToolBar;
    private TextView tvToolbarTitle;
    private RecyclerView rvBannerImage;
    private  Sessionmanager mSessionManager;
    private  ArrayList<ImageForm> arrayListImage;
    private  String image_name,mImageName;
    private  int hotelId;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private LinearLayout lllNoData;
    private SpinKitView skLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_image);

         init();
         setUpToolBar();

        HashMap<String, String> hotelInfo = mSessionManager.getHotelDetails();
        hotelId = Integer.parseInt(hotelInfo.get(HOTEL_ID));


        initRetrofitCallBackForBanner();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityBannerImage.this);
        mRetrofitService.retrofitData(IMAGE_LIST, service.getBannerImage(hotelId));
    }

    private void initRetrofitCallBackForBanner() {

        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                String imageValue = jsonObject.toString();
                try {
                    JSONObject object = new JSONObject(imageValue);
                    int status = object.getInt("status");
                    if (status == 1) {
                        lllNoData.setVisibility(View.GONE);
                        JSONArray jsonArray = object.getJSONArray("dailyimg");
                        arrayListImage.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            ImageForm imageForm = new ImageForm();
                            imageForm.setImage(object1.getString("image").toString());
                            arrayListImage.add(imageForm);
                        }
                        rvBannerImage.setHasFixedSize(true);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ActivityBannerImage.this, 4);
                        RVVImageAdapter adapter = new RVVImageAdapter(ActivityBannerImage.this, arrayListImage, mImageName, new PositionListener() {

                            @Override
                            public void positionListern(int position) {
                                Toast.makeText(ActivityBannerImage.this, "" + position, Toast.LENGTH_SHORT).show();
                                image_name = arrayListImage.get(position).getImage();
                            }
                        });
                        rvBannerImage.setLayoutManager(gridLayoutManager);
                        rvBannerImage.setAdapter(adapter);
                        skLoading.setVisibility(View.GONE);
                    }
                    else
                    {
                        lllNoData.setVisibility(View.VISIBLE);
                        skLoading.setVisibility(View.GONE);

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","RequestId"+requestId);
                Log.d("","RetrofitError"+error);
                skLoading.setVisibility(View.GONE);

            }
        };
    }

    private void setUpToolBar() {
        setSupportActionBar(mToolBar);
        tvToolbarTitle.setText("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        rvBannerImage=findViewById(R.id.rv_banner_gallery);
        arrayListImage = new ArrayList<>();
        mSessionManager = new Sessionmanager(this);
        mToolBar =  findViewById(R.id.toolbar);
        tvToolbarTitle = mToolBar.findViewById(R.id.tx_title);

        lllNoData=findViewById(R.id.llNoImageData);
        skLoading=findViewById(R.id.skLoading);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_save, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.image_save) {
            Intent intent = new Intent();
            intent.putExtra("image_name", image_name);
            setResult(IMAGE_RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
