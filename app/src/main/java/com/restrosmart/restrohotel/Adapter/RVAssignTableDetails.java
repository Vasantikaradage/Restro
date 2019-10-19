package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

    public RVAssignTableDetails(Context context, ArrayList<CaptainTableForm> arrayListTable) {
        this.mContext = context;
        this.tableFormArrayList = arrayListTable;
    }

    @NonNull
    @Override
    public RVAssignTableDetails.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_assign_details_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAssignTableDetails.MyHolder myHolder, int i) {
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
