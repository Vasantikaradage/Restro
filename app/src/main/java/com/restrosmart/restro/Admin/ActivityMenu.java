package com.restrosmart.restro.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Adapter.AdapterDisplayAllMenus;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.Category;
import com.restrosmart.restro.Interfaces.DeleteResult;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.MenuDisplayForm;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.ADD_MENU;
import static com.restrosmart.restro.ConstantVariables.DELETE_CATEGORY;
import static com.restrosmart.restro.ConstantVariables.EDIT_CATEGORY;
import static com.restrosmart.restro.ConstantVariables.EDIT_MENU;
import static com.restrosmart.restro.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restro.ConstantVariables.MENU_LIST;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<MenuDisplayForm> arrayListMenu;
    int Category_Id, btnId;
    FrameLayout btnAddMenu;
    TextView txTitle;
    EditText etxCategoryNme, etxMenuNme, etxMenuPrice, etxMenuDiscrp;
    MenuItem edit, save;
    private String mHotelId, mBranchId, categoryName, categoryImageName, image, image_result, mFinalImageName;
    private int position;

    private  RadioButton btnRadiosweet,btnRadiospicy,btnRadionull;
    private  RadioGroup radioGrpMenu;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private CircleImageView circleImageView1;
    private View dialoglayout;
    private  int menu_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_menu_items);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAddMenu = (FrameLayout) findViewById(R.id.ivAddMenu);

        Intent intent = getIntent();
        Category_Id = intent.getIntExtra("Category_Id", 0);
        categoryName = intent.getStringExtra("Category_Name");
        categoryImageName = intent.getStringExtra("Category_image");

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);

        setSupportActionBar(mTopToolbar);
        txTitle.setText(categoryName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Sessionmanager sharedPreferanceManage = new Sessionmanager(this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = name_info.get(HOTEL_ID);
        mBranchId = name_info.get(BRANCH_ID);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu_item);
        arrayListMenu = new ArrayList<MenuDisplayForm>();
        btnAddMenu.setOnClickListener(this);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddMenu:
                AddMenuInfo();
                break;
        }
    }


    //Add menu  and edit menu Information
    private void AddMenuInfo() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialoglayout = li.inflate(R.layout.activity_add_new_menu, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenu.this);
        builder.setView(dialoglayout);

        final AlertDialog dialog = builder.create();

        FrameLayout btnCamara = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);

        etxMenuNme = dialoglayout.findViewById(R.id.etx_menu_name);
        etxMenuPrice = dialoglayout.findViewById(R.id.etx_menu_price);
        etxMenuDiscrp = dialoglayout.findViewById(R.id.etx_menu_discp);
        Button btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        Button btnSave = dialoglayout.findViewById(R.id.btnSave);
        Button btnUpadte = dialoglayout.findViewById(R.id.btnUpdate);
        TextView txTitle = dialoglayout.findViewById(R.id.tx_add_menu);
        TextView etxTitle = dialoglayout.findViewById(R.id.tx_edit_menu);

        circleImageView1 = (CircleImageView) dialoglayout.findViewById(R.id.img_category);
        radioGrpMenu=(RadioGroup)dialoglayout.findViewById(R.id.radio_grp_menu);
        btnRadiosweet=(RadioButton)dialoglayout.findViewById(R.id.btn_radio_sweet);
        btnRadiospicy=(RadioButton)dialoglayout.findViewById(R.id.btn_radio_spicy);
        btnRadionull=(RadioButton)dialoglayout.findViewById(R.id.btn_radio_null);





       //Add Menu information
        if (position == 0)
        {
            txTitle.setVisibility(View.VISIBLE);
            etxTitle.setVisibility(View.GONE);

            btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityMenu.this, ActivityCategoryGallery.class);
                    startActivityForResult(intent, IMAGE_RESULT_OK);
                }
            });


            radioGrpMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {

                        case R.id.btn_radio_sweet:
                            menu_test=1;
                            break;
                        case R.id.btn_radio_spicy:
                            menu_test=2;
                            break;
                        case R.id.btn_radio_null:
                            menu_test=3;
                            break;
                    }
                }
            });



            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String selImage = image_result;
                    int start = selImage.indexOf("t/");
                    String suffix = selImage.substring(start + 1);
                    int start1 = suffix.indexOf("/");
                    mFinalImageName = suffix.substring(start1 + 1);
                    getRetrofitDataforMenusave();
                }

                private void getRetrofitDataforMenusave() {

                    initRetrofitCallback();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    //  mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                    mRetrofitService.retrofitData(ADD_MENU, (service.getMenuAdd(etxMenuNme.getText().toString(),
                            etxMenuDiscrp.getText().toString(),
                            mFinalImageName,
                            Category_Id,
                            menu_test,
                            etxMenuPrice.getText().toString(),
                            Integer.parseInt(mHotelId),
                            Integer.parseInt(mBranchId))));

                    init();
                    dialog.dismiss();
                }
            });
        }
        //edit menu Information
        else {
            etxTitle.setVisibility(View.VISIBLE);
            txTitle.setVisibility(View.GONE);

            btnUpadte.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);

            String cName = arrayListMenu.get(position - 1).getMenu_Name();
            String cPrice = String.valueOf(arrayListMenu.get(position - 1).getNon_Ac_Rate());
            String cDisp = arrayListMenu.get(position - 1).getMenu_Descrip();
            image = arrayListMenu.get(position - 1).getMenu_Image_Name();
            int mTeste=(arrayListMenu.get(position-1).getMenu_Test());

            etxMenuNme.setText(cName);
            etxMenuPrice.setText(cPrice);
            etxMenuDiscrp.setText(cDisp);

            if(mTeste==1)
            {
                btnRadiosweet.setChecked(true);
                menu_test=1;
            }
            else if(mTeste==2)
            {
                btnRadiospicy.setChecked(true);
                menu_test=2;

            }else
            {
                btnRadionull.setChecked(true);
                menu_test=3;
            }


            Picasso.with(dialoglayout.getContext())
                    .load(image)
                    .resize(500, 500)
                    .into(circleImageView1);

            btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityMenu.this, ActivityCategoryGallery.class);
                    startActivityForResult(intent, IMAGE_RESULT_OK);


                }
            });

            radioGrpMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {

                        case R.id.btn_radio_sweet:
                            menu_test=1;
                            break;
                        case R.id.btn_radio_spicy:
                            menu_test=2;
                            break;
                        case R.id.btn_radio_null:
                            menu_test=3;
                            break;
                    }
                }
            });




        }


        //edit menu btn
        btnUpadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int start, start1 = 0;
                String suffix = null;

                if (image_result == null) {

                    Picasso.with(dialoglayout.getContext())
                            .load(image)
                            .resize(500, 500)
                            .into(circleImageView1);
                    start = image.indexOf("t/");
                    suffix = image.substring(start + 1);
                    start1 = suffix.indexOf("/");
                    mFinalImageName = suffix.substring(start1 + 1);
                } else {
                    String selImage = image_result;
                    int start2 = selImage.indexOf("t/");
                    String suffix1 = selImage.substring(start2 + 1);
                    int start3 = suffix1.indexOf("/");
                    mFinalImageName = suffix1.substring(start3 + 1);
                }


                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                //  mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                mRetrofitService.retrofitData(EDIT_MENU, (service.editMenu(etxMenuNme.getText().toString(),
                        etxMenuDiscrp.getText().toString(),
                        mFinalImageName,
                        menu_test,
                        etxMenuPrice.getText().toString(),
                        (arrayListMenu.get(position - 1).getMenu_Id()),
                        Integer.parseInt(mHotelId),
                        Integer.parseInt(mBranchId))));

                //arrayListMenu.clear();
               // init();
                dialog.dismiss();

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


    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        edit = menu.findItem(R.id.edit);
        save = menu.findItem(R.id.save);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //category delete
        if (id == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Delete Menu")
                    .setMessage("Are you sure you want to delete this Menu ?")
                    .setIcon(R.drawable.ic_action_btn_delete)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            delete();
                        }
                    });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }


        //category edit
        else if (id == R.id.edit) {
            imageAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }



