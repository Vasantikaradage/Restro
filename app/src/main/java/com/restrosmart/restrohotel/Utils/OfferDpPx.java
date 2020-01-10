package com.restrosmart.restrohotel.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class OfferDpPx {
    public static int OfferDpPx(float dp, Context context) {
        return OfferDpPx(dp, context.getResources());
    }

    public static int OfferDpPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}
