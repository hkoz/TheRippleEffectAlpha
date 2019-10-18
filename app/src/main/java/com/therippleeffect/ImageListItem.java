package com.therippleeffect;

import android.graphics.Bitmap;

import java.util.HashMap;

public class ImageListItem {
    Bitmap mBitmap;
    String mDescription;

    public ImageListItem(){}

    public ImageListItem (Bitmap bitmap, String description){
        mBitmap = bitmap;
        mDescription = description;
    }
    public Bitmap getBitmap() {
        return mBitmap;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public HashMap <String, Object> toMap(){
        HashMap <String, Object> hashMap = new HashMap<>();
        hashMap.put("bitmap",mBitmap);
        hashMap.put("description",mDescription );
        return hashMap;
    }

    public boolean hasBitmap(){ return mBitmap != null;}
}
