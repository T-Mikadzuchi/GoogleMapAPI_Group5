package com.example.mapapptest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    private Bitmap btmp;
    public MyInfoWindowAdapter(Activity context, Bitmap result) {
        this.context = context; this.btmp = result;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        View v = this.context.getLayoutInflater().inflate(R.layout.custom_info, null);
        LatLng latLng = marker.getPosition();
        TextView tvLat = (TextView) v.findViewById(R.id.lat);
        TextView tvLng = (TextView) v.findViewById(R.id.lng);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView snippet = (TextView) v.findViewById(R.id.snippet);
        ImageView img = (ImageView) v.findViewById(R.id.image);

        tvLat.setText("Lattitude: " + latLng.latitude);
        tvLng.setText("Longitude: " + latLng.longitude);
        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());
        img.setImageBitmap(btmp);
        return v;

    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
