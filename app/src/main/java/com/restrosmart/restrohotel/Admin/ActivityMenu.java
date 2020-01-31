package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllMenus;
import com.restrosmart.restrohotel.Adapter.RVToppingsAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.MarkToppingListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
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
import static com.restrosmart.restrohotel.ConstantVariables.MENU_DELETE;
import static com.restrosmart.restrohotel.ConstantVariables.MENU_LIST;
import static com.restrosmart.restrohotel.ConstantVariables.MENU_STATUS;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_TOPPINGS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityMenu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<MenuDisplayForm> arrayListMenu;
    private ArrayList<ToppingsForm> arrayListToppings, arrayListToppingAddMenu;
    private int Category_Id, pcId, mHotelId, mBranchId, editPosition;
    private FrameLayout btnAddMenu;
    private TextView txTitle;
    private String categoryName, image, imageName, mFinalImageName,editmenuId;
    private BottomSheetDialog bottomSheetDialog;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private CircleImageView mCircularViewMenu;
    private View dialoglayoutMenu;
    private ArrayList<ToppingsForm> arrayListToppingsEditinfo;

    private Toolbar mTopToolbar;
    private ProgressBar progressBar;
    private LinearLayout llNoMenuData;
    private EditText etvMenu;
    private ArrayList<ToppingsForm> markArrayListToppings;
    private RecyclerView rvTopping;
    private FrameLayout btnCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_menu_items);
        init();

        Intent intent = getIntent();
        Category_Id = intent.getIntExtra("Category_Id", 0);
        pcId = intent.getIntExtra("Pc_Id", 0);
        categoryName = intent.getStringExtra("Category_Name");

        setupToolBar();

        Sessionmanager sharedPreferanceManage = new Sessionmanager(this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));


        MenuListRetrofitServiceCall();
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pcId == 3) {
                    bottomSheetDialogMenu();
                } else {
                    Intent intent1 = new Intent(ActivityMenu.this, ActivityAddNewMenu.class);
                    intent1.putExtra("pc_Id", pcId);
                    intent1.putExtra("categoryId", Category_Id);
                    startActivity(intent1);
                }
            }


        });
    }

    private void bottomSheetDialogMenu() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayoutMenu = li.inflate(R.layout.bottomsheet_liquor_add_menu, null);
        bottomSheetDialog = new BottomSheetDialog(ActivityMenu.this);
        bottomSheetDialog.setContentView(dialoglayoutMenu);

        btnCamara = (FrameLayout) dialoglayoutMenu.findViewById(R.id.iv_select_image);
        etvMenu = dialoglayoutMenu.findViewById(R.id.etv_menu_name);
        Button btnCancel = dialoglayoutMenu.findViewById(R.id.btnCancel);
        Button btnSave = dialoglayoutMenu.findViewById(R.id.btnSave);
        Button btnUpdate = dialoglayoutMenu.findViewById(R.id.btnUpdate);
        TextView txTitle = dialoglayoutMenu.findViewById(R.id.tv_add_menu);
        TextView txEditTitle = dialoglayoutMenu.findViewById(R.id.tv_edit_menu);
        rvTopping = dialoglayoutMenu.findViewById(R.id.rv_toppings);
        txTitle.setVisibility(View.VISIBLE);
        rvTopping.setVisibility(View.VISIBLE);

        etvMenu.setText("");
        editmenuId = null;
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getBaseContext());
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(mHotelId)));


        mCircularViewMenu = (CircleImageView) dialoglayoutMenu.findViewById(R.id.img_menu);
        Picasso.with(ActivityMenu.this)
                .load(R.drawable.ic_steak)
                .resize(500, 500)
                .into(mCircularViewMenu);


        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenu.this, ActivityMenuGallery.class);
                intent.putExtra("Pc_Id", pcId);
                intent.putExtra("Category_Id", Category_Id);
                startActivityForResult(intent, IMAGE_RESULT_OK);
            }
        });


        btnSave.setVisibility(View.VISIBLE);
        txTitle.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);
        txEditTitle.setVisibility(View.GONE);

        etvMenu.setText("");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageName == null) {
                    mFinalImageName = "null";
                } else {
                    mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                }

                if (etvMenu.getText().toString().length() == 0) {
                    Toast.makeText(ActivityMenu.this, "Please enter menu name", Toast.LENGTH_LONG).show();

                } else {

                    initRetrofitCallback();
                    String ToppingList = getToppingList();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                    mRetrofitService.retrofitData(ADD_MENU, (service.getMenuAdd(etvMenu.getText().toString(),
                            "",
                            mFinalImageName,
                            Category_Id,
                            0,
                            "",
                            mHotelId,
                            pcId,
                            ToppingList
                    )));
                    bottomSheetDialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();

    }

    private String getToppingList() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < markArrayListToppings.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Topping_Id", markArrayListToppings.get(i).getToppingId());
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    private void setupToolBar() {
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        setSupportActionBar(mTopToolbar);
        txTitle.setText(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }


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
            Toast.makeText(this, "imageName" + imageName, Toast.LENGTH_SHORT).show();
            // Log.e("Result", imageName);

            Picasso.with(this)
                    .load(imageName)
                    .resize(500, 500)
                    .into(mCircularViewMenu);
        }
    }


    private void MenuListRetrofitServiceCall() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
        mRetrofitService.retrofitData(MENU_LIST, (service.getMenus(Category_Id, pcId, mHotelId)));
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
                                    menuForm.setMenu_Id(jsonObject2.getString("Menu_Id"));

                                    menuForm.setCategory_Id(jsonObject2.getInt("Category_Id"));
                                    menuForm.setMenu_Name(jsonObject2.getString("Menu_Name"));
                                    menuForm.setMenu_Image_Name(jsonObject2.getString("Menu_Image_Name"));
                                    menuForm.setNon_Ac_Rate(jsonObject2.getInt("Non_Ac_Rate"));
                                    menuForm.setMenu_Test(jsonObject2.getInt("Menu_Test"));
                                    menuForm.setMenu_Descrip(jsonObject2.getString("Menu_Descrip"));
                                    menuForm.setHotel_Id(jsonObject2.getInt("Hotel_Id"));
                                    menuForm.setStatus(jsonObject2.getInt("Menu_Status"));
                                    JSONArray toppingArray = jsonObject2.getJSONArray("Topping");

                                    arrayListToppings = new ArrayList<>();
                                    // arrayListToppingAddMenu.clear();
                                    for (int in = 0; in < toppingArray.length(); in++) {

                                        JSONObject toppingsObject = toppingArray.getJSONObject(in);
                                        ToppingsForm toppingsForm = new ToppingsForm();
                                        toppingsForm.setToppingId(toppingsObject.getInt("Topping_Id"));
                                        toppingsForm.setToppingsName(toppingsObject.getString("Topping_Name"));
                                        toppingsForm.setToppingsPrice(toppingsObject.getInt("Topping_Price"));

                                        arrayListToppings.add(toppingsForm);
                                    }

                                    menuForm.setArrayListtoppings(arrayListToppings);
                                    arrayListMenu.add(menuForm);
                                }


                                callAdapter();

                            } else {
                                llNoMenuData.setVisibility(View.VISIBLE);
                                arrayListMenu.clear();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case PARENT_CATEGORY_WITH_TOPPINGS:
                        JsonObject jsonObject1 = response.body();
                        String mParentSubcategory = jsonObject1.toString();
                        try {
                            JSONObject object = new JSONObject(mParentSubcategory);
                            int status = object.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = object.getJSONArray("Topping_List");
                                arrayListToppingAddMenu.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject2.getJSONArray("Topping");

                                    for (int in = 0; in < jsonArray1.length(); in++) {
                                        Log.v("", "jsonArray1.length()" + jsonArray1.length());
                                        JSONObject jsonObject3 = jsonArray1.getJSONObject(in);
                                        int pc_Id = jsonObject2.getInt("Pc_Id");
                                        if (pc_Id == 3) {
                                            ToppingsForm toppingsForm = new ToppingsForm();
                                            toppingsForm.setToppingsName(jsonObject3.getString("Topping_Name"));
                                            toppingsForm.setToppingsPrice(jsonObject3.getInt("Topping_Price"));
                                            toppingsForm.setToppingId(jsonObject3.getInt("Topping_Id"));
                                            toppingsForm.setPcId(jsonObject2.getInt("Pc_Id"));
                                            toppingsForm.setSelected(false);
                                            arrayListToppingAddMenu.add(toppingsForm);
                                        }
                                    }
                                }
                                callAdapterToppingAdd();
                            } else {

                            }
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
                                Toast.makeText(ActivityMenu.this, "Menu Added Successfully..", Toast.LENGTH_LONG).show();
                               // finish();

                            } else {
                                Toast.makeText(ActivityMenu.this, addMenuObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            MenuListRetrofitServiceCall();
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
                                Toast.makeText(ActivityMenu.this, "Menu Updated Successfully..", Toast.LENGTH_LONG).show();
                              finish();
                                MenuListRetrofitServiceCall();
                            } else {
                                Toast.makeText(ActivityMenu.this, "Try again later..", Toast.LENGTH_LONG).show();
                            }
                            MenuListRetrofitServiceCall();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case MENU_DELETE:
                        JsonObject MenuDeleteObject = response.body();
                        String deleteValue = MenuDeleteObject.toString();
                        try {
                            JSONObject object1 = new JSONObject(deleteValue);
                            int status = object1.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenu.this, "Menu Deleted Successfully", Toast.LENGTH_LONG).show();
                                MenuListRetrofitServiceCall();
                            } else {
                                Toast.makeText(ActivityMenu.this, "Something went wrong...! Try Again..", Toast.LENGTH_LONG).show();
                            }
                            MenuListRetrofitServiceCall();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case MENU_STATUS:

                        JsonObject menuStatusObject = response.body();
                        String value = menuStatusObject.toString();
                        try {
                            JSONObject jsonObjectmenu = new JSONObject(value);
                            int status = jsonObjectmenu.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityMenu.this, "Menu Status Updated Successfully", Toast.LENGTH_LONG).show();
                                MenuListRetrofitServiceCall();
                            } else {
                                Toast.makeText(ActivityMenu.this, "Try Again..", Toast.LENGTH_LONG).show();
                            }
                            MenuListRetrofitServiceCall();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            private void callAdapterToppingAdd() {

                if (editmenuId != null) {
                    arrayListToppingsEditinfo = arrayListMenu.get(editPosition).getArrayListtoppings();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMenu.this);
                    rvTopping.setHasFixedSize(true);
                    rvTopping.setLayoutManager(linearLayoutManager);
                    RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(getBaseContext(), arrayListToppingAddMenu, arrayListToppingsEditinfo, new MarkToppingListerner() {
                        @Override
                        public void markToppings(ArrayList<ToppingsForm> arrayList) {
                            if (arrayList != null && arrayList.size() != 0) {
                                markArrayListToppings.clear();
                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (arrayList.get(i).isSelected()) {
                                        markArrayListToppings.add(arrayList.get(i));
                                    }
                                }
                            }
                        }
                    });
                    rvTopping.setAdapter(rvToppingsAdapter);
                } else {
                    arrayListToppingsEditinfo.clear();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMenu.this);
                    rvTopping.setHasFixedSize(true);
                    rvTopping.setLayoutManager(linearLayoutManager);
                    RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(getBaseContext(), arrayListToppingAddMenu, arrayListToppingsEditinfo, new MarkToppingListerner() {
                        @Override
                        public void markToppings(ArrayList<ToppingsForm> arrayList) {
                            if (arrayList != null && arrayList.size() != 0) {
                                markArrayListToppings.clear();
                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (arrayList.get(i).isSelected()) {
                                        markArrayListToppings.add(arrayList.get(i));
                                    }
                                }
                            }
                        }
                    });
                    rvTopping.setAdapter(rvToppingsAdapter);
                }
            }


            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }

    public void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterDisplayAllMenus displayAllMenus = new AdapterDisplayAllMenus(this, pcId, new StatusListener() {
            @Override
            public void statusListern(int position, int status) {
                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                mRetrofitService.retrofitData(MENU_STATUS, (service.getMenuStatus(arrayListMenu.get(position).getMenu_Id(),
                        mHotelId,
                        status)));

            }
        },
                new DeleteListener() {

                    @Override
                    public void getDeleteListenerPosition(int position) {
                        initRetrofitCallback();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                        mRetrofitService.retrofitData(MENU_DELETE, (service.getMenuDelete(arrayListMenu.get(position).getMenu_Id(),
                                mHotelId,
                                arrayListMenu.get(position).getCategory_Id(), pcId)));

                    }
                }, arrayListMenu, new EditListener() {
            @Override
            public void getEditListenerPosition(int position) {
                editPosition = position;
                editmenuId = null;
                editmenuId = arrayListMenu.get(editPosition).getMenu_Id();
                bottomSheetDialogMenuEdit();

            }
        });
        {
            recyclerView.setAdapter(displayAllMenus);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void bottomSheetDialogMenuEdit() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialoglayoutMenu = li.inflate(R.layout.bottomsheet_liquor_add_menu, null);
        bottomSheetDialog = new BottomSheetDialog(ActivityMenu.this);
        bottomSheetDialog.setContentView(dialoglayoutMenu);

        btnCamara = (FrameLayout) dialoglayoutMenu.findViewById(R.id.iv_select_image);
        etvMenu = dialoglayoutMenu.findViewById(R.id.etv_menu_name);
        final Button btnCancel = dialoglayoutMenu.findViewById(R.id.btnCancel);
        Button btnSave = dialoglayoutMenu.findViewById(R.id.btnSave);
        Button btnUpdate = dialoglayoutMenu.findViewById(R.id.btnUpdate);
        TextView txTitle = dialoglayoutMenu.findViewById(R.id.tv_add_menu);
        TextView txEditTitle = dialoglayoutMenu.findViewById(R.id.tv_edit_menu);
        rvTopping = dialoglayoutMenu.findViewById(R.id.rv_toppings);
        mCircularViewMenu = (CircleImageView) dialoglayoutMenu.findViewById(R.id.img_menu);

        btnSave.setVisibility(View.GONE);
        txTitle.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.VISIBLE);
        txEditTitle.setVisibility(View.VISIBLE);

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getBaseContext());
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_TOPPINGS, (service.toppingDisplay(mHotelId)));

        etvMenu.setText(arrayListMenu.get(editPosition).getMenu_Name());

        Picasso.with(ActivityMenu.this)
                .load(arrayListMenu.get(editPosition).getMenu_Image_Name())
                .resize(500,
                        500)
                .into(mCircularViewMenu);


        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenu.this, ActivityMenuGallery.class);
                intent.putExtra("Pc_Id", pcId);
                intent.putExtra("Category_Id", Category_Id);
                startActivityForResult(intent, IMAGE_RESULT_OK);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image = arrayListMenu.get(editPosition).getMenu_Image_Name().substring(arrayListMenu.get(editPosition).getMenu_Image_Name().lastIndexOf("/") + 1);
                if (imageName == null) {
                    mFinalImageName = "null";

                    Picasso.with(ActivityMenu.this)
                            .load(arrayListMenu.get(editPosition).getMenu_Image_Name())
                            .resize(500, 500)
                            .into(mCircularViewMenu);

                    if (image.equals("def_liq.png")) {
                        mFinalImageName = "null";
                    } else {
                        mFinalImageName = image.substring(image.lastIndexOf("/") + 1);
                    }
                } else {
                    mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                    Picasso.with(ActivityMenu.this)
                            .load(imageName)
                            .resize(500, 500)
                            .into(mCircularViewMenu);

                }

//                    if (etvMenu.getText().toString().length() == 0) {
//                        Toast.makeText(ActivityMenu.this, "Please enter menu name", Toast.LENGTH_LONG).show();
//                    } else {

                initRetrofitCallback();

                String ToppingList = getToppingList();
                ApiService service1 = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityMenu.this);
                mRetrofitService.retrofitData(EDIT_MENU, (service1.editMenu(etvMenu.getText().toString(),
                        "",
                        mFinalImageName,
                        0,
                        0,
                        arrayListMenu.get(editPosition).getMenu_Id(),
                        mHotelId,
                        Category_Id,
                        pcId,
                        ToppingList
                )));
                bottomSheetDialog.dismiss();


                //
                // }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void init() {
        btnAddMenu = (FrameLayout) findViewById(R.id.ivAddMenu);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        llNoMenuData = findViewById(R.id.llNoMenuData);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu_item);

        arrayListMenu = new ArrayList<MenuDisplayForm>();
        arrayListToppings = new ArrayList<>();
        markArrayListToppings = new ArrayList<>();
        arrayListToppingsEditinfo = new ArrayList<>();
        arrayListToppingAddMenu = new ArrayList<>();

    }
}
