package com.therippleeffect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    EditText title;
    EditText quest;
    EditText country;
    EditText city;
    EditText requiredRipples;
    Spinner type;
    EditText details;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;
    String typeText;
    String nameText;
    String questText ;
    String countryText ;
    String cityText ;
    String requiredRipplesText ;
    String detailsText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle(R.string.create_new_puddle);
        title=findViewById(R.id.edit_puddle_name_text);
        quest=findViewById(R.id.edit_quest_text);
        country=findViewById(R.id.edit_location_country_text);
        city=findViewById(R.id.edit_location_city_text);
        requiredRipples=findViewById(R.id.edit_required_ripple_text);
        details=findViewById(R.id.edit_details_text);
        image1=findViewById(R.id.write_image_view_1);

        type=findViewById(R.id.type_spinner);
        // Spinner click listener
        type.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.types_arrays));
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, list);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        type.setAdapter(dataAdapter);

    }
    public void createFunction(View view){
        nameText =title.getText().toString();
        questText = quest.getText().toString();
        countryText= country.getText().toString();
        cityText = city.getText().toString();
        requiredRipplesText= requiredRipples.getText().toString();
        detailsText = details.getText().toString();
        if(TextUtils.isEmpty(nameText)){
            Toast.makeText(CreateActivity.this,getString(R.string.no_name),Toast.LENGTH_SHORT).show();}
        else if (TextUtils.isEmpty(questText)){
            Toast.makeText(CreateActivity.this,getString(R.string.no_quest),Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(countryText)){
            Toast.makeText(CreateActivity.this,getString(R.string.no_country),Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(cityText)){
            Toast.makeText(CreateActivity.this,getString(R.string.no_city),Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(detailsText)){
            Toast.makeText(CreateActivity.this,getString(R.string.no_details),Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(requiredRipplesText)){
            Toast.makeText(CreateActivity.this,getString(R.string.no_ripples),Toast.LENGTH_SHORT).show(); }
        else {

            Map<String, String> keysValuesMap = new HashMap<>();
            keysValuesMap.put(Puddle.nameKey, nameText);
            keysValuesMap.put(Puddle.questKey, questText);
            keysValuesMap.put(Puddle.countryKey, countryText);
            keysValuesMap.put(Puddle.cityKey, cityText);
            keysValuesMap.put(Puddle.detailsKey, detailsText);
            keysValuesMap.put(Puddle.typeKey, typeText);
            keysValuesMap.put(Puddle.credibilityKey, "0");
            keysValuesMap.put(Puddle.reportsKey, "0");
            keysValuesMap.put(Puddle.reqRipplesKey, requiredRipplesText);
            keysValuesMap.put(Puddle.statusKey, getString(R.string.Ongoing));
            keysValuesMap.put(Puddle.createdRipplesKey, "0");
            keysValuesMap.put(Puddle.initiatorKey, mauth.getCurrentUser().getUid());
            keysValuesMap.put(Puddle.dateKey, Puddle.getCurrentDate());
            DatabaseReference reference = db.getReference("Puddles");
            reference.push().setValue(keysValuesMap);
            Log.i("Map", keysValuesMap.toString());
            Intent intent = new Intent(CreateActivity.this, MyQuestsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
         }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner typeText
        typeText = parent.getItemAtPosition(position).toString();

    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
