package com.example.mapapptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Vector;

public class MapsFragment extends FragmentActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    FusedLocationProviderClient client;
    LatLng latLng;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(this);

        //change map type
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                        break;
                    case 2: default:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 3:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 4:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button zoomin = (Button) findViewById(R.id.btnZoomin);
        Button zoomout = (Button) findViewById(R.id.btnZoomout);
        Button locate = (Button) findViewById(R.id.btnLocate);
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.zoomIn());
            }
        });
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.zoomOut());
            }
        });
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MapsFragment.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    enableMyLocation();
                else
                    ActivityCompat.requestPermissions(MapsFragment.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng center = new LatLng(21, 106);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 1));

        // Add a marker in Sydney and move the camera
/*        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        //Add many marks
        Vector<MarkerOptions> marks = new Vector<MarkerOptions>();
        marks.add(new MarkerOptions().position(new LatLng(10.823099, 106.629662)).title("Hồ Chí Minh"));
        marks.add(new MarkerOptions().position(new LatLng(21.027763, 105.8544441)).title("Hà Nội"));
        marks.add(new MarkerOptions().position(new LatLng(11.936230, 108.445259)).title("Đà Lạt"));
        marks.add(new MarkerOptions().position(new LatLng(16.054407, 108.202164)).title("Đà Nẵng"));

        for (MarkerOptions m: marks) {
            Marker current = mMap.addMarker(m);
            ImageLoadTask imageLoadTask = new ImageLoadTask(MapsFragment.this, "https://www.toponseek.com/blogs/wp-content/uploads/2019/06/toi-uu-hinh-anh-optimize-image-4-1200x700.jpg", mMap, current);
            imageLoadTask.execute();
        }

        //draw polyline
        LatLng latLng1 = new LatLng(10, 106);
        LatLng latLng2 = new LatLng(11, 105);
        LatLng latLng3 = new LatLng(12, 104);
        LatLng latLng4 = new LatLng(11, 106);
        LatLng latLng5 = new LatLng(10, 107);
        PolylineOptions options = new PolylineOptions().add(latLng1).add(latLng2)
                .add(latLng3).add(latLng4).add(latLng5).color(Color.YELLOW).width(10);
        Polyline polyline = mMap.addPolyline(options);
        //draw circle
        Circle circle1 = mMap.addCircle(new CircleOptions().center(latLng1).radius(50).fillColor(Color.GRAY).strokeColor(Color.BLUE));
        Circle circle2 = mMap.addCircle(new CircleOptions().center(latLng2).radius(50).fillColor(Color.GRAY).strokeColor(Color.BLUE));
        Circle circle3 = mMap.addCircle(new CircleOptions().center(latLng3).radius(50).fillColor(Color.GRAY).strokeColor(Color.BLUE));
        Circle circle4 = mMap.addCircle(new CircleOptions().center(latLng4).radius(50).fillColor(Color.GRAY).strokeColor(Color.BLUE));
        Circle circle5 = mMap.addCircle(new CircleOptions().center(latLng5).radius(50).fillColor(Color.GRAY).strokeColor(Color.BLUE));
    }
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions myLocation = new MarkerOptions();
                            myLocation.position(latLng).title("Your current location").snippet("Here");
                            myLocation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                            mMap.addMarker(myLocation);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                        }
                    });
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
        }
    }
}