package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.restrosmart.restro.Model.Liquor;
import com.restrosmart.restro.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityCategoryMenu extends AppCompatActivity {

    RecyclerView recyclerView;

    private List<Liquor> liquors;

    int[] ints = {R.drawable.bottle1, R.drawable.bottle1, R.drawable.bottle1, R.drawable.bottle1, R.drawable.bottle1, R.drawable.bottle1, R.drawable.bottle1,
            R.drawable.bottle1, R.drawable.bottle1};

    List<String> list = new ArrayList<String>();
    List<String> desc = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquors);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // recyclerView = (RecyclerView)findViewById(R.id.recycler_menus);
        //Recycler();

        list.add("yyugu");
        list.add("yyugu");
        list.add("yyugu");
        list.add("yyugu");
        list.add("yyugu");
        list.add("yyugu");
        list.add("yyugu");
        list.add("yyugu");
        list.add("yyugu");
        desc.add("sefgsdhigesjg");

        liquors = new ArrayList<Liquor>();


        for (int i = 0; i < ints.length; i++) {
            Liquor liquor = new Liquor(list.get(i), ints[i]);

            liquors.add(liquor);
        }
    }

   /* public void Recycler()
    {

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
       // SnapHelper snapHelper = new PagerSnapHelper();
       // snapHelper.attachToRecyclerView(recyclerView);
        AdapterDisplayAllMenus displayAllMenus = new AdapterDisplayAllMenus(this);
        recyclerView.setAdapter(displayAllMenus);

    }*/


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
