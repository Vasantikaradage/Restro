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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayAllCategory;
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

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;


/**
 * Created by SHREE on 16/10/2018.
 */

public class FragmentTabParentCategory extends Fragment {

    private AdapterDisplayAllCategory adapterDisplayAllMenu;
    private RecyclerView recyclerView;
    private View view;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionmanager;
    private int mHotelId, mBranchId;
    private View dialoglayout;
    private EditText etxCategoryNme;
    private CircleImageView mImageView;
    private String imageName, image, mFinalImageName;
    private BottomSheetDialog dialog;
    private SpinKitView skLoading;

    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_items, null);

        init();

        Bundle bundle = getArguments();
        final ArrayList<CategoryForm> categoryForms = bundle.getParcelableArrayList("menuobj");
        skLoading.setVisibility(View.GONE);


        HashMap<String, String> name_info = mSessionmanager.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        adapterDisplayAllMenu = new AdapterDisplayAllCategory(getActivity(), categoryForms, new DeleteListener() {
            @Override
            public void getDeleteListenerPosition(int position) {
                skLoading.setVisibility(View.VISIBLE);
                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(DELETE_CATEGORY, service.deleteCategory(categoryForms.get(position).getCategory_id(),
                        mHotelId,
                        categoryForms.get(position).getPc_Id()));

            }
        }, new EditListener() {
            @Override
            public void getEditListenerPosition(final int position) {

                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                dialoglayout = li.inflate(R.layout.activity_add_category, null);
                // final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(dialoglayout);
                final FrameLayout btnCamara = (FrameLayout) dialoglayout.findViewById(R.id.iv_select_image);

                etxCategoryNme = dialoglayout.findViewById(R.id.etx_category_name);
                final Button btnCancel = dialoglayout.findViewById(R.id.btnCancel);
                Button btnSave = dialoglayout.findViewById(R.id.btnSave);
                Button btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);

                btnSave.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);

                TextView txTitle = dialoglayout.findViewById(R.id.tx_edit_cat);
                txTitle.setVisibility(View.VISIBLE);
                dialog.show();
                mImageView = (CircleImageView) dialoglayout.findViewById(R.id.img_category);
                etxCategoryNme.setText(categoryForms.get(position).getCategory_Name());

                Picasso.with(dialoglayout.getContext())
                        .load(categoryForms.get(position).getC_Image_Name())
                        .resize(500, 500)
                        .into(mImageView);

                btnCamara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityCategoryGallery.class);
                        intent.putExtra("Pc_Id", categoryForms.get(position).getPc_Id());
                        startActivityForResult(intent, IMAGE_RESULT_OK);
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image = (categoryForms.get(position).getC_Image_Name()).substring((categoryForms.get(position).getC_Image_Name()).lastIndexOf("/") + 1);

                        Picasso.with(dialoglayout.getContext())
                                .load(categoryForms.get(position).getC_Image_Name())
                                .resize(500, 500)
                                .into(mImageView);

                        btnCamara.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), ActivityCategoryGallery.class);
                                intent.putExtra("Pc_Id", categoryForms.get(position).getPc_Id());
                                startActivityForResult(intent, IMAGE_RESULT_OK);
                            }
                        });


                        if (imageName == null) {
                            mFinalImageName = "null";
                            Picasso.with(getActivity())
                                    .load(categoryForms.get(position).getC_Image_Name())
                                    .resize(500, 500)
                                    .into(mImageView);

                            if((image.equals("def_veg.png")) || (image.equals("def_non_veg.png")) || (image .equals("def_liq.png")) || (image.equals("def_dessert.png)"))){
                                mFinalImageName = "null";}
                            else
                            {
                                mFinalImageName = image.substring(image.lastIndexOf("/") + 1);
                            }
                        } else {
                            mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                            Picasso.with(getActivity())
                                    .load( imageName)
                                    .resize(500, 500)
                                    .into(mImageView);

                        }
                        getRetrofitDataUpdate();
                        skLoading.setVisibility(View.VISIBLE);
                    }

                    //category edit web service call
                    private void getRetrofitDataUpdate() {
                        initRetrofitCallback();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(EDIT_CATEGORY, service.editCategory(
                                categoryForms.get(position).getCategory_Name(),
                                etxCategoryNme.getText().toString(),
                                mFinalImageName,
                                categoryForms.get(position).getCategory_id(),
                                mHotelId,
                                categoryForms.get(position).getPc_Id()));
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }


                });
                dialog.show();

            }
        });
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapterDisplayAllMenu);
        adapterDisplayAllMenu.notifyDataSetChanged();
        return view;
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case DELETE_CATEGORY:
                        JsonObject jsonObject1 = response.body();
                        String valueInfo=jsonObject1.toString();
                        try {
                            JSONObject object=new JSONObject(valueInfo);
                            int status=object.getInt("status");
                            if(status==1) {
                                Toast.makeText(getActivity(), "Category deleted successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.setAction("Refresh_CategoryList");
                                getActivity().sendBroadcast(intent);
                            }else
                            {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                            } else {
                                Toast.makeText(getActivity(),object.getString("message") , Toast.LENGTH_SHORT).show();

                            }
                            skLoading.setVisibility(View.GONE);
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

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_item);
        mSessionmanager = new Sessionmanager(getActivity());
        skLoading=view.findViewById(R.id.skLoading);
    }

    public static Fragment newInstance(ArrayList<CategoryForm> menu, int position) {
        FragmentTabParentCategory fragment = new FragmentTabParentCategory();
        Bundle args = new Bundle();
        args.putParcelableArrayList("menuobj", menu);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_RESULT_OK) {
            imageName = data.getStringExtra("image_name");

            Picasso.with(dialoglayout.getContext())
                    .load(imageName)
                    .resize(500, 500)
                    .into(mImageView);
        }
    }
}



