package com.restrosmart.restrohotel.Captain.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Models.AreaSwapModel;
import com.restrosmart.restrohotel.Captain.Models.TableSwapModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_BOOKED_TABLE;
import static com.restrosmart.restrohotel.ConstantVariables.SWAP_TABLE;

public class RVSwapFreeTablesAdapter extends RecyclerView.Adapter<RVSwapFreeTablesAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<TableSwapModel> arrayList;
    private int mOldTableId, mCustId;
    private Dialog swapDialog;
    private ProgressDialog progressDialog;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    RVSwapFreeTablesAdapter(Context context, ArrayList<TableSwapModel> tableSwapModelArrayList, int tableId, int custId) {
        this.mContext = context;
        this.arrayList = tableSwapModelArrayList;
        this.mOldTableId = tableId;
        this.mCustId = custId;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_table_detail_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {
        holder.tvTableNo.setText(String.valueOf(arrayList.get(i).getTableId()));
        holder.ivTable.setImageResource(R.drawable.ic_table_gray);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTableNo;
        private ImageView ivTable;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTable = itemView.findViewById(R.id.ivTable);
            tvTableNo = itemView.findViewById(R.id.tvTableNo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swapDialog = new Dialog(mContext);
                    //swapDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //swapDialog.setCancelable(false);
                    swapDialog.setContentView(R.layout.dialog_swap_table);

                    Window window = swapDialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    //window.setBackgroundDrawableResource(R.color.colorTransparent);
                    window.setGravity(Gravity.CENTER);
                    swapDialog.show();

                    TextView tvOldTable = swapDialog.findViewById(R.id.tvOldTable);
                    TextView tvNewTable = swapDialog.findViewById(R.id.tvNewTable);
                    Button btnCancel = swapDialog.findViewById(R.id.btnCancel);
                    Button btnSwap = swapDialog.findViewById(R.id.btnSwap);

                    tvOldTable.setText(String.valueOf(mOldTableId));
                    tvNewTable.setText(String.valueOf(arrayList.get(getAdapterPosition()).getTableId()));

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swapDialog.dismiss();
                        }
                    });

                    btnSwap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swapBookedTable(arrayList.get(getAdapterPosition()).getTableId());
                            swapDialog.dismiss();

                            progressDialog = new ProgressDialog(mContext);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            //Without this user can hide loader by tapping outside screen
                            progressDialog.setCancelable(false);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setTitle(mContext.getResources().getString(R.string.app_name));
                            progressDialog.setMessage("Swapping tables. Please wait...");
                            progressDialog.show();
                        }
                    });
                }
            });
        }
    }

    private void swapBookedTable(int mNewTableId) {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, mContext);
        mRetrofitService.retrofitData(SWAP_TABLE, (service.swapTable(1, mOldTableId, mNewTableId, mCustId)));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String mResponseString = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(mResponseString);
                    int status = object.getInt("status");
                    String msg = object.getString("message");

                    if (status == 1) {
                        Intent intent = new Intent("com.restrohotel.captain.swap.table");
                        mContext.sendBroadcast(intent);
                    } else {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

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
}
