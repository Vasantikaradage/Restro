package com.restrosmart.restrohotel.Adapter;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Interfaces.TableMoveListerner;
import com.restrosmart.restrohotel.Model.TableForm;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.SWAP_TABLE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class AdapterTableDisplay extends RecyclerView.Adapter<AdapterTableDisplay.MyHolder>{
   private  Context mContext;
   private  ArrayList<TableFormId> arrayListTableId;
   private  ArrayList<TableForm>arrayListTableDetails;
   private  StatusListener statusListener;
   private  Dialog dialog,dialogBtnOk;
   private  int oldAreaId,newAreaId,branchId,hotelId;
    private BottomSheetDialog bottomSheetDialog;
    private  String selectedArea,oldAreaName;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    private  TableMoveListerner tableMoveListerner;

    public AdapterTableDisplay(Context context, String oldAreaName, int oldAreaId, ArrayList<TableFormId> arrayListTableIds, ArrayList<TableForm> arrayListTableDeatils, TableMoveListerner tableMoveListerner, StatusListener statusListener) {
   this.mContext=context;
   this.arrayListTableId=arrayListTableIds;
   this.oldAreaName=oldAreaName;
   this.statusListener=statusListener;
   this.oldAreaId=oldAreaId;
   this.arrayListTableDetails=arrayListTableDeatils;
   this.tableMoveListerner=tableMoveListerner;

       bottomSheetDialog = new BottomSheetDialog(mContext);
      //  mContext.registerReceiver(mSwapTableReceiver, new IntentFilter("com.restrohotel.admin.swap.table"));

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
    public AdapterTableDisplay.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTableDisplay.MyHolder myHolder, final int i) {

      /*  Picasso.with(mContext).
                load(R.drawable.ic_table_green)
                .resize(500, 500).
                       into(myHolder.imageView);*/
        String id = String.valueOf(arrayListTableId.get(i).getTableId());
      myHolder.textView.setText(id);
      if(arrayListTableId.get(i).getTableStatus()==1) {

          myHolder.imageView.setBackgroundResource(R.drawable.ic_table_green);
      }
      else
      {
          myHolder.imageView.setBackgroundResource(R.drawable.ic_table_gray);
      }


    }

    @Override
    public int getItemCount() {
        return arrayListTableId.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private FrameLayout frameLayout;



        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.table_image);
            textView=itemView.findViewById(R.id.tv_table_no);
            frameLayout =itemView.findViewById(R.id.frameLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.dialog_active_table);

                    // set the custom dialog components - text, image and button
                    ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
                    ImageView ivActive=dialog.findViewById(R.id.ivActiveIcon);
                    ImageView ivInActive=dialog.findViewById(R.id.ivInActvieIcon);
                    RelativeLayout tvActive = dialog.findViewById(R.id.tvActive);
                    RelativeLayout tvInActive = dialog.findViewById(R.id.tvInActive);

                    tvActive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            statusListener.statusListern(getAdapterPosition(),1);
                            dialog.dismiss();
                        }
                    });

                    tvInActive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            statusListener.statusListern(getAdapterPosition(),0);
                            dialog.dismiss();

                        }
                    });



                    ivCloseDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    if(arrayListTableId.get(getAdapterPosition()).getTableStatus()==1)
                    {
                        ivActive.setVisibility(View.VISIBLE);
                        ivInActive.setVisibility(View.GONE);
                    }
                    else
                    {
                        ivActive.setVisibility(View.GONE);
                        ivInActive.setVisibility(View.VISIBLE);
                    }
                    dialog.show();

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.dialog_admin_free_table, null);
                    bottomSheetDialog.setContentView(view);

                    final SearchableSpinner spArea=view.findViewById(R.id.sp_area);
                    ImageView ivClose=view.findViewById(R.id.ivCloseDialog);
                    Button btnOk=view.findViewById(R.id.btn_ok);

                    ArrayAdapter<TableForm> tableFormArrayAdapter=new ArrayAdapter<TableForm>(mContext,android.R.layout.simple_spinner_item,arrayListTableDetails);
                    tableFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spArea.setAdapter(tableFormArrayAdapter);

                    spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedArea=spArea.getSelectedItem().toString();
                            newAreaId=arrayListTableDetails.get(i).getAreaId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog.dismiss();
                        }
                    });

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialogBtnOk = new Dialog(mContext);
                            dialogBtnOk.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogBtnOk.setCancelable(false);
                            dialogBtnOk.setCanceledOnTouchOutside(false);
                            dialogBtnOk.setContentView(R.layout.dialog_move_area);

                            TextView tvOldArea=dialogBtnOk.findViewById(R.id.tvOldArea);
                            TextView tvNewArea=dialogBtnOk.findViewById(R.id.tvNewArea);
                            TextView tvTableId=dialogBtnOk.findViewById(R.id.tv_table_id);
                            Button btnMoveTable=dialogBtnOk.findViewById(R.id.btnMove);
                            Button btnCancel=dialogBtnOk.findViewById(R.id.btnCancel);

                           tvOldArea.setText(oldAreaName);
                            tvNewArea.setText(selectedArea);
                            String tableId= String.valueOf(arrayListTableId.get(getAdapterPosition()).getTableId());
                            tvTableId.setText(tableId);

                            sessionmanager = new Sessionmanager(mContext);
                            HashMap<String, String> name_info = sessionmanager.getHotelDetails();
                            hotelId = Integer.parseInt(name_info.get(HOTEL_ID));

                            btnMoveTable.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    tableMoveListerner.tableMoveListerner(oldAreaId,newAreaId,arrayListTableId.get(getAdapterPosition()).getTableId(),getAdapterPosition());
                                    dialogBtnOk.dismiss();
                                    bottomSheetDialog.dismiss();
                                }
                            });
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogBtnOk.dismiss();
                                }
                            });

                            dialogBtnOk.show();
                        }
                    });
                    bottomSheetDialog.show();
                    return true;
                }
            });
        }
    }

    public  void refreshList(ArrayList<TableFormId> arrayListTableId1){
        this.arrayListTableId=arrayListTableId1;
        notifyDataSetChanged();
    }
}
