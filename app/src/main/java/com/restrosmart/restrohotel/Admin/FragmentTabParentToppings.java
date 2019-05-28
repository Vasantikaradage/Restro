package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllToppings;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.ConstantVariables.TOPPING_DELETE;
import static com.restrosmart.restrohotel.ConstantVariables.TOPPING_EDIT;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentTabParentToppings extends Fragment {

    private AdapterDisplayAllToppings adapterDisplayAllToppings;
    private RecyclerView recyclerView;
    private View view;
    private  Sessionmanager mSessionmanager;
    private  int mHotelId,mBranchId;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
     //private ArrayList<ToppingsForm>  toppingsForms;
     private  View dialoglayout;
     private  BottomSheetDialog dialog;
     private TextView tvToppingTitle;
    private CircleImageView mImageView;
     private EditText etvToppingName,etvToppingPrice;
     private  ApiService apiService;
     private    ArrayList<ToppingsForm> toppingsForms;
    private String  imageName;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_items, null);

        init();
        Bundle bundle = getArguments();
        toppingsForms = bundle.getParcelableArrayList("toppingObject");

        HashMap<String, String> name_info = mSessionmanager.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        adapterDisplayAllToppings = new AdapterDisplayAllToppings(getActivity(), toppingsForms, new EditListener() {
            @Override
            public void getEditListenerPosition(final int position) {

                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                dialoglayout = li.inflate(R.layout.dialog_add_toppings, null);
                // final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(dialoglayout);
                // mBottomSheetDialog.show();
                FrameLayout btnCamara = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);
                etvToppingName=dialoglayout.findViewById(R.id.etv_topping_name);
                etvToppingPrice=dialoglayout.findViewById(R.id.etv_topping_price);

               // etxCategoryNme = dialoglayout.findViewById(R.id.etx_category_name);
                Button btnCancel = dialoglayout.findViewById(R.id.btnCancel);
                Button btnSave = dialoglayout.findViewById(R.id.btnSave);
                Button btnUpdate=dialoglayout.findViewById(R.id.btnUpdate);
                mImageView=dialoglayout.findViewById(R.id.img_toppings);
                tvToppingTitle=dialoglayout.findViewById(R.id.tv_edit_toppingTitle);
                tvToppingTitle.setVisibility(View.VISIBLE);
                etvToppingName.setText(toppingsForms.get(position).getToppingsName());
                String price= String.valueOf(toppingsForms.get(position).getToppingsPrice());
                etvToppingPrice.setText(price);

                Picasso.with(dialoglayout.getContext())
                        .load(apiService.BASE_URL+toppingsForms.get(position).getImage())
                        .resize(500, 500)
                        .into(mImageView);

                btnCamara.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Intent intent = new Intent(getActivity(), ActivityImageTopping.class);
                                                     intent.putExtra("pcId", toppingsForms.get(position).getPcId());
                                                     startActivityForResult(intent, IMAGE_RESULT_OK);
                                                 }
                                             });

                dialog.show();
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name=imageName;

                        if(imageName==null)
                        {
                            imageName=toppingsForms.get(position).getImage();

                        }
                        initRetrofitCallBack();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());

                        mRetrofitService.retrofitData(TOPPING_EDIT, service.toppingEdit(etvToppingName.getText().toString(),
                               imageName,
                                Integer.parseInt(etvToppingPrice.getText().toString()),
                                mHotelId,
                                mBranchId,
                                toppingsForms.get(position).getPcId(),
                                toppingsForms.get(position).getToppingId()));
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        }, new DeleteListener() {
            @Override
            public void getDeleteListenerPosition(int position) {

                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());

                mRetrofitService.retrofitData(TOPPING_DELETE, service.toppingDelete(toppingsForms.get(position).getToppingId(),
                        mHotelId,
                        mBranchId,
                        toppingsForms.get(position).getPcId()));


            }
        });
        recyclerView.setAdapter(adapterDisplayAllToppings);
        adapterDisplayAllToppings.notifyDataSetChanged();

        return view;

    }

    private void initRetrofitCallBack() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId)
                {
                    case TOPPING_DELETE:
                        JsonObject object=response.body();
                        String deleteValue=object.toString() ;

                        try {
                            JSONObject jsonObject=new JSONObject(deleteValue);
                            int status=jsonObject.getInt("status");
                            if(status==1){
                                Toast.makeText(getActivity(),"Topping Deleted Successfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.setAction("Refresh_ToppingList");
                                getActivity().sendBroadcast(intent);
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Something Went Wrong..!",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case TOPPING_EDIT:
                        JsonObject objectEdit=response.body();
                        String editValue=objectEdit.toString() ;

                        try {
                            JSONObject jsonObject=new JSONObject(editValue);
                            int status=jsonObject.getInt("status");
                            if(status==1){
                                Toast.makeText(getActivity(),"Topping Edited Successfully",Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent();
                                intent1.setAction("Refresh_ToppingList");
                                getActivity().sendBroadcast(intent1);
                                dialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Something Went Wrong..!",Toast.LENGTH_LONG).show();

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


    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_item);
        mSessionmanager = new Sessionmanager(getActivity());
    }

    public static Fragment newInstance(ArrayList<ToppingsForm> toppingsForms, int position) {
        FragmentTabParentToppings fragment = new FragmentTabParentToppings();
        Bundle args = new Bundle();
        args.putParcelableArrayList("toppingObject", toppingsForms);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            imageName = data.getStringExtra("image_name");
            // Log.e("Result", imageName);

            Picasso.with(dialoglayout.getContext())
                    .load(apiService.BASE_URL+imageName)
                    .resize(500, 500)
                    .into(mImageView);
        }
    }

}

