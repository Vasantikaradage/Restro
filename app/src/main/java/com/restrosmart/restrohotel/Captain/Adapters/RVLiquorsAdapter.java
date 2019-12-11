package com.restrosmart.restrohotel.Captain.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Interfaces.FlavourSelectedListener;
import com.restrosmart.restrohotel.Captain.Interfaces.FlavourUnitSelectedListener;
import com.restrosmart.restrohotel.Captain.Interfaces.ToppingsListener;
import com.restrosmart.restrohotel.Captain.Models.FlavourUnitModel;
import com.restrosmart.restrohotel.Captain.Models.FlavoursModel;
import com.restrosmart.restrohotel.Captain.Models.SpecificLiqourBrandModel;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.LIQOUR_ADD_TO_CART;
import static com.restrosmart.restrohotel.ConstantVariables.UNIQUE_KEY;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.CUST_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.TABLE_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.TABLE_NO;

public class RVLiquorsAdapter extends RecyclerView.Adapter<RVLiquorsAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<SpecificLiqourBrandModel> arraylist;
    private ArrayList<FlavoursModel> flavoursModelArrayList;
    private RVFlavourUnitsAdapter rvFlavourUnitsAdapter;
    private String toppingsList;
    private int flavourId;
    private String flavourName;
    private TextView tvLiqourQty;
    private String selectedUnitName, selectedUnitPrice;

    private BottomSheetDialog dialogView;
    private ProgressDialog progressDialog;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private HashMap<String, String> hotelDetails, userDetails;

    RVLiquorsAdapter(Context context, ArrayList<SpecificLiqourBrandModel> arraylist) {
        this.arraylist = arraylist;
        this.mContext = context;
        this.mSessionmanager = new Sessionmanager(mContext);

        userDetails = mSessionmanager.getCustDetails();
        hotelDetails = mSessionmanager.getHotelDetails();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_liquor_item, parent, false);
        return new RVLiquorsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Picasso.with(mContext).load(arraylist.get(position).getMenuImage()).into(holder.ivImage);

        //holder.ivImage.setImageResource(arraylist.get(position).getMenuImage());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arraylist.get(getAdapterPosition()).getFlavoursModelArrayList() != null && arraylist.get(getAdapterPosition()).getFlavoursModelArrayList().size() > 0) {
                        flavourDialog();
                    } else {
                        Toast.makeText(mContext, "No flavours added to this liquor brand", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void flavourDialog() {
            /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.specific_liquor_dialog, null);
            }
            dialogBuilder.setView(dialogView);*/

            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialoglayout = li.inflate(R.layout.specific_liquor_dialog, null);
            dialogView = new BottomSheetDialog(mContext);
            dialogView.setContentView(dialoglayout);

            RecyclerView rvLiquorFlavours = dialogView.findViewById(R.id.rvLiquorFlavours);
            final ImageView ivFullFlavour = dialogView.findViewById(R.id.ivFullFlavour);
            final Button btnAddLiqour = dialogView.findViewById(R.id.btnAddLiqour);
            final TextView tvLiqourBrandName = dialogView.findViewById(R.id.tvLiqourBrandName);
            final TextView tvFlavourName = dialogView.findViewById(R.id.tvFlavourName);
            final RecyclerView rvUnits = dialogView.findViewById(R.id.rvUnits);
            final RecyclerView rvToppings = dialogView.findViewById(R.id.rvToppings);
            final TextView tvToppingsLabel = dialogView.findViewById(R.id.tvToppingsLabel);
            final TextView tvUnitPrice = dialogView.findViewById(R.id.tvUnitPrice);
            final TextView tvMinusQty = dialogView.findViewById(R.id.tvMinusQty);
            tvLiqourQty = dialogView.findViewById(R.id.tvLiqourQty);
            final TextView tvAddQty = dialogView.findViewById(R.id.tvAddQty);

            tvLiqourBrandName.setText(arraylist.get(getAdapterPosition()).getMenuName());

            /*Setting all default values*/
            flavoursModelArrayList = arraylist.get(getAdapterPosition()).getFlavoursModelArrayList();
            Picasso.with(mContext).load(flavoursModelArrayList.get(0).getFlavourImg()).into(ivFullFlavour);
            tvFlavourName.setText(flavoursModelArrayList.get(0).getFlavourName());
            flavourId = flavoursModelArrayList.get(0).getFlavourId();
            flavourName = flavoursModelArrayList.get(0).getFlavourName();

            ArrayList<FlavourUnitModel> flavourUnitModelArrayList = flavoursModelArrayList.get(0).getFlavourUnitModelArrayList();
            tvUnitPrice.setText(mContext.getResources().getString(R.string.currency) + " " + String.valueOf(flavourUnitModelArrayList.get(0).getUnitPrice()));
            selectedUnitName = flavourUnitModelArrayList.get(0).getUnitName();
            selectedUnitPrice = String.valueOf(flavourUnitModelArrayList.get(0).getUnitPrice());

            /*Liqour flavours recyclerview*/
            rvLiquorFlavours.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            //rvLiquors.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));
            RVLiquorFlavoursAdapter rvLiquorFlavoursAdapter = new RVLiquorFlavoursAdapter(mContext, arraylist.get(getAdapterPosition()).getFlavoursModelArrayList(), new FlavourSelectedListener() {
                @Override
                public void flavourSelected(int adapterPosition) {

                    ArrayList<FlavoursModel> flavoursModelArrayList1 = arraylist.get(getAdapterPosition()).getFlavoursModelArrayList();

                    Picasso.with(mContext).load(flavoursModelArrayList1.get(adapterPosition).getFlavourImg()).into(ivFullFlavour);
                    tvFlavourName.setText(flavoursModelArrayList1.get(adapterPosition).getFlavourName());
                    flavourId = flavoursModelArrayList.get(adapterPosition).getFlavourId();
                    flavourName = flavoursModelArrayList.get(adapterPosition).getFlavourName();
                    rvFlavourUnitsAdapter.refreshList(flavoursModelArrayList1.get(adapterPosition).getFlavourUnitModelArrayList());
                    tvUnitPrice.setText(mContext.getResources().getString(R.string.currency) + " " + String.valueOf(flavoursModelArrayList1.get(adapterPosition).getFlavourUnitModelArrayList().get(0).getUnitPrice()));
                }
            });
            rvLiquorFlavours.setAdapter(rvLiquorFlavoursAdapter);

            /*Liqour flavours unit recyclerview*/
            rvUnits.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            rvFlavourUnitsAdapter = new RVFlavourUnitsAdapter(mContext, flavourUnitModelArrayList, new FlavourUnitSelectedListener() {
                @Override
                public void unitSelected(int adapterPosition, ArrayList<FlavourUnitModel> arrayList) {
                    tvUnitPrice.setText(mContext.getResources().getString(R.string.currency) + " " + String.valueOf(arrayList.get(adapterPosition).getUnitPrice()));
                    selectedUnitName = arrayList.get(adapterPosition).getUnitName();
                    selectedUnitPrice = String.valueOf(arrayList.get(adapterPosition).getUnitPrice());
                }
            });
            rvUnits.setAdapter(rvFlavourUnitsAdapter);

            /*Toppings recyclerview*/
            if (arraylist.get(getAdapterPosition()).getToppingsModelArrayList() != null && arraylist.get(getAdapterPosition()).getToppingsModelArrayList().size() > 0) {

                rvToppings.setLayoutManager(new LinearLayoutManager(mContext));
                RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(mContext, arraylist.get(getAdapterPosition()).getToppingsModelArrayList(), new ToppingsListener() {
                    @Override
                    public void AddRemoveToppings(ArrayList<ToppingsModel> arrayList) {
                        if (arrayList != null && arrayList.size() > 0) {
                            toppingsList = getToppingList(arrayList);
                        } else {
                            toppingsList = null;
                        }
                    }
                });
                rvToppings.setAdapter(rvToppingsAdapter);
                rvToppings.setVisibility(View.VISIBLE);
                tvToppingsLabel.setVisibility(View.VISIBLE);
            } else {
                tvToppingsLabel.setVisibility(View.GONE);
                rvToppings.setVisibility(View.GONE);
            }

            //flavourDialog = dialogBuilder.create();
            //flavourDialog.setCanceledOnTouchOutside(false);
            //flavourDialog.setCancelable(false);
            dialogView.show();

            btnAddLiqour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Adding to cart...");
                    progressDialog.show();
                    LiquorAddToCart();
                }
            });

            tvAddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvLiqourQty.setText(String.valueOf(Integer.parseInt(tvLiqourQty.getText().toString()) + 1));
                }
            });

            tvMinusQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(tvLiqourQty.getText().toString()) == 1) {
                        tvLiqourQty.setText(String.valueOf(1));
                    } else {
                        tvLiqourQty.setText(String.valueOf(Integer.parseInt(tvLiqourQty.getText().toString()) - 1));
                    }
                }
            });

        }

        private void LiquorAddToCart() {
            initRetrofitCallback();

            if (toppingsList == null || toppingsList.equalsIgnoreCase("")) {
                toppingsList = "";
            }

            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, mContext);
            mRetrofitService.retrofitData(LIQOUR_ADD_TO_CART, (service.addToCart(mSessionmanager.getOrderID(),
                    Integer.parseInt(userDetails.get(TABLE_ID)),
                    Integer.parseInt(userDetails.get(TABLE_NO)),
                    userDetails.get(CUST_ID),
                    Integer.parseInt(hotelDetails.get(HOTEL_ID)),
                    String.valueOf(flavourId), "", "", 0,
                    flavourName,
                    Integer.parseInt(tvLiqourQty.getText().toString()),
                    selectedUnitName,
                    selectedUnitPrice,
                    toppingsList, 0, 0, 2, 7, UNIQUE_KEY)));
        }

        private void initRetrofitCallback() {
            mResultCallBack = new IResult() {
                @Override
                public void notifySuccess(int requestId, Response<JsonObject> response) {
                    JsonObject jsonObjectCat = response.body();
                    String responseValue = jsonObjectCat.toString();

                    try {
                        JSONObject jsonObject = new JSONObject(responseValue);

                        int status = jsonObject.getInt("status");
                        String msg = jsonObject.getString("message");

                        progressDialog.dismiss();
                        if (status == 1) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            tvLiqourQty.setText("1");
                            mSessionmanager.saveCartCount();
                            mSessionmanager.saveOrderID(jsonObject.getInt("Order_Id"));

                            Intent intent = new Intent("com.restrosmart.restro.addmenu");
                            //intent.putExtra("menuname", arraylist.get(getAdapterPosition()).getMenuName());
                            mContext.sendBroadcast(intent);

                            dialogView.dismiss();
                        } else {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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

        private String getToppingList(ArrayList<ToppingsModel> toppingsArrayList) {

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < toppingsArrayList.size(); i++) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Topping_Id", toppingsArrayList.get(i).getToppingsId());
                    jsonObject.put("Topping_Name", toppingsArrayList.get(i).getToppingsName());
                    jsonObject.put("Topping_Price", toppingsArrayList.get(i).getToppingsPrice());
                    jsonArray.put(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return jsonArray.toString();
        }
    }
}
