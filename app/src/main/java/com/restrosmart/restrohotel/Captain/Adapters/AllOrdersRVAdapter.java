package com.restrosmart.restrohotel.Captain.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.restrosmart.restrohotel.Captain.Models.OrderStatusOrderList;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.CAP_READY_ORDER;

public class AllOrdersRVAdapter extends RecyclerView.Adapter<AllOrdersRVAdapter.ItemViewHolder> {

    private Context mContext;
    private int mHotelId;
    private ArrayList<String> mOrderIdArrayList;
    private ArrayList<OrderStatusOrderList> mArrayList;
    private ArrayList<Integer> counter = new ArrayList<Integer>();

    private IResult mResultCallBack;

    public AllOrdersRVAdapter(Context context, int hotelId, ArrayList<String> orderIdArrayList, ArrayList<OrderStatusOrderList> orderStatusOrderListArrayList) {
        this.mContext = context;
        this.mHotelId = hotelId;
        this.mOrderIdArrayList = orderIdArrayList;
        this.mArrayList = orderStatusOrderListArrayList;

        for (int i = 0; i < mOrderIdArrayList.size(); i++) {
            counter.add(0);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_order_list_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvOrderName.setText(mOrderIdArrayList.get(position).toString());

        OrderStatusOrderList orderStatusOrderList = mArrayList.get(position);

        if (orderStatusOrderList.getOrderStatus() != null && !orderStatusOrderList.getOrderStatus().equalsIgnoreCase("null")) {
            holder.tvOrderStatus.setText("(" + orderStatusOrderList.getOrderStatus() + ")");
        }

        OrderDetailRVAdapter orderDetailRVAdapter = new OrderDetailRVAdapter(mContext, orderStatusOrderList.getOrderStatusOrders());
        holder.rvSpecificOrder.setHasFixedSize(true);
        holder.rvSpecificOrder.setNestedScrollingEnabled(false);
        holder.rvSpecificOrder.setLayoutManager(new GridLayoutManager(mContext, 1));
        holder.rvSpecificOrder.setItemAnimator(new DefaultItemAnimator());
        holder.rvSpecificOrder.setAdapter(orderDetailRVAdapter);
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

        private TextView tvOrderName, tvOrderStatus;
        private ImageView ivArrow;
        private RecyclerView rvSpecificOrder;
        private Button btnReady;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            rvSpecificOrder = itemView.findViewById(R.id.rvSpecificOrder);
            btnReady = itemView.findViewById(R.id.btnReady);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    OrderStatusOrderList orderStatusOrderList = mArrayList.get(getAdapterPosition());

                    if (counter.get(getAdapterPosition()) % 2 == 0) {
                        rvSpecificOrder.setVisibility(View.VISIBLE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_up_arrow_16dp));
                        if (orderStatusOrderList.getOrderStatus().equalsIgnoreCase("Preparing")) {
                            btnReady.setVisibility(View.VISIBLE);
                        } else {
                            btnReady.setVisibility(View.GONE);
                        }
                    } else {
                        rvSpecificOrder.setVisibility(View.GONE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_down_arrow_16dp));
                    }

                    counter.set(getAdapterPosition(), counter.get(getAdapterPosition()) + 1);
                }
            });

            btnReady.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(mContext.getResources().getString(R.string.app_name));
                    builder.setMessage("Are you sure you want to ready this order?");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    OrderStatusOrderList orderStatusOrderList = mArrayList.get(getAdapterPosition());
                                    readyOrder(mHotelId, orderStatusOrderList.getoId());
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

        private void readyOrder(int mHotelId, int mOrderId) {
            initRetrofitCallback();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            RetrofitService mRetrofitService = new RetrofitService(mResultCallBack, mContext);
            mRetrofitService.retrofitData(CAP_READY_ORDER, (service.capReadyOrder(mHotelId, mOrderId)));
        }

        private void initRetrofitCallback() {
            mResultCallBack = new IResult() {
                @Override
                public void notifySuccess(int requestId, Response<JsonObject> response) {
                    JsonObject jsonObjectResponse = response.body();
                    String responseValue = jsonObjectResponse.toString();

                    if (requestId == CAP_READY_ORDER) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);

                            int status = jsonObject.getInt("status");
                            String msg = jsonObject.getString("message");

                            if (status == 1) {
                                Intent intent = new Intent("com.restrohotel.captain.swap.table");
                                intent.putExtra("data", "blank");
                                mContext.sendBroadcast(intent);

                                ((Activity) mContext).finish();
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
