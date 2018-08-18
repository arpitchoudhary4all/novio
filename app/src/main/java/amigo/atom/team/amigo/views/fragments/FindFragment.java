package amigo.atom.team.amigo.views.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.adapters.CustomInfoWindowAdapter;
import amigo.atom.team.amigo.modal.BookNotification;
import amigo.atom.team.amigo.modal.Therapist;
import amigo.atom.team.amigo.modal.User;
import amigo.atom.team.amigo.utils.FirebaseUtils;

public class FindFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, android.location.LocationListener {

    public static final int PERMISSION_REQ_CODE_LOCATION = 100;
    private static final int PERMISSION_REQ_LOCATION_SMART_BOOK = 200 ;

    private View view;
    private SupportMapFragment mapView;
    private GoogleMap gMap;
    //    private FusedLocationProviderClient fusedLocClient;
    private GoogleApiClient apiClient;
    private LocationManager locationManager;
    private static Marker currentLocMarker;

    private ArrayList<Marker> markerMap;

    private ArrayList<Therapist> therapists;
    private CustomInfoWindowAdapter infoAdapter;

    private LinkedHashMap<Therapist, Marker> therapistMarkerMap;


    void initDummyCrowd() {

        therapists.clear();

        for(int i = 0; i< 5; i++){
            Therapist t = new Therapist();
            therapists.add(t);
        }

        Therapist engi = new Therapist();

        engi.setLatitude(28.1217077); engi.setLongitude(77.1216087);
        engi = therapists.get(0);
        engi.setTid("012345434");
        therapists.set(0, engi);


        engi.setLatitude(28.6996789); engi.setLongitude(77.1217087);
        engi = therapists.get(1);
        engi.setTid("012345589");
        therapists.set(1, engi);



        engi.setLatitude(28.7020868); engi.setLongitude(77.1170674);
        engi = therapists.get(2);
        engi.setTid("012345609");
        therapists.set(2, engi);



        engi.setLatitude(28.6994810); engi.setLongitude(77.1170580);
        engi = therapists.get(3);
        engi.setTid("012345711");
        therapists.set(3, engi);



        engi.setLatitude(28.7040795); engi.setLongitude(77.1273969);
        engi = therapists.get(4);
        engi.setTid("012345834");
        therapists.set(4, engi);


    }

    {
        this.markerMap = new ArrayList<>();
        this.therapists = new ArrayList<>();
        this.therapistMarkerMap = new LinkedHashMap<>();
    }

    public static LatLng getCurrentLatLang() {
        return currentLocMarker.getPosition();
    }

