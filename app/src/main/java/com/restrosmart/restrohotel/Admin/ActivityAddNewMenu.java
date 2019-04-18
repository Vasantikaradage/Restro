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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<String> arrayListToppingsEdit;
    private ArrayList<ToppingsForm> markArrayListToppings;
    private CircleImageView mCircularImageView;
    private FrameLayout fmBtnCamera;
    private EditText etxMenuName, etxMenuPrice, etxMenuDiscription;
    private RadioGroup radioGroup;
    private RadioButton radioBtnNull, radioBtnSweet, radioBtnSpicy;
    private Button btnSaveMenu, btnCancelMenu;
    private int menuTeste;
    private String imageName, mFinalImageName;
    private Intent intent;
    private Toolbar mToolbar;
    private TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_menu);

        init();
        setUpToolBar();

        intent = getIntent();

        mSessionManager = new Sessionmanager(this);
        HashMap<String, String> name_info = mSessionManager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        branchId = Integer.parseInt(name_info.get(BRANCH_ID));

        if(intent.getIntExtra("MenuId",0)!=0)
        {
            etxMenuName.setText(intent.getStringExtra("MenuName"));
            etxMenuDiscription.setText(intent.getStringExtra("MenuDiscription"));
            int price= intent.getIntExtra("Price",0);
            etxMenuPrice.setText("\u20B9 "+price);
            Picasso.with(this)
                    .load(intent.getStringExtra("ImageName"))
                    .resize(500, 500)
                    .into(mCircularImageView);
            int status=intent.getIntExtra("MenuTaste",0);
            radioBtnNull.setChecked(false);
            if(status == 1)
            {
                radioBtnSweet.setChecked(true);

            }else  if(status == 2)
            {
                radioBtnSpicy.setChecked(true);
            }
            else
            {
                radioBtnNull.setChecked(true);
            }

         ArrayList<? extends String> arrayListToppingsEditinfo=intent.getParcelableArrayListExtra("ArrayListToppings");
            //JSONArray  jsonArray=new JSONArray(array);

          for(int i=0;i<arrayListToppingsEditinfo.size();i++) {
              arrayListToppingsEdit.add(arrayListToppingsEditinfo.get(i));
          }
            //web service for toppings
            initRetrofitCallBack();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, this);
            mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId,
                    (branchId))));


           /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvTopping.setHasFixedSize(true);
            rvTopping.setLayoutManager(linearLayoutManager);
            RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(this, arrayListToppings, new MarkToppingListerner() {
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
*/


        }


        //web service for toppings
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId,
                (branchId))));

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fmBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallry = new Intent(ActivityAddNewMenu.this, ActivityMenuGallery.class);
                int categoryId = intent.getIntExtra("categoryId", 0);
                int pcId = intent.getIntExtra("pcId", 0);
                intentGallry.putExtra("Category_Id", categoryId);
                intentGallry.putExtra("Pc_Id", intent.getIntExtra("pcId", 0));
                startActivityForResult(intentGallry, IMAGE_RESULT_OK);
            }
        });

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

        btnSaveMenu.setOnClickListener(new View.OnClickListener() {
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
        });

        btnCancelMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //add menu webservice


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
                                        Log.d("", "jsonArray1.length()" + jsonArray1.length());
                                        JSONObject jsonObject3 = jsonArray1.getJSONObject(in);
                                        int pcId = jsonObject2.getInt("Pc_Id");
                                        if (pcId == 1) {
                                            ToppingsForm toppingsForm = new ToppingsForm();
                                            toppingsForm.setToppingsName(jsonObject3.getString("Topping_Name"));
                                            toppingsForm.setToppingsPrice(jsonObject3.getInt("Topping_Price"));
                                            toppingsForm.setToppingId(jsonObject3.getInt("Topping_Id"));
                                            toppingsForm.setPcId(jsonObject2.getInt("Pc_Id"));
                                            int id=jsonObject3.getInt("Topping_Id");
                                            if(arrayListToppingsEdit.size()==0)
                                            {
                                                toppingsForm.setSelected(false);
                                            }

                                           else  if(arrayListToppingsEdit.contains(id))
                                            {
                                                toppingsForm.setSelected(true);
                                            }
                                            else
                                            {
                                                toppingsForm.setSelected(false);
                                            }



                                            arrayListToppings.add(toppingsForm);
                                        }
                                    }
                                }
                            } else {

                            }
                            callAdapter();
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
                                Toast.makeText(ActivityAddNewMenu.this, "Try again later..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

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
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(hotelId,
                (branchId))));
    }

    private void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTopping.setHasFixedSize(true);
        rvTopping.setLayoutManager(linearLayoutManager);
        RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(this, arrayListToppings, new MarkToppingListerner() {
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
    }


    private void setUpToolBar() {
        tvTitle.setText("Add New Menu");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mSessionManager = new Sessionmanager(this);
        mToolbar = findViewById(R.id.toolbar);
        tvTitle = mToolbar.findViewById(R.id.tx_title);
        rvTopping = findViewById(R.id.rv_toppings);
        arrayListToppings = new ArrayList();
        arrayListToppingsEdit=new ArrayList<>();
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
        btnCancelMenu = findViewById(R.id.btnCancel);
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
        initRetrofitCallBack();
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
                branchId,
                intent.getIntExtra("pcId", 0),
                ToppingList
        )));

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
