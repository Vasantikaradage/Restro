package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllCategory;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllCategoryOffer;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.CategoryForm;
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

import static com.restrosmart.restrohotel.ConstantVariables.DELETE_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentTabParentCategoryOffer extends Fragment {

    private AdapterDisplayAllCategoryOffer adapterDisplayAllMenuOffer;
    private RecyclerView recyclerView;
    private View view;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionmanager;
    private  int mHotelId,mBranchId;
    private  View dialoglayout;
    private EditText etxCategoryNme;
    private CircleImageView mImageView;
    private String  imageName, mFinalImageName;
    private BottomSheetDialog dialog;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_items, null);

        init();
        Bundle bundle = getArguments();
        final ArrayList<CategoryForm> categoryForms = bundle.getParcelableArrayList("menuobj");

        HashMap<String, String> name_info = mSessionmanager.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        adapterDisplayAllMenuOffer = new AdapterDisplayAllCategoryOffer(getActivity(), categoryForms);
        recyclerView.setAdapter(adapterDisplayAllMenuOffer);
        adapterDisplayAllMenuOffer.notifyDataSetChanged();


        return view;

    }

   /* private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case DELETE_CATEGORY:
                        JsonObject jsonObject1 = response.body();
                        Toast.makeText(getActivity(), "Category deleted successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction("Refresh_CategoryList");
                        getActivity().sendBroadcast(intent);
                        break;

                    case EDIT_CATEGORY:
                        JsonObject jsonObject2 = response.body();
                        String valueinfo = jsonObject2.toString();
                        try {
                            JSONObject object = new JSONObject(valueinfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(getActivity(), "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent();
                                intent1.setAction("Refresh_CategoryList");
                                getActivity().sendBroadcast(intent1);
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Try again...", Toast.LENGTH_SHORT).show();

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
    }*/

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_item);
        mSessionmanager=new Sessionmanager(getActivity());
    }

    public static Fragment newInstance(ArrayList<CategoryForm> menu, int position) {
        FragmentTabParentCategoryOffer fragment = new FragmentTabParentCategoryOffer();
        Bundle args = new Bundle();
        args.putParcelableArrayList("menuobj", menu);
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




