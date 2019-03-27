package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.AddImage;
import com.restrosmart.restro.Model.ParentCategoryForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitService;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class AddCategory extends AppCompatActivity {
    ArrayList<AddImage> arrayList_image = new ArrayList<AddImage>();
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<ParentCategoryForm> arrayListParentCat = new ArrayList<ParentCategoryForm>();
    int mPcId;
    ArrayList<AddImage> arrayListImage = new ArrayList<AddImage>();
    private RecyclerView image_recyclerview;
    private EditText etxCategoryNme;
    private RetrofitService retrofitService;
    private IResult iResult;
    private LinearLayout saveCategory, btn_Update,cancelCategory;
    private int pcId, position;

    private String mImageName, mHotelId, mBranchId;
    private String image_name;
    private SearchableSpinner sp_parent_category;
    private int edt_categoryId;
    private String edt_categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

       // Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(mTopToolbar);

      //  TextView txTitle=(TextView)mTopToolbar.findViewById(R.id.tx_title);
       // txTitle.setText("Add Category");

     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.iv_select_image);
        fab.setBackgroundColor(R.drawable.login_bg);
*/
       // saveCategory=(LinearLayout)findViewById(R.id.add_category);
       // cancelCategory=(LinearLayout)findViewById(R.id.cancel_category);
     /* //  cancelCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
*/
        /*save_category=(Button)findViewById(R.id.btn_add_category);
        save_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getRetrofitDataSave();
                finish();
            }
        });
*/

     /*   Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);*/


        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  Intent i=getIntent();
        branch_id=i.getStringExtra("branch_id");
        hotel_id=i.getStringExtra("hotel_id");*/



       /* Sessionmanager sessionmanager = new Sessionmanager(AddCategory.this);
        position = ((sessionmanager.getTabposition()));
        pcId = position;
        Log.d("", "positionId" + pcId);
        Bundle bundle = getIntent().getExtras();
        init();

        if (bundle != null) {
            edt_categoryId = bundle.getInt("edt_category_item");
            edt_categoryName = bundle.getString("edt_cat_name");

            etxCategoryNme.setText(edt_categoryName);
            btn_Update.setVisibility(View.VISIBLE);
            save_category.setVisibility(View.GONE);
            sp_parent_category.setVisibility(View.GONE);

            btn_Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid()) {
                        getRetrofitDataUpDate();
                    }
                }

                private void getRetrofitDataUpDate() {
                    initRetrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    retrofitService = new RetrofitService(iResult, getApplicationContext());
                    retrofitService.retrofitData(EDIT_CATEGORY, service.editCategory(etxCategoryNme.getText().toString(), mImageName, edt_categoryId));

                }
            });


        } else {

            btn_Update.setVisibility(View.GONE);
            save_category.setVisibility(View.VISIBLE);
        }




    }

    private void getRetrofitDataSave() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        retrofitService = new RetrofitService(iResult, getApplicationContext());
        retrofitService.retrofitData(SAVE_CATEGORY, service.addCategory(etxCategoryNme.getText().toString(), mImageName, 1 *//*Integer.parseInt(hotel_id)*//*, 1 *//*Integer.parseInt(branch_id)*//*, mPcId));

    }

    private void getRetrofitData() {

        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        retrofitService = new RetrofitService(iResult, getApplicationContext());
       // retrofitService.retrofitData(PARENT_CATEGORY, service.getParentCategory("1","1"));
        retrofitService.retrofitData(IMAGE_LIST, service.getCategoryImage());



    }

    private void initRetrofitCallBack() {
        iResult = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId)
                {
                   *//* case PARENT_CATEGORY:
                        JsonObject jsonObject = response.body();
                        String value=jsonObject.toString();
                        try {
                            JSONObject object=new JSONObject(value);
                            int status=object.getInt("status");
                            if(status==1)
                            {
                                JSONArray jsonArray=object.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject object1=jsonArray.getJSONObject(i);
                                    ParentCategoryForm parentCategoryForm=new ParentCategoryForm();
                                    parentCategoryForm.setPc_id(object1.getInt("Pc_Id"));
                                    parentCategoryForm.setName(object1.getString("Name"));
                                    arrayListParentCat.add(parentCategoryForm);
                                }
                                spinnerInfo();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;*//*

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

                    case EDIT_CATEGORY:

                        JsonObject jsonObject2 = response.body();
                        String valueinfo=jsonObject2.toString();
                        try {
                            JSONObject object=new JSONObject(valueinfo);
                            int status=object.getInt("status");
                            if(status==1)
                            {
                                Toast.makeText(AddCategory.this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setAction("Update_Category");
                                sendBroadcast(intent);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case SAVE_CATEGORY:
                        JsonObject jsonObject3 = response.body();
                        Toast.makeText(AddCategory.this, "Category added successfully", Toast.LENGTH_LONG).show();
                        break;


                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        } ;


    }

    private void spinnerInfo() {
        ArrayAdapter<ParentCategoryForm> dataAdapter = new ArrayAdapter<ParentCategoryForm>(AddCategory.this,
                android.R.layout.simple_spinner_item, arrayListParentCat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_parent_category.setAdapter(dataAdapter);
        sp_parent_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPcId=arrayListParentCat.get(position).getPc_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private boolean isValid() {

        if (etxCategoryNme.getText().toString().equals("")) {
            etxCategoryNme.setError("Please Enter Category name");

            //Toast.makeText(this, "Please ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mImageName == null || mImageName.equals("")) {
            Toast.makeText(this, "Please Select Category Image", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }



    private void init() {
        btn_Update = (Button) findViewById(R.id.btn_update_cat);
        etxCategoryNme = (EditText) findViewById(R.id.etx_category_name);
        save_category = (Button) findViewById(R.id.btn_save_cateroty);
      //  sp_parent_category=(SearchableSpinner)findViewById(R.id.sp_select_category);
        getRetrofitData();

    }

    private void getImage(final ArrayList<AddImage> images) {
        image_recyclerview = (RecyclerView) findViewById(R.id.choose_image_recycler);

        image_recyclerview.setHasFixedSize(true);
        int no_of_col = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 6);
        RecyclerViewImageAdapter adapter = new RecyclerViewImageAdapter(this, images,mImageName ,new Category() {
            @Override
            public void categoryListern(int position) {
                Toast.makeText(AddCategory.this, "" + position, Toast.LENGTH_SHORT).show();
                image_name = images.get(position).getImage();
              //  mImageName = image_name.substring(image_name.lastIndexOf('/') + 1);
                int start = image_name.indexOf("t/");
                String suffix = image_name.substring(start + 1);
                int start1=suffix.indexOf("/");
                mImageName=suffix.substring(start1+1);


            }
        });
        image_recyclerview.setLayoutManager(gridLayoutManager);
        image_recyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }*/
    }
}
