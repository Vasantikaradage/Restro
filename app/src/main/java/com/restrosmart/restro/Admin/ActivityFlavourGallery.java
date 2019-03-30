package com.restrosmart.restro.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Adapter.RVVImageAdapter;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.Category;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.AddImage;
import com.restrosmart.restro.R;

import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.IMAGE_MENU_LIST;
import static com.restrosmart.restro.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 04/01/2019.
 */

public class ActivityFlavourGallery extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private ArrayList<AddImage> arrayListImage;
    private Toolbar mTopToolbar;
    private TextView txTitle;
    private String image_name, mImageName, mHotelId, mBranchId;
    private Sessionmanager sessionmanager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_gallery);
        init();
        setUpToolBar();

        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        mHotelId = name_info.get(HOTEL_ID);
        mBranchId = name_info.get(BRANCH_ID);

        Intent intent = getIntent();
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavourGallery.this);
        mRetrofitService.retrofitData(IMAGE_MENU_LIST, service.getFlavourImage(intent.getIntExtra("menuId", 0),
                intent.getIntExtra("categoryId", 0),
                Integer.parseInt(mBranchId),
                Integer.parseInt(mHotelId),
                intent.getIntExtra("pcId", 0)));
    }

    private void setUpToolBar() {
        setSupportActionBar(mTopToolbar);
        txTitle.setText("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_category_gallery);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        arrayListImage = new ArrayList<AddImage>();
        sessionmanager = new Sessionmanager(this);
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


    private void initRetrofitCallBack() {

        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject1 = response.body();
                String imageValue = jsonObject1.toString();
                try {
                    JSONObject object = new JSONObject(imageValue);
                    JSONArray jsonArray = object.getJSONArray("data");
                    arrayListImage.clear();
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
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void getImage(final ArrayList<AddImage> arrayListImage) {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        RVVImageAdapter adapter = new RVVImageAdapter(this, arrayListImage, mImageName, new Category() {

            @Override
            public void categoryListern(int position) {
                Toast.makeText(ActivityFlavourGallery.this, "" + position, Toast.LENGTH_SHORT).show();
                image_name = arrayListImage.get(position).getImage();
                /*  //  mImageName = image_name.substring(image_name.lastIndexOf('/') + 1);
                int start = image_name.indexOf("t/");
                String suffix = image_name.substring(start + 1);
                int start1 = suffix.indexOf("/");
                mImageName = suffix.substring(start1 + 1);*/
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


