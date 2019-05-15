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
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Model.TableForm;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.UPDATE_TABLE_STATUS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class RVTableDetailsAdapter extends RecyclerView.Adapter<RVTableDetailsAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<TableForm> arrayListTableDeatils;
    private EditListener editListener;
    private int staus, status_info, tableStatus, position, mHotelId, mBranchId;
    private Menu menuOpts;
    private StatusListener statusListener;

    private AdapterTableDisplay adapterTableDisplay;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    //private ArrayList<TableFormId> arrayListTable;
    private ArrayList<TableFormId> arrayListTable;
    private PositionListener positionListener;


    public RVTableDetailsAdapter(Context context, ArrayList<TableForm> arrayListTable, PositionListener positionListener, StatusListener statusListener, EditListener editListener) {
        this.mContext = context;
        this.arrayListTableDeatils = arrayListTable;
        this.editListener = editListener;
        this.statusListener = statusListener;
        this.positionListener = positionListener;
    }

    @NonNull
    @Override
    public RVTableDetailsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_details_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVTableDetailsAdapter.MyHolder myHolder, int i) {
        myHolder.tvAreaName.setText(arrayListTableDeatils.get(i).getAreaName());

        String count = String.valueOf(arrayListTableDeatils.get(i).getTableCount());
        myHolder.tvTableCount.setText("(" + count + "T" + ")");


        staus = arrayListTableDeatils.get(i).getArea_Status();
        if (staus == 1) {
            myHolder.imageActive.setVisibility(View.VISIBLE);
            myHolder.imageInActive.setVisibility(View.GONE);
        } else {
            myHolder.imageActive.setVisibility(View.GONE);
            myHolder.imageInActive.setVisibility(View.VISIBLE);
        }


        Sessionmanager sharedPreferanceManage = new Sessionmanager(mContext);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);


        myHolder.mRecyclerView.setLayoutManager(gridLayoutManager);
        myHolder.mRecyclerView.setHasFixedSize(true);
        myHolder.mRecyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        adapterTableDisplay = new AdapterTableDisplay(mContext, arrayListTableDeatils.get(myHolder.getAdapterPosition()).getArrayTableFormIds(), new StatusListener() {
            @Override
            public void statusListern(int position1, int status) {

                initRetrofitCallBack(arrayListTableDeatils.get(myHolder.getAdapterPosition()).getArrayTableFormIds());
                tableStatus = status;
                position = position1;

                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, mContext);
                mRetrofitService.retrofitData(UPDATE_TABLE_STATUS, service.TableStatus(tableStatus, arrayListTableDeatils.get(myHolder.getAdapterPosition()).getArrayTableFormIds().get(position).getTableId(), mHotelId,
                        mBranchId));
            }
        });

        myHolder.mRecyclerView.setAdapter(adapterTableDisplay);


        myHolder.tvTableOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, myHolder.tvTableOptionMenu);
                //inflating menu from xml resource

                popup.inflate(R.menu.table_option_menu);

                menuOpts = popup.getMenu();
                if (arrayListTableDeatils.get(myHolder.getAdapterPosition()).getArea_Status() == 1) {
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
                                editListener.getEditListenerPosition(myHolder.getAdapterPosition());
                                break;

                            case R.id.table_status:
                                status_info = 1;

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder
                                        .setTitle("Active Status")
                                        .setMessage("Are you sure you want to Active this Table ?")
                                        .setIcon(R.drawable.ic_action_btn_delete)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                statusListener.statusListern(myHolder.getAdapterPosition(), status_info);
                                            }

                                           /* private void removeMenu(int menu_id) {

                                                initRetrofitCallback();
                                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                                mRetrofitService = new RetrofitService(mResultCallBack,context);
                                                mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(menu_id,
                                                        Integer.parseInt ( mHotelId),
                                                        Integer.parseInt(mBranchId))
                                                ));*/


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

                            case R.id.table_inactive:
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

                                           /* private void removeMenu(int menu_id) {

                                                initRetrofitCallback();
                                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                                mRetrofitService = new RetrofitService(mResultCallBack,context);
                                                mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(menu_id,
                                                        Integer.parseInt ( mHotelId),
                                                        Integer.parseInt(mBranchId))
                                                ));*/


                                        });


                                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert1 = builder1.create();
                                alert1.show();
                                //statusListener.statusListern(i, status_info);
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

    private void initRetrofitCallBack(ArrayList<TableFormId> arrayListids) {
        arrayListTable = arrayListids;
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId) {
                    case UPDATE_TABLE_STATUS:

                        JsonObject object = response.body();
                        String value = object.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(value);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(mContext, "Table Status Updated Successfully", Toast.LENGTH_LONG).show();


                                arrayListTable.get(position).setTableStatus(tableStatus);
                                adapterTableDisplay.notifyDataSetChanged();
                                positionListener.positionListern(position);
                                // adapterTableDisplay.refreshList(arrayListTable);
                            } else {
                                Toast.makeText(mContext, "Try Again..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }


    @Override
    public int getItemCount() {
        return arrayListTableDeatils.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvAreaName, tvTableCount, tvTableOptionMenu;
        private ImageView imageActive, imageInActive;
        private RecyclerView mRecyclerView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvAreaName = itemView.findViewById(R.id.tv_area_name);
            tvTableCount = itemView.findViewById(R.id.tv_table_count);
            tvTableOptionMenu = itemView.findViewById(R.id.tv_table_option);
            imageActive = itemView.findViewById(R.id.img_active);
            imageInActive = itemView.findViewById(R.id.img_inactive);
            mRecyclerView = itemView.findViewById(R.id.recycler_table_details);
        }
    }
}
