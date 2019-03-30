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
import android.widget.ImageView;
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
public class AdapterDisplayAllCategory extends RecyclerView.Adapter<AdapterDisplayAllCategory.MyHolder> {
    private  Context context;
    private  List<CategoryForm> arrayList;

    public AdapterDisplayAllCategory(Context activity, ArrayList<CategoryForm> arrayList) {
        this.context = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.tx_name.setText(arrayList.get(position).getCategory_Name());
        Picasso.with(context)
                .load(arrayList.get(position).getC_Image_Name())
                .transform( new CircleTransform())
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
        TextView tx_name;
        ImageView circleImageView;

        public MyHolder(final View itemView) {
            super(itemView);

            circleImageView = (ImageView) itemView.findViewById(R.id.circle_image);
            tx_name = (TextView) itemView.findViewById(R.id.itemname1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityMenu.class);
                    intent.putExtra("Pc_Id",arrayList.get(getLayoutPosition()).getPc_Id());
                    intent.putExtra("Category_Id", arrayList.get(getLayoutPosition()).getCategory_id());
                    intent.putExtra("Category_Name", arrayList.get(getLayoutPosition()).getCategory_Name());
                    intent.putExtra("Category_image", arrayList.get(getLayoutPosition()).getC_Image_Name());
                    context.startActivity(intent);

                }
            });
        }
    }
}
