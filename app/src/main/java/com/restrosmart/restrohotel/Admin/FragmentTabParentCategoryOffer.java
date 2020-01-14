package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllCategoryOffer;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;


public class FragmentTabParentCategoryOffer extends Fragment {

    private AdapterDisplayAllCategoryOffer adapterDisplayAllMenuOffer;
    private RecyclerView recyclerView;
    private View view;
    private Sessionmanager mSessionmanager;
    private  int mHotelId;
    private String  imageName;
    private SpinKitView spinKitView;
    private  int winnerQty,buyQty,getQty,offerTypeId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_items, null);

        init();
        Bundle bundle = getArguments();
        final ArrayList<CategoryForm> categoryForms = bundle.getParcelableArrayList("menuobj");
        winnerQty=bundle.getInt("winnerQty",0);
        buyQty=bundle.getInt("buyQty",0);
        getQty=bundle.getInt("getQty",0);
        offerTypeId=bundle.getInt("offerTypeId",0);


        String win= String.valueOf(winnerQty);
        Toast.makeText(getActivity(),win , Toast.LENGTH_SHORT).show();
        spinKitView.setVisibility(View.GONE);

        HashMap<String, String> name_info = mSessionmanager.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        adapterDisplayAllMenuOffer = new AdapterDisplayAllCategoryOffer(getActivity(), categoryForms,winnerQty,buyQty,getQty,offerTypeId);
        recyclerView.setAdapter(adapterDisplayAllMenuOffer);
        adapterDisplayAllMenuOffer.notifyDataSetChanged();
        return view;

    }



    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_item);
        mSessionmanager=new Sessionmanager(getActivity());
        spinKitView=view.findViewById(R.id.skLoading);
        //arrayListMenu=new ArrayList<>();
    }

    public static Fragment newInstance(ArrayList<CategoryForm> menu, int winnerQty, int buyQty,int getQty, int position, int offerTypeId) {
        FragmentTabParentCategoryOffer fragment = new FragmentTabParentCategoryOffer();
        Bundle args = new Bundle();
        args.putParcelableArrayList("menuobj", menu);
        args.putInt("buyQty",buyQty);
        args.putInt("position", position);
        args.putInt("winnerQty",winnerQty);
        args.putInt("getQty",getQty);
        args.putInt("offerTypeId",offerTypeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            imageName = data.getStringExtra("image_name");
            // Log.e("Result", imageName);

           /* Picasso.with(dialoglayout.getContext())
                    .load(apiService.BASE_URL+imageName)
                    .resize(500, 500)
                    .into(mImageView);*/
        }
    }

}




