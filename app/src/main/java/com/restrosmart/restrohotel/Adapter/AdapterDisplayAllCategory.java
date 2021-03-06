package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityMenu;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SHREE on 11/10/2018.
 */
public class AdapterDisplayAllCategory extends RecyclerView.Adapter<AdapterDisplayAllCategory.MyHolder> {
    private Context context;
    private List<CategoryForm> arrayList;
    private EditListener editListener;
    private DeleteListener deleteListener;


    public AdapterDisplayAllCategory(Context activity, ArrayList<CategoryForm> arrayList, DeleteListener deleteListener, EditListener editListener) {
        this.context = activity;
        this.arrayList = arrayList;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category_itemlist, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.tx_name.setText(arrayList.get(position).getCategory_Name());
        Picasso.with(context)
                .load(arrayList.get(position).getC_Image_Name())
                .transform(new CircleTransform())
                .into(holder.circleImageView);


        holder.imagBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setTitle("Delete Category")
                        .setMessage("Are you sure you want to delete this category ?")
                        .setIcon(R.drawable.ic_action_btn_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteListener.getDeleteListenerPosition(holder.getAdapterPosition());

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
        ImageButton imgBtnEdit, imagBtnDelete;
        // Button btnCategory;

        public MyHolder(final View itemView) {
            super(itemView);

            circleImageView = (ImageView) itemView.findViewById(R.id.circle_image);
            tx_name = (TextView) itemView.findViewById(R.id.tv_category_name);
            imgBtnEdit = itemView.findViewById(R.id.btn_edit_button);
            imagBtnDelete = itemView.findViewById(R.id.btn_delete_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityMenu.class);
                    intent.putExtra("Pc_Id", arrayList.get(getLayoutPosition()).getPc_Id());
                    intent.putExtra("Category_Id", arrayList.get(getLayoutPosition()).getCategory_id());
                    intent.putExtra("Category_Name", arrayList.get(getLayoutPosition()).getCategory_Name());
                    intent.putExtra("Category_image", arrayList.get(getLayoutPosition()).getC_Image_Name());
                    context.startActivity(intent);

                }
            });

            imgBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editListener.getEditListenerPosition(getAdapterPosition());

                }
            });
        }
    }
}
