package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;

import java.util.ArrayList;

public class FragmentTabLiquoreCateogory extends Fragment {

    public static Fragment newInstance(ArrayList<MenuDisplayForm> menu, int position) {
        FragmentTabParentCategoryOffer fragment = new FragmentTabParentCategoryOffer();
        Bundle args = new Bundle();
        args.putParcelableArrayList("menuobj", menu);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }
}
