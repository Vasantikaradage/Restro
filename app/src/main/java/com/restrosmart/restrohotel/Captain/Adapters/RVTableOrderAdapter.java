package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Activities.ActivityCapSpecificOrders;
import com.restrosmart.restrohotel.Captain.Interfaces.CapOrderDeleteListener;
import com.restrosmart.restrohotel.Captain.Models.AllOrderModel;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.CAP_DELETE_ORDER;

public class RVTableOrderAdapter extends RecyclerView.Adapter<RVTableOrderAdapter.ViewHolder> {

    private Context mContext;
    private int mHotelId;
    private ArrayList<AllOrderModel> arrayList;
    private CapOrderDeleteListener mCapOrderDeleteListener;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    public RVTableOrderAdapter(Context context, int hotelId, ArrayList<AllOrderModel> allOrderModelArrayList, CapOrderDeleteListener capOrderDeleteListener) {
        this.mContext = context;
        this.mHotelId = hotelId;
        this.arrayList = allOrderModelArrayList;
        this.mCapOrderDeleteListener = capOrderDeleteListener;
    }

    @NonNull
    @Override
    public RVTableOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_order_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVTableOrderAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvCustName.setText(arrayList.get(i).getCustName());
        viewHolder.tvCustMobNo.setText(arrayList.get(i).getCustMob());
        viewHolder.tvTableId.setText(String.valueOf(arrayList.get(i).getTableNo()));
        //viewHolder.tvDateTime.setText(arrayList.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCustName, tvCustMobNo, tvDateTime, tvTableId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustName = itemView.findViewById(R.id.tvCustName);
            tvCustMobNo = itemView.findViewById(R.id.tvCustMobNo);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTableId = itemView.findViewById(R.id.tvTableId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityCapSpecificOrders.class);
                    intent.putExtra("arraylist", arrayList.get(getAdapterPosition()).getCapOrderModelArrayList());
                    mContext.startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(mContext.getResources().getString(R.string.app_name));
                    builder.setMessage("Are you sure you want to delete this order?");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteOrder(arrayList.get(getAdapterPosition()).getCustId(), arrayList.get(getAdapterPosition()).getTableNo());
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
                    return true;
                }
            });
        }

        private void deleteOrder(String custId, int tableNo) {
            initRetrofitCallback();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, mContext);
            mRetrofitService.retrofitData(CAP_DELETE_ORDER, (service.capDeleteOrder(mHotelId, tableNo, custId)));
        }

        private void initRetrofitCallback() {
            mResultCallBack = new IResult() {
                @Override
                public void notifySuccess(int requestId, Response<JsonObject> response) {
                    JsonObject jsonObjectResponse = response.body();
                    String responseValue = jsonObjectResponse.toString();

                    if (requestId == CAP_DELETE_ORDER) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);

                            int status = jsonObject.getInt("status");
                            String msg = jsonObject.getString("message");

                            if (status == 1) {
                                arrayList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                                mCapOrderDeleteListener.deleteOrder(arrayList.size());
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
