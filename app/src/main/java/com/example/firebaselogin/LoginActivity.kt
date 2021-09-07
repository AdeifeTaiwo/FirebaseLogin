package com.example.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        val loginButton  = findViewById<Button>(R.id.loginButton)
        val email = findViewById<TextInputEditText>(R.id.emailTextField)
        val pwd = findViewById<TextInputEditText>(R.id.pwdTextField)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        loginButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            mAuth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener {
                    if(!it.isSuccessful){
                        Log.d("tag", email.text.toString() + pwd.text.toString())
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show()

                    }
                    else{
                        progressBar.visibility = View.GONE
                        startActivity(Intent(this, MainApp::class.java))
                        finish()

                    }
                }
        }


    }

    fun onRegisterClick(view: android.view.View) {

        val intent = Intent(this, MainActivity::class.java )
        startActivity(intent)
    }


}