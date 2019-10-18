package com.therippleeffect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_quest);

        Task task = ImageDownloader();

        Bitmap myImage;

        try {
            myImage = task.excute(intent.getStringExtra("imageURL")).get();

            snapImageView?.setImageBitmap(myImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
