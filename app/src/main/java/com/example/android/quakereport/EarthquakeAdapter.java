package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import static com.example.android.quakereport.R.id.location;
import static com.example.android.quakereport.R.id.magnitud;

/**
 * Created by at on 7/1/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    private static final String TAG = EarthquakeAdapter.class.getSimpleName();
    private static final String LOCATION_SEPARATOR = "of";


    //create new constructor

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {

        super(context, 0, earthquakes);

    }

    //Modify get View
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        //Create first batch of View
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);

        }

        //Get earthquake object
        Earthquake currentEarthquake = getItem(position);


        //Get and modify different Views
        TextView magnitudView = (TextView) listItemView.findViewById(magnitud);


        String formatMag = formatMag(currentEarthquake.getmMagnitud());

        magnitudView.setText(formatMag);


      // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        //GradientDrawable magnitudeCircle = (GradientDrawable) magnitudView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitud());


        // Set the color on the magnitude circle
       /// magnitudeCircle.setColor(magnitudeColor);
        magnitudView.setBackgroundColor(magnitudeColor);





        //Get original location
        String originalLocation = currentEarthquake.getmLocation();



        //specific kilometer distance from the primary
        if (originalLocation.contains(LOCATION_SEPARATOR)) {

            String[] splits = originalLocation.split(LOCATION_SEPARATOR);


            //setoff
            TextView offsetView = (TextView) listItemView.findViewById(R.id.offsetloc);
            offsetView.setText(splits[0]+ LOCATION_SEPARATOR);

            // Location
            TextView locationView = (TextView) listItemView.findViewById(location);
            locationView.setText(splits[1]);
        }

        //if no specific kilometer distance from the primary
        else {

            TextView offsetView = (TextView) listItemView.findViewById(R.id.offsetloc);
            offsetView.setText(R.string.near_the);

            TextView locationView = (TextView) listItemView.findViewById(location);
            locationView.setText(originalLocation);


        }


        //Time and Date

        //get the time in milliseconds  from object
        Date dateObject = new Date(currentEarthquake.getmTimeInMilliseconds());


        //Date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);

        dateView.setText(formattedDate);


        //Time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        // Format the date string (i.e. "Mar 3, 1984")
        String formatTime = formatTime(dateObject);

        timeView.setText(formatTime);


        //Return the View

        return listItemView;
    }

  //helper methods
    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);

    }

    public String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    public String formatMag(Double mag) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(mag);

        return output;
    }

    public int  getMagnitudeColor (double Magnitude) {

      int magnitudResourceColorId;
        int magnitudeFloor = (int) Math.floor(Magnitude);
        switch(magnitudeFloor){
            case 0:
            case 1:
                magnitudResourceColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudResourceColorId = R.color.magnitude2;
                break;
             case 3:
                 magnitudResourceColorId = R.color.magnitude3;
                 break;
            case 4:
                magnitudResourceColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudResourceColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudResourceColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudResourceColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudResourceColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudResourceColorId = R.color.magnitude9;
                break;
           default:
                magnitudResourceColorId = R.color.magnitude10plus;
               break;

        }

        //convert the color resource ID into an actual integer color value
        return  ContextCompat.getColor(getContext(),  magnitudResourceColorId);

    }
}

