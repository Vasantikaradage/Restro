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
    private ArrayList<ToppingsForm> arrayListToppings,arrayList;
    private MarkToppingListerner markToppingListerner;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public RVToppingsAdapter(Context context, ArrayList<ToppingsForm> rvVegToppings, MarkToppingListerner markToppingListerner) {
        this.mContext = context;
        this.arrayListToppings = rvVegToppings;
        this.markToppingListerner=markToppingListerner;

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

            String price= String.valueOf(arrayListToppings.get(i - 1).getToppingsPrice());
            ((ItemViewHolder) viewHolder).tvToppingPrice.setText("\u20B9 "+price);

            ((ItemViewHolder) viewHolder).cbSelect.setChecked(arrayListToppings.get(i - 1).isSelected());
        }
    }



   /* @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        // myHolder.tvToppingsName.setText(arrayListToppings.get(i).getToppingsName());
        ViewHolder.tvToppingsName.setText(arrayListToppings.get(i).getToppingsName());
        String price = String.valueOf(arrayListToppings.get(i).getToppingsPrice());
        ViewHolder.tvToppingsPrice.setText("\u20B9 "+price);
    }*/

    @Override
    public int getItemCount() {
        return arrayListToppings.size() +1 ;
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

    /*public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvToppingsName;
        private TextView tvToppingsPrice, tvToppingsOptionMenu;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingsName = itemView.findViewById(R.id.tv_toppings_name);
            tvToppingsPrice = itemView.findViewById(R.id.tv_toppings_price);
            tvToppingsOptionMenu = itemView.findViewById(R.id.tv_toppings_option_menu);

        }
    }*/


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



//////////////
/*package com.pocket.pocketschool.teacherProfile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pocket.pocketschool.R;
import com.pocket.pocketschool.teacherProfile.listeners.MarkAttendanceListener;
import com.pocket.pocketschool.teacherProfile.model.AttendanceListModel;

import java.util.ArrayList;

public class RVAttendanceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<AttendanceListModel> arrayList, checkedArrayList;
    private MarkAttendanceListener mMarkAttendanceListener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public RVAttendanceListAdapter(Context context, ArrayList<AttendanceListModel> attendanceListModelArrayList, MarkAttendanceListener markAttendanceListener) {
        this.mContext = context;
        this.arrayList = attendanceListModelArrayList;
        this.mMarkAttendanceListener = markAttendanceListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_header_item, parent, false);
            return new HeaderViewHolder(itemView);

        } else if (viewType == TYPE_ITEM) {
            View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_item, parent, false);
            return new ItemViewHolder(itemView1);
        }

        throw new RuntimeException("no match for : " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tvToppingId.setText(arrayList.get(position - 1).getStudentId());
            ((ItemViewHolder) holder).tvStudentName.setText(arrayList.get(position - 1).getStudentName());
            ((ItemViewHolder) holder).cbSelect.setChecked(arrayList.get(position - 1).isSelected());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
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

    public void checkedist(ArrayList<AttendanceListModel> checkedArrayList) {
        this.arrayList = checkedArrayList;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvToppingId, tvStudentName;
        private CheckBox cbSelect;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvToppingId = itemView.findViewById(R.id.tvToppingId);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            cbSelect = itemView.findViewById(R.id.cbSelect);

            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        arrayList.get(getAdapterPosition() - 1).setSelected(true);
                        arrayList.get(getAdapterPosition() - 1).setStudentAttendance("P");
                        mMarkAttendanceListener.markToppings(arrayList);
                    } else {
                        arrayList.get(getAdapterPosition() - 1).setSelected(false);
                        arrayList.get(getAdapterPosition() - 1).setStudentAttendance("A");
                        mMarkAttendanceListener.markToppings(arrayList);
                    }
                }
            });
        }
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

                        checkedArrayList = new ArrayList<>();
                        for (int i = 0; i < arrayList.size(); i++) {
                            AttendanceListModel attendanceListModel = new AttendanceListModel();
                            attendanceListModel.setStudentId(arrayList.get(i).getStudentId());
                            attendanceListModel.setStudentName(arrayList.get(i).getStudentName());
                            attendanceListModel.setStudentAttendance("P");
                            attendanceListModel.setSelected(true);
                            checkedArrayList.add(attendanceListModel);
                        }
                        checkedist(checkedArrayList);
                        mMarkAttendanceListener.markToppings(checkedArrayList);

                    } else {
                        checkedArrayList = new ArrayList<>();
                        for (int i = 0; i < arrayList.size(); i++) {
                            AttendanceListModel attendanceListModel = new AttendanceListModel();
                            attendanceListModel.setStudentId(arrayList.get(i).getStudentId());
                            attendanceListModel.setStudentName(arrayList.get(i).getStudentName());
                            attendanceListModel.setStudentAttendance("A");
                            attendanceListModel.setSelected(false);
                            checkedArrayList.add(attendanceListModel);
                        }
                        checkedist(checkedArrayList);
                        mMarkAttendanceListener.markToppings(checkedArrayList);
                    }
                }
            });
        }
    }
}
*/