//Edit category information
    private void imageAlertDialog() {
        LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.activity_add_category, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final AlertDialog dialog = builder.create();

        FrameLayout btnCamara = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);

        etxCategoryNme = dialoglayout.findViewById(R.id.etx_category_name);
        Button btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        Button btnSave = dialoglayout.findViewById(R.id.btnSave);
        Button btnUpdate=dialoglayout.findViewById(R.id.btnUpdate);

        btnSave.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.VISIBLE);

        TextView txTitle = dialoglayout.findViewById(R.id.tx_edit_cat);
        txTitle.setVisibility(View.VISIBLE);
        circleImageView1 = (CircleImageView) dialoglayout.findViewById(R.id.img_category);

        etxCategoryNme.setText(categoryName);

        Picasso.with(dialoglayout.getContext())
                .load(categoryImageName)
                .resize(500, 500)
                .into(circleImageView1);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenu.this, ActivityCategoryGallery.class);
                startActivityForResult(intent, IMAGE_RESULT_OK);
            }
        });

         //category update btn
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start, start1;
                String suffix;

                if (image_result == null) {
                    start = categoryImageName.indexOf("t/");
                    suffix = categoryImageName.substring(start + 1);
                    start1 = suffix.indexOf("/");
                } else {
                    String selImage = image_result;
                    start = selImage.indexOf("t/");
                    suffix = selImage.substring(start + 1);
                    start1 = suffix.indexOf("/");

                }
                mFinalImageName = suffix.substring(start1 + 1);
                getRetrofitDataUpdate();


            }

            //category edit web service call
            private void getRetrofitDataUpdate() {
                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                mRetrofitService.retrofitData(EDIT_CATEGORY, service.editCategory(etxCategoryNme.getText().toString(),
                        mFinalImageName,
                        Category_Id,
                        Integer.parseInt(mHotelId),
                        Integer.parseInt(mBranchId)
                ));
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            image_result = data.getStringExtra("image_name");
            Log.e("Result", image_result);

            Picasso.with(dialoglayout.getContext())
                    .load(image_result)
                    .resize(500, 500)
                    .into(circleImageView1);
        }
    }


    //category delete web service call
    private void delete() {
        //catId = arrayList.get(position).getCategory_id();
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
        mRetrofitService.retrofitData(DELETE_CATEGORY, service.deleteCategory(Category_Id,
                Integer.parseInt(mBranchId),
                Integer.parseInt(mHotelId)));
    }

    private void init() {
        retrofitServiceCall();
    }

    private void retrofitServiceCall() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
        mRetrofitService.retrofitData(MENU_LIST, (service.getMenus(Category_Id,
                Integer.parseInt(mBranchId),
                Integer.parseInt(mHotelId))));
    }

    //retrofit call
    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {

            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case MENU_LIST:
                        JsonObject jsonObject = response.body();
                        String mRespnse = jsonObject.toString();
                        try {
                            JSONObject jsonObject1 = new JSONObject(mRespnse);

                            int status = jsonObject1.getInt("status");
                            if (status == 1) {

                                JSONArray jsonArray = jsonObject1.getJSONArray("data");

                                arrayListMenu.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                    MenuDisplayForm menuForm = new MenuDisplayForm();
                                    menuForm.setMenu_Id(jsonObject2.getInt("Menu_Id"));
                                    menuForm.setBranch_Id(jsonObject2.getInt("Branch_Id"));
                                    menuForm.setCategory_Id(jsonObject2.getInt("Category_Id"));
                                    menuForm.setMenu_Name(jsonObject2.getString("Menu_Name"));
                                    menuForm.setMenu_Image_Name(jsonObject2.getString("Menu_Image_Name"));
                                    menuForm.setNon_Ac_Rate(jsonObject2.getInt("Non_Ac_Rate"));
                                    menuForm.setMenu_Test(jsonObject2.getInt("Menu_Test"));
                                    menuForm.setMenu_Descrip(jsonObject2.getString("Menu_Descrip"));
                                    menuForm.setHotel_Id(jsonObject2.getInt("Hotel_Id"));
                                    arrayListMenu.add(menuForm);
                                }
                                callAdapter();

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case DELETE_CATEGORY:

                        JsonObject jsonObject1 = response.body();
                        Toast.makeText(ActivityMenu.this, "Category deleted successfully", Toast.LENGTH_LONG).show();

                        Intent deleteIntent = new Intent();
                        deleteIntent.setAction("cat_Delete");
                        sendBroadcast(deleteIntent);
                        finish();
                        break;

                    case EDIT_CATEGORY:

                        JsonObject jsonObject2 = response.body();
                        String valueinfo = jsonObject2.toString();
                        try {
                            JSONObject object = new JSONObject(valueinfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenu.this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setAction("Update_Category");
                                sendBroadcast(intent);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case ADD_MENU:
                        JsonObject jsonAddMenuObj = response.body();
                        Toast.makeText(ActivityMenu.this, "Menu Added successfully", Toast.LENGTH_LONG).show();
                        break;

                    case EDIT_MENU:

                        JsonObject jsonObjectedit = response.body();
                        String value = jsonObjectedit.toString();
                        try {
                            JSONObject object = new JSONObject(value);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenu.this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setAction("Update_Category");
                                sendBroadcast(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        init();
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }


    public void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterDisplayAllMenus displayAllMenus = new AdapterDisplayAllMenus(this, new DeleteResult() {
            @Override
            public void getDeleteInfoCallBack() {
                retrofitServiceCall();
            }
        }, arrayListMenu, new Category() {
            @Override
            public void categoryListern(int pos) {
                position = pos + 1;
                AddMenuInfo();


            }
        });
        {
            recyclerView.setAdapter(displayAllMenus);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
