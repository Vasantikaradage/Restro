package com.restrosmart.restrohotel.Captain.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Models.CapMenuModel;
import com.restrosmart.restrohotel.Captain.Models.CapOrderModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.CAP_DELETE_ORDER;
import static com.restrosmart.restrohotel.ConstantVariables.PLACE_ORDER;
import static com.restrosmart.restrohotel.ConstantVariables.UNIQUE_KEY;

public class CapSpecificOrderRVAdapter extends RecyclerView.Adapter<CapSpecificOrderRVAdapter.ItemViewHolder> {

    private Context mContext;
    private int mHotelId;
    private String mCustId;
    private int mTableId, mTableNo;
    private ArrayList<String> mOrderIdArrayList;
    private ArrayList<CapOrderModel> mArrayList;
    private ArrayList<Integer> counter = new ArrayList<Integer>();

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    public CapSpecificOrderRVAdapter(Context context, int hotelId, String custId, int tableId, int tableNo, ArrayList<String> orderIdArrayList, ArrayList<CapOrderModel> capOrderModelArrayList) {
        this.mContext = context;
        this.mHotelId = hotelId;
        this.mCustId = custId;
        this.mTableId = tableId;
        this.mTableNo = tableNo;
        this.mOrderIdArrayList = orderIdArrayList;
        this.mArrayList = capOrderModelArrayList;

        for (int i = 0; i < mOrderIdArrayList.size(); i++) {
            counter.add(0);
        }
    }

    @NonNull
    @Override
    public CapSpecificOrderRVAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cap_order_list_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CapSpecificOrderRVAdapter.ItemViewHolder holder, int position) {
        holder.tvOrderName.setText(mOrderIdArrayList.get(position).toString());
        CapOrderModel capMenuModel = mArrayList.get(position);

        if (capMenuModel.getOrderStatus() != null && !capMenuModel.getOrderStatus().equalsIgnoreCase("null"))
            holder.tvOrderStatus.setText("(" + capMenuModel.getOrderStatus() + ")");

        holder.tvSubTotal.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(capMenuModel.getSubTotal()));

        if (capMenuModel.getOrderStatus().equalsIgnoreCase("Not Placed")) {
            holder.viewVertical.setVisibility(View.VISIBLE);
            holder.tvPlaceOrder.setVisibility(View.VISIBLE);
        }

        if (!capMenuModel.getOrderStatus().equalsIgnoreCase("Complete")){
            holder.tvDeleteOrder.setVisibility(View.VISIBLE);
        }

        ArrayList<CapMenuModel> capMenuModelArrayList = capMenuModel.getCapMenuModelArrayList();
        CapSpecificOrderDetailRVAdapter capSpecificOrderDetailRVAdapter = new CapSpecificOrderDetailRVAdapter(mContext, capMenuModelArrayList);
        holder.rvSpecificOrder.setHasFixedSize(true);
        holder.rvSpecificOrder.setNestedScrollingEnabled(false);
        holder.rvSpecificOrder.setLayoutManager(new GridLayoutManager(mContext, 1));
        holder.rvSpecificOrder.setItemAnimator(new DefaultItemAnimator());
        holder.rvSpecificOrder.setAdapter(capSpecificOrderDetailRVAdapter);
    }

    @Override
    public int getItemCount() {
        return mOrderIdArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llView;
        private TextView tvOrderName, tvOrderStatus, tvSubTotal, tvPlaceOrder, tvDeleteOrder;
        private View viewVertical;
        private ImageView ivArrow;
        private RecyclerView rvSpecificOrder;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            llView = itemView.findViewById(R.id.llView);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvSubTotal = itemView.findViewById(R.id.tvSubTotal);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            rvSpecificOrder = itemView.findViewById(R.id.rvSpecificOrder);
            tvPlaceOrder = itemView.findViewById(R.id.tvPlaceOrder);
            tvDeleteOrder = itemView.findViewById(R.id.tvDeleteOrder);
            viewVertical = itemView.findViewById(R.id.viewVertical);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter.get(getAdapterPosition()) % 2 == 0) {
                        llView.setVisibility(View.VISIBLE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_up_arrow_16dp));
                    } else {
                        llView.setVisibility(View.GONE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_down_arrow_16dp));
                    }

                    counter.set(getAdapterPosition(), counter.get(getAdapterPosition()) + 1);
                }
            });

            tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(mContext.getResources().getString(R.string.app_name));
                    builder.setMessage("Are you sure you want to place this order?");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    placeOrder();
                                    dialog.dismiss();
                                }
                            });

                    builder.setNegativeButton(
                            android.R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            tvDeleteOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(mContext.getResources().getString(R.string.app_name));
                    builder.setMessage("Are you sure you want to delete this order?");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteOrder();
                                    dialog.dismiss();
                                }
                            });

                    builder.setNegativeButton(
                            android.R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

        private void placeOrder() {
            CapOrderModel capOrderModel = mArrayList.get(getAdapterPosition());

            initRetrofitCallback();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, mContext);
            mRetrofitService.retrofitData(PLACE_ORDER, (service.placeOrder(mHotelId, mTableId, mTableNo, mCustId, capOrderModel.getOrderId(), "", capOrderModel.getSubTotal(), UNIQUE_KEY)));
        }

        private void deleteOrder() {
            CapOrderModel capOrderModel = mArrayList.get(getAdapterPosition());

            initRetrofitCallback();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, mContext);
            mRetrofitService.retrofitData(CAP_DELETE_ORDER, (service.capDeleteOrder(mHotelId, mTableId, capOrderModel.getOrderId(), mCustId)));
        }

        private void initRetrofitCallback() {
            mResultCallBack = new IResult() {
                @Override
                public void notifySuccess(int requestId, Response<JsonObject> response) {
                    JsonObject jsonObjectResponse = response.body();
                    String responseValue = jsonObjectResponse.toString();

                    switch (requestId) {
                        case CAP_DELETE_ORDER:
                            try {
                                JSONObject jsonObject = new JSONObject(responseValue);

                                int status = jsonObject.getInt("status");
                                String msg = jsonObject.getString("message");

                                if (status == 1) {
                                    //mArrayList.remove(getAdapterPosition());
                                    //notifyDataSetChanged();
                                    //mCapOrderDeleteListener.deleteOrder(arrayList.size());
                                    ((Activity)mContext).finish();
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case PLACE_ORDER:
                            try {
                                JSONObject jsonObject = new JSONObject(responseValue);

                                int status = jsonObject.getInt("status");
                                String msg = jsonObject.getString("message");

                                if (status == 1) {
                                    //mArrayList.remove(getAdapterPosition());
                                    //notifyDataSetChanged();
                                    //mCapOrderDeleteListener.deleteOrder(arrayList.size());
                                    ((Activity)mContext).finish();
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }

                @Override
                public void notifyError(int requestId, Throwable error) {
                    Log.v("Retrofit RequestId", String.valueOf(requestId));
                    Log.v("Retrofit Error", String.valueOf(error));
                }
            };
        }
    }
}
