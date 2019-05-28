package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Captain.Activities.ActivityTableOrders;
import com.restrosmart.restrohotel.Captain.Models.AreaSwapModel;
import com.restrosmart.restrohotel.Captain.Models.TableSwapModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVSwapBookedTablesAdapter extends RecyclerView.Adapter<RVSwapBookedTablesAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<AreaSwapModel> mFreeAreaSwapArrayList;
    private ArrayList<TableSwapModel> arrayList;
    private BottomSheetDialog bottomSheetDialog;

    RVSwapBookedTablesAdapter(Context context, ArrayList<TableSwapModel> tableSwapModelArrayList, ArrayList<AreaSwapModel> freeAreaSwapArrayList) {
        this.mContext = context;
        this.arrayList = tableSwapModelArrayList;
        this.mFreeAreaSwapArrayList = freeAreaSwapArrayList;

        bottomSheetDialog = new BottomSheetDialog(mContext);
        mContext.registerReceiver(mSwapTableReceiver, new IntentFilter("com.restrohotel.captain.swap.table"));
    }

    BroadcastReceiver mSwapTableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            bottomSheetDialog.dismiss();
        }
    };

    public void onDestroyReceiver() {
        mContext.unregisterReceiver(mSwapTableReceiver);
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

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTableNo = itemView.findViewById(R.id.tvTableNo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityTableOrders.class);
                    intent.putExtra("orderId", arrayList.get(getAdapterPosition()).getOrderId());
                    intent.putExtra("custId", arrayList.get(getAdapterPosition()).getCustId());
                    mContext.startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.dialog_free_table, null);

                    bottomSheetDialog.setContentView(view);

                    RecyclerView rvFreeTables = view.findViewById(R.id.rvFreeTables);

                    RVSwapFreeAreaAdapter rvSwapFreeAreaAdapter = new RVSwapFreeAreaAdapter(mContext, mFreeAreaSwapArrayList,
                            arrayList.get(getAdapterPosition()).getTableId(), arrayList.get(getAdapterPosition()).getCustId());
                    rvFreeTables.setHasFixedSize(true);
                    rvFreeTables.setNestedScrollingEnabled(false);
                    rvFreeTables.setLayoutManager(new GridLayoutManager(mContext, 1));
                    rvFreeTables.setItemAnimator(new DefaultItemAnimator());
                    rvFreeTables.setAdapter(rvSwapFreeAreaAdapter);

                    //addRadiogroup(llSwapTable);
                    bottomSheetDialog.show();
                    return true;
                }
            });
        }
    }
}
