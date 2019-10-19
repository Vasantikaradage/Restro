package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.R;


public class FragmentPaymentMethods extends Fragment {
    private WebView webvPaymentMethod;
    ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_methods, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        webvPaymentMethod.getSettings().setJavaScriptEnabled(true);

        webvPaymentMethod.loadUrl(apiService.BASE_URL+"PayUMoney.html");
    }

    private void init() {
        webvPaymentMethod = getView().findViewById(R.id.webv_payment_details);
    }
}


