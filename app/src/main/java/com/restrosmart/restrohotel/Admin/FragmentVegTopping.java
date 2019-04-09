package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVToppingsAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_TOPPINGS;
import static com.restrosmart.restrohotel.ConstantVariables.SAVE_CATEGORY;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentVegTopping extends Fragment {
    private  View  view;
    private RecyclerView rvVegToppings;
    String [] toppingsName={"Extra Chease","Extra masala","Extra Chease","Extra Chease"};
    int  [] toppingsPrice={30,20,20,10,};

    private ArrayList<ToppingsForm> arrayListToppings;
    private FrameLayout flAddVegToppings;
    private View dialoglayout;
    private BottomSheetDialog dialog;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  EditText etvToppingName,etvToppingPrice;
    private int branchId, hotelId, mPcId;
    private  Sessionmanager sessionmanager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_toppings, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        branchId = Integer.parseInt(name_info.get(BRANCH_ID));


        arrayListToppings.clear();
        for(int i=0;i<toppingsName.length;i++)
        {

            ToppingsForm toppingsForm=new ToppingsForm();
            toppingsForm.setToppingsName(toppingsName[i]);
            toppingsForm.setToppingsPrice(toppingsPrice[i]);
            arrayListToppings.add(toppingsForm);


        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvVegToppings.setHasFixedSize(true);
        rvVegToppings.setLayoutManager(linearLayoutManager);
        RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(getContext(),arrayListToppings, new DeleteListener() {
            @Override
            public void getDeleteListenerPosition(int position) {

            }
        }, new EditListener() {
            @Override
            public void getEditListenerPosition(int position) {

            }
        });
        rvVegToppings.setAdapter(rvToppingsAdapter);

        flAddVegToppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialoglayout = li.inflate(R.layout.dialog_add_toppings, null);
                dialog= new BottomSheetDialog(getActivity());
                dialog.setContentView(dialoglayout);
                etvToppingName=dialoglayout.findViewById(R.id.etv_topping_name);
                etvToppingPrice=dialoglayout.findViewById(R.id.etv_topping_price);
                Button saveTopping=dialoglayout.findViewById(R.id.btnSave);
                Button canCelTopping=dialoglayout.findViewById(R.id.btnCancel);
                Button updateTopping=dialoglayout.findViewById(R.id.btnUpdate);

                saveTopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initRetrofitCallBack();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());

                        mRetrofitService.retrofitData(ADD_TOPPINGS, service.addToppings(etvToppingName.getText().toString(),
                                Integer.parseInt(etvToppingPrice.getText().toString()),
                                hotelId,
                                branchId,
                                1));
                    }
                });




                //dialog = builder.create();
                dialog.show();


            }
        });



    }

    private void initRetrofitCallBack() {
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
        rvVegToppings=view.findViewById(R.id.rv_topins);
        arrayListToppings=new ArrayList<ToppingsForm>();
        flAddVegToppings=view.findViewById(R.id.ivAddToppings);
    }
}
