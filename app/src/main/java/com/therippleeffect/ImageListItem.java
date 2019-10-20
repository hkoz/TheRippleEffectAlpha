package com.therippleeffect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

public class ImageListItem {
    Bitmap bitmap;
    String description;
    String url;
    private final static String SPLITTER = "~";

    public ImageListItem(){}

    public ImageListItem (Bitmap bitmap, @Nullable  String description, String url){
        this.bitmap = bitmap;
        if (TextUtils.isEmpty(description) || description == null) {
            this.description = "No description was provided";}
        this.description = description;
        this.url = url;
    }

    public static class ImageDownload extends AsyncTask<String,Void, Bitmap> {
        public interface AsyncResponse {
            void processFinish(Bitmap bitmap);
        }

        public AsyncResponse delegate = null;

        public ImageDownload(AsyncResponse delegate){
            this.delegate = delegate;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            delegate.processFinish(result);
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    public static ArrayList<ImageListItem> createImageItemListArrayListFromString (String s ){
        ArrayList<String> bitmapDescStringArray = Puddle.StringToArrayList(s);
        ArrayList<ImageListItem> imageListItemArrayList = new ArrayList<>();
        for (String bds :bitmapDescStringArray){
            String imageBitmapURL = bds.split(SPLITTER)[0];
            ImageDownload task = new ImageDownload(new ImageDownload.AsyncResponse() {
                @Override
                public void processFinish(Bitmap bitmap) {

                }
            });
            String imageDescription = bds.split(SPLITTER)[1];
            try {
                ImageListItem imageListItem = new ImageListItem(task.execute(imageBitmapURL).get(),imageDescription, imageBitmapURL);
                imageListItemArrayList.add(imageListItem);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return imageListItemArrayList;
    }
    public static String createStringFromImageListArrayList (ArrayList<ImageListItem> a){
        ArrayList<String> urlDescriptionArrayList = new ArrayList<>();
        for (ImageListItem imageListItem: a){
            String url = imageListItem.getUrl();
            String description = imageListItem.getDescription();
            StringBuilder resultedURLDescription = new StringBuilder();
            resultedURLDescription.append(url);
            resultedURLDescription.append(SPLITTER);
            resultedURLDescription.append(description);
            urlDescriptionArrayList.add(resultedURLDescription.toString());}
        return Puddle.ArrayToString(urlDescriptionArrayList);
        }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public HashMap <String, Object> toMap(){
        HashMap <String, Object> hashMap = new HashMap<>();
        hashMap.put("bitmap", bitmap);
        hashMap.put("description", description);
        return hashMap;
    }

    public boolean hasBitmap(){ return bitmap != null;}
    public boolean hasDescription(){
        if (description != null || TextUtils.isEmpty(description)) return true;
    else return false;}
}
