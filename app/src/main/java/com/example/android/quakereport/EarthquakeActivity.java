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

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EarthquakeActivity extends AppCompatActivity {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    ListView earthquakeListView;

    private EarthquakeAdapter  mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);



        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter  = new EarthquakeAdapter (this,new ArrayList<Earthquake>());


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);



       //register a callback when an item has been clicked on
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
             @Override
              //modify onitem click
               public void onItemClick(AdapterView<?> adapterView , View view , int position ,long l ){
                         //get the current earthquake
                     Earthquake currEarthquake = mAdapter.getItem(position);
                      //parse its string into uri
                     Uri earthquakeUri = Uri.parse(currEarthquake.getmURL());
                    //create an intent of viewing
                 Intent webIntent = new Intent(Intent.ACTION_VIEW,earthquakeUri);
                   //start the action
                 startActivity(webIntent);


                  }

              });

        new DownloadEarthquake().execute(USGS_REQUEST_URL);
    }

    private class DownloadEarthquake extends AsyncTask<String, Void, ArrayList<Earthquake>> {
        protected ArrayList<Earthquake>  doInBackground(String ... urls) {

            // check for possible events
            if (urls.length < 1 || urls [0] == null ){
                return null ;
            }

            //get url and parse it
            ArrayList<Earthquake> earthquake = QueryUtils.fetchEarthquakeData(urls[0]);


            return earthquake;

        }



        protected void onPostExecute(ArrayList<Earthquake>  earthquakes) {

            //if their is no result do nothing

            if ( earthquakes == null || earthquakes.isEmpty()){
                 mAdapter.clear();
            }
            else {
               mAdapter.addAll(earthquakes);
            }
        }
    }

}
