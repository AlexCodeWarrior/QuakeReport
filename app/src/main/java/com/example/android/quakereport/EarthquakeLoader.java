package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Created by at on 7/6/17.
 * Load a list of earthquake from an http request using AsyncTask
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    //set private variable in order to acess in entire class
    private String  mURL;


    //Constructor
    //* initialize properties
    public EarthquakeLoader(Context context , String url){
        super(context);
      mURL = url;
    }


    /// fetch the earthquakes
    @Override
    public ArrayList<Earthquake> loadInBackground() {

        Log.e(LOG_TAG,"loadInBackground()");

        if (mURL==null){
            return null ;
        }

        // implement this background
        //get url and parse it
        ArrayList<Earthquake> earthquake = QueryUtils.fetchEarthquakeData(mURL);
        return earthquake;


    }


    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG,"onStartLoading()");

        //Required to actually trigger the loadInBackground() method to execute
        forceLoad();
    }
}
