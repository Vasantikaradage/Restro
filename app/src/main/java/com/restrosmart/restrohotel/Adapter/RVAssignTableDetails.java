package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.CaptainTableForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class RVAssignTableDetails extends RecyclerView.Adapter<RVAssignTableDetails.MyHolder> {
    private Context mContext;
    private ArrayList<CaptainTableForm> tableFormArrayList;
    private int staus, mHotelId;
    private RVAssignCaptainTableDetails rvAssignCaptainTableDetails;
    private Menu menuOpts;
    private  EditListener editListener;

    public RVAssignTableDetails(Context context, ArrayList<CaptainTableForm> arrayListTable, EditListener editListener) {
        this.mContext = context;
        this.tableFormArrayList = arrayListTable;
        this.editListener=editListener;
    }

    @NonNull
    @Override
    public RVAssignTableDetails.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_assign_details_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAssignTableDetails.MyHolder myHolder, int i) {
        myHolder.tvCaptainName.setText(tableFormArrayList.get(i).getCaptainName() +" ("+tableFormArrayList.get(i).getArea_Name()+")");
        /*String count = String.valueOf(tableFormArrayList.get(i).getTableCount());
        myHolder.tvTableCount.setText("(" + count + "T" + ")");

        staus = tableFormArrayList.get(i).getArea_Status();
        if (staus == 1) {
            myHolder.imageActive.setVisibility(View.VISIBLE);
            myHolder.imageInActive.setVisibility(View.GONE);
        } else {
            myHolder.imageActive.setVisibility(View.GONE);
            myHolder.imageInActive.setVisibility(View.VISIBLE);
        }
*/

        Sessionmanager sharedPreferanceManage = new Sessionmanager(mContext);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        myHolder.mRecyclerView.setLayoutManager(gridLayoutManager);
        myHolder.mRecyclerView.setHasFixedSize(true);
        myHolder.mRecyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        rvAssignCaptainTableDetails = new RVAssignCaptainTableDetails(mContext, tableFormArrayList.get(i).getArrayTableFormIds());
        myHolder.mRecyclerView.setAdapter(rvAssignCaptainTableDetails);


        myHolder.tvTableOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, myHolder.tvTableOptionMenu);
                //inflating menu from xml resource

                popup.inflate(R.menu.table_option_menu);
                Menu popupMenu = popup.getMenu();
                popupMenu.findItem(R.id.table_inactive).setVisible(false);
                menuOpts = popup.getMenu();
               /* if (tableFormArrayList.get(myHolder.getAdapterPosition()).getArea_Status() == 1) {
                    menuOpts.getItem(1).setVisible(false);
                    menuOpts.getItem(2).setVisible(true);

                } else {
                    menuOpts.getItem(1).setVisible(true);
                    menuOpts.getItem(2).setVisible(false);
                }*/


                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.table_edit:
                                editListener.getEditListenerPosition(myHolder.getAdapterPosition());
                                break;

                          /*  case R.id.table_status:
                                status_info = 1;


                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder
                                        .setTitle("Active Status")
                                        .setMessage("Are you sure you want to Active this Table ?")
                                        *//* .setIcon(R.drawable.ic_action_btn_delete)*//*
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                statusListener.statusListern(myHolder.getAdapterPosition(), status_info);
                                            }

                                           *//* private void removeMenu(int menu_id) {

                                                initRetrofitCallback();
                                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                                mRetrofitService = new RetrofitService(mResultCallBack,context);
                                                mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(menu_id,
                                                        Integer.parseInt ( mHotelId),
                                                        Integer.parseInt(mBranchId))
                                                ));*//*


                                        });


                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                // statusListener.statusListern(i, status_info);
                                break;
*/
                        /*    case R.id.table_inactive:
                                status_info = 0;
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                builder1
                                        .setTitle("Active Status")
                                        .setMessage("Are you sure you want to InActive this Table ?")
                                        .setIcon(R.drawable.ic_action_btn_delete)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                statusListener.statusListern(myHolder.getAdapterPosition(), status_info);
                                            }

                                           *//* private void removeMenu(int menu_id) {

                                                initRetrofitCallback();
                                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                                mRetrofitService = new RetrofitService(mResultCallBack,context);
                                                mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(menu_id,
                                                        Integer.parseInt ( mHotelId),
                                                        Integer.parseInt(mBranchId))
                                                ));*//*


                                        });


                                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert1 = builder1.create();
                                alert1.show();
                                //statusListener.statusListern(i, status_info);
                                break;*/

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
        return tableFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvCaptainName, tvTableCount, tvTableOptionMenu;
        private ImageView imageActive, imageInActive;
        private RecyclerView mRecyclerView;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvCaptainName = itemView.findViewById(R.id.tv_captain_name);
            tvTableCount = itemView.findViewById(R.id.tv_table_count);
            tvTableOptionMenu = itemView.findViewById(R.id.tv_table_option);
            imageActive = itemView.findViewById(R.id.img_active);
            imageInActive = itemView.findViewById(R.id.img_inactive);
            mRecyclerView = itemView.findViewById(R.id.recycler_table_details);
        }
    }
}
