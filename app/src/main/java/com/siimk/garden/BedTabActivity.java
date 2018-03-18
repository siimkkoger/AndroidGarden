package com.siimk.garden;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class BedTabActivity extends AppCompatActivity{
    private static final String TAG = "GardenActivity";
    final String SAVE_GARDEN = "save_garden";
    int resultCode;
    int position;
    Garden garden;
    Bed bed;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    FragmentGardenInfo fgi;
    FragmentGardenMap fgm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_tab_layout);
        Bundle gardenData = getIntent().getExtras();
        position = gardenData.getInt("view_bed_position");
        garden = gardenData.getParcelable("view_garden");
        bed = gardenData.getParcelable("view_bed");

        mViewPager = (ViewPager) findViewById(R.id.container1);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabsMain1);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();

        bundle1.putParcelable("send_garden", garden);
        bundle1.putParcelable("send_bed", bed);
        Log.d(TAG, "onTouch2: " + garden.getViewWidth() + " " + garden.getViewHeight());
        fgm = new FragmentGardenMap();
        fgm.setArguments(bundle1);

        bundle2.putParcelable("send_garden", garden);
        bundle2.putParcelable("send_bed", bed);
        fgi = new FragmentGardenInfo();
        fgi.setArguments(bundle2);

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(fgm, garden.getName());
        adapter.addFragment(fgi, "SETTINGS");

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        Intent sendIntent = new Intent(BedTabActivity.this, GardenListActivity.class);

        sendIntent.putExtra(SAVE_GARDEN, garden);
        setResult(this.resultCode, sendIntent);
        finish();
        super.onBackPressed();
    }

    public void onDelete(){
        garden.remove(position);
        this.resultCode = 104;
        this.onBackPressed();
    }

    public void onSave(String description){
        Log.d(TAG, "onSave: " + description);
        bed.setDescription(description);
        garden.remove(position);
        Log.d(TAG, "onBackPressed: " + bed.getDescription());
        garden.add(position, bed);
        this.resultCode = 102;
        this.onBackPressed();
    }
}



























