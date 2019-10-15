package com.therippleeffect

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import java.util.*
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnCompleteListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.fragment.app.FragmentActivity


class LogInActivity : AppCompatActivity() {
    val mauth = FirebaseAuth.getInstance()
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    var logInTrue: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        title = getString(R.string.log_in)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        logInTrue = true

    }
    fun logIn(view: View) {
        var email = emailEditText?.text.toString()
        var password = passwordEditText?.text.toString()
        if (emailEditText!= null && passwordEditText!=null && email != "" && password != ""){
                mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { Task ->
                    if (Task.isSuccessful){
                        logInTrue = true
                        enterAsMember()

                    }
                    else Toast.makeText(this, getString(R.string.failed_log_in), Toast.LENGTH_SHORT).show()
                }
            }
            else Toast.makeText(this, getString(R.string.failed_log_in), Toast.LENGTH_SHORT).show()

    }
    fun signup(view: View) {
        var email = emailEditText?.text.toString()
        var password = passwordEditText?.text.toString()
        if (emailEditText!= null && passwordEditText!=null && email != "" && password != "")
            mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { Task ->
            if (Task.isSuccessful){
                val user = mauth.currentUser

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(UUID.randomUUID().toString()).build()
                user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                    }
                }

                logInTrue = false
                enterAsMember()

            }
            else Toast.makeText(this, getString(R.string.failed_sign_up), Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, getString(R.string.failed_sign_up), Toast.LENGTH_SHORT).show()

    }
    private fun enterAsMember() = if (logInTrue) {
        Toast.makeText(this, getString(R.string.loggedIn), Toast.LENGTH_SHORT).show()
        var myIntent = Intent (this, SearchActivity::class.java)
        startActivity(myIntent)
    }
    else {
        Toast.makeText(this, getString(R.string.signed_up), Toast.LENGTH_SHORT).show()
        var myIntent = Intent (this, SearchActivity::class.java)
        startActivity(myIntent)
    }

}
