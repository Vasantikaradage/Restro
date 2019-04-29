package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityTableInformation;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.TableForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVTableDetailsAdapter extends RecyclerView.Adapter<RVTableDetailsAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<TableForm> arrayListTableDeatils;
    private EditListener editListener;

    public RVTableDetailsAdapter(Context context, ArrayList<TableForm> arrayListTable, EditListener editListener) {
        this.mContext = context;
        this.arrayListTableDeatils = arrayListTable;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public RVTableDetailsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_details_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVTableDetailsAdapter.MyHolder myHolder, final int i) {
        myHolder.tvAreaName.setText(arrayListTableDeatils.get(i).getAreaName());

        String count = String.valueOf(arrayListTableDeatils.get(i).getTableCount());
        myHolder.tvTableCount.setText(count);

        myHolder.tvTableOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, myHolder.tvTableCount);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                editListener.getEditListenerPosition(i);
                                //handle menu1 click
                                return true;

                            case R.id.menu_delete:
                                 /*//handle menu2 click
                                 AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                 builder
                                         .setTitle("T")
                                         .setMessage("Are you sure you want to delete this Topping ?")
                                         .setIcon(R.drawable.ic_action_btn_delete)
                                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                             public void onClick(DialogInterface dialog, int which) {
                                                 deleteListener.getDeleteListenerPosition(i);

                                             }
                                         });


                                 builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.dismiss();
                                     }
                                 });

                                 AlertDialog alert = builder.create();
                                 alert.show();
                                 return true;*/
                            default:
                                return false;

                        }

                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListTableDeatils.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvAreaName, tvTableCount, tvTableOptionMenu;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvAreaName = itemView.findViewById(R.id.tv_area_name);
            tvTableCount = itemView.findViewById(R.id.tv_table_count);
            tvTableOptionMenu = itemView.findViewById(R.id.tv_table_option);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ActivityTableInformation.class);
                    //  intent.putExtra("totalCount",arrayListTableDeatils.get(getAdapterPosition()).getTableCount());
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
