package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.restrosmart.restrohotel.Admin.ActivityTableInformation;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Model.TableForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVTableDetailsAdapter extends RecyclerView.Adapter<RVTableDetailsAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<TableForm> arrayListTableDeatils;
    private EditListener editListener;
    private int staus, status_info;
    private Menu menuOpts;
    private StatusListener statusListener;

    public RVTableDetailsAdapter(Context context, ArrayList<TableForm> arrayListTable, StatusListener statusListener, EditListener editListener) {
        this.mContext = context;
        this.arrayListTableDeatils = arrayListTable;
        this.editListener = editListener;
        this.statusListener = statusListener;
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

        staus = arrayListTableDeatils.get(i).getArea_Status();
        if (staus == 1) {
            myHolder.imageActive.setVisibility(View.VISIBLE);
            myHolder.imageInActive.setVisibility(View.GONE);
        } else {
            myHolder.imageActive.setVisibility(View.GONE);
            myHolder.imageInActive.setVisibility(View.VISIBLE);
        }

        myHolder.tvTableOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, myHolder.tvTableOptionMenu);
                //inflating menu from xml resource

                popup.inflate(R.menu.table_option_menu);

                menuOpts = popup.getMenu();
                if (arrayListTableDeatils.get(i).getArea_Status() == 1) {
                    menuOpts.getItem(1).setVisible(false);
                    menuOpts.getItem(2).setVisible(true);

                } else {
                    menuOpts.getItem(1).setVisible(true);
                    menuOpts.getItem(2).setVisible(false);
                }


                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.table_edit:
                                editListener.getEditListenerPosition(i);
                                break;

                            case R.id.table_status:
                                status_info = 1;
                                statusListener.statusListern(i, status_info);
                                break;
                            case R.id.table_inactive:
                                status_info = 0;
                                statusListener.statusListern(i, status_info);
                                break;

                            default:
                                return false;

                        }

                        return true;
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
        private ImageView imageActive, imageInActive;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvAreaName = itemView.findViewById(R.id.tv_area_name);
            tvTableCount = itemView.findViewById(R.id.tv_table_count);
            tvTableOptionMenu = itemView.findViewById(R.id.tv_table_option);
            imageActive = itemView.findViewById(R.id.img_active);
            imageInActive = itemView.findViewById(R.id.img_inactive);


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
