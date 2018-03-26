package com.test_drawer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    // Within which the entire activity is enclosed
    DrawerLayout mDrawerLayout;

    // ListView represents Navigation Drawer
    ListView mDrawerList;

    // ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
    ActionBarDrawerToggle mDrawerToggle;
    static ActionBar actionBar;

    // Title of the action bar
    String mTitle="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = (String) getTitle();

        // Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        actionBar = getSupportActionBar();
        // Getting reference to the ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle( this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close){

            /** Called when drawer is closed */
            public void onDrawerClosed(View view)
            {
                actionBar.setTitle(mTitle);
                invalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView)
            {
                invalidateOptionsMenu();
            }
        };

        // Setting DrawerToggle on DrawerLayout
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Creating an ArrayAdapter to add items to the listview mDrawerList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getBaseContext(),
                R.layout.drawer_list_item ,
                getResources().getStringArray(R.array.rivers)
        );

        // Setting the adapter on mDrawerList
        mDrawerList.setAdapter(adapter);

        // Enabling Home button
        actionBar.setHomeButtonEnabled(true);

        // Enabling Up navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawer);

        // Setting item click listener for the listview mDrawerList
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {

                // Getting an array of rivers
                String[] rivers = getResources().getStringArray(R.array.rivers);

                //Currently selected river
                mTitle = rivers[position];

                // Creating a fragment object
                RiverFragment rFragment = new RiverFragment();

                // Creating a Bundle object
                Bundle data = new Bundle();

                // Setting the index of the currently selected item of mDrawerList
                data.putInt("position", position);

                // Setting the position to the fragment
                rFragment.setArguments(data);

                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();

                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /** Handling the touch event of app icon */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

       // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}