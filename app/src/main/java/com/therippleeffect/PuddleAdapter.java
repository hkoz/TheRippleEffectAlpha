package com.therippleeffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PuddleAdapter extends ArrayAdapter<Puddle> {

        /**
         * Create a new {@link PuddleAdapter} object.
         *
         * @param context is the current context (i.e. Activity) that the adapter is being created in.
         * @param puddles is the valuesList of {@link Puddle}s to be displayed.
         */
        public PuddleAdapter(Context context, ArrayList<Puddle> puddles) {
            super(context, 0, puddles);
        }

        @Override
        public View getView(int position, View convertView, @NotNull ViewGroup parent) {
            // Check if an existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.puddle_list_item, parent, false);
            }
            // Get the {@link Puddle} object located at this position in the valuesList
            Puddle currentPuddle = getItem(position);
            // Find the TextView in the list_item.xml layout with the ID puddle_initiator_name.
            TextView initiatorName =  listItemView.findViewById(R.id.puddle_initiator_name);
            // Get the initiator name from the currentPuddle object and set this text on
            // the initiator_name TextView.
            initiatorName.setText(currentPuddle.getPuddleInitiator());
            // Find the TextView in the list_item.xml layout with the ID puddle_name.
            TextView puddleName =  listItemView.findViewById(R.id.puddle_name_edit);
            // Get the puddle name from the currentPuddle object and set this text on
            // the puddle_name TextView.
            puddleName.setText(currentPuddle.getPuddleName());
            // Find the TextView in the list_item.xml layout with the ID puddle_quest.
            TextView puddleQuest =  listItemView.findViewById(R.id.puddle_quest);
            // Get the puddle questEditText from the currentPuddle object and set this text on
            // the puddle_quest TextView.
            puddleQuest.setText(currentPuddle.getPuddleQuest());
            // Find the TextView in the list_item.xml layout with the ID puddle_status.
            TextView puddleStatus =  listItemView.findViewById(R.id.puddle_status);
            // Get the puddle questEditText from the currentPuddle object and set this text on
            // the puddle_quest TextView.
            puddleStatus.setText(currentPuddle.getPuddleStatus());
            // Find the ImageView in the list_item.xml layout with the ID puddle_image.
            ImageView puddleImage =  listItemView.findViewById(R.id.puddle_image);
            // Check if an image is provided for this word or not

            if (currentPuddle.puddleHasImage()) {
                ImageListItem.ImageDownload task = new ImageListItem.ImageDownload(new ImageListItem.ImageDownload.AsyncResponse() {
                    @Override
                    public void processFinish(Bitmap bitmap) {

                    }
                });
                String string = currentPuddle.getImageResourceURL();
                try {
                    // If an image is available, display the provided image based on the resource ID
                    // Make sure the view is visible
                    puddleImage.setVisibility(View.VISIBLE);
                    puddleImage.setImageBitmap(task.execute(string).get());
                }
                catch (Exception e){
                    e.printStackTrace();
                    puddleImage.setVisibility(View.GONE);
                }

            } else {
                // Otherwise hide the ImageView (set visibility to GONE)
                puddleImage.setVisibility(View.GONE);
            }
            // Return the whole valuesList typeText layout (containing 3 TextViews) so that it can be shown in
            // the ListView.
            return listItemView;
        }
    }

