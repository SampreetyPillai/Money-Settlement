package com.example.newapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirm_password: EditText
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        username = findViewById(R.id.reg_username_r)
        password = findViewById(R.id.reg_password_r)
        confirm_password = findViewById(R.id.reg_confirm_password_r)
        fAuth = Firebase.auth


        findViewById<Button>(R.id.btn_login_reg_r).setOnClickListener {
            val newIntent = Intent(this, LoginPage::class.java)
            startActivity(newIntent)
            finish()

        }

        findViewById<Button>(R.id.btn_register_reg_r).setOnClickListener {
            validateEmptyForm()
        }
    }

    private fun fireBaseSignUp(){
        fAuth.createUserWithEmailAndPassword(username.text.toString(),password.text.toString()).addOnCompleteListener(){
                task->
            if(task.isSuccessful){

                fAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                }




                val users = hashMapOf("username" to username.text.toString(), "password" to password.text.toString())
                reg_users.add(users)
                val verified = fAuth.currentUser?.isEmailVerified
                if (verified==true){
                    val newIntent = Intent(this, HomeActivity::class.java)
                    startActivity(newIntent)

                }else{
                    Toast.makeText(this, "Verify your mail", Toast.LENGTH_SHORT).show()
                }


                //getActivity()?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit();
                //getActivity()?.getSupportFragmentManager()?.popBackStack();

//                val users = hashMapOf("username" to username.text.toString(), "password" to password.text.toString(), "authentication" to true)
//                reg_users.add(users)


            }else{
                Toast.makeText(this,task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateEmptyForm(){
        var icon = AppCompatResources.getDrawable(this,R.drawable.error)
        icon?.setBounds(0,0,100,100)
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please enter username", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please enter password", icon)
            }
            TextUtils.isEmpty(confirm_password.text.toString().trim()) -> {
                confirm_password.setError("Please re-enter password", icon)
            }


            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() &&
                    confirm_password.text.toString().isNotEmpty()-> {
                //[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+
                if (username.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    if (password.text.toString().length>=5){
                        if(password.text.toString()==confirm_password.text.toString()){
                            fireBaseSignUp()
                            // Toast.makeText(context, "Registration Successful",Toast.LENGTH_SHORT).show()
                        }else{
                            confirm_password.setError("Please re-enter valid password",icon)
                        }
                    }else{
                        password.setError("Please enter valid password",icon)
                    }
                }else{
                    username.setError("Please enter valid email id",icon)
                }
            }
        }
    }

}