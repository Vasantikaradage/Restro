package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Admin.ActivityAddNewOffer;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteResult;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.OfferForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.DELETE_OFFER;
import static com.restrosmart.restrohotel.ConstantVariables.OFF_OFFER;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;


/**
 * Created by SHREE on 01/10/2018.
 */

public class DailyOffersAdapter extends RecyclerView.Adapter<DailyOffersAdapter.MyHolderView> {
    Context context;
    ArrayList<OfferForm> offerForms;
    private RetrofitService retrofitService;
    private IResult iResult;
    private DeleteResult deleteResult;
    private View dialoglayout;
    private Sessionmanager sessionmanager;
    private  String hotelId,branchId;
    private  int status_value;
    private AlertDialog dialog;



    public DailyOffersAdapter(Context context, ArrayList<OfferForm> arrayList,DeleteResult deleteResult) {
        this.context=context;
        this.offerForms=arrayList;
        this.deleteResult=deleteResult;

    }


    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_daily_offer_listview, parent, false);
        return new MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolderView holder, final int position) {

        sessionmanager = new Sessionmanager(context);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();

        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);

        Picasso.with(context)
                .load(offerForms.get(position).getMenu_Image_Name())
                .resize(500,500)
                .into(holder.mImageView);

        holder.offerName.setText(offerForms.get(position).getOffer_Name());
        holder.txOfferBy.setText(offerForms.get(position).getMenu_Name());
        holder.txOfferValue.setText(offerForms.get(position).getOffer_Value());
        int status=offerForms.get(position).getStatus();
        if(status==1){
            holder.switchStatus.setChecked(true);
        }
        else {
            holder.switchStatus.setChecked(false);
        }


        holder.switchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.switchStatus.isChecked()) {
                    status_value = 1;
                } else {
                    status_value = 0;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setTitle("Offer Status")
                        .setMessage("Are you sure you want to off this offer?")
                        .setIcon(R.drawable.ic_action_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeOffer(offerForms.get(position).getOffer_Id());

                            }

                            private void removeOffer(int offer_id) {

                                initRetrofitCallBack(position);
                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                retrofitService = new RetrofitService(iResult, context);
                                retrofitService.retrofitData(OFF_OFFER, service.offOffer(offer_id,
                                        Integer.parseInt(hotelId),
                                        Integer.parseInt(branchId),
                                        status_value));
                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });


    }



    private void initRetrofitCallBack(final int position) {
        iResult=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId) {
                    case DELETE_OFFER:
                        Toast.makeText(context, "Offer Menu Deleted Successfully", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, offerForms.size());
                        notifyDataSetChanged();
                        deleteResult.getDeleteInfoCallBack();
                        break;

                    case OFF_OFFER:

                        JsonObject jsonObject=response.body();
                        String value=jsonObject.toString();
                        Toast.makeText(context, "Offer Menu off Successfully", Toast.LENGTH_LONG).show();
                        deleteResult.getDeleteInfoCallBack();
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
        return offerForms.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        private CircleImageView mImageView;
        private TextView  mMenuName,mMenuDiscrip;
        private ImageButton btnEdit,btnDelete;
        private TextView offerName,txOfferBy,txOfferValue;
        private Switch switchStatus;

        public MyHolderView(View itemView) {
            super(itemView);
            offerName=(TextView)itemView.findViewById(R.id.tx_offer_name) ;
            txOfferBy=(TextView)itemView.findViewById(R.id.tx_offer_by);
            txOfferValue=(TextView)itemView.findViewById(R.id.tx_offer_value);
            mImageView=(CircleImageView) itemView.findViewById(R.id.circle_image);
            switchStatus=(Switch)itemView.findViewById(R.id.swStatus) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    dialoglayout = li.inflate(R.layout.alert_dailog_offer_details, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(dialoglayout);
                    dialog = builder.create();

                    final int pos=getAdapterPosition();


                    TextView tvOfferName= dialoglayout.findViewById(R.id.tv_offer_name);
                    TextView tvFromDate=dialoglayout.findViewById(R.id.tv_from_date);
                    TextView  tvToDate=dialoglayout.findViewById(R.id.tv_to_date);
                    TextView  tvFromTime=dialoglayout.findViewById(R.id.tv_from_time);
                    TextView tvToTime=dialoglayout.findViewById(R.id.tv_to_time);
                    TextView tvOfferValue=dialoglayout.findViewById(R.id.tv_offer_value);
                    TextView tvOfferBy=dialoglayout.findViewById(R.id.tv_offer_by);

                    Button btnOfferEdit=dialoglayout.findViewById(R.id.btnEdit);
                    Button btnOfferCancel=dialoglayout.findViewById(R.id.btnCancel);
                    Button btnOfferDelete=dialoglayout.findViewById(R.id.btnDelete);


                    tvOfferName.setText(offerForms.get(pos).getOffer_Name());
                    tvFromDate.setText(offerForms.get(pos).getOffer_From());
                    tvToDate.setText(offerForms.get(pos).getOffer_To());
                    tvOfferBy.setText(offerForms.get(pos).getMenu_Name());
                    tvOfferValue.setText(offerForms.get(pos).getOffer_Value());

                    btnOfferEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=new Intent(context, ActivityAddNewOffer.class);
                            intent.putExtra("offerId", offerForms.get(getAdapterPosition()).getOffer_Id());
                            intent.putExtra("offerName",offerForms.get(getAdapterPosition()).getOffer_Name());
                            intent.putExtra("fromDate",offerForms.get(getAdapterPosition()).getOffer_From());
                            intent.putExtra("toDate",offerForms.get(getAdapterPosition()).getOffer_To());
                            intent.putExtra("offerValue",offerForms.get(getAdapterPosition()).getOffer_Value());
                            intent.putExtra("offerStatus",offerForms.get(getAdapterPosition()).getStatus());
                            intent.putExtra("offerBy",offerForms.get(getAdapterPosition()).getMenu_Name());
                            context.startActivity(intent);

                        }
                    });

                    btnOfferCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btnOfferDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder
                                    .setTitle("Offer Delete")
                                    .setMessage("Are you sure you want to Delete this offer?")
                                    .setIcon(R.drawable.ic_action_delete)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            removeOffer(offerForms.get(getAdapterPosition()).getOffer_Id());

                                        }
                                        private void removeOffer(int offer_id) {

                                            initRetrofitCallBack(getAdapterPosition());
                                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                            retrofitService = new RetrofitService(iResult, context);
                                            retrofitService.retrofitData(DELETE_OFFER, service.deleteOffer(offer_id,
                                                    Integer.parseInt(hotelId),
                                                    Integer.parseInt(branchId)));
                                        }
                                    });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
