package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.restrosmart.restrohotel.Adapter.RVToppingsAdapter;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class FragmentVegTopping extends Fragment {
    private  View  view;
    private RecyclerView rvVegToppings;
    String [] toppingsName={"Extra Chease","Extra masala","Extra Chease","Extra Chease"};
    int  [] toppingsPrice={30,20,20,10,};

    private ArrayList<ToppingsForm> arrayListToppings;
    private FrameLayout flAddVegToppings;
    private View dialoglayout;
    private AlertDialog dialog;


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
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialoglayout);
                dialog = builder.create();
                dialog.show();


            }
        });



    }

    private void init() {
        rvVegToppings=view.findViewById(R.id.rv_topins);
        arrayListToppings=new ArrayList<ToppingsForm>();
        flAddVegToppings=view.findViewById(R.id.ivAddToppings);
    }
}
