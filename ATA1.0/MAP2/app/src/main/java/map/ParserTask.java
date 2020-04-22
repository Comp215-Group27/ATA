package map;

import android.graphics.Color;
import android.os.AsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool,referred to some codes on GitHub
 */


/** A class to parse the Google Places in JSON format */

public class ParserTask extends
        AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    private static String jOrder= null;

    public static GoogleMap g;
    public ParserTask(GoogleMap googleMap) {
        this.g=googleMap;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(
            String... jsonData) {
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;
        try {
            jObject = new JSONObject(jsonData[0]);
            // change JSONObjects into a list of lists containing latitude and longitude, using DirectionsJSONParser
            DirectionsJSONParser parser = new DirectionsJSONParser(g);
            // Starts parsing data
            routes = parser.parse(jObject);
            System.out.println("do in background:" + routes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;
        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();
            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);
            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }
            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(3);
            // Changing the color polyline according to the mode
            lineOptions.color(Color.BLUE);
        }
        // Drawing polyline in the Google Map for the i-th route
        g.addPolyline(lineOptions);
    }

}

