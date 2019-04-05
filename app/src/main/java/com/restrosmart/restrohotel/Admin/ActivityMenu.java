package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllMenus;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.Category;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_MENU;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_MENU;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.ConstantVariables.MENU_DELETE;
import static com.restrosmart.restrohotel.ConstantVariables.MENU_LIST;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ArrayList<MenuDisplayForm> arrayListMenu;
    private int Category_Id, pcId,mHotelId,mBranchId,position,menu_test;
    private FrameLayout btnAddMenu;
    private TextView txTitle;
    private EditText etxMenuNme, etxMenuPrice, etxMenuDiscrp;

    private String categoryName, image, imageName, mFinalImageName;

    private AlertDialog dialog;

    private  RadioButton btnRadiosweet,btnRadiospicy,btnRadionull;
    private  RadioGroup radioGrpMenu;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private CircleImageView mCircleImageView;
    private View dialoglayout;

    private Toolbar mTopToolbar;
    private ProgressBar progressBar;
    private LinearLayout llNoMenuData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_menu_items);
        init();

        Intent intent = getIntent();
        Category_Id = intent.getIntExtra("Category_Id", 0);
        pcId = intent.getIntExtra("Pc_Id", 0);
        categoryName = intent.getStringExtra("Category_Name");
       // categoryImageName = intent.getStringExtra("Category_image");

        setupToolBar();

        Sessionmanager sharedPreferanceManage = new Sessionmanager(this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));

        MenuListRetrofitServiceCall();
    }

    private void setupToolBar() {
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        setSupportActionBar(mTopToolbar);
        txTitle.setText(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
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

        Intent intent=new Intent(ActivityMenu.this,ActivityAddMenu.class);
        startActivity(intent);

      /*  LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialoglayout = li.inflate(R.layout.activity_add_new_menu, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenu.this);
        builder.setView(dialoglayout);

        dialog = builder.create();

        FrameLayout btnCamara = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);

        etxMenuNme = dialoglayout.findViewById(R.id.etx_menu_name);
        etxMenuPrice = dialoglayout.findViewById(R.id.etx_menu_price);
        etxMenuDiscrp = dialoglayout.findViewById(R.id.etx_menu_discp);
        Button btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        Button btnSave = dialoglayout.findViewById(R.id.btnSave);
        Button btnUpadte = dialoglayout.findViewById(R.id.btnUpdate);
        TextView txTitle = dialoglayout.findViewById(R.id.tx_add_menu);
        TextView etxTitle = dialoglayout.findViewById(R.id.tx_edit_menu);

        mCircleImageView = (CircleImageView) dialoglayout.findViewById(R.id.img_category);
        radioGrpMenu = (RadioGroup) dialoglayout.findViewById(R.id.radio_grp_menu);
        btnRadiosweet = (RadioButton) dialoglayout.findViewById(R.id.btn_radio_sweet);
        btnRadiospicy = (RadioButton) dialoglayout.findViewById(R.id.btn_radio_spicy);
        btnRadionull = (RadioButton) dialoglayout.findViewById(R.id.btn_radio_null);

        //Add Menu information
        if (position == 0) {
            txTitle.setVisibility(View.VISIBLE);
            etxTitle.setVisibility(View.GONE);

            btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityMenu.this, ActivityMenuGallery.class);
                    intent.putExtra("Category_Id", Category_Id);
                    intent.putExtra("Pc_Id", pcId);
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
                    if (imageName != null) {
                        String selImage = imageName;
                        int start = selImage.indexOf("t/");
                        String suffix = selImage.substring(start + 1);
                        int start1 = suffix.indexOf("/");
                        mFinalImageName = suffix.substring(start1 + 1);
                    } else {
                        mFinalImageName = "null";
                    }
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
                           mHotelId,
                           mBranchId,
                            pcId)));

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
            int mTeste = (arrayListMenu.get(position - 1).getMenu_Test());

            etxMenuNme.setText(cName);
            etxMenuPrice.setText(cPrice);
            etxMenuDiscrp.setText(cDisp);

            if (mTeste == 1) {
                btnRadiosweet.setChecked(true);
                menu_test = 1;
            } else if (mTeste == 2) {
                btnRadiospicy.setChecked(true);
                menu_test = 2;

            } else {
                btnRadionull.setChecked(true);
                menu_test = 3;
            }


            Picasso.with(dialoglayout.getContext())
                    .load(image)
                    .resize(500, 500)
                    .into(mCircleImageView);

            btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityMenu.this, ActivityMenuGallery.class);
                    intent.putExtra("Category_Id", Category_Id);
                    intent.putExtra("Pc_Id", pcId);
                    startActivityForResult(intent, IMAGE_RESULT_OK);
                }
            });

            radioGrpMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.btn_radio_sweet:
                            menu_test = 1;
                            break;
                        case R.id.btn_radio_spicy:
                            menu_test = 2;
                            break;
                        case R.id.btn_radio_null:
                            menu_test = 3;
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
                if (imageName == null) {
                    Picasso.with(dialoglayout.getContext())
                            .load(image)
                            .resize(500, 500)
                            .into(mCircleImageView);
                    start = image.indexOf("t/");
                    suffix = image.substring(start + 1);
                    start1 = suffix.indexOf("/");
                    mFinalImageName = suffix.substring(start1 + 1);
                } else {
                    String selImage = imageName;
                    int start2 = selImage.indexOf("t/");
                    String suffix1 = selImage.substring(start2 + 1);
                    int start3 = suffix1.indexOf("/");
                    mFinalImageName = suffix1.substring(start3 + 1);
                }


                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                mRetrofitService.retrofitData(EDIT_MENU, (service.editMenu(etxMenuNme.getText().toString(),
                        etxMenuDiscrp.getText().toString(),
                        mFinalImageName,
                        menu_test,
                        etxMenuPrice.getText().toString(),
                        arrayListMenu.get(position - 1).getMenu_Id(),
                        mHotelId,
                        mBranchId)));
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
*/    }


    @Override
    protected void onResume() {
        super.onResume();
        MenuListRetrofitServiceCall();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            imageName = data.getStringExtra("image_name");
            // Log.e("Result", imageName);

            Picasso.with(dialoglayout.getContext())
                    .load(imageName)
                    .resize(500, 500)
                    .into(mCircleImageView);
        }
    }




    private void init() {
        btnAddMenu = (FrameLayout) findViewById(R.id.ivAddMenu);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        llNoMenuData=findViewById(R.id.llNoMenuData);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu_item);
        arrayListMenu = new ArrayList<MenuDisplayForm>();
        btnAddMenu.setOnClickListener(this);
    }

    private void MenuListRetrofitServiceCall() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
        mRetrofitService.retrofitData(MENU_LIST, (service.getMenus(Category_Id,
                mBranchId,
               mHotelId)));
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
                                llNoMenuData.setVisibility(View.GONE);

                                JSONArray jsonArray = jsonObject1.getJSONArray("submenu");

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
                                llNoMenuData.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;


                    case ADD_MENU:
                        JsonObject jsonAddMenuObj = response.body();
                        Toast.makeText(ActivityMenu.this, "Menu Added successfully", Toast.LENGTH_LONG).show();
                        MenuListRetrofitServiceCall();
                        break;

                    case EDIT_MENU:

                        JsonObject jsonObjectedit = response.body();
                        String value = jsonObjectedit.toString();
                        try {
                            JSONObject object = new JSONObject(value);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenu.this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                                MenuListRetrofitServiceCall();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        init();
                        break;
                    case MENU_DELETE:

                        JsonObject MenuDeleteObject = response.body();
                        String deleteValue = MenuDeleteObject.toString();
                        try {
                            JSONObject object = new JSONObject(deleteValue);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenu.this, "Menu Deleted Successfully", Toast.LENGTH_LONG).show();
                                MenuListRetrofitServiceCall();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }


                    @Override
                    public void notifyError ( int requestId, Throwable error){
                        Log.d("", "requestId" + requestId);
                        Log.d("", "RetrofitError" + error);
                    }
                };
            }






    public void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterDisplayAllMenus displayAllMenus = new AdapterDisplayAllMenus(this, pcId, new DeleteListener() {

            @Override
            public void getDeleteListenerPosition(int position) {
                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack,ActivityMenu.this);
                mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(arrayListMenu.get(position).getMenu_Id(),
                        mHotelId,
                      mBranchId)
                ));

            }
        }, arrayListMenu, new Category() {
            @Override
            public void categoryListern(int pos) {
                position = pos + 1;
                AddMenuInfo();


            }
        }, new EditListener() {
            @Override
            public void getEditListenerPosition(int position) {

            }
        });
        {
            recyclerView.setAdapter(displayAllMenus);
        }
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
