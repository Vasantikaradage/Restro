package com.restrosmart.restrohotel.Interfaces;

import com.restrosmart.restrohotel.Model.OfferMenuForm;

import java.util.ArrayList;

public interface OfferListerner {
      void offerDisplay(ArrayList<OfferMenuForm> offerMenuForms ,int pos);
}
