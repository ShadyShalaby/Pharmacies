package com.shady.pharmacies.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.shady.pharmacies.PharmacyAdapter;
import com.shady.pharmacies.R;
import com.shady.pharmacies.model.GPSTracker;
import com.shady.pharmacies.model.Place;
import com.shady.pharmacies.model.PlacesService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment
        extends Fragment
implements MainActivity.Callback{

    private final String TAG = getClass().getSimpleName();
    private Context context;
    private GPSTracker gps;
    private double latitude;
    private double longitude;
    private ArrayList<Place> places = new ArrayList<>();

    private ListView listView;
    private PharmacyAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.listView_pharms);
        context = getContext();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadPlaces();
    }

    public void loadPlaces() {
        gps = new GPSTracker(context);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
            new GetPlaces(context, latitude, longitude).execute();
        } else {
            Toast.makeText(context, "Can not get location !!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        loadPlaces();
    }

    private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

        private ProgressDialog dialog;
        private Context context;
        double Latitude;
        double Longitude;

        public GetPlaces(Context context, double Latitude, double Longitude) {
            this.context = context;
            this.Latitude = Latitude;
            this.Longitude = Longitude;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            adapter = new PharmacyAdapter(context, places);
            listView.setAdapter(adapter);

//            for (int i = 0; i < result.size(); i++) {
//                mMap.addMarker(new MarkerOptions()
//                        .title(result.get(i).getName())
//                        .position(
//
//                   new LatLng(result.get(i).getLatitude(), result
//                                        .get(i).getLongitude()))
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
//                        .snippet(result.get(i).getVicinity()));
//            }
           /* CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(result.get(0).getLatitude(), result
                            .get(0).getLongitude())) // Sets the center of the map to
                            // Mountain View
                    .zoom(14) // Sets the zoom
                    .tilt(30) // Sets the tilt of the camera to 30 degrees
                    .build(); // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
*/
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.setMessage("Loading..");
            dialog.isIndeterminate();
            dialog.show();
        }

        @Override
        protected ArrayList<Place> doInBackground(Void... arg0) {
            PlacesService service = new PlacesService("AIzaSyDN1QX-gWUR-mIYo_D21PNFLHHpNQkIkGU");

            places = service.findPlaces(Latitude, Longitude, "pharmacy");
            Toast.makeText(context, "SIZE " + places.size(), Toast.LENGTH_SHORT).show();

            for (int i = 0; i < places.size(); i++) {

                Place placeDetail = places.get(i);
//                Log.e(TAG, "places : " + placeDetail.getName());
                Toast.makeText(context, placeDetail.getName(), Toast.LENGTH_SHORT).show();
            }
            return places;
        }

    }


}
