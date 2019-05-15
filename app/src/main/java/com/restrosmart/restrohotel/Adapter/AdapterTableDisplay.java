package com.restrosmart.restrohotel.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterTableDisplay extends RecyclerView.Adapter<AdapterTableDisplay.MyHolder>{
   private  Context mContext;
   private  ArrayList<TableFormId> arrayListTableId;
   private  StatusListener statusListener;
   private  Dialog dialog;

    public AdapterTableDisplay(Context context, ArrayList<TableFormId> arrayListTableIds, StatusListener statusListener) {
   this.mContext=context;
   this.arrayListTableId=arrayListTableIds;
   this.statusListener=statusListener;

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

            frameLayout.setOnClickListener(new View.OnClickListener() {
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
        }


    }
    public  void refreshList(ArrayList<TableFormId> arrayListTableId1){
        this.arrayListTableId=arrayListTableId1;
        notifyDataSetChanged();
    }
}
