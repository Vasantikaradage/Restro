package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Admin.ActivityMenu;
import com.restrosmart.restro.Interfaces.Category;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.AddImage;
import com.restrosmart.restro.Model.CategoryForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 11/10/2018.
 */

public class AdapterDisplayAllCategory extends RecyclerView.Adapter<AdapterDisplayAllCategory.MyHolder>{
    Context context;
    List<CategoryForm> arrayList;
    int catId;
    int mEdt_catId;
    private String mEdt_catName;

    private IResult mIResultCallBack;
    private RetrofitService mRetrofitService;

    private RecyclerView image_recyclerview;

    private  String mHotelId,mBranchId,mImageName,image_name;
    ArrayList<AddImage> arrayListImage=new ArrayList<AddImage>();
  


    public AdapterDisplayAllCategory(Context activity, ArrayList<CategoryForm> arrayList) {
        this.context=activity;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_list, parent, false);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.tx_name.setText(arrayList.get(position).getCategory_Name());


        Picasso.with(context)
                .load(arrayList.get(position).getC_Image_Name())
                .resize(500,500)
                .into(holder.circleImageView);

        Sessionmanager sharedPreferanceManage = new Sessionmanager(context);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();

        mHotelId=name_info.get(HOTEL_ID);
        mBranchId=name_info.get(BRANCH_ID);
    }

    private void getImage(final ArrayList<AddImage> images) {

        image_recyclerview.setVisibility(View.VISIBLE);
        image_recyclerview.setHasFixedSize(true);
        int no_of_col = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        RecyclerViewImageAdapter adapter = new RecyclerViewImageAdapter(context, images,mImageName ,new Category() {

            @Override
            public void categoryListern(int position) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                image_name = images.get(position).getImage();
                //  mImageName = image_name.substring(image_name.lastIndexOf('/') + 1);
                int start = image_name.indexOf("t/");
                String suffix = image_name.substring(start + 1);
                int start1 = suffix.indexOf("/");
                mImageName = suffix.substring(start1 + 1);
            }
        });

        image_recyclerview.setLayoutManager(gridLayoutManager);
        image_recyclerview.setAdapter(adapter);
    }


    private void InitRetrofitCallback() {

        mIResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {


                JsonObject jsonObject = response.body();
                Intent deleteIntent = new Intent();
                deleteIntent.setAction("cat_Delete");
                context.sendBroadcast(deleteIntent);

            }

            @Override
            public void notifyError(int requestId, Throwable error) {

                Toast.makeText(context, "failed to delete" + error, Toast.LENGTH_SHORT).show();

            }
        };
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


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tx_name;

        CircleImageView circleImageView;
        ImageButton btn_delete, btn_edit;
        LinearLayout swipeRevealLayout;

        public MyHolder(final View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.circle_image);
            tx_name = (TextView) itemView.findViewById(R.id.itemname1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context,ActivityMenu.class);
                    intent.putExtra("Category_Id",arrayList.get(getLayoutPosition()).getCategory_id());
                    intent.putExtra("Category_Name",arrayList.get(getLayoutPosition()).getCategory_Name());
                    intent.putExtra("Category_image",arrayList.get(getLayoutPosition()).getC_Image_Name());
                    context.startActivity(intent);

                  //  Toast.makeText(context, "ye na baba", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
