package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restro.Model.OfferBannerModel;
import com.restrosmart.restro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferBannerImageAdapter extends PagerAdapter {
    private ArrayList<OfferBannerModel> arrayList;
    private LayoutInflater inflater;
    private Context mContext;

    public OfferBannerImageAdapter(Context context, ArrayList<OfferBannerModel> offerBannerModelArrayList) {
        this.arrayList = offerBannerModelArrayList;
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.offer_banner_item, view, false);

        ImageView ivOfferImage = imageLayout.findViewById(R.id.ivOfferImage);
        TextView tvOfferItemName = imageLayout.findViewById(R.id.tvOfferItemName);
        TextView tvOfferPrice = imageLayout.findViewById(R.id.tvOfferPrice);
        Button btnOfferOrder = imageLayout.findViewById(R.id.btnOfferOrder);


        Picasso.with(mContext)
                .load(arrayList.get(position).getImageUrl())
                .into(ivOfferImage);

        tvOfferItemName.setText(arrayList.get(position).getOfferName());
        tvOfferPrice.setText("Offer " + "\n" + mContext.getResources().getString(R.string.Rs) + arrayList.get(position).getOfferPrice());

        btnOfferOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "clicked " +  position, Toast.LENGTH_SHORT).show();
            }
        });
        view.addView(imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
