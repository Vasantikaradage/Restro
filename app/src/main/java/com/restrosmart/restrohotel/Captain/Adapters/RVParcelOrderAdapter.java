package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Activities.ActivityCapSpecificOrders;
import com.restrosmart.restrohotel.Captain.Interfaces.CapOrderDeleteListener;
import com.restrosmart.restrohotel.Captain.Models.AllOrderModel;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.CAP_DELETE_ORDER;

public class RVParcelOrderAdapter extends RecyclerView.Adapter<RVParcelOrderAdapter.ViewHolder> {

    private Context mContext;
    private int mHotelId;
    private ArrayList<AllOrderModel> arrayList;
    private ArrayList<UserCategory> mUserCategoryArrayList;
    private CapOrderDeleteListener mCapOrderDeleteListener;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    public RVParcelOrderAdapter(Context context, int hotelId, ArrayList<AllOrderModel> allOrderModelArrayList, ArrayList<UserCategory> userCategoryArrayList, CapOrderDeleteListener capOrderDeleteListener) {
        this.mContext = context;
        this.mHotelId = hotelId;
        this.arrayList = allOrderModelArrayList;
        this.mUserCategoryArrayList = userCategoryArrayList;
        this.mCapOrderDeleteListener = capOrderDeleteListener;
    }

    @NonNull
    @Override
    public RVParcelOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parcel_order_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVParcelOrderAdapter.ViewHolder viewHolder, int position) {
        AllOrderModel allOrderModel = arrayList.get(position);

        viewHolder.tvCustName.setText(allOrderModel.getCustName());
        viewHolder.tvCustMobNo.setText(allOrderModel.getCustMob());
        //viewHolder.tvTableId.setText(String.valueOf(arrayList.get(i).getTableId()));
        //viewHolder.tvDateTime.setText(arrayList.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
                    AllOrderModel allOrderModel = arrayList.get(getAdapterPosition());

                    Intent intent = new Intent(mContext, ActivityCapSpecificOrders.class);
                    intent.putExtra("title", "Parcel Order");
                    intent.putExtra("hotelId", mHotelId);
                    intent.putExtra("custId", allOrderModel.getCustId());
                    intent.putExtra("custName", allOrderModel.getCustName());
                    intent.putExtra("custMob", allOrderModel.getCustMob());
                    intent.putExtra("tableId", allOrderModel.getTableId());
                    intent.putExtra("tableNo", allOrderModel.getTableNo());
                    intent.putExtra("arraylist", allOrderModel.getCapOrderModelArrayList());
                    intent.putExtra("categoryArraylist", mUserCategoryArrayList);
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
                                    deleteOrder(arrayList.get(getAdapterPosition()).getCustId(), arrayList.get(getAdapterPosition()).getTableId());
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
            mRetrofitService.retrofitData(CAP_DELETE_ORDER, (service.capDeleteOrder(mHotelId, tableNo, 0, custId)));
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
