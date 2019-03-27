package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.Category;
import com.restrosmart.restro.Interfaces.DeleteResult;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.MenuDisplayForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.MENU_DELETE;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;

public class AdapterDisplayAllMenus extends RecyclerView.Adapter<AdapterDisplayAllMenus.MyHolder> {

    Context context;

    ArrayList<MenuDisplayForm> arrayListMenu;

     RetrofitService mRetrofitService;
     IResult mResultCallBack;
     Category resultPosition;
     DeleteResult  deleteResult;
     private  String mHotelId,mBranchId;

     private EditText etxMenuNme,etxMenuPrice,etxMenuDiscrp;
     private CircleImageView circleImageView1;

   /* public AdapterDisplayAllMenus(Context context, ArrayList<MenuDisplayForm> arrayListMenu, Category  resultPosition) {
        this.context = context;
        this.arrayListMenu = arrayListMenu;
        this.resultPosition=resultPosition;
    }*/

    public AdapterDisplayAllMenus(Context context, DeleteResult deleteResult, ArrayList<MenuDisplayForm> arrayListMenu, Category category) {
        this.context = context;
        this.deleteResult=deleteResult;
        this.arrayListMenu = arrayListMenu;
        this.resultPosition=category;

    }

    @NonNull
    @Override
    public AdapterDisplayAllMenus.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_menu_list, parent, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayAllMenus.MyHolder holder, final int position) {

        Picasso.with(context).load(arrayListMenu.get(position).getMenu_Image_Name())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(holder.image);

        holder.mMenuName.setText(arrayListMenu.get(position).getMenu_Name());
        holder.mMenuDisp.setText(arrayListMenu.get(position).getMenu_Descrip());

        int mTeste=arrayListMenu.get(position).getMenu_Test();

        if(mTeste==1)
        {
            holder.mMenuTeste.setVisibility(View.VISIBLE);
            holder.mMenuTeste.setText("(Sweet)");
        }
        else if(mTeste==2)
        {
            holder.img_spicy.setVisibility(View.VISIBLE);
        }
        else
        {

        }



        Sessionmanager sharedPreferanceManage = new Sessionmanager(context);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();

        mHotelId=name_info.get(HOTEL_ID);
        mBranchId=name_info.get(BRANCH_ID);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    // final int pos = position;
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                    builder
                                                            .setTitle("Delete Menu")
                                                            .setMessage("Are you sure you want to delete this Menu ?")
                                                            .setIcon(R.drawable.ic_action_btn_delete)
                                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    removeMenu(arrayListMenu.get(position).getMenu_Id());

                                                                }

                                                                private void removeMenu(int menu_id) {

                                                                    initRetrofitCallback();
                                                                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                                                    mRetrofitService = new RetrofitService(mResultCallBack,context);
                                                                    mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(menu_id,
                                                                           Integer.parseInt ( mHotelId),
                                                                            Integer.parseInt(mBranchId))
                                                                           ));

                                                                }

                                                                private void initRetrofitCallback() {
                                                                    mResultCallBack = new IResult() {
                                                                        @Override
                                                                        public void notifySuccess(int requestId, Response<JsonObject> response) {
                                                                            JsonObject jsonObject=response.body();

                                                                            String value=jsonObject.toString();

                                                                            try {
                                                                                JSONObject object=new JSONObject(value);
                                                                                int status=object.getInt("status");
                                                                                if(status==1)
                                                                                {
                                                                                    Toast.makeText(context,"Menu Deleted Successfully",Toast.LENGTH_LONG).show();
                                                                                    notifyItemRemoved(position);
                                                                                    notifyItemRangeChanged(position, arrayListMenu.size());
                                                                                    notifyDataSetChanged();
                                                                                    deleteResult.getDeleteInfoCallBack();


                                                                                }
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }


                                                                        }

                                                                        @Override
                                                                        public void notifyError(int requestId, Throwable error) {

                                                                        }
                                                                    };
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

                                            }
        );



        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultPosition.categoryListern(position);

               /*  Intent intent=new Intent(context,AddMenu.class);
                 intent.putExtra("menuId",arrayListMenu.get(position).getMenu_Id());
                intent.putExtra("menuName",arrayListMenu.get(position).getMenu_Name());
                intent.putExtra("menuAcRate",arrayListMenu.get(position).getAc_Rate());
                intent.putExtra("menuNonAcRate",arrayListMenu.get(position).getNon_Ac_Rate());
                intent.putExtra("menuDiscription",arrayListMenu.get(position).getMenu_Descrip());
                intent.putExtra("menuImage",arrayListMenu.get(position).getMenu_Image_Name());
                context.startActivity(intent);*/



            }
        });


    }







    @Override
    public int getItemCount() {
        return arrayListMenu.size();
    }


    public static class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mMenuName,mMenuDisp,mMenuTeste;
        ImageButton btnEdit,btnDelete;
        private CircleImageView image;
        private  ImageView img_spicy;


        public MyHolder(View itemView) {
            super(itemView);

            image = (CircleImageView) itemView.findViewById(R.id.circle_image);
            mMenuName=(TextView)itemView.findViewById(R.id.tx_menu_name);
            mMenuDisp=(TextView)itemView.findViewById(R.id.tx_menu_disp);
            btnEdit=(ImageButton)itemView.findViewById(R.id.edit_button);
            btnDelete=(ImageButton)itemView.findViewById(R.id.delete_button);
            mMenuTeste=(TextView)itemView.findViewById(R.id.menu_test);
            img_spicy=(ImageView)itemView.findViewById(R.id.img_spicy);

        }
    }
}
