package com.example.newapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

//import com.example.newapp.R.id.buttona

//FragmentNavigation
class MainActivity : AppCompatActivity()  {
    //@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val num:String = "45"
        Log.d(TAG,"number is ${(num.toInt()/4).toString()}")




            val newbut: Button = findViewById(R.id.buttona)
//            newbut.setOnClickListener() {
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.container,LoginFragment()).commit()
//
//                newbut.setVisibility (View.GONE);
//
//            }
        newbut.setOnClickListener(){
            val newIntent = Intent(this, LoginPage::class.java)
            startActivity(newIntent)
        }




        //supportFragmentManager.beginTransaction().remove(LoginFragment()).commit()

        //.commit()
            //.replace(R.id.container, BlankFragment()).commit()


    }

//    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
//        val transaction = supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.container, fragment)
////        transaction.commit()
//
//
//        if (addToStack){
//            transaction.addToBackStack(null )
//
//
//        }
//        transaction.commit()

  //  }

}








