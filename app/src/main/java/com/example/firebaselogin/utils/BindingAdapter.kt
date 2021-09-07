package com.example.firebaselogin.utils


import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText


@BindingAdapter("emptySpace")
fun TextInputEditText.detectEmptySpace(string: String){
    if(text?.isEmpty() == true){

        when(string){
            "fullName" -> error = "field cannot be empty"
            "pwd" -> error = "password cannot be empty"
            "age" -> error = "age cannot be empty"
        }
        error = "field cannot be empty"

    }
}