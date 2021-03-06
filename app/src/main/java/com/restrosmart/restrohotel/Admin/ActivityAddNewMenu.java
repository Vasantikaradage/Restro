package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVToppingsAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.MarkToppingListerner;
import com.restrosmart.restrohotel.Model.ToppingsForm;
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
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_TOPPINGS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityAddNewMenu extends AppCompatActivity {
    private RecyclerView rvTopping;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionManager;
    private int hotelId, branchId;
    private ArrayList<ToppingsForm> arrayListToppings;

    private ArrayList<ToppingsForm> markArrayListToppings;
    private CircleImageView mCircularImageView;
    private FrameLayout fmBtnCamera;
    private EditText etxMenuName, etxMenuPrice, etxMenuDiscription;
    private RadioGroup radioGroup;
    private RadioButton radioBtnNull, radioBtnSweet, radioBtnSpicy;
    private Button btnSaveMenu, btnCancelMenu, btnUpdateMenu;
    private int menuTeste;
    private String imageName, mFinalImageName;
    private Intent intent;
    private Toolbar mToolbar;
    private TextView tvTitle,tvTeste;
    private ArrayList<ToppingsForm> arrayListToppingsEditinfo;
    private int pcId;
    private ApiService apiService;
    private SpinKitView skLoading;
    private  String menuId;
    private LinearLayout llteste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_menu);

        init();
        setUpToolBar();
        skLoading.setVisibility(View.GONE);

        intent = getIntent();
        pcId = intent.getIntExtra("pc_Id", 0);

        mSessionManager = new Sessionmanager(this);
        HashMap<String, String> name_info = mSessionManager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        btnSaveMenu.setVisibility(View.VISIBLE);
        btnUpdateMenu.setVisibility(View.GONE);

        menuId= (intent.getStringExtra("MenuId"));
        if(menuId!=null){

            pcId = intent.getIntExtra("pc_Id", 0);
            tvTitle.setText("Edit Menu");
            btnSaveMenu.setVisibility(View.GONE);
            btnUpdateMenu.setVisibility(View.VISIBLE);
            etxMenuName.setText(intent.getStringExtra("MenuName"));
            etxMenuDiscription.setText(intent.getStringExtra("MenuDiscription"));
            String price = String.valueOf(intent.getIntExtra("Price", 0));
            etxMenuPrice.setText(price);

            fmBtnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentGallry = new Intent(ActivityAddNewMenu.this, ActivityMenuGallery.class);
                    intentGallry.putExtra("Category_Id", intent.getIntExtra("categoryId", 0));
                    intentGallry.putExtra("Pc_Id", pcId);
                    startActivityForResult(intentGallry, IMAGE_RESULT_OK);
                }
            });

            Picasso.with(this)
                    .load(intent.getStringExtra("ImageName"))
                    .resize(500, 500)
                    .into(mCircularImageView);

            if(pcId!=4) {
                llteste.setVisibility(View.VISIBLE);
                tvTeste.setVisibility(View.VISIBLE);

                int status = intent.getIntExtra("MenuTaste", 0);
                radioBtnNull.setChecked(false);
                if (status == 1) {
                    radioBtnSweet.setChecked(true);
                    menuTeste = 1;
                } else if (status == 2) {
                    radioBtnSpicy.setChecked(true);
                    menuTeste = 2;
                } else {
                    radioBtnNull.setChecked(true);
                    menuTeste = 3;
                }

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {

                            case R.id.btn_radio_sweet:
                                menuTeste = 1;
                                break;
                            case R.id.btn_radio_spicy:
                                menuTeste = 2;
                                break;
                            case R.id.btn_radio_null:
                                menuTeste = 3;
                                break;
                        }
                    }
                });
            }
            else {
                llteste.setVisibility(View.GONE);
                tvTeste.setVisibility(View.GONE);

            }

            arrayListToppingsEditinfo = intent.getParcelableArrayListExtra("ArrayListToppings");
            btnUpdateMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String ToppingList = getToppingList();
                    initRetrofitCallBack();

                    if (imageName == null) {

                        String image = intent.getStringExtra("ImageName");

                        String strImage=image.substring(image.lastIndexOf("/") + 1);
                        if ((strImage.equals("def_menu.png")) || (strImage.equals("def_liq.png"))) {
                            mFinalImageName = "null";
                        } else {
                            mFinalImageName = image.substring(image.lastIndexOf("/") + 1);
                        }
                    } else {
                        mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                        Picasso.with(ActivityAddNewMenu.this)
                                .load(imageName)
                                .resize(500, 500)
                                .into(mCircularImageView);
                    }
                    int price = Integer.parseInt(etxMenuPrice.getText().toString());
                    skLoading.setVisibility(View.VISIBLE);
                    ApiService service1 = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddNewMenu.this);
                    mRetrofitService.retrofitData(EDIT_MENU, (service1.editMenu(etxMenuName.getText().toString(),
                            etxMenuDiscription.getText().toString(),
                            mFinalImageName,
                            menuTeste,
                            price,
                            intent.getStringExtra("MenuId"),
                            hotelId,
                            intent.getIntExtra("categoryId", 0),
                            intent.getIntExtra("pc_Id", 0),
                            ToppingList
                    )));


                }
            });

            btnCancelMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
           btnUpdateMenu.setVisibility(View.GONE);
           btnSaveMenu.setVisibility(View.VISIBLE);
           tvTitle.setText("Add Menu");

            initRetrofitCallBack();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, this);
            mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId)));



            if(pcId!=4) {
                llteste.setVisibility(View.VISIBLE);
                tvTeste.setVisibility(View.VISIBLE);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {

                            case R.id.btn_radio_sweet:
                                menuTeste = 1;
                                break;
                            case R.id.btn_radio_spicy:
                                menuTeste = 2;
                                break;
                            case R.id.btn_radio_null:
                                menuTeste = 3;
                                break;
                        }
                    }
                });
            }else
            {
                llteste.setVisibility(View.GONE);
                tvTeste.setVisibility(View.GONE);
            }

            fmBtnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentGallry = new Intent(ActivityAddNewMenu.this, ActivityMenuGallery.class);
                    intentGallry.putExtra("Category_Id", intent.getIntExtra("categoryId", 0));
                    intentGallry.putExtra("Pc_Id", pcId);
                    startActivityForResult(intentGallry, IMAGE_RESULT_OK);
                }
            });

            btnSaveMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imageName == null) {
                        mFinalImageName = "null";
                    } else {
                        mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                    }
                    getRetrofitDataforMenusave();

                }
            });
            btnCancelMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case PARENT_CATEGORY_WITH_TOPPINGS:
                        JsonObject jsonObject = response.body();
                        String mParentSubcategory = jsonObject.toString();
                        try {
                            JSONObject object = new JSONObject(mParentSubcategory);
                            int status = object.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = object.getJSONArray("Topping_List");
                                arrayListToppings.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject2.getJSONArray("Topping");

                                    for (int in = 0; in < jsonArray1.length(); in++) {
                                        Log.v("", "jsonArray1.length()" + jsonArray1.length());
                                        JSONObject jsonObject3 = jsonArray1.getJSONObject(in);
                                        int pcId = jsonObject2.getInt("Pc_Id");
                                        if (pcId == intent.getIntExtra("pc_Id", 0)) {
                                            ToppingsForm toppingsForm = new ToppingsForm();
                                            toppingsForm.setToppingsName(jsonObject3.getString("Topping_Name"));
                                            toppingsForm.setToppingsPrice(jsonObject3.getInt("Topping_Price"));
                                            toppingsForm.setToppingId(jsonObject3.getInt("Topping_Id"));
                                            toppingsForm.setPcId(jsonObject2.getInt("Pc_Id"));
                                            toppingsForm.setSelected(false);
                                            arrayListToppings.add(toppingsForm);
                                        }
                                    }
                                }
                            } else {
                                skLoading.setVisibility(View.GONE);

                            }
                            callAdapter();
                            skLoading.setVisibility(View.GONE);
                            // progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case ADD_MENU:
                        JsonObject object = response.body();
                        String addMenu = object.toString();

                        try {
                            JSONObject addMenuObject = new JSONObject(addMenu);
                            int status = addMenuObject.getInt("status");

                            if (status == 1) {
                                Toast.makeText(ActivityAddNewMenu.this, "Menu Added Successfully..", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityAddNewMenu.this,  addMenuObject.getString("message"), Toast.LENGTH_LONG).show();

                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EDIT_MENU:
                        JsonObject menuObject = response.body();
                        String editMenu = menuObject.toString();

                        try {
                            JSONObject editMenuObject = new JSONObject(editMenu);
                            int status = editMenuObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityAddNewMenu.this, "Menu Updated Successfully..", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityAddNewMenu.this,  editMenuObject.getString("message"), Toast.LENGTH_LONG).show();

                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","requestId"+requestId);
                Log.d("","RetrofitError"+error);
                finish();
                skLoading.setVisibility(View.GONE);

            }
        };
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //web service for toppings
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId)));
    }

    private void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTopping.setHasFixedSize(true);
        rvTopping.setLayoutManager(linearLayoutManager);

        if (arrayListToppings.size() != 0) {
            RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(this, arrayListToppings, arrayListToppingsEditinfo, new MarkToppingListerner() {
                @Override
                public void markToppings(ArrayList<ToppingsForm> arrayList) {
                    if (arrayList != null && arrayList.size() != 0) {
                        markArrayListToppings.clear();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).isSelected()) {
                                //if (arrayList.get(i).isSelected()) {
                                // }
                                //  Toast.makeText(ActivityAddNewMenu.this, "id"+arrayList.get(3).getToppingId(), Toast.LENGTH_SHORT).show();
                                markArrayListToppings.add(arrayList.get(i));

                            }
                        }
                    }
                }
            });
            rvTopping.setAdapter(rvToppingsAdapter);
        } else {
            rvTopping.setVisibility(View.GONE);
        }

    }

    private void setUpToolBar() {
        tvTitle.setText("Add New Menu");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mSessionManager = new Sessionmanager(this);
        skLoading=findViewById(R.id.skLoading);
        mToolbar = findViewById(R.id.toolbar);
        tvTitle = mToolbar.findViewById(R.id.tx_title);
        rvTopping = findViewById(R.id.rv_toppings);
        arrayListToppings = new ArrayList();
        arrayListToppingsEditinfo = new ArrayList<>();
        markArrayListToppings = new ArrayList<>();
        mCircularImageView = findViewById(R.id.img_menu);
        fmBtnCamera = findViewById(R.id.iv_select_image);
        etxMenuName = findViewById(R.id.etx_menu_name);
        etxMenuPrice = findViewById(R.id.etx_menu_price);
        etxMenuDiscription = findViewById(R.id.etx_menu_discp);
        radioGroup = findViewById(R.id.radio_grp_menu);
        radioBtnNull = findViewById(R.id.btn_radio_null);
        radioBtnSweet = findViewById(R.id.btn_radio_sweet);
        radioBtnSpicy = findViewById(R.id.btn_radio_spicy);
        btnSaveMenu = findViewById(R.id.btnSave);
        btnUpdateMenu = findViewById(R.id.btnUpdate);
        btnCancelMenu = findViewById(R.id.btnCancel);
        llteste=findViewById(R.id.teste_linear_layout);
        tvTeste=findViewById(R.id.tv_teste);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            imageName = data.getStringExtra("image_name");
            // Log.e("Result", imageName);

            Picasso.with(this)
                    .load(imageName)
                    .resize(500, 500)
                    .into(mCircularImageView);
        }
    }


    private void getRetrofitDataforMenusave() {

        if((etxMenuName.getText().toString().length()==0) ||(etxMenuPrice.getText().toString().length()==0)){
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();

        }else {
            initRetrofitCallBack();
            skLoading.setVisibility(View.VISIBLE);
            String ToppingList = getToppingList();

            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, this);
            mRetrofitService.retrofitData(ADD_MENU, (service.getMenuAdd(etxMenuName.getText().toString(),
                    etxMenuDiscription.getText().toString(),
                    mFinalImageName,
                    intent.getIntExtra("categoryId", 0),
                    menuTeste,
                    etxMenuPrice.getText().toString(),
                    hotelId,
                    intent.getIntExtra("pc_Id", 0),
                    ToppingList
            )));
        }
    }

    private String getToppingList() {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < markArrayListToppings.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Topping_Id", markArrayListToppings.get(i).getToppingId());
                //jsonObject.put("Topping_Name", arrayListToppings.get(i).getStudentName());
                Toast.makeText(ActivityAddNewMenu.this, "id" + markArrayListToppings.get(i).getToppingId(), Toast.LENGTH_LONG).show();
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return jsonArray.toString();
    }
}
