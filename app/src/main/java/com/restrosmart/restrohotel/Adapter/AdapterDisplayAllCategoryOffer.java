package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_SUB;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class AdapterDisplayAllCategoryOffer extends RecyclerView.Adapter<AdapterDisplayAllCategoryOffer.MyHolder> {
    private Context context;
    private List<CategoryForm> arrayList;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;


    private  Sessionmanager sessionmanager;
    private  int hotelId;


    private  MyHolder myHolder;
    private  int pos;
    private int mExpandedPosition = -1, previousExpandedPosition;

    private ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<MenuDisplayForm>  arrayListMenu;
    private  int winnerQty,buyQty,getQty,offerTypeId;



    public AdapterDisplayAllCategoryOffer(Context activity, ArrayList<CategoryForm> arrayList, int winnerQty, int buyQty, int getQty,int offerTypeId) {
        this.context = activity;
        this.arrayList = arrayList;
        this.winnerQty=winnerQty;
        this.buyQty=buyQty;
        this.getQty=getQty;
        this.offerTypeId=offerTypeId;

        for (int i = 0; i < arrayList.size(); i++) {
            counter.add(0);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category_itemlist_offer, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.tx_name.setText(arrayList.get(position).getCategory_Name());
        arrayListMenu =arrayList.get(position).getMenuDisplayFormArrayList();
       /* ArrayList<MenuDisplayForm>  arrayListMenu=new ArrayList<MenuDisplayForm>();
        arrayListMenu =arrayList.get(position).getMenuDisplayFormArrayList();
*/
         if(arrayListMenu.size()>0 && arrayListMenu!=null) {

             LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
             holder.rvMenu.setHasFixedSize(true);
             holder.rvMenu.setLayoutManager(linearLayoutManager);

             AdapterDisplayAllMenusOffer displayAllMenus = new AdapterDisplayAllMenusOffer(context, arrayListMenu,winnerQty,buyQty,getQty,offerTypeId,arrayList.get(position).getCategory_Name());
             holder.rvMenu.setAdapter(displayAllMenus);
         }
         else
         {
             holder.linearLayout.setVisibility(View.GONE);

         }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private  TextView tx_name;
        private  CircleImageView circleImageView;
        private ImageView ivArrow ;
        private  RecyclerView rvMenu;
        private LinearLayout linearLayout;

        public MyHolder(final View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.circle_image);
            tx_name = (TextView) itemView.findViewById(R.id.tv_category_name);
            ivArrow=itemView.findViewById(R.id.ivArrow);
            rvMenu=itemView.findViewById(R.id.rvMenu);
            linearLayout=itemView.findViewById(R.id.linear_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter.get(getAdapterPosition()) % 2 == 0) {
                        if(arrayList.get(getAdapterPosition()).getMenuDisplayFormArrayList().size()>0 && arrayList.get(getAdapterPosition()).getMenuDisplayFormArrayList()!=null) {
                            rvMenu.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);

                        }else
                        {
                            linearLayout.setVisibility(View.GONE);
                            Toast.makeText(context,"No Record Found",Toast.LENGTH_SHORT).show();
                        }
                        ivArrow.setImageDrawable(context.getDrawable(R.drawable.ic_up_arrow_16dp));
                    } else {
                        rvMenu.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        ivArrow.setImageDrawable(context.getDrawable(R.drawable.ic_down_arrow_16dp));
                    }

                    counter.set(getAdapterPosition(), counter.get(getAdapterPosition()) + 1);
                }
            });
        }
    }
}
