package com.therippleeffect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImagePickActivity extends AppCompatActivity {
    private Button btnChoose, btnUpload, btnContinue;
    private ImageView imageView;
    private EditText description, quality;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    public String imageURL;
    private static final int PICK_IMAGE_REQUEST = 71;
    private static final int qualityNumber = 50;
    String imageName = UUID.randomUUID().toString() + ".png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        btnContinue = findViewById(R.id.btnNext);
        imageView = findViewById(R.id.imgView);
        description = findViewById(R.id.description_text);
        quality = findViewById(R.id.quality_text);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClicked();
            }
        });
    }
    private void nextClicked(){
        String descriptionText =  description.getText().toString() ;
        Intent intent = new Intent(ImagePickActivity.this, CreateQuestActivity.class);
        if (getIntent().getStringExtra("for").equals(Puddle.mainImageKey))
        {intent.putExtra(Puddle.mainImageKey,imageURL);}
        else
        {CreateQuestActivity.imagesURLsList.add(imageURL);
        if (descriptionText == null || TextUtils.isEmpty(descriptionText)) {CreateQuestActivity.descriptionList.add(getString(R.string.default_description));}
         CreateQuestActivity.descriptionList.add(descriptionText);}
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }
    private void uploadImage() {
        if(filePath != null)
        {
            imageView.setDrawingCacheEnabled(true);
           imageView.buildDrawingCache();
            Bitmap bitmap = imageView.getDrawingCache();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, qualityNumber, baos);
            byte[] data = baos.toByteArray();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.uploading));
            progressDialog.show();
            final StorageReference ref = storageReference.child("images").child(imageName);
            ref.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ImagePickActivity.this, getString(R.string.uploaded), Toast.LENGTH_SHORT).show();
                            imageURL = ref.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ImagePickActivity.this, getString(R.string.failed)+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage(getString(R.string.uploaded)+" "+(int)progress+"%");
                        }
                    });
        }
        else {Toast.makeText(ImagePickActivity.this,getString(R.string.please_pick_image),Toast.LENGTH_SHORT).show();}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            imageView.setImageURI(filePath);
        }
    }

}
