package com.therippleeffect;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AcceptQuestActivity extends AppCompatActivity {


    TextView puddleName, dateCreated, initiator, quest, countryLocation, cityLocation, requiredRipples,
            createdRipples, type, status, credibility, reports, details;
    Puddle receivedPuddle;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Puddles");
    ImageView mainImage;
    ListView imageList, heroesList;

    public static class ImageDownload extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_quest);

        puddleName=findViewById(R.id.puddle_name_text);
        initiator=findViewById(R.id.initiator_name_text);
        dateCreated=findViewById(R.id.date_text);
        quest=findViewById(R.id.quest_text);
        countryLocation=findViewById(R.id.location_country_text);
        cityLocation=findViewById(R.id.location_city_text);
        requiredRipples=findViewById(R.id.required_ripple_text);
        createdRipples =findViewById(R.id.ripples_created_text);
        type=findViewById(R.id.type_text);
        status=findViewById(R.id.Status_text);
        credibility=findViewById(R.id.credibility_text);
        reports=findViewById(R.id.reports_text);
        details=findViewById(R.id.details_text);
        mainImage = findViewById(R.id.main_image);

        imageList = findViewById(R.id.images_list);
        imageList.setEmptyView(findViewById(R.id.empty_image_view));

        heroesList = findViewById(R.id.heroes_list);
        heroesList.setEmptyView(findViewById(R.id.empty_heroes_view));



        databaseReference = databaseReference.child(getIntent().getStringExtra(Puddle.key));
        if (getIntent().getStringExtra("source").equals("From MyPuddlesFragment"))
        {receivedPuddle = MyPuddlesFragment.puddlesList.get(getIntent().getIntExtra("position In listView", 0));}
        else if (getIntent().getStringExtra("source").equals("From MyRipplesFragment"))
        {receivedPuddle = MyRipplesFragment.puddlesList.get(getIntent().getIntExtra("position In listView", 0));}


        puddleName.setText(receivedPuddle.getPuddleName());
        initiator.setText(receivedPuddle.getPuddleInitiator());
        dateCreated.setText(receivedPuddle.getPuddleDateCreated());
        quest.setText(receivedPuddle.getPuddleQuest());
        countryLocation.setText(receivedPuddle.getPuddleCountryLocation());
        cityLocation.setText(receivedPuddle.getPuddleCityLocation());
        LatLng location = new LatLng(Double.parseDouble(receivedPuddle.getLocationLatitude()),Double.parseDouble(receivedPuddle.getLocationLongitude()));
        requiredRipples.setText(String.valueOf(receivedPuddle.getPuddleRequiredRipples()));
        createdRipples.setText(String.valueOf(receivedPuddle.getPuddleCreatedRipples()));
        type.setText(receivedPuddle.getPuddleType());
        status.setText(receivedPuddle.getPuddleStatus());
        credibility.setText(String.valueOf(receivedPuddle.getPuddleCredibilityBoostsNumber()));
        reports.setText(String.valueOf(receivedPuddle.getPuddleCredibilityReportsNumber()));
        details.setText(receivedPuddle.getPuddleDetails());
        ImageDownload task = new ImageDownload();
        try {
            mainImage.setImageBitmap(task.execute(receivedPuddle.getImageResourceURL()).get());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<ImageListItem> imagesArray = receivedPuddle.getPuddleImagesSources();
        ImageListItemAdapter imagesAdapter = new ImageListItemAdapter(AcceptQuestActivity.this,imagesArray);
        imageList.setAdapter(imagesAdapter);



        ArrayList<String> heroesArray = receivedPuddle.getPuddleHeroes();
        ArrayAdapter<String> heroesAdapter = new  ArrayAdapter<>(AcceptQuestActivity.this,android.R.layout.simple_list_item_1, heroesArray);
        heroesList.setAdapter(heroesAdapter);


        Log.i("khklhlkhkl", receivedPuddle.toMap().toString());



    }
    public void futureFeature(View view){
        Toast.makeText(this,getString(R.string.no_function), Toast.LENGTH_SHORT).show();
    }
    public void promoteCredibility (View view){
        int value = receivedPuddle.getPuddleCredibilityBoostsNumber();
        value += 1;
        receivedPuddle.setPuddleCredibilityBoostsNumber(value);
        databaseReference.child(Puddle.credibilityKey).setValue(value);
        credibility.setText(String.valueOf(receivedPuddle.getPuddleCredibilityBoostsNumber()));
    }

    public void reportScam (View view){
        int value = receivedPuddle.getPuddleCredibilityReportsNumber();
        value += 1;
        receivedPuddle.setPuddleCredibilityReportsNumber(value);
        databaseReference.child(Puddle.reportsKey).setValue(value);
        reports.setText(String.valueOf(receivedPuddle.getPuddleCredibilityReportsNumber()));
    }

    public void acceptQuest(View view){
        Toast.makeText(AcceptQuestActivity.this,getString(R.string.accepted_quest), Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Puddles").child(getIntent().getStringExtra(Puddle.key));
        databaseReference.removeValue();

    }
}
