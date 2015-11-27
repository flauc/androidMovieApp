package com.example.detectives.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        setPage(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // From http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    private void setPage(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainActivityFragmentPopularity(), "Popularity");
        adapter.addFragment(new MainActivityFragmentVotes(), "Rating");
        adapter.addFragment(new MainActivityFragmentPopularity(), "Favorites");
        viewPager.setAdapter(adapter);
    }

        private void setTabIcons() {

            TextView popularTab = (TextView) LayoutInflater.from(this).inflate(R.layout.single_tab, null);
            popularTab.setText("Popularity");
            popularTab.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_tab_popularity, 0, 0);
            tabLayout.getTabAt(0).setCustomView(popularTab);

            TextView votesTab = (TextView) LayoutInflater.from(this).inflate(R.layout.single_tab, null);
            votesTab.setText("Rating");
            votesTab.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_tab_votes, 0, 0);
            tabLayout.getTabAt(1).setCustomView(votesTab);

            TextView favoritesTab = (TextView) LayoutInflater.from(this).inflate(R.layout.single_tab, null);
            favoritesTab.setText("Favorites");
            favoritesTab.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_tab_favorites, 0, 0);
            tabLayout.getTabAt(2).setCustomView(favoritesTab);
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
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
