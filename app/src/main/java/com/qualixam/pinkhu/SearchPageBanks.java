package com.qualixam.pinkhu;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.qualixam.constant.dumpclass;

import java.util.ArrayList;
import java.util.List;

public class SearchPageBanks extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    static String name = "";
    String page = "";
    static String state, city, locality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Search Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager(viewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        locality = getIntent().getStringExtra("locality");
        /*page = getIntent().getStringExtra("page");
        if(page.equals("one"))
        {
            viewPager.setCurrentItem(0);
        }
        else if(page.equals("two"))
        {
            viewPager.setCurrentItem(1);
        }
        else if(page.equals("three"))
        {
            viewPager.setCurrentItem(2);
        }
        else if(page.equals("four"))
        {
            viewPager.setCurrentItem(3);
        }
        else if(page.equals("five"))
        {
            viewPager.setCurrentItem(4);
        }*/
    }
    private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new MedBanks(), "MedBanks");
            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            Bundle bundle = new Bundle();
            Log.d("name","A"+name);
            bundle.putString("name", name);
            bundle.putString("city",city);
            bundle.putString("state",state);
            bundle.putString("locality",locality);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent i = new Intent(SearchPageBanks.this,SearchPage.class);
            i.putExtra("city",city);
            i.putExtra("locality",locality);
            i.putExtra("state",state);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        dumpclass.deleteCache(getApplicationContext());
    }
}
