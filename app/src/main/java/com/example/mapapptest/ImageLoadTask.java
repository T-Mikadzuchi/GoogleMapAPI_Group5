package com.example.mapapptest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private GoogleMap map;
    private Activity context;
    private boolean isCompleted = false;
    private Marker current;

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void setCompleted() {
        this.isCompleted = isCompleted;
    }

    public ImageLoadTask(Activity context, String url, GoogleMap map, Marker marker) {
        this.context = context;
        this.url = url;
        this.map = map;
        this.current = marker;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            //tạo đối tượng URL
            URL urlConnection = new URL(url);
            //mở kết nối
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            //đọc dữ liệu
            InputStream input = connection.getInputStream();
            //tiến hành convert qua hình ảnh
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            if (myBitmap == null)
                return null;
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result){
        //thiết lập info cho map khi hoàn tất
        map.setInfoWindowAdapter(new MyInfoWindowAdapter(context, result));
        //hiển thị lên marker option
        current.showInfoWindow();
    }
}
