package com.therippleeffect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImageListItemAdapter extends ArrayAdapter<ImageListItem> {


    public ImageListItemAdapter(@NonNull Context context, ArrayList<ImageListItem> item) {
        super(context, 0, item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View imagesItemView = convertView;
        if (imagesItemView == null) {
            imagesItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.image_list_item, parent, false); }
        ImageListItem imageListItem = getItem(position);

        ImageView listItemImageView = imagesItemView.findViewById(R.id.image_list_item_image);

        TextView listItemDescription = imagesItemView.findViewById(R.id.image_list_item_description);
        if (imageListItem.hasDescription()){
            listItemDescription.setText(imageListItem.getDescription());
            listItemDescription.setVisibility(View.VISIBLE);
        }
        else listItemDescription.setVisibility(View.GONE);


        if (imageListItem.hasBitmap()) {
            // If an image is available, display the provided image based on the bitmap
            listItemImageView.setImageBitmap(imageListItem.getBitmap());
            // Make sure the view is visible
            listItemImageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            listItemImageView.setVisibility(View.GONE);
            listItemDescription.setVisibility(View.GONE);
            imagesItemView.setVisibility(View.GONE);
        }
        return imagesItemView;
    }
}
