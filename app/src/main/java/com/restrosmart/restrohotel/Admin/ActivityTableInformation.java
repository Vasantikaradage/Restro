package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.restrosmart.restrohotel.Adapter.AdapterTableDisplay;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class ActivityTableInformation  extends AppCompatActivity {
    private TextView txTitle;
    private Toolbar mTopToolbar;
    private RecyclerView mRecyclerView;
    private ArrayList<TableFormId> arrayListTableIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_info);
        init();
        setUpToolBar();

        Intent intent=getIntent();
        arrayListTableIds=intent.getExtras().getParcelableArrayList("tableIds");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        AdapterTableDisplay adapterTableDisplay=new AdapterTableDisplay(this,arrayListTableIds);
        mRecyclerView.setAdapter(adapterTableDisplay);
    }

    private void init() {
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        mRecyclerView=findViewById(R.id.recycler_table_details);
    }

    private void setUpToolBar() {
        setSupportActionBar(mTopToolbar);
        txTitle.setText("Table Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
