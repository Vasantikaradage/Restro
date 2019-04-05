package com.restrosmart.restrohotel.Admin;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by SHREE on 28/11/2018.
 */

public  class ActivityAddPayment extends AppCompatActivity {
    /*private EditText  etxPaymentName,etxPersonName,etxAccountNo,etxIfscCode,etxPhoneNo,etxBranchName,etxUpiCode,etxBankName;
    private ImageView imagIcon,ImageQrCode;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  String imageIcon,QRIcon,hotelId,branchId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_add);
        init();
        initRetrofitCall();


    }

    private void initRetrofitCall() {

        initRetrofitCallback();

        ApiService apiService= RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        mRetrofitService = new RetrofitService(mResultCallBack,ActivityAddPayment.this);
        mRetrofitService.retrofitData(ADD_PAYMENT_MODE,(apiService.AddPaymentMode(etxPaymentName.getText().toString(),
                etxPersonName.getText().toString(),
                etxBankName.getText().toString(),
                etxIfscCode.getText().toString(),
                etxBranchName.getText().toString(),
                etxUpiCode.getText().toString(),
                Integer.parseInt(etxPhoneNo.getText().toString()),
                imageIcon,
                QRIcon,
                Integer.parseInt(branchId),
                Integer.parseInt(hotelId)

        )));


    }

    private void initRetrofitCallback() {

        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void init() {
        etxAccountNo=findViewById(R.id.etx_account_no);
        etxBranchName=findViewById(R.id.etx_bank_branck);

        etxPaymentName=findViewById(R.id.etx_payment_type);
        etxPhoneNo=findViewById(R.id.etx_mob_no);
        etxUpiCode=findViewById(R.id.etx_upi_code);
        etxBankName=findViewById(R.id.etx_bank);
        ImageQrCode=findViewById(R.id.img_qr_code);

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityAddPayment.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();

        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);

    }*/
}
