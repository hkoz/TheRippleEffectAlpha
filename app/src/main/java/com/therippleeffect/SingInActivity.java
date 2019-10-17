package com.therippleeffect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SingInActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Boolean logIn;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        setTitle(getString(R.string.log_in));
        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);
        logIn = true;
    }
    public void logIn(View view){
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        if (email!= null && password!=null && emailText != "" && passwordText != ""){
                mauth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SingInActivity.this, getString(R.string.loggedIn), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SingInActivity.this, MyQuestsActivity.class));
                        }
                        else Toast.makeText(SingInActivity.this, getString(R.string.failed_log_in), Toast.LENGTH_SHORT).show();
                    }
                    }); }
        else Toast.makeText(SingInActivity.this, getString(R.string.failed_log_in), Toast.LENGTH_SHORT).show();
    }

    public void signUp(View view){
        final String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (email!= null && password!=null && !emailText.equals("") && !passwordText.equals("")){
            mauth.createUserWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SingInActivity.this, getString(R.string.signed_up), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SingInActivity.this, MyQuestsActivity.class));
                        FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("email").setValue(emailText);
                    }
                    else Toast.makeText(SingInActivity.this, getString(R.string.failed_sign_up), Toast.LENGTH_SHORT).show();
                }
            }); }
        else Toast.makeText(SingInActivity.this, getString(R.string.failed_sign_up), Toast.LENGTH_SHORT).show();
    }


    }

