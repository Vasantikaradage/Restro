package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Captain.Models.ScanTableModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVTableDetailAdapter extends RecyclerView.Adapter<RVTableDetailAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<ScanTableModel> arrayList;
    private int mAreaId;

    RVTableDetailAdapter(Context context, ArrayList<ScanTableModel> scanTableModelArrayList, int areaId) {
        this.mContext = context;
        this.arrayList = scanTableModelArrayList;
        this.mAreaId = areaId;
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
                    Toast.makeText(mContext, "Show orders list which are placed on this table", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext, "Show table list for swapping", Toast.LENGTH_SHORT).show();

                    /*LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.dialog_swap_table, null);
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
                    bottomSheetDialog.setContentView(view);

                    LinearLayout llSwapTable = view.findViewById(R.id.llSwapTable);

                    addRadiogroup(llSwapTable);
                    bottomSheetDialog.show();*/
                    return true;
                }
            });
        }

        private void addRadiogroup(LinearLayout llSwapTable) {
            RadioGroup radioGroup = null;
            RadioButton radioButton = null;

            radioGroup = new RadioGroup(mContext);
            //radioGroup.setId(i + 100);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    radioButton = new RadioButton(mContext);
                    radioButton.setText(String.valueOf(j));
                    //radioButton.setId(j + 100);
                    radioGroup.addView(radioButton);
                }
            }

            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutparams.setMargins(10, 10, 10, 0);
            radioGroup.setOrientation(LinearLayout.VERTICAL);
            radioGroup.setLayoutParams(layoutparams);

            llSwapTable.addView(radioGroup);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Toast.makeText(mContext, String.valueOf(checkedId), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
