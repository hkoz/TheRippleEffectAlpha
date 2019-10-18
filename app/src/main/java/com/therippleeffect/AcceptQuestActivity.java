package com.therippleeffect;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AcceptQuestActivity extends AppCompatActivity {


    TextView puddleName;
    TextView dateCreated;
    TextView initiator;
    TextView quest;
    TextView countryLocation;
    TextView cityLocation;
    TextView requiredRipples;
    TextView createdRipples;
    TextView type;
    TextView status;
    TextView credibility;
    TextView reports;
    TextView details;
    Puddle receivedPuddle;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Puddles");
    ImageView mainImage;
    ListView imageList;
    ListView heroesList;

    public static class ImageDownload extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                Bitmap myImage;
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                myImage = BitmapFactory.decodeStream(inputStream);
                return myImage;
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
        ArrayList<ImageListItem> imagesArray = new ArrayList<>();
        ImageAdapter imageAdapter = new ImageAdapter(AcceptQuestActivity.this,imagesArray);
        heroesList = findViewById(R.id.heroes_list);
        heroesList.setEmptyView(findViewById(R.id.empty_heroes_view));
        ArrayList<String> heroesArray = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new  ArrayAdapter<>(AcceptQuestActivity.this,android.R.layout.simple_list_item_1, heroesArray);


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
        requiredRipples.setText(String.valueOf(receivedPuddle.getPuddleRequiredRipples()));
        createdRipples.setText(String.valueOf(receivedPuddle.getPuddleCreatedRipples()));
        type.setText(receivedPuddle.getPuddleType());
        status.setText(receivedPuddle.getPuddleStatus());
        credibility.setText(String.valueOf(receivedPuddle.getPuddleCredibilityBoostsNumber()));
        reports.setText(String.valueOf(receivedPuddle.getPuddleCredibilityReportsNumber()));
        details.setText(receivedPuddle.getPuddleDetails());
        ImageDownload task = new ImageDownload();
        String url = "https://pics.me.me/luffy-king-of-the-pirates-post-2nd-timeskip-drawn-by-35404338.png";
        try {
            mainImage.setImageBitmap(task.execute(url).get());
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
    public void futureFeature(View view){
        Toast.makeText(this,getString(R.string.no_function), Toast.LENGTH_SHORT).show();
    }
    public void promoteCredibility (View view){
        int credibilityValue = receivedPuddle.getPuddleCredibilityBoostsNumber();
        credibilityValue += 1;
        receivedPuddle.setMpuddleCredibilityBoostsNumber(credibilityValue);
        databaseReference.updateChildren(receivedPuddle.toMap());
        credibility.setText(String.valueOf(receivedPuddle.getPuddleCredibilityBoostsNumber()));
    }

    public void reportScam (View view){
        int Value = receivedPuddle.getPuddleCredibilityReportsNumber();
        Value += 1;
        receivedPuddle.setMpuddleCredibilityReportsNumber(Value);
        databaseReference.updateChildren(receivedPuddle.toMap());
        reports.setText(String.valueOf(receivedPuddle.getPuddleCredibilityReportsNumber()));
    }

    public void acceptQuest(View view){
        Toast.makeText(AcceptQuestActivity.this,getString(R.string.accepted_quest), Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Puddles").child(getIntent().getStringExtra(Puddle.key));
        databaseReference.removeValue();

    }
}
