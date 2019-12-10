package com.restrosmart.restrohotel.Utils;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.restrosmart.restrohotel.R;


public class OfferView extends View {

    Bitmap bm;
    Canvas cv;
    Paint eraser;
    int mBackgroundColor;

    public OfferView(Context context) {
        super(context);
        init(null);
    }

    public OfferView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OfferView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OfferView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs) {
        eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.setAntiAlias(true);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomOfferView);
            //mOrientation = typedArray.getInt(R.styleable.CustomTicketView_orientation, Orientation.HORIZONTAL);
            mBackgroundColor = typedArray.getColor(R.styleable.CustomOfferView_offerBackgroundColor, getResources().getColor(android.R.color.white));
           // mScallopRadius = typedArray.getDimensionPixelSize(R.styleable.CustomTicketView_scallopRadius, Utils.dpToPx(20f, getContext()));
          //  mScallopPositionPercent = typedArray.getFloat(R.styleable.CustomTicketView_scallopPositionPercent, 50);
           // mShowBorder = typedArray.getBoolean(R.styleable.CustomTicketView_showBorder, false);
           // mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.CustomTicketView_borderWidth, Utils.dpToPx(2f, getContext()));
           // mBorderColor = typedArray.getColor(R.styleable.CustomTicketView_borderColor, getResources().getColor(android.R.color.black));
           // mShowDivider = typedArray.getBoolean(R.styleable.CustomTicketView_showDivider, false);
          //  mDividerType = typedArray.getInt(R.styleable.CustomTicketView_dividerType, DividerType.NORMAL);
          //  mDividerWidth = typedArray.getDimensionPixelSize(R.styleable.CustomTicketView_dividerWidth, Utils.dpToPx(2f, getContext()));
          //  mDividerColor = typedArray.getColor(R.styleable.CustomTicketView_dividerColor, getResources().getColor(android.R.color.darker_gray));
          //  mDividerDashLength = typedArray.getDimensionPixelSize(R.styleable.CustomTicketView_dividerDashLength, Utils.dpToPx(8f, getContext()));
          //  mDividerDashGap = typedArray.getDimensionPixelSize(R.styleable.CustomTicketView_dividerDashGap, Utils.dpToPx(4f, getContext()));
         //   mCornerType = typedArray.getInt(R.styleable.CustomTicketView_cornerType, CornerType.NORMAL);
         //   mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.CustomTicketView_cornerRadius, Utils.dpToPx(4f, getContext()));
          /*  float elevation = 0f;
            if (typedArray.hasValue(R.styleable.CustomTicketView_ticketElevation)) {
                elevation = typedArray.getDimension(R.styleable.CustomTicketView_ticketElevation, elevation);
            } else if (typedArray.hasValue(R.styleable.CustomTicketView_android_elevation)) {
                elevation = typedArray.getDimension(R.styleable.CustomTicketView_android_elevation, elevation);
            }
            if (elevation > 0f) {
                setShadowBlurRadius(elevation);
            }
*/
            typedArray.recycle();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (w != oldw || h != oldh) {
            bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            cv = new Canvas(bm);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        int radius = w > h ? h / 2 : w / 2;

        //Toast.makeText(getContext(), String.valueOf(w) + "-" + String.valueOf(h), Toast.LENGTH_SHORT).show();

        bm.eraseColor(Color.TRANSPARENT);
        cv.drawColor(Color.BLUE);
        cv.drawCircle(w / 2, 20, 80, eraser);
        canvas.drawBitmap(bm, 0, 0, null);
        super.onDraw(canvas);
    }
}