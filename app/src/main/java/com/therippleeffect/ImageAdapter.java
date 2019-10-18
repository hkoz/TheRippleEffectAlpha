package com.therippleeffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Bitmap> {
    public ImageAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ImageAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ImageAdapter(@NonNull Context context, int resource, @NonNull Bitmap[] objects) {
        super(context, resource, objects);
    }

    public ImageAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Bitmap[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ImageAdapter(@NonNull Context context, int resource, @NonNull List<Bitmap> objects) {
        super(context, resource, objects);
    }

    public ImageAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Bitmap> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
