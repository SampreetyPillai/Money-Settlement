package com.example.newapp

//import android.R

//import com.google.android.material.internal.ContextUtils.getActivity
import android.R.attr.data
import android.R.attr.start
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginPage : AppCompatActivity() {
    private lateinit var usern: EditText
    private lateinit var passw: EditText
    private lateinit var fAuth: FirebaseAuth

    val RC_SIGN_IN = 9001


    public var USERNAME = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        fAuth = Firebase.auth

        usern = findViewById(R.id.reg_username_l)
        passw = findViewById(R.id.reg_password_l)

        findViewById<Button>(R.id.btn_register_l).setOnClickListener {
            val newIntent = Intent(this, RegisterPage::class.java)
            startActivity(newIntent)
            finish()

        }

        findViewById<Button>(R.id.btn_login_l).setOnClickListener {
//
            var present = false
            login_users.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val name = document.data.get("username")
                        val password = document.data.get("password")
                        //Log.d(TAG, "${name} => ${usern.getText().toString()}")
                        if (name == usern.getText().toString() && password == passw.getText().toString()) {
                            present = true
                            val verified = fAuth.currentUser?.isEmailVerified
                            //val verified  = user?.isEmailVerified
                            val usertask = fAuth.currentUser?.reload()
                            if(verified==true)
                            {
                                val username_pass = usern.getText().toString()
                                val newIntent = Intent(this, HomeActivity::class.java)

                                newIntent.putExtra("USERNAME", username_pass)
                                //finish()

                                startActivity(newIntent)
                                finish()


                                Toast.makeText(this,"Login successful", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show()
                            }

                            break

                        }

                    }
                }




        }

        var myusername:String? = null
        val getResult =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()) {
                if(it.resultCode == Activity.RESULT_OK){
                    val value = it.data?.getStringExtra("input")
//                        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                        Log.d(TAG, "have got a value of ${value}")
                }
            }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()


        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)


        val signInButton:SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener{



            if (account != null) {
                val personName = account.displayName
                val personGivenName = account.givenName
                val personFamilyName = account.familyName
                val personEmail = account.email
                val personId = account.id
                val personPhoto: Uri? = account.photoUrl

                Log.d(TAG, "personName is ${personName}")
                val newIntent  = Intent(this, HomeActivity::class.java)
                newIntent.putExtra("USERNAME", personEmail)
                startActivity(newIntent)
            }else {

                val signInIntent = mGoogleSignInClient.signInIntent
                getResult.launch(signInIntent)

//                val newpage = Intent(this, HomeActivity::class.java)
//                newpage.putExtra("USERNAME", myusername)
//                startActivity(newpage)
            }


        }


    }


}