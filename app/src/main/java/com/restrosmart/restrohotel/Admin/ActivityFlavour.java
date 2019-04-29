package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;


import com.restrosmart.restrohotel.Adapter.AdapterDisplayFlavour;
import com.restrosmart.restrohotel.Adapter.RVAdapterUnitView;
import com.restrosmart.restrohotel.Interfaces.ApiService;

import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;

import com.restrosmart.restrohotel.Model.FlavourForm;
import com.restrosmart.restrohotel.Model.FlavourUnitForm;
import com.restrosmart.restrohotel.Model.UnitForm;
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


import static com.restrosmart.restrohotel.ConstantVariables.ADD_FLAVOUR;
import static com.restrosmart.restrohotel.ConstantVariables.FLAVOUR_DELETE;
import static com.restrosmart.restrohotel.ConstantVariables.FLAVOUR_DISPLAY;
import static com.restrosmart.restrohotel.ConstantVariables.FLAVOUR_EDIT;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 28/12/2018.
 */

public class ActivityFlavour extends AppCompatActivity {
    private ArrayList<FlavourForm> arrayListFlavour;
    private ArrayList<FlavourUnitForm> arrayListflavourUnit;
    private ArrayList<FlavourUnitForm> arrayList;
    private TableRow tableRow;

    private ArrayList<EditText> arrayListUnitName;
    private ArrayList<EditText> arrayListUnitPrice;
    private ArrayList<ImageButton> arrayListUnitButton;
    private ApiService apiService;


    private ArrayList<FlavourUnitForm> arrayListUnit;
    private TextView txTitle;
    private int mHotelId, mBranchId;
    private RecyclerView recyclerView;
    LinearLayout parentLinearLayout;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private FrameLayout frameLayoutBtnAdd;
    private View dialoglayout, dialogLayoutField;
    private BottomSheetDialog dialog;
    private AlertDialog alertDialog;
    private EditText tv0, tv1;
    private String image_result;
    EditText unitName, unitPrice;

    private JSONArray jsonArray;
    private CircleImageView circleImageView;
    private Toolbar mTopToolbar;
    private Intent intent;
    private ImageButton imageButton;
    int i;

