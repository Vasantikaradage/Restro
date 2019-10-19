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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVVImageAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
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
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 21/11/2018.
 */

public class ActivityCategoryGallery extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private ArrayList<ImageForm> arrayListImage;
    private String image_name, mImageName;
    private Sessionmanager sessionmanager;
    private String hotelId, branchId;
    private int mPcId;
    private Toolbar mTopToolbar;
    private TextView txTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_gallery);
        init();
        setUpToolBar();

        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);
        Intent intent = getIntent();
        mPcId = intent.getIntExtra("Pc_Id", 0);

        initRetrofitCallBackForCategory();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityCategoryGallery.this);
        mRetrofitService.retrofitData(IMAGE_LIST, service.getCategoryImage(mPcId));

    }

    private void setUpToolBar() {
        setSupportActionBar(mTopToolbar);
        txTitle.setText("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_category_gallery);
        arrayListImage = new ArrayList<ImageForm>();
        sessionmanager = new Sessionmanager(this);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
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


    private void initRetrofitCallBackForCategory() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject1 = response.body();
                String imageValue = jsonObject1.toString();
                try {
                    JSONObject object = new JSONObject(imageValue);
                    int status = object.getInt("status");
                    if (status == 1) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        arrayListImage.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            ImageForm imageForm = new ImageForm();
                            imageForm.setImage(object1.getString("image").toString());
                            arrayListImage.add(imageForm);
                        }
                        getImage(arrayListImage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "RetrofitError" + error);

            }
        };
    }

    private void getImage(final ArrayList<ImageForm> arrayListImage) {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        RVVImageAdapter adapter = new RVVImageAdapter(this, arrayListImage, mImageName, new PositionListener() {

            @Override
            public void positionListern(int position) {
                Toast.makeText(ActivityCategoryGallery.this, "" + position, Toast.LENGTH_SHORT).show();
                image_name = arrayListImage.get(position).getImage();
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
