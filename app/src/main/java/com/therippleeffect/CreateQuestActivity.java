package com.therippleeffect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CreateQuestActivity extends AppCompatActivity {
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    EditText titleEditText, questEditText, cityEditText, requiredRipplesEditText, detailsEditText, descriptionEditText;
    Spinner countrySpinner, typeSpinner;
    ImageView mainImage, extraImage;
    String initiatorName, typeText, titleText, questText, countryText, cityText, requiredRipplesText, detailsText, mainImageURL, extraImageURL, heroesString;
    Button btnCreate, btnAddMainImage, btnDeleteMainImage,btnAddLocation, btnUploadMainImage, btnPickExtraImages, btnDeleteExtraImages, btnAddExtraImage ;
    ArrayList<String> heroesList, tobeUploadedUrls;
    public static List<String> imagesURLsList, descriptionList;
    public static ArrayList<ImageListItem> imageListItemArrayList;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 71;
    private static final int qualityNumber = 50;
    String imageName = UUID.randomUUID().toString() + ".png";
    Boolean mainImagePicked = true;
    ListView extraImagesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle(R.string.create_new_puddle);
        btnCreate = findViewById(R.id.form_new_puddle_button);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFunction();
            }
        });
        titleEditText =findViewById(R.id.edit_puddle_name_text);
        questEditText =findViewById(R.id.edit_quest_text);
        cityEditText =findViewById(R.id.edit_location_city_text);
        requiredRipplesEditText =findViewById(R.id.edit_required_ripple_text);
        detailsEditText =findViewById(R.id.edit_details_text);

        mainImage=findViewById(R.id.main_image_view);

        btnAddMainImage =findViewById(R.id.add_main_images_button);
        btnAddMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainImagePicked =true;
                chooseImage();
            }
        });
        btnDeleteMainImage = findViewById(R.id.delete_main_images_button);
        btnDeleteMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainImage.setImageURI(null);
            }
        });
        btnUploadMainImage = findViewById(R.id.upload_main_images_button);
        btnUploadMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainImage.getDrawable() == null){
                    Toast.makeText(CreateQuestActivity.this,getString(R.string.no_image),Toast.LENGTH_SHORT).show(); }
                else {
                new AlertDialog.Builder(CreateQuestActivity.this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setMessage(getString(R.string.upload_message))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                uploadImage(mainImage);
                                btnUploadMainImage.setVisibility(View.GONE);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();}
            }
        });
        btnAddLocation = findViewById(R.id.add_location_button);
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateQuestActivity.this, MapsActivity.class));
            }
        });

        extraImage = findViewById(R.id.extra_image_view);
        imageListItemArrayList = new ArrayList<>();
        descriptionEditText = findViewById(R.id.extra_image_description);
        extraImagesListView = findViewById(R.id.extra_images_list);
        final ImageListItemAdapter imageListItemAdapter = new ImageListItemAdapter(CreateQuestActivity.this, imageListItemArrayList);
        extraImagesListView.setEmptyView(findViewById(R.id.empty_write_image_view));
        imagesURLsList = new ArrayList<>();
        extraImagesListView.setAdapter(imageListItemAdapter);
        extraImagesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(CreateQuestActivity.this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setMessage(getString(R.string.delete_message))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               imageListItemArrayList.remove(i);
                               imageListItemAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

        btnPickExtraImages = findViewById(R.id.pick_extra_images_button);
        btnPickExtraImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainImagePicked = false;
                chooseImage();
            }
        });
        btnDeleteExtraImages =findViewById(R.id.delete_extra_images_button);
        btnDeleteExtraImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extraImage.setImageURI(null);
            }
        });
        btnAddExtraImage = findViewById(R.id.add_extra_images_button);
        btnAddExtraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (extraImage.getDrawable() == null){
                    Toast.makeText(CreateQuestActivity.this,getString(R.string.no_extra_image),Toast.LENGTH_SHORT).show(); }
                else {
                    new AlertDialog.Builder(CreateQuestActivity.this)
                            .setTitle(getString(R.string.are_you_sure))
                            .setMessage(getString(R.string.add_extra_image))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   String description= descriptionEditText.getText().toString();
                                   if (TextUtils.isEmpty(description)){description= getString(R.string.no_description);}
                                   else description= descriptionEditText.getText().toString();
                                    extraImage.invalidate();
                                    BitmapDrawable drawable = (BitmapDrawable) extraImage.getDrawable();
                                    Bitmap bitmap = drawable.getBitmap();
                                   ImageListItem imageListItem = new ImageListItem(bitmap,description," ");
                                   imageListItemArrayList.add(imageListItem);
                                   imageListItemAdapter.notifyDataSetChanged();
                                   descriptionEditText.setText("");
                                   extraImage.setImageDrawable(null);

                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();}
            }
        });





        countrySpinner =findViewById(R.id.country_spinner);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryText = adapterView.getItemAtPosition(i).toString(); }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        List<String> countryList = Arrays.asList(getResources().getStringArray(R.array.countries_array));
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, countryList);
        countryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        typeSpinner =findViewById(R.id.type_spinner);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeText = adapterView.getItemAtPosition(i).toString(); }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        List<String> typesList = Arrays.asList(getResources().getStringArray(R.array.types_arrays));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, typesList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        typeSpinner.setAdapter(dataAdapter);
        tobeUploadedUrls = new ArrayList<>();

    }
    public void createFunction(){

        titleText = titleEditText.getText().toString();
        questText = questEditText.getText().toString();
        cityText = cityEditText.getText().toString();
        requiredRipplesText= requiredRipplesEditText.getText().toString();
        detailsText = detailsEditText.getText().toString();
        mainImageURL = uploadImage(mainImage);
        initiatorName = mauth.getCurrentUser().getUid();
        heroesList = new ArrayList<>();
        heroesList.add(initiatorName + ",");
        heroesString = Puddle.ArrayToString(heroesList);
        extraImageURL = uploadExtraImages(imageListItemArrayList);


        if(TextUtils.isEmpty(titleText)){
            Toast.makeText(CreateQuestActivity.this,getString(R.string.no_name),Toast.LENGTH_SHORT).show();}
        else if (TextUtils.isEmpty(questText)){
            Toast.makeText(CreateQuestActivity.this,getString(R.string.no_quest),Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(cityText)){
            Toast.makeText(CreateQuestActivity.this,getString(R.string.no_city),Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(detailsText)){
            Toast.makeText(CreateQuestActivity.this,getString(R.string.no_details),Toast.LENGTH_SHORT).show(); }
        else if (TextUtils.isEmpty(requiredRipplesText)){
            Toast.makeText(CreateQuestActivity.this,getString(R.string.no_ripples),Toast.LENGTH_SHORT).show(); }
        else if (mainImage.getDrawable() == null && TextUtils.isEmpty(mainImageURL)){
            Toast.makeText(CreateQuestActivity.this,getString(R.string.no_image),Toast.LENGTH_SHORT).show(); }
        else {
            Map<String, Object> keysValuesMap = new HashMap<>();
            keysValuesMap.put(Puddle.mainImageKey, mainImageURL);
            keysValuesMap.put(Puddle.nameKey, titleText);
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
            keysValuesMap.put(Puddle.initiatorKey, initiatorName);
            keysValuesMap.put(Puddle.dateKey, Puddle.getCurrentDate());
            keysValuesMap.put(Puddle.imagesArrayKey, extraImageURL );
            keysValuesMap.put(Puddle.heroesArrayKey, heroesString);
            keysValuesMap.put(Puddle.locationLatitudeKey, "0");
            keysValuesMap.put(Puddle.locationLongitudeKey, "0");
            DatabaseReference reference = db.getReference("Puddles");
            reference.push().setValue(keysValuesMap);

            Log.i("Map", keysValuesMap.toString());
            Intent intent = new Intent(CreateQuestActivity.this, MyQuestsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(this,getString(R.string.created_successfully), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
         }
    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }
    private String uploadImage(ImageView tobeUploadedImage) {
        final String[] resultedUrl = {" "};
        if(filePath != null)
        {
            tobeUploadedImage.setDrawingCacheEnabled(true);
            tobeUploadedImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) tobeUploadedImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, qualityNumber, baos);
            byte[] data = baos.toByteArray();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.uploading));
            progressDialog.show();
            final StorageReference ref = storage.child("images").child(imageName);
            ref.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateQuestActivity.this, getString(R.string.uploaded), Toast.LENGTH_SHORT).show();
                            resultedUrl[0] = ref.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateQuestActivity.this, getString(R.string.failed)+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage(getString(R.string.uploaded)+" "+(int)progress+"%");
                        }
                    }).getResult();
        }
        else {Toast.makeText(CreateQuestActivity.this,getString(R.string.please_pick_image),Toast.LENGTH_SHORT).show();}
        return String.valueOf(resultedUrl);
    }
    private String uploadExtraImages (ArrayList<ImageListItem> imageListItemArrayList){
        if (imageListItemArrayList.isEmpty() || imageListItemArrayList.equals(null))
        {return " ~ ";}
     else
     for (ImageListItem imageListItem: imageListItemArrayList) {
         extraImage.setImageBitmap(imageListItem.getBitmap());
         ImageListItem tempImageListItem = new ImageListItem(imageListItem.bitmap,imageListItem.description,
                 uploadImage(extraImage));
         imageListItemArrayList.remove(imageListItem);
         imageListItemArrayList.add(tempImageListItem);
     }
     return ImageListItem.createStringFromImageListArrayList(imageListItemArrayList);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            if(mainImagePicked){mainImage.setImageURI(filePath);}
            else extraImage.setImageURI(filePath);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(CreateQuestActivity.this)
                .setTitle(getString(R.string.are_you_sure))
                .setMessage(getString(R.string.back_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tobeUploadedUrls.clear();
                        imageListItemArrayList.clear();
                        imagesURLsList.clear();
                        startActivity(new Intent(CreateQuestActivity.this, MyQuestsActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();}

}
