package com.restrosmart.restrohotel.Admin;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;


import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;

import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Response;


import static com.restrosmart.restrohotel.ConstantVariables.BRANCH_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 03/01/2019.
 */

public class FragmentHotelDetails extends Fragment {

    private TextView tvhotelname,tvBranchName,tvAddress,tvEmail,tvMob,tvGstnNo,tvTblNo,tvHotelTiming;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    private  String mHotelId,mBranchId;

    private ImageButton imgBtnEdit;

    private  String mHotelName,mBranchName,mAddress,mEmail,mMob,mGstnNo,mTblNo,tmHotelTiming;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_info, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        Sessionmanager sharedPreferanceManage = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = name_info.get(HOTEL_ID);
        mBranchId = name_info.get(BRANCH_ID);

        imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent=new Intent(getActivity(),ActivityAddNewBranch.class);
                editIntent.putExtra("hotelName",mHotelName);
                editIntent.putExtra("branchName",mBranchName);
                editIntent.putExtra("branchAddress",mAddress);
                editIntent.putExtra("branchEmail",mEmail);
                editIntent.putExtra("branchMob",mMob);
                editIntent.putExtra("gstnNo",mGstnNo);
                editIntent.putExtra("tableNo",mTblNo);
                editIntent.putExtra("timing",tmHotelTiming);
                startActivity(editIntent);
            }
        });

        //branch profile display
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(BRANCH_DETAILS, (service.getBranchDetail(Integer.parseInt(mHotelId),
                (Integer.parseInt(mBranchId)))));
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        tvhotelname=(TextView) getView().findViewById(R.id.tv_hotel_name);
        tvBranchName=(TextView)getView().findViewById(R.id.tv_branch_name);
        tvAddress=(TextView)getView().findViewById(R.id.tv_address);
        tvEmail=(TextView)getView().findViewById(R.id.tv_email);
        tvMob=(TextView)getView().findViewById(R.id.tv_mob);
        tvGstnNo=(TextView)getView().findViewById(R.id.tv_gstn_no);
        tvTblNo=(TextView)getView().findViewById(R.id.tv_no_of_table);
        tvHotelTiming=(TextView)getView().findViewById(R.id.tv_hotel_time);
        imgBtnEdit=(ImageButton)getView().findViewById(R.id.img_btn_edit);
    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId){
                    case  BRANCH_DETAILS:

                        JsonObject jsonObject=response.body();
                        String hotelValue=jsonObject.toString() ;

                        try {
                            JSONObject object=new JSONObject(hotelValue);
                            int status=object.getInt("status");

                            if(status==1)
                            {
                                JSONObject object1=object.getJSONObject("Bdetail");
                                mHotelName=object1.getString("Hotel_Name").toString();
                                mBranchName=object1.getString("Branch_Name").toString();
                                mAddress=object1.getString("Branch_Address").toString();
                                mEmail=object1.getString("Branch_Email").toString();
                                mMob=object1.getString("Branch_Mob").toString();
                                mGstnNo=object1.getString("Branch_Gstnno").toString();
                                mTblNo=object1.getString("Branch_Table_Count").toString();
                                tmHotelTiming=object1.getString("Branch_Timing").toString();

                                tvhotelname.setText(mHotelName);
                                tvBranchName.setText(mBranchName);
                                tvAddress.setText(mAddress);
                                tvEmail.setText(mEmail);
                                tvMob.setText(mMob);
                                tvGstnNo.setText(mGstnNo);
                                tvTblNo.setText(mTblNo);
                                tvHotelTiming.setText(tmHotelTiming);
                            }
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
}
