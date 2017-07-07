/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

//import android.os.AsyncTask;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static final int EarthquakeLoaderId = 1;
    private ListView earthquakeListView;

    private EarthquakeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);


        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);


        //register a callback when an item has been clicked on
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //modify onitem click
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //get the current earthquake
                Earthquake currEarthquake = mAdapter.getItem(position);
                //parse its string into uri
                Uri earthquakeUri = Uri.parse(currEarthquake.getmURL());
                //create an intent of viewing
                Intent webIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                //start the action
                startActivity(webIntent);


            }

        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EarthquakeLoaderId, null, this);
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG,"onCreateLoader");

        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        Log.e(LOG_TAG,"onLoadFinished()");
        //clear previous data
        if (data == null || data.isEmpty()) {
            mAdapter.clear();
        } else {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        Log.e(LOG_TAG,"onLoaderReset()");
        loader.reset();
    }


}
