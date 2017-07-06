package com.example.android.quakereport;

import android.location.Location;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by at on 7/1/17.
 */

public class Earthquake {

    private Double mMagnitud;

    private String mLocation;

    private long  mTimeInMilliseconds;

    private String mURL;




    public Earthquake(Double Magnitud , String Location, long secs , String url){

        mMagnitud = Magnitud;
        mLocation = Location;
        mTimeInMilliseconds = secs;
        mURL = url ;
    }

    public Long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getmLocation() {
        return mLocation;
    }

    public Double getmMagnitud() {
        return mMagnitud;
    }

    public String getmURL() {
        return mURL;
    }
}
