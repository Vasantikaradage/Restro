package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.AddImage;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitService;

import java.util.ArrayList;


/**
 * Created by SHREE on 29/10/2018.
 */

public class AddMenu extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;

    IResult mResultCallBack;

    RetrofitService mRetrofitService;


    ArrayList<AddImage> arrayListImage = new ArrayList<AddImage>();


    private String mImageName, mMenuName, mMenuDisp, mUpdatedImage, imageNamePos;
    private String mMenuAcRate, mMenuNonAcRate;
    private int mCategoryId, mMenuId;

    private String mBranchId, mHotelId;
    private EditText etxMenuName, etxMenuDispcription, etxAcRate, etxNonAcRate;

    private Button btnMenuSave, btnUpdateMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_menu);

  /*      Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView   txTitle=(TextView)mTopToolbar.findViewById(R.id.tx_title);


        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.choose_image_recycler);
        btnMenuSave=(Button)findViewById(R.id.btn_menu_save) ;
        btnUpdateMenu=(Button)findViewById(R.id.btn_update_menu) ;
        etxMenuName=(EditText) findViewById(R.id.etx_menu_name);
        etxAcRate=(EditText) findViewById(R.id.etx_ac_rate);
        etxNonAcRate=(EditText) findViewById(R.id.etx_non_ac_rate);
        etxMenuDispcription=(EditText) findViewById(R.id.etx_menu_discription);

        Intent intent=getIntent();
        mCategoryId=intent.getIntExtra("Category_Id",0);

        Sessionmanager sharedPreferanceManage = new Sessionmanager(this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();

        mHotelId=name_info.get(HOTEL_ID);
        mBranchId=name_info.get(BRANCH_ID);

        mMenuId=intent.getIntExtra("menuId",0);

        if(mMenuId!=0)
        {

            txTitle.setText("Edit Menu");
            String mMenuName=intent.getStringExtra("menuName");
            int acRate=intent.getIntExtra("menuAcRate",0);
            int nonAcRate=intent.getIntExtra("menuNonAcRate",0);
            String menuDisp=intent.getStringExtra("menuDiscription");

            mUpdatedImage=intent.getStringExtra("menuImage");



            etxMenuName.setText(mMenuName);
            etxAcRate.setText(String.valueOf(acRate));
            etxNonAcRate.setText(String.valueOf(nonAcRate));
            etxMenuDispcription.setText(menuDisp);

            btnUpdateMenu.setVisibility(View.VISIBLE);
            btnMenuSave.setVisibility(View.GONE);




            btnUpdateMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getUpdateMenu();

                }

                private void getUpdateMenu() {

                    getRetrofitData();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, getApplicationContext());
                    mRetrofitService.retrofitData(EDIT_MENU, service.editMenu(etxMenuName.getText().toString(),
                            etxMenuDispcription.getText().toString(),
                            mUpdatedImage,
                            etxAcRate.getText().toString(),
                            etxNonAcRate.getText().toString(),
                            mMenuId,
                            Integer.parseInt(mHotelId),
                            Integer.parseInt(mBranchId)));

                }
            });
        }
        else
        {
            btnUpdateMenu.setVisibility(View.GONE);
            btnMenuSave.setVisibility(View.VISIBLE);
            txTitle.setText("Add Menu");

        }



        init();

        btnMenuSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getRetrofitDataforsave();
            }

            private void getRetrofitDataforsave() {
                getRetrofitData();

                mMenuName = etxMenuName.getText().toString();
                mMenuDisp = etxMenuDispcription.getText().toString();
                mMenuAcRate = (etxAcRate.getText().toString());
                mMenuNonAcRate = (etxNonAcRate.getText().toString());

                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
              //  mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService = new RetrofitService(mResultCallBack,AddMenu.this);
                mRetrofitService.retrofitData(ADD_MENU,(service.getMenuAdd(mMenuName,
                        mMenuDisp,
                        mImageName,
                        mCategoryId,
                       mMenuAcRate,
                       mMenuNonAcRate,
                        Integer.parseInt(mHotelId),
                        Integer.parseInt(mBranchId))));
            }

        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        init();
    }

    private void init() {
        getRetrofitData();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, AddMenu.this);
        mRetrofitService.retrofitData(IMAGE_LIST, service.getCategoryImage(Integer.parseInt(mHotelId)));

    }

    private void getRetrofitData() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId)
                {
                    case IMAGE_LIST:

                        JsonObject jsonObject1 = response.body();
                        String imageValue=jsonObject1.toString();
                        try {
                            JSONObject object=new JSONObject(imageValue);
                            JSONArray jsonArray=object.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object1=jsonArray.getJSONObject(i);
                                AddImage addImage=new AddImage();
                                addImage.setImage(object1.getString("image").toString());
                                arrayListImage.add(addImage);
                            }
                            getImage(arrayListImage);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case ADD_MENU:
                        JsonObject jsonObject2 = response.body();
                        String value=jsonObject2.toString();
                        Toast.makeText(AddMenu.this, "Menu added successfully", Toast.LENGTH_LONG).show();
                        finish();
                        break;

                    case EDIT_MENU:
                        JsonObject jsonObject3 = response.body();
                        Toast.makeText(AddMenu.this, "Menu Updated successfully", Toast.LENGTH_LONG).show();
                       finish();
                        break;

                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Toast.makeText(AddMenu.this, "Error for adding information", Toast.LENGTH_LONG).show();


            }
        };

    }

    private void getImage(final ArrayList<AddImage> arrayListImage) {
        recyclerView=(RecyclerView)findViewById(R.id.choose_image_recycler);


        recyclerView.setHasFixedSize(true);
        int no_of_col=3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),5);
        RecyclerViewImageAdapter adapter = new RecyclerViewImageAdapter(this, arrayListImage,mUpdatedImage, new PositionListener() {
            @Override
            public void positionListern(int position) {
                // Log.d("","CategoryPosition"+position);
                Toast.makeText(AddMenu.this, ""+position, Toast.LENGTH_SHORT).show();


                imageNamePos=arrayListImage.get(position).getImage();

             //  mImageName = imageNamePos.substring(imageNamePos.lastIndexOf('/') + 1);
                int start = imageNamePos.indexOf("t/");
                String suffix = imageNamePos.substring(start + 1);
                int start1=suffix.indexOf("/");
                mImageName=suffix.substring(start1+1);

            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        //  image_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }*/

    }

    @Override
    public void onClick(View v) {

    }
}
