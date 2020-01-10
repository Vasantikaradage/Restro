package com.restrosmart.restrohotel.Interfaces;

import com.restrosmart.restrohotel.Model.OfferMenuForm;

import java.util.ArrayList;

public interface DailyOfferDisplayListerner {
    void dailyOfferDisplay(ArrayList<OfferMenuForm> offerMenuForms,ArrayList<OfferMenuForm> offerSubMenuForms , int pos);


}
