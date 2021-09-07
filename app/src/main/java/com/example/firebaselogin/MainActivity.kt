package com.example.firebaselogin

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.firebaselogin.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val fullNameTextField = binding.fullNameTextField.text.toString()
        val emailTextField = binding.fullNameTextField.text.toString()
        val ageTextField = binding.ageTextField.text.toString()



        binding.signUpButton.setOnClickListener {

            if(TextUtils.isEmpty(binding.fullNameTextField.text)){
                binding.fullNameTextField.requestFocus()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(binding.ageTextField.text)){
                binding.ageTextField.requestFocus()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(binding.pwdTextField.text.toString())){
                binding.fullNameTextField.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailTextField.text.toString()).matches()) {
                binding.emailTextField.error = "Please provide a valid email address"
                return@setOnClickListener

            }

            mAuth.createUserWithEmailAndPassword(binding.emailTextField.text.toString(), binding.pwdTextField.text.toString())
                .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val data = Data(
                        fullNameTextField,
                        ageTextField,
                        emailTextField
                    )
                    FirebaseAuth.getInstance().currentUser?.let { it1 ->

                        FirebaseDatabase.getInstance().getReference("User")
                            .child(it1.uid).setValue(data).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, "User has been registered successfully", Toast.LENGTH_LONG).show()
                                    binding.progressBar.visibility = View.VISIBLE
                                    startActivity(Intent(this, MainApp::class.java))
                                    finish()

                                } else {
                                    Toast.makeText(this, "Failed to Resister, Try again", Toast.LENGTH_LONG).show()
                                    binding.progressBar.visibility = View.GONE
                                }

                            }
                    }
                } else {
                    Toast.makeText(this, "Failed to Resister, it seems User already exist", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }


            }
        }
    }
}