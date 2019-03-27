package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Interfaces.DeleteResult;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.OfferNameForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitService;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;


/**
 * Created by SHREE on 01/10/2018.
 */

public class DailyOffersAdapter extends RecyclerView.Adapter<DailyOffersAdapter.MyHolderView> {
    Context context;
    ArrayList<OfferNameForm> offerNameForms;
    RetrofitService retrofitService;
    IResult iResult;
    DeleteResult deleteResult;

   // ArrayList<String> arrayOfferList=new ArrayList<>();

    public DailyOffersAdapter(Context context, ArrayList<OfferNameForm> arrayList) {
   this.context=context;
   this.offerNameForms=arrayList;
   //this.deleteResult=deleteResult;

    }


    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_daily_offer_listview, parent, false);
        return new MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, final int position) {

       holder.offerName.setText(offerNameForms.get(position).getOfferName());
        /*holder.mMenuName.setText(offerForms.get(position).getMenu_Name());
        holder.mMenuDiscrip.setText(offerForms.get(position).getMenu_Descrip());
        Picasso.with(context)
                .load(offerForms.get(position).getMenu_Image_Name())
                .resize(500,500)
                .into(holder.mImageView);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setTitle("Delete Menu")
                        .setMessage("Are you sure you want to delete this Menu ?")
                        .setIcon(R.drawable.ic_action_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        removeOffer(offerForms.get(position).getOffer_Id());

                                    }

                            private void removeOffer(int offer_id) {

                                initRetrofitCallBack(position);
                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                retrofitService = new RetrofitService(iResult, context);
                                retrofitService.retrofitData(DELETE_OFFER, service.getOfferDelete(offer_id));

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

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }



    private void initRetrofitCallBack(final int position) {
        iResult=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                Toast.makeText(context,"Offer Menu Deleted Successfully",Toast.LENGTH_LONG).show();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, offerNameForms.size());

                notifyDataSetChanged();
                deleteResult.getDeleteInfoCallBack();
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    @Override
    public int getItemCount() {
        return offerNameForms.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        CircleImageView mImageView;
        TextView  mMenuName,mMenuDiscrip;

        ImageButton btnEdit,btnDelete;

        RecyclerView recyclerView;

        TextView offerName;

        public MyHolderView(View itemView) {
            super(itemView);
            offerName=(TextView)itemView.findViewById(R.id.tx_offer_name) ;
         //   recyclerView=(RecyclerView)itemView.findViewById(R.id.recycler_offer) ;
          /*  mImageView=(CircleImageView) itemView.findViewById(R.id.circle_image);
            mMenuName=(TextView)itemView.findViewById(R.id.tx_menu_name);
            mMenuDiscrip=(TextView)itemView.findViewById(R.id.tx_menu_disp);
            btnEdit=(ImageButton)itemView.findViewById(R.id.btn_edit);
            btnDelete=(ImageButton)itemView.findViewById(R.id.btn_delete) ;*/
          /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
                    recyclerView.setHasFixedSize(true);

                    DailyOfferListAdapter dailyOfferListAdapter=new DailyOfferListAdapter(context,offerNameForms);
                    recyclerView.setAdapter(dailyOfferListAdapter);
                 //   Intent intent = new Intent(context,Activity_Category_Menu.class);
                  //  context.startActivity(intent);

                  //  Toast.makeText(context, "ye na baba", Toast.LENGTH_SHORT).show();
                }
            });*/
        }



    }
}
