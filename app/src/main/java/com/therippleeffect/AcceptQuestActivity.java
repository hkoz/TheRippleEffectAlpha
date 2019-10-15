package com.therippleeffect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        puddleName.setText(getIntent().getStringExtra(Puddle.nameKey));
        initiator.setText(getIntent().getStringExtra(Puddle.initiatorKey));
        dateCreated.setText(getIntent().getStringExtra(Puddle.dateKey));
        quest.setText(getIntent().getStringExtra(Puddle.questKey));
        countryLocation.setText(getIntent().getStringExtra(Puddle.countryKey));
        cityLocation.setText(getIntent().getStringExtra(Puddle.cityKey));
        requiredRipples.setText(getIntent().getStringExtra(Puddle.reqRipplesKey));
        createdRipples.setText(getIntent().getStringExtra(Puddle.createdRipplesKey));
        type.setText(getIntent().getStringExtra(Puddle.typeKey));
        status.setText(getIntent().getStringExtra(Puddle.statusKey));
        credibility.setText(getIntent().getStringExtra(Puddle.credibilityKey));
        reports.setText(getIntent().getStringExtra(Puddle.reportsKey));
        details.setText(getIntent().getStringExtra(Puddle.detailsKey));

    }
    public void futureFeature(View view){
        Toast.makeText(this,getString(R.string.no_function), Toast.LENGTH_SHORT).show();
    }



}
