package map;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import Main.BudgetActivity;
import com.example.map2.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import Community.Comment.Comment;
import Community.Comment.Edit_main;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool,referred to some codes on GitHub
 */

/** A class to show shortest path in google map */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG ="a" ;
    public GoogleMap mMap;
    public String url;
    int[] order1;
    ArrayList<MarkerOptions> markerOptions = new ArrayList<>();
    ArrayList<String> nlist = new ArrayList<>();
    public static ArrayList<String> nameOrdered;
    String from;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // get list
        nameOrdered=new ArrayList<>();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<Location> plist = new ArrayList<>();
        ArrayList<LatLng> polyline = new ArrayList<>();
        Button bt = (Button)findViewById(R.id.share_plan);
        Button bt2 = (Button)findViewById(R.id.map_return);
        from=getIntent().getStringExtra("from");
        //Judge the previous activity
        if (!from.equals("generate")){
            bt.setVisibility(View.INVISIBLE);
        }


        //catch places list from other classes
        nlist = getIntent().getStringArrayListExtra("places");
        for (int i = 0; i < nlist.size(); i++) {
            System.out.println("nlist" + nlist.get(i));
        }
        //get the latlng for places
        for (int l = 0; l < nlist.size(); l++) {
            Intent intent = getIntent();
            Location targtLoc = getLocation(MapsActivity.this, nlist.get(l));
            plist.add(targtLoc);
            String c = nlist.get(l);
            Location loc = plist.get(l);
            LatLng p = new LatLng(loc.getLatitude(),
                    loc.getLongitude());
            polyline.add(p);
        }

        // find path using origin and dest, via getDirectionsUrl method
        if (polyline.size() > 1) {
            //find origin and dest
            LatLng origin = new LatLng(plist.get(0).getLatitude(),
                    plist.get(0).getLongitude());
            LatLng dest = new LatLng(plist.get(plist.size() - 1).getLatitude(),
                    plist.get(plist.size() - 1).getLongitude());
            //get Url via getDirectionsUrl method
            try {
                url = getDirectionsUrl(origin, dest, polyline, mMap);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        // get data from Url via json, using downloadData method
        new Thread(new Runnable() {
            @Override
            public void run() {
                order1 = downloadData(url);
            }
        }).start();


        // wait for thread running
        try {
            Thread.sleep(5000);
            io.github.yuweiguocn.lib.squareloading.SquareLoading l= findViewById(R.id.loading);
            l.setVisibility(View.INVISIBLE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //match places with order, and add markers according to order
        Map<Integer, LatLng> map = new TreeMap<Integer, LatLng>();
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        for (int i = 0; i < polyline.size(); i++) {
            LatLng p = polyline.get(i);
            String c = nlist.get(i);
            //add marker with position, name and order
            MarkerOptions m = new MarkerOptions().position(p).title("No." + (order1[i] + 1) + " " + c);
            mMap.addMarker(m);
            markers.add(m);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(p));

            map.put(order1[i], p);
        }

        // get polyline list
        ArrayList<LatLng> hashMapPolyline = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            LatLng p = map.get(i);
            hashMapPolyline.add(p);
        }

        // add polyline
        mMap.addPolyline(new PolylineOptions()
                .addAll(hashMapPolyline)
                .color(ContextCompat.getColor(MapsActivity.this, R.color.red))
                .width(5));

        // set zoom of map
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 5;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.moveCamera(cu);



        // match the order and places list
        Map<Integer, String> map2 = new TreeMap<Integer, String>();
        for (int i = 0; i < nlist.size(); i++) {
            String c = nlist.get(i);
            map2.put(order1[i], c);
        }
        for (int i = 0; i < map2.size(); i++) {
            String s = map2.get(i);
            nameOrdered.add(s);
        }


        // see plan details button
        bt.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {

                                         Intent i = new Intent(MapsActivity.this, load.class);
                                         startActivity(i);
                                     }
                                 }

        );
        // cancel button
        bt2.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                        if (from.equals("comment")){
                                            Intent i = new Intent(MapsActivity.this, Comment.class);
                                            startActivity(i);
                                        }else if(from.equals("generate")){
                                            Intent i = new Intent(MapsActivity.this, BudgetActivity.class);
                                            startActivity(i);
                                        }else if (from.equals("edit")){
                                            String ATA=getIntent().getStringExtra("ATA");
                                            Intent i = new Intent(MapsActivity.this, Edit_main.class);
                                            i.putExtra("randomATA",ATA);
                                            startActivity(i);
                                            finish();
                                        }

                                  }
                              }

        );
   }







   // get places locations according to their names
    public Location getLocation(Context context, String address) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address,1);
            Log.i("get name", "/" + addresses);
            Location location = new Location(address);
            location.setLatitude(addresses.get(0).getLatitude());
            location.setLongitude(addresses.get(0).getLongitude());
            return location;
        } catch (Exception e) {
            Log.i(TAG, "no valid data");
            e.printStackTrace();
            return new Location(address);
        }
    }


    //  combine a Url via start and end points
    private String getDirectionsUrl(LatLng origin, LatLng dest, ArrayList<LatLng> allpoints, GoogleMap googleMap) throws IOException, JSONException {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Travelling Mode
        String optimizeWaypoints = "waypoints=optimize:true|";
        // Print all points in Url
        for (int l = 0; l < allpoints.size(); l++) {
            if (l != allpoints.size() - 1) {
                optimizeWaypoints = optimizeWaypoints + allpoints.get(l).latitude + "," + allpoints.get(l).longitude + "|";
            } else if (l == allpoints.size() - 1) {
                optimizeWaypoints = optimizeWaypoints + allpoints.get(l).latitude + "," + allpoints.get(l).longitude;
            }
        }
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + optimizeWaypoints;
        // Output format
        String output = "json";
        // Building the url to the web service
        final String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyCgN3yVS1258_Lf0d6JPRrkptFxQ2tTOXE";
        System.out.println("getDerectionsURL--->: " + url);

        return url;
    }


    // download the data from url, using DirectionsJSONParser and ParserTask class
    protected int[] downloadData(String url) {
        // For storing data from web service
        String data = "";
        int[] order=new int[nlist.size()];;

        try {
            // Fetching the data from web service using downloadUrl method
            data = downloadUrl(url);
            // Get the order of places in the shortest path
            int i=data.indexOf("waypoint_order");
            int start=i+19;
            int end =start+3*nlist.size();
            String o = data.substring(start,end);
            String[] ss = o.split(",");

            for(int j=0;j<ss.length;j++){
                ss[j]=ss[j].trim();
                try{
                    // Change the order to int and check them
                    order[j]=Integer.parseInt(ss[j]);
                    Log.e("order",""+order[j]);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }

        // Invokes the thread for parsing the JSON data using ParserTask
        ParserTask parserTask = new ParserTask(mMap);
        parserTask.execute(data);

        return order;
    }

    // Fetching the data from web service
    @SuppressLint("LongLogTag")
    static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        System.out.println("url:" + strUrl + "---->   downloadurl:" + data);
        return data;
    }






}
