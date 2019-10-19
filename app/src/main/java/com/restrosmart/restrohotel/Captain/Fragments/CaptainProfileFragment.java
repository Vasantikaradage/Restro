package com.restrosmart.restrohotel.Captain.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_CAPTAIN_PROFILE;
import static com.restrosmart.restrohotel.ConstantVariables.TABLE_CONF_STATUS;

public class CaptainProfileFragment extends Fragment {

    private View view;
    private ImageView ivCapProfileImg;
    private TextView tvUsername, tvEmpRole, tvEmpStatus, tvHotelName, tvCapName, tvEmpMobno, tvEmpEmail, tvEmpAadharNo, tvEmpAddress;

    private Sessionmanager mSessionmanager;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_captain_profile, container, false);

        init();

        /*progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();*/

        getCaptainProfile();
        return view;
    }

    private void getCaptainProfile() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(GET_CAPTAIN_PROFILE, (service.getCaptainProfile(1, 78)));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String mResponseString = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(mResponseString);
                    int status = object.getInt("status");
                    String msg = object.getString("message");

                    //progressDialog.dismiss();
                    if (status == 1) {
                        JSONObject profileObject = object.getJSONObject("profiledata");

                        tvUsername.setText(profileObject.getString("User_Name"));
                        tvEmpRole.setText(profileObject.getString("Role"));
                        tvHotelName.setText(profileObject.getString("Hotel_Name"));
                        tvCapName.setText(profileObject.getString("Emp_Name"));
                        tvEmpMobno.setText(profileObject.getString("Emp_Mob"));
                        tvEmpEmail.setText(profileObject.getString("Emp_Email"));
                        tvEmpAadharNo.setText(profileObject.getString("Emp_Adhar_Id"));
                        tvEmpAddress.setText(profileObject.getString("Emp_Address"));

                        if (profileObject.getInt("Active_Status") == 1) {
                            tvEmpStatus.setText("Active");
                        } else {
                            tvEmpStatus.setText("InActive");
                        }

                        if (!profileObject.getString("Emp_Img").equalsIgnoreCase("")) {
                            Picasso.with(getContext())
                                    .load(profileObject.getString("Emp_Img"))
                                    .into(ivCapProfileImg);
                        } else {
                            ivCapProfileImg.setImageResource(R.drawable.ic_action_user);
                        }
                    } else {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.v("Retrofit RequestId", String.valueOf(requestId));
                Log.v("Retrofit Error", String.valueOf(error));
            }
        };
    }

    private void init() {
        ivCapProfileImg = view.findViewById(R.id.ivCapProfileImg);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmpRole = view.findViewById(R.id.tvEmpRole);
        tvEmpStatus = view.findViewById(R.id.tvEmpStatus);
        tvHotelName = view.findViewById(R.id.tvHotelName);
        tvCapName = view.findViewById(R.id.tvCapName);
        tvEmpMobno = view.findViewById(R.id.tvEmpMobno);
        tvEmpEmail = view.findViewById(R.id.tvEmpEmail);
        tvEmpAadharNo = view.findViewById(R.id.tvEmpAadharNo);
        tvEmpAddress = view.findViewById(R.id.tvEmpAddress);
    }
}
