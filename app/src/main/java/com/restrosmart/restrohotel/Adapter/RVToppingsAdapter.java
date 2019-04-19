package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.MarkToppingListerner;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;


public class RVToppingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ToppingsForm> arrayListToppings, arrayList, arrayListToppingsEditInfo;
    private MarkToppingListerner markToppingListerner;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public RVToppingsAdapter(Context context, ArrayList<ToppingsForm> rvVegToppings, ArrayList<ToppingsForm> arrayListToppingsEditinfo, MarkToppingListerner markToppingListerner) {
        this.mContext = context;
        this.arrayListToppings = rvVegToppings;
        this.markToppingListerner = markToppingListerner;
        this.arrayListToppingsEditInfo = arrayListToppingsEditinfo;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      /*  View view = LayoutInflater.from(mContext).inflate(R.layout.rv_toppings_item_menu, viewGroup, false);
        return new MyHolder(view);*/

        if (i == TYPE_HEADER) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.toppings_header_item, viewGroup, false);
            return new HeaderViewHolder(itemView);

        } else if (i == TYPE_ITEM) {
            View itemView1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.topping_list_item, viewGroup, false);
            return new ItemViewHolder(itemView1);
        }

        throw new RuntimeException("no match for : " + i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).tvToppingName.setText(arrayListToppings.get(i - 1).getToppingsName());

            String price = String.valueOf(arrayListToppings.get(i - 1).getToppingsPrice());
            ((ItemViewHolder) viewHolder).tvToppingPrice.setText("\u20B9 " + price);

            if (arrayListToppingsEditInfo == null || arrayListToppingsEditInfo.size() == 0) {
                ((ItemViewHolder) viewHolder).cbSelect.setChecked(arrayListToppings.get(i - 1).isSelected());

            } else {
                for (int position = 0; position < arrayListToppingsEditInfo.size(); position++) {
                    if (arrayListToppings.get(i - 1).getToppingId() == arrayListToppingsEditInfo.get(position).getToppingId()) {
                        ((ItemViewHolder) viewHolder).cbSelect.setChecked(true);
                        break;

                    } else {
                        ((ItemViewHolder) viewHolder).cbSelect.setChecked(arrayListToppings.get(i - 1).isSelected());

                    }
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return arrayListToppings.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionItem(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionItem(int position) {
        return position == 0;
    }


    public void checkedist(ArrayList<ToppingsForm> checkedArrayList) {
        this.arrayListToppings = checkedArrayList;
        notifyDataSetChanged();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbSelectAll;

        HeaderViewHolder(View itemView) {
            super(itemView);

            cbSelectAll = itemView.findViewById(R.id.cbSelectAll);

            cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        arrayList = new ArrayList<>();
                        for (int i = 0; i < arrayListToppings.size(); i++) {
                            ToppingsForm toppingsForm = new ToppingsForm();
                            toppingsForm.setToppingId(arrayListToppings.get(i).getToppingId());
                            toppingsForm.setToppingsName(arrayListToppings.get(i).getToppingsName());
                            toppingsForm.setToppingsPrice(arrayListToppings.get(i).getToppingsPrice());
                            toppingsForm.setSelected(true);
                            arrayList.add(toppingsForm);
                        }
                        checkedist(arrayList);
                        markToppingListerner.markToppings(arrayList);

                    } else {
                        arrayList = new ArrayList<>();
                        for (int i = 0; i < arrayListToppings.size(); i++) {
                            ToppingsForm toppingsForm = new ToppingsForm();
                            toppingsForm.setToppingId(arrayListToppings.get(i).getToppingId());
                            toppingsForm.setToppingsName(arrayListToppings.get(i).getToppingsName());
                            toppingsForm.setToppingsPrice(arrayListToppings.get(i).getToppingsPrice());
                            toppingsForm.setSelected(false);
                            arrayList.add(toppingsForm);
                        }
                        checkedist(arrayList);
                        markToppingListerner.markToppings(arrayList);
                    }
                }
            });
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvToppingPrice, tvToppingName;
        private CheckBox cbSelect;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvToppingPrice = itemView.findViewById(R.id.tv_topping_price);
            tvToppingName = itemView.findViewById(R.id.tv_topping_name);
            cbSelect = itemView.findViewById(R.id.cbSelect);

            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // arrayList=new ArrayList<>();
                        arrayListToppings.get(getAdapterPosition() - 1).setSelected(true);

                        markToppingListerner.markToppings(arrayListToppings);
                    } else {
                        arrayListToppings.get(getAdapterPosition() - 1).setSelected(false);
                        // arrayList.get(getAdapterPosition() - 1).setStudentAttendance("A");
                        markToppingListerner.markToppings(arrayListToppings);
                    }
                }
            });
        }
    }
}



