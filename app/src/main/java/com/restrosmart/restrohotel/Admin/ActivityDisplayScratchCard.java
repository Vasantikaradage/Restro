package com.restrosmart.restrohotel.Admin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterRVScratchcard;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.Model.ScratchCardForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.ADD_SCRATCHCARD;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_SCRATCHCARD;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_SCRATCHCARD;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityDisplayScratchCard extends AppCompatActivity {

    private RecyclerView rvScratchCard;
    private ArrayList<ScratchCardForm> scratchCardFormArrayList;
    private TextView tvToolBarTitle, tvSelectMenu;
    private Toolbar mToolbar;
    private Spinner spParentcategory;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private int mHotelId, mBranchId;
    private Sessionmanager sharedPreferanceManage;
    private ArrayList<ParentCategoryForm> parentCategoryFormArrayList;
    private FrameLayout flAddScratchCard;

    private View dialoglayout;
    private BottomSheetDialog dialog;
    private AlertDialog alertDialog;
    private TextView tvTitleAdd;
    private EditText etFromDate, etToDate, etDescription;

    private Button btnSave, btnUpdate, btnCancel;
    private int editedItemPosition;

    private String date[] = {"17 Jun", "18 Jun", "19 Jun", "20 Jun"};
    private int count[] = {13, 5, 21, 50};

    private String messgae[] = {"Customer can get a ScratchCard and win 13 food Surprises offer ir valid till Jun 3-5", "Customer can get a ScratchCard and win 5  food Surprises offer ir valid till Jun 15-17",
            "Customer can get a ScratchCard and win 21 food Surprises offer ir valid till Jun 13-25", "Customer can get a ScratchCard and win 50 food Surprises offer ir valid till Jun 1-15"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_scratchcard);

        init();
        setUpToolBar();


        for (int i = 0; i < date.length; i++) {
            ScratchCardForm scratchCardForm = new ScratchCardForm();
            scratchCardForm.setCount(count[i]);
            scratchCardForm.setDate(date[i]);
            scratchCardForm.setMessage(messgae[i]);
            scratchCardFormArrayList.add(scratchCardForm);
        }


        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        flAddScratchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditScratchCard();
            }
        });


        ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
        parentCategoryForm.setPc_id(0);
        parentCategoryForm.setName("Select Category");
        parentCategoryFormArrayList.add(parentCategoryForm);



       /* initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayScratchCard.this);
        mRetrofitService.retrofitData(PARENT_CATEGORY, (service.getParentCategory(mHotelId,
                mBranchId)));*/


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvScratchCard.setHasFixedSize(true);
        rvScratchCard.setLayoutManager(linearLayoutManager);
        AdapterRVScratchcard adapterRVScratchcard = new AdapterRVScratchcard(getApplicationContext(), scratchCardFormArrayList, new PositionListener() {
            @Override
            public void positionListern(int position) {

                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialoglayout = li.inflate(R.layout.dialog_promocode_view, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisplayScratchCard.this);
                builder.setView(dialoglayout);
                alertDialog = builder.create();

                TextView price = dialoglayout.findViewById(R.id.tv_price);

                TextView date = dialoglayout.findViewById(R.id.tv_date);
                TextView promoCodeName = dialoglayout.findViewById(R.id.tv_promocode_name);
                TextView promoCodeDesription = dialoglayout.findViewById(R.id.tv_promo_description);
                //  ImageView btnClose=dialoglayout.findViewById(R.id.ivCloseDialog);


               // String strPrice = scratchCardFormArrayList.get(position).getOfferValue();

              //  price.setText(strPrice);
                date.setText(scratchCardFormArrayList.get(position).getDate());
                promoCodeName.setText(scratchCardFormArrayList.get(position).getDate());
                promoCodeDesription.setText("Customer need to apply FLAT  code to avail 30% off. offer is valid till JUN 15 2019");


                alertDialog.show();




            }
        }, new EditListener() {
            @Override
            public void getEditListenerPosition(int position) {
                editedItemPosition = position;
                AddEditScratchCard();

            }
        }, new DeleteListener() {
            @Override
            public void getDeleteListenerPosition(int position) {
                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                mRetrofitService.retrofitData(DELETE_SCRATCHCARD, service.deletePromoCode("",
                        ""));

            }
        });

        rvScratchCard.setAdapter(adapterRVScratchcard);

       /* tvSelectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ActivityDisplayScratchCard.this,ActivitySelectMenu.class);
                startActivity(intent);

            }
        });
*/

    }

    private void AddEditScratchCard() {

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.bottomsheet_add_scratchcard, null);
        dialog = new BottomSheetDialog(ActivityDisplayScratchCard.this);
        dialog.setContentView(dialoglayout);

        tvTitleAdd = dialoglayout.findViewById(R.id.tv_add_scratchcard);
        tvTitleAdd.setVisibility(View.VISIBLE);

        etDescription = dialoglayout.findViewById(R.id.et_description);
        etFromDate = dialoglayout.findViewById(R.id.et_from_date);
        etToDate = dialoglayout.findViewById(R.id.et_to_date);


        btnSave = dialoglayout.findViewById(R.id.btnSave);
        btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(dialoglayout.getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String year = String.valueOf(selectedyear);
                        String mon = String.valueOf(selectedmonth);
                        String day = String.valueOf(selectedday);
                        etFromDate.setText(day + "/" + mon + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });


        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(dialoglayout.getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String year = String.valueOf(selectedyear);
                        String mon = String.valueOf(selectedmonth);
                        String day = String.valueOf(selectedday);
                        etToDate.setText(day + "/" + mon + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        if (editedItemPosition != 0) {
            tvTitleAdd.setText("Edit ScratchCard");
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            etFromDate.setText("");
            etToDate.setText("");

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                    mRetrofitService.retrofitData(EDIT_SCRATCHCARD, service.editScratchcard("",
                            etDescription.getText().toString(),
                            etFromDate.getText().toString(),
                            etToDate.getText().toString()));
                }
            });
        } else {
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            tvTitleAdd.setText("Add ScratchCard");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                    mRetrofitService.retrofitData(ADD_SCRATCHCARD, service.addScratchcard(
                            etDescription.getText().toString(),
                            etFromDate.getText().toString(),
                            etToDate.getText().toString()));


                }
            });
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();

                switch (requestId) {
                    case PARENT_CATEGORY:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {

                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
                                    parentCategoryForm.setPc_id(jsonObject1.getInt("Pc_Id"));
                                    parentCategoryForm.setName(jsonObject1.getString("Name"));
                                    parentCategoryFormArrayList.add(parentCategoryForm);
                                }
                                ArrayAdapter<ParentCategoryForm> arrayAdapter = new ArrayAdapter<ParentCategoryForm>(ActivityDisplayScratchCard.this, R.layout.item_category, parentCategoryFormArrayList);
                                // spParentcategory.setAdapter(arrayAdapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case ADD_SCRATCHCARD:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case EDIT_SCRATCHCARD:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case DELETE_SCRATCHCARD:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }


            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }

    private void setUpToolBar() {
        tvToolBarTitle.setText("Scratch Card");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        rvScratchCard = findViewById(R.id.rv_scratch_card);
        scratchCardFormArrayList = new ArrayList<>();
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        //  spParentcategory=findViewById(R.id.sp_parent_category);
        sharedPreferanceManage = new Sessionmanager(ActivityDisplayScratchCard.this);
        parentCategoryFormArrayList = new ArrayList<>();

        flAddScratchCard = findViewById(R.id.ivAddScratchcard);

        //  tvSelectMenu=findViewById(R.id.tv_select_menu);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