    public static void setCurrentLocMarker(Marker currentLocMarker) {
        FindFragment.currentLocMarker = currentLocMarker;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_find, container, false);
        this.locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);

        mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);

        infoAdapter = new CustomInfoWindowAdapter(getActivity(), therapists);

        checkLocationPermission(PERMISSION_REQ_CODE_LOCATION);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showGPSAlert();
        } else{
            if(gMap == null) {
                initMap();
            }
        }

        return this.view;
    }

    private void initMap() {

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setRotateGesturesEnabled(true);

        // tilt map at 30 deg
//        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().tilt(30).build()));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            initLocationOnMap();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQ_CODE_LOCATION);
        }

        infoAdapter = new CustomInfoWindowAdapter(getActivity(), therapists);
        gMap.setInfoWindowAdapter(infoAdapter);

        initClients();

        // setting up the click for info adpater marker view
        gMap.setOnInfoWindowClickListener((marker)->{

            String uid = marker.getSnippet();
            Toast.makeText(getActivity(), "UID : " + uid, Toast.LENGTH_SHORT).show();


            for(Therapist th : therapists){
                if(th.getTid() != null && th.getTid().equals(uid)){
                    bookOrder(th,"Computer repair");
                    Toast.makeText(getActivity(), "Booking done", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });

    }

    public synchronized void buildApiClient() {
        Toast.makeText(view.getContext(), "buildGoogleApiClient", Toast.LENGTH_SHORT).show();

        apiClient = new GoogleApiClient.Builder(view.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void initClients() {

        initDummyCrowd();

        for (int i = 0; i < this.therapists.size(); i++) {
            LatLng latLang = new LatLng(this.therapists.get(i).getLatitude(), this.therapists.get(i).getLongitude());
            Marker mrkr = gMap.addMarker(LocationMarkerFactory.getMapMarker(getActivity(), latLang, i));
            mrkr.setSnippet(therapists.get(i).getTid());
            therapistMarkerMap.put(therapists.get(i), mrkr);
        }

    }

    private void initLocationOnMap() throws SecurityException {

        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        buildApiClient();
        apiClient.connect();

        Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);

        if (location != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
    }

    private void renderLocation(Location location) {

        if (currentLocMarker != null) {
            currentLocMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // creating and setting up the marker
        MarkerOptions markerOptions = LocationMarkerFactory.getMapMarker(getActivity(), latLng, "Me");
        currentLocMarker = gMap.addMarker(markerOptions);

    }

    private void showGPSAlert() {

        //if(gpsAlertDialog == null) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());

        alertBuilder.setTitle("GPS Provider Alert");
        Log.d("LocationFragment", "inside showGPSAlert");

        alertBuilder.setPositiveButton("Enable GPS", (dialog, which) -> {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            // init maps
            initMap();
        });

        alertBuilder.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(view.getContext(), "GPS permission is denied", Toast.LENGTH_SHORT).show());

        //gpsAlertDialog = alertBuilder.create();
        //}
        alertBuilder.show();
    }

    private void checkLocationPermission(int permissionCode) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    PERMISSION_REQ_CODE_LOCATION);
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        permissionCode);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ_CODE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                showGPSAlert();
                initMap();
            }
        }
    }


    //    --------------- Google APi interface IMPLEMENTATIONS --------------
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LatLng latLng = null;


        try {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                showGPSAlert();
            }

            if (lastLocation != null) {
                latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                // creating and setting up the marker
                MarkerOptions markerOptions = LocationMarkerFactory.getMapMarker(getActivity(), latLng, "Me");

                currentLocMarker = gMap.addMarker(markerOptions);

                renderLocation(lastLocation);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            }

            LocationRequest locRequest = new LocationRequest();
            locRequest.setInterval(1000 * 60); // in millisec
            locRequest.setFastestInterval(1000 * 60);
            locRequest.setSmallestDisplacement(400);   // in metres
            locRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            if(apiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locRequest, this);
            }


        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
    @Override
    public void onConnectionSuspended(int i) {}


    //    --------------- GOOGLE LOCATION LISTENER IMPLEMENTATIONS --------------
    @Override
    public void onLocationChanged(Location location) {
        renderLocation(location);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        Toast.makeText(view.getContext(), "Loaction Changed", Toast.LENGTH_SHORT).show();
    }


    //    --------------- ANDROID LOCATION LISTENER IMPLEMENTATIONS --------------
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        initMap();
    }

    @Override
    public void onProviderDisabled(String provider) {
        initMap();
    }

    //    --------------- CLICK LISTENER IMPLEMENTATIONS --------------
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnSmartBook:
//                double minDist = 0;
//                User engineer = null;
//
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                    // request the permission and leave the routine
//                    checkLocationPermission(PERMISSION_REQ_LOCATION_SMART_BOOK);
//                    return;
//                }
//
//                LatLng ll = currentLocMarker.getPosition();
//                Location lastLocation = new Location("");
//                lastLocation.setLatitude(ll.latitude);
//                lastLocation.setLongitude(ll.longitude);
//
//                for(User e : this.therapists){
//
//                    if(e == null)    continue;
//
//                    engineer = e;
//                    Location loc = engineer.getGeoLocation();
//
//                    if(loc != null) {
////                                double distance = GeoDistanceCalculator.findDistance(currentLocMarker.getPosition().latitude, currentLocMarker.getPosition().longitude, latlang.latitude, latlang.longitude);
//
//                        // in built
//                        double distance = lastLocation.distanceTo(loc);
//
//                        if (minDist == 0) {
//                            minDist = distance;
//                            continue;
//                        }
//
//                        // equal distances are handled in FCFS order
//                        if (distance < minDist) {
//                            minDist = distance;
//                        }
//                    }
//                }
//
//                if(minDist != -1 && engineer != null){
//                    CameraPosition camPos = new CameraPosition.Builder()
//                            .target(new LatLng(engineer.getGeoLocation().getLatitude(), engineer.getGeoLocation().getLongitude()))
//                            .zoom(18)
//                            .bearing(engineer.getGeoLocation().getBearing())
//                            .tilt(70)
//                            .build();
//
//                    Marker mrkr = therapistMarkerMap.get(engineer);
//
//                    if(mrkr.isInfoWindowShown()){
//                        mrkr.showInfoWindow();
//                    }
//
//                }
//
//                break;
//        }
//    }

    private void bookOrder(final Therapist selectedTherapist, final String task) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Initializing request");

        progressDialog.show();

        final DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference(FirebaseUtils.FR_USERS);
        final DatabaseReference dbTherapist = FirebaseDatabase.getInstance().getReference(FirebaseUtils.FR_THERAPISTS);


        final String tid = selectedTherapist.getTid();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dbUsers.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {

            final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User u = dataSnapshot.getValue(User.class);
                String date = formatter.format(new Date());

                ArrayList<Map<String, String>> list = u.getBookings();

                Map<String, String> booking = new HashMap<>();
                booking.put("date", date);
                booking.put("therapist", tid);

                list.add(booking);

                u.setBookings(list);

                dbUsers.child(uid).setValue(list);

                dbTherapist.child(tid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String therapistName = dataSnapshot.getValue(Therapist.class).getName();

                        addNotificationToDB(therapistName, u.getName(), date);

                        Toast.makeText(getActivity(), "Request sent to the therapist", Toast.LENGTH_SHORT).show();

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

            }

            private void addNotificationToDB(String therapistName , String userName, String date){
                //building notification
                BookNotification notification = new BookNotification();
                notification.setUid(uid);
                notification.setTid(tid);
                notification.setDate(date);

                addNotificationToTherapist(notification, userName);
                addNotificationToUser(notification, therapistName);

            }

            private void addNotificationToTherapist(BookNotification notification, String uname) {

                notification.setTitle("New Booking");
                notification.setContent(uname + " requested for your help");

                // adding notification for the user
                dbTherapist.child(tid).child("notifications").child(generateNotifId(uid, tid)).setValue(notification);
            }

            private void addNotificationToUser(BookNotification notification, String tname ) {

                notification.setTitle("Your request");
                notification.setContent("You requested " + tname);

                // adding notification for the user
                dbUsers.child(uid).child("notifications").child(generateNotifId(uid, tid)).setValue(notification);
            }

            // orderid generation stratergy
            private String generateNotifId(String uid, String tid) {

                return uid.substring(3, 7) + tid.substring(3, 7) + System.currentTimeMillis();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

                Toast.makeText(getActivity(), "Unable to book, please check your connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

abstract class GeoDistanceCalculator{

    // custom method to calculate the distance between two geo locations on earth
    private double findDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}

abstract class LocationMarkerFactory{

    public static MarkerOptions getMapMarker(Context context, LatLng latLng, int position){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
//        markerOptions.snippet(String.valueOf(position + 1));//Here we are saving the position of marker (converted into string).
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(LocationMarkerFactory.getMarkerBitmapFromView(context, "Person" + position)));

        return markerOptions;
    }

    public static MarkerOptions getMapMarker(Context context, LatLng latLng, String title){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
//        markerOptions.snippet("0");
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(LocationMarkerFactory.getMarkerBitmapFromView(context, title)));

        return markerOptions;
    }

    public static Bitmap getMarkerBitmapFromView(Context context, String name) {

        //HERE YOU CAN ADD YOUR CUSTOM VIEW
        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_marker, null);

        //IN THIS EXAMPLE WE ARE TAKING TEXTVIEW BUT YOU CAN ALSO TAKE ANY KIND OF VIEW LIKE IMAGEVIEW, BUTTON ETC.
//        TextView textView = (TextView) customMarkerView.findViewById(R.id.txt_name);
//        textView.setText(name);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return returnedBitmap;
    }
}
