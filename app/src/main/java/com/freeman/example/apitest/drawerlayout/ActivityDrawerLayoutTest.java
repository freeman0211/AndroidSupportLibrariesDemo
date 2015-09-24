package com.freeman.example.apitest.drawerlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.freeman.example.apitest.R;
import com.freeman.example.apitest.fragment.FragmentFirst;
import com.freeman.example.apitest.fragment.FragmentSecond;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by freeman on 9/19/15.
 */
public class ActivityDrawerLayoutTest extends FragmentActivity {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout_test_layout);

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        List<HashMap<String, String>> listData = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("title", "First");
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("title", "Second");

        listData.add(map1);
        listData.add(map2);

        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.simple_text_view, R.id.simple_text, mPlanetTitles));
        mDrawerList.setAdapter(new SimpleAdapter(this, listData, R.layout.simple_text_view, new String[]{"title"}, new int[] {R.id.simple_text}));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        selectItem(0);


    }


    public  class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FragmentFirst();
        } else {
            fragment = new FragmentSecond();
        }
        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getActionBar().setTitle(mTitle);
    }
}
