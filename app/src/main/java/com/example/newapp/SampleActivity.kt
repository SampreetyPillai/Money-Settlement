package com.example.newapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//
//var newdb = Firebase.firestore
//val transaction_details = newdb.collection("transactions")

class SampleActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)


//        submission.setOnClickListener(){
//            //supportFragmentManager.beginTransaction()?.replace(R.id.container,BlankFragment())?.commit()

//
//
//        }
    }
}