    int count = 0;
    private RecyclerView rvUnit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour_items);
        init();
        setUpToolBar();

        Sessionmanager sharedPreferanceManage = new Sessionmanager(this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));

        intent = getIntent();
        initRetrofitCall();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
        mRetrofitService.retrofitData(FLAVOUR_DISPLAY, service.flavourDisplay(intent.getIntExtra("menuId", 0),
                mHotelId,
                mBranchId
        ));


        frameLayoutBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callAddMethod();
            }
        });


    }

    private void callAddMethod() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.dialog_add_flavour, null);
        dialog = new BottomSheetDialog(ActivityFlavour.this);
        dialog.setContentView(dialoglayout);


        // dialog = builder.create();
        final EditText etflavourName = (EditText) dialoglayout.findViewById(R.id.etx_flavour_name);
        TextView tvAddUnit = (TextView) dialoglayout.findViewById(R.id.tv_add_unit);
        circleImageView = (CircleImageView) dialoglayout.findViewById(R.id.img_flavour);
        FrameLayout btnCamera = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);
        Button btnSaveFlavour = (Button) dialoglayout.findViewById(R.id.btnSave);
        Button btnCancel = (Button) dialoglayout.findViewById(R.id.btnCancel);
        TextView tvTitle=dialoglayout.findViewById(R.id.tx_add_flavour);
        tvTitle.setVisibility(View.VISIBLE);
        rvUnit = dialoglayout.findViewById(R.id.rv_units);
        dialog.show();

        // callTableLayout();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent flavourIntent = new Intent(ActivityFlavour.this, ActivityFlavourGallery.class);
                // intent.putExtra("Flavour_Name", "1");
                flavourIntent.putExtra("categoryId", intent.getIntExtra("categoryId", 0));
                flavourIntent.putExtra("pcId", intent.getIntExtra("pcId", 0));
                flavourIntent.putExtra("menuId", intent.getIntExtra("menuId", 0));
                startActivityForResult(flavourIntent, IMAGE_RESULT_OK);

            }
        });

        Picasso.with(dialoglayout.getContext())
                .load(apiService.BASE_URL + image_result)
                .resize(500, 500)
                .into(circleImageView);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        arrayListUnitName.clear();
        arrayListUnitPrice.clear();
        arrayListUnit.clear();
        count = 0;
        tvAddUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                dialogLayoutField = li.inflate(R.layout.unit_diaplay_fields, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityFlavour.this);
                builder.setView(dialogLayoutField);
                alertDialog = builder.create();


                Button save = dialogLayoutField.findViewById(R.id.btnSave);
                Button cancel = dialogLayoutField.findViewById(R.id.btnCancel);

                unitName = dialogLayoutField.findViewById(R.id.etv_unit_name);
                unitPrice = dialogLayoutField.findViewById(R.id.etv_unit_price);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FlavourUnitForm unitForm = new FlavourUnitForm();
                        unitForm.setUnitName(unitName.getText().toString());
                        unitForm.setUnitPrice(Integer.parseInt(unitPrice.getText().toString()));
                        arrayListUnit.add(unitForm);
                        alertDialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFlavour.this, LinearLayoutManager.VERTICAL, false);
                rvUnit.setHasFixedSize(true);
                rvUnit.setLayoutManager(linearLayoutManager);
                RVAdapterUnitView adapterUnitView = new RVAdapterUnitView(getApplicationContext(), arrayListUnit, new DeleteListener() {
                    @Override
                    public void getDeleteListenerPosition(int position) {
                        arrayListUnit.remove(position);
                    }
                });
                rvUnit.setAdapter(adapterUnitView);
                adapterUnitView.notifyDataSetChanged();

            }
        });


        btnSaveFlavour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etflavourName.getText().toString().length() == 0 || arrayListUnit.size() == 0) {
                    alertDialog();

                } else {

                    arrayList.clear();
                    for (int i = 0; i < arrayListUnit.size(); i++) {

                        FlavourUnitForm flavourUnitForm = new FlavourUnitForm();
                        flavourUnitForm.setUnitName(arrayListUnit.get(i).getUnitName());
                        flavourUnitForm.setUnitPrice(arrayListUnit.get(i).getUnitPrice());
                        arrayList.add(flavourUnitForm);
                    }

                    jsonArray = new JSONArray();
                    for (int x = 0; x < arrayList.size(); x++) {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("UnitName", arrayList.get(x).getUnitName());
                            object.put("UnitPrice", arrayList.get(x).getUnitPrice());
                            jsonArray.put(object);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if (image_result == null) {
                        image_result = "null";
                    }

                           /* if (image_result != null) {
                                String selImage = image_result;
                                int start = selImage.indexOf("t/");
                                String suffix = selImage.substring(start + 1);
                                int start1 = suffix.indexOf("/");
                                mFinalImageName = suffix.substring(start1 + 1);
                            } else {
                                mFinalImageName = "null";
                            }
*/
                    initRetrofitCall();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
                    mRetrofitService.retrofitData(ADD_FLAVOUR, (service.flavourAdd(etflavourName.getText().toString(),
                            image_result,
                            intent.getIntExtra("menuId", 0),
                            mHotelId,
                            mBranchId,
                            jsonArray.toString())));
                }
            }


        });

    }

    private void setUpToolBar() {
        setSupportActionBar(mTopToolbar);
        txTitle.setText(getIntent().getStringExtra("menuName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }


    private void init() {
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_flavour_item);
        frameLayoutBtnAdd = (FrameLayout) findViewById(R.id.ivAddFlavour);
        arrayListFlavour = new ArrayList<FlavourForm>();
        arrayListflavourUnit = new ArrayList<FlavourUnitForm>();
        arrayList = new ArrayList<FlavourUnitForm>();
        arrayListUnitName = new ArrayList<EditText>();
        arrayListUnitPrice = new ArrayList<EditText>();
        arrayListUnitButton = new ArrayList<>();
        arrayListUnit = new ArrayList<>();
    }

    private void alertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityFlavour.this);
        alert.setTitle("Alert");
        alert.setMessage("Please add Flavour name with add Unit");
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();


    }

    private void initRetrofitCall() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case FLAVOUR_DISPLAY:
                        JsonObject jsonObject = response.body();
                        String value = jsonObject.toString();

                        try {
                            JSONObject object = new JSONObject(value);
                            int status = object.getInt("status");
                            if (status == 1) {

                                JSONArray jsonArray = object.getJSONArray("flavour");
                                arrayListFlavour.clear();
                                arrayListflavourUnit.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    FlavourForm flavourForm = new FlavourForm();
                                    flavourForm.setFlavourName(object1.getString("Flavour_Name"));
                                    flavourForm.setFlavourImage(object1.getString("F_Image_Name"));
                                    flavourForm.setFlavourId(object1.getInt("Flavour_Id"));


                                    JSONArray array = object1.getJSONArray("cost");
                                    arrayListflavourUnit = new ArrayList<>();
                                    for (int j = 0; j < array.length(); j++) {
                                        JSONObject jsonObjectUnit = array.getJSONObject(j);
                                        FlavourUnitForm flavourUnitForm = new FlavourUnitForm();

                                        flavourUnitForm.setUnitName(jsonObjectUnit.getString("Unit_Name").toString());
                                        flavourUnitForm.setUnitPrice(jsonObjectUnit.getInt("Unit_Cost"));
                                        arrayListflavourUnit.add(flavourUnitForm);
                                    }
                                    flavourForm.setArrayListUnits(arrayListflavourUnit);
                                    arrayListFlavour.add(flavourForm);
                                }
                                callAdapter();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case ADD_FLAVOUR:
                        JsonObject jsonObject1 = response.body();
                        Toast.makeText(ActivityFlavour.this, "Flavour Added Successfully..", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        initRetrofitCall();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
                        mRetrofitService.retrofitData(FLAVOUR_DISPLAY, service.flavourDisplay(intent.getIntExtra("menuId", 0),
                                mHotelId,
                                mBranchId
                        ));
                        break;

                    case FLAVOUR_DELETE:
                        JsonObject deleteFlavourObject = response.body();

                        String deleteFlavour = deleteFlavourObject.toString();
                        try {
                            JSONObject object = new JSONObject(deleteFlavour);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityFlavour.this, "Flavour Deleted Successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ActivityFlavour.this, "Try Again..", Toast.LENGTH_LONG).show();

                            }

                            initRetrofitCall();
                            ApiService service1 = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
                            mRetrofitService.retrofitData(FLAVOUR_DISPLAY, service1.flavourDisplay(intent.getIntExtra("menuId", 0),
                                    mHotelId,
                                    mBranchId
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case FLAVOUR_EDIT:
                        JsonObject editFlavourObject = response.body();

                        String editFlavour = editFlavourObject.toString();
                        try {
                            JSONObject object = new JSONObject(editFlavour);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityFlavour.this, "Flavour Updaetd Successfully", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(ActivityFlavour.this, "Try Again..", Toast.LENGTH_LONG).show();

                            }



                            initRetrofitCall();
                            ApiService service1 = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
                            mRetrofitService.retrofitData(FLAVOUR_DISPLAY, service1.flavourDisplay(intent.getIntExtra("menuId", 0),
                                    mHotelId,
                                    mBranchId
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRetrofitCall();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
        mRetrofitService.retrofitData(FLAVOUR_DISPLAY, service.flavourDisplay(intent.getIntExtra("menuId", 0),
                mHotelId,
                mBranchId
        ));

    }


    private void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterDisplayFlavour adapterDisplayFlavour = new AdapterDisplayFlavour(this, arrayListFlavour, arrayListflavourUnit, new EditListener() {
            @Override
            public void getEditListenerPosition(int position) {
             callAddMethod(position);
            }
        }, new DeleteListener() {
            @Override
            public void getDeleteListenerPosition(int position) {
                initRetrofitCall();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
                mRetrofitService.retrofitData(FLAVOUR_DELETE, (service.flavourDelete(
                        mHotelId,
                        mBranchId,
                        arrayListFlavour.get(position).getFlavourId())
                ));

            }
        });
        recyclerView.setAdapter(adapterDisplayFlavour);
    }

    private void callAddMethod(final int position) {

            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            dialoglayout = li.inflate(R.layout.dialog_add_flavour, null);
            dialog = new BottomSheetDialog(ActivityFlavour.this);
            dialog.setContentView(dialoglayout);


            // dialog = builder.create();
            final EditText etflavourName = (EditText) dialoglayout.findViewById(R.id.etx_flavour_name);
            TextView tvAddUnit = (TextView) dialoglayout.findViewById(R.id.tv_add_unit);
            circleImageView = (CircleImageView) dialoglayout.findViewById(R.id.img_flavour);
            FrameLayout btnCamera = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);
            Button btnSaveFlavour = (Button) dialoglayout.findViewById(R.id.btnSave);
           Button btnUpdateFlavour=dialoglayout.findViewById(R.id.btnUpdate);
        btnUpdateFlavour.setVisibility(View.VISIBLE);
            Button btnCancel = (Button) dialoglayout.findViewById(R.id.btnCancel);
            TextView tvTitle=dialoglayout.findViewById(R.id.tx_edit_flavour);
            tvTitle.setVisibility(View.VISIBLE);
            rvUnit = dialoglayout.findViewById(R.id.rv_units);
            btnSaveFlavour.setVisibility(View.GONE);
            dialog.show();

        etflavourName.setText(arrayListFlavour.get(position).getFlavourName());

            // callTableLayout();
            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent flavourIntent = new Intent(ActivityFlavour.this, ActivityFlavourGallery.class);
                    // intent.putExtra("Flavour_Name", "1");
                    flavourIntent.putExtra("categoryId", intent.getIntExtra("categoryId", 0));
                    flavourIntent.putExtra("pcId", intent.getIntExtra("pcId", 0));
                    flavourIntent.putExtra("menuId", intent.getIntExtra("menuId", 0));
                    startActivityForResult(flavourIntent, IMAGE_RESULT_OK);

                }
            });

            if(image_result==null)
            {
                Picasso.with(dialoglayout.getContext())
                        .load(apiService.BASE_URL + arrayListFlavour.get(position).getFlavourImage())
                        .resize(500, 500)
                        .into(circleImageView);
            }
            else {

                Picasso.with(dialoglayout.getContext())
                        .load(apiService.BASE_URL + image_result)
                        .resize(500, 500)
                        .into(circleImageView);
            }
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


           // arrayListUnit.clear();
          //  count = 0;
        arrayListUnit=(arrayListFlavour.get(position).getArrayListUnits());

        tvAddUnit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    dialogLayoutField = li.inflate(R.layout.unit_diaplay_fields, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityFlavour.this);
                    builder.setView(dialogLayoutField);
                    alertDialog = builder.create();


                    Button save = dialogLayoutField.findViewById(R.id.btnSave);
                    Button cancel = dialogLayoutField.findViewById(R.id.btnCancel);

                    unitName = dialogLayoutField.findViewById(R.id.etv_unit_name);
                    unitPrice = dialogLayoutField.findViewById(R.id.etv_unit_price);


                   save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FlavourUnitForm unitForm = new FlavourUnitForm();
                            unitForm.setUnitName(unitName.getText().toString());
                            String price=unitPrice.getText().toString();
                            unitForm.setUnitPrice(Integer.parseInt(price));
                            arrayListUnit.add(unitForm);
                            alertDialog.dismiss();

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFlavour.this, LinearLayoutManager.VERTICAL, false);
                            rvUnit.setHasFixedSize(true);
                            rvUnit.setLayoutManager(linearLayoutManager);
                            RVAdapterUnitView adapterUnitView = new RVAdapterUnitView(getApplicationContext(), arrayListUnit, new DeleteListener() {
                                @Override
                                public void getDeleteListenerPosition(int position) {
                                    arrayListUnit.remove(position);
                                }
                            });
                            rvUnit.setAdapter(adapterUnitView);
                            adapterUnitView.notifyDataSetChanged();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();


                }
            });

        if(arrayListUnit.size()!=0)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFlavour.this, LinearLayoutManager.VERTICAL, false);
            rvUnit.setHasFixedSize(true);
            rvUnit.setLayoutManager(linearLayoutManager);
            RVAdapterUnitView adapterUnitView = new RVAdapterUnitView(getApplicationContext(), arrayListUnit, new DeleteListener() {
                @Override
                public void getDeleteListenerPosition(int position) {
                    arrayListUnit.remove(position);
                }
            });
            rvUnit.setAdapter(adapterUnitView);
            adapterUnitView.notifyDataSetChanged();
        }

        btnUpdateFlavour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etflavourName.getText().toString().length() == 0 || arrayListUnit.size() == 0) {
                        alertDialog();

                    } else {

                        arrayList.clear();
                        for (int i = 0; i < arrayListUnit.size(); i++) {

                            FlavourUnitForm flavourUnitForm = new FlavourUnitForm();
                            flavourUnitForm.setUnitName(arrayListUnit.get(i).getUnitName());
                            flavourUnitForm.setUnitPrice((arrayListUnit.get(i).getUnitPrice()));
                            arrayList.add(flavourUnitForm);
                        }

                        jsonArray = new JSONArray();
                        for (int x = 0; x < arrayList.size(); x++) {
                            try {
                                JSONObject object = new JSONObject();
                                object.put("Unit_Name", arrayList.get(x).getUnitName());
                                object.put("Unit_Cost", arrayList.get(x).getUnitPrice());
                                jsonArray.put(object);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if (image_result == null) {
                            image_result = arrayListFlavour.get(position).getFlavourImage();
                        }


                        initRetrofitCall();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityFlavour.this);
                        mRetrofitService.retrofitData(FLAVOUR_EDIT, (service.flavourEdit(etflavourName.getText().toString(),
                                image_result,
                                arrayListFlavour.get(position).getFlavourId(),
                                mHotelId,
                                mBranchId,
                                jsonArray.toString())));

                    }
                }


            });

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
            image_result = data.getStringExtra("image_name");
            // Log.e("Result", image_result);

            Picasso.with(dialoglayout.getContext())
                    .load(apiService.BASE_URL + image_result)
                    .resize(500, 500)
                    .into(circleImageView);
        }
    }
}
