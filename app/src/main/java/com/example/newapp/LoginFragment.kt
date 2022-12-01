package com.example.newapp


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


val db = Firebase.firestore

var login_users = db.collection("users")

const val RC_SIGN_IN = 123




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var usern: EditText
    private lateinit var passw: EditText
    private lateinit var fAuth: FirebaseAuth

    public var USERNAME = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_login, container, false)
        usern = view.findViewById(R.id.reg_username)
        passw = view.findViewById(R.id.reg_password)

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

 //       val signInButton: SignInButton = view.findViewById(R.id.sign_in_button)
   //     signInButton.setSize(SignInButton.SIZE_STANDARD)


 //       signInButton.setOnClickListener{
 //           Log.d(TAG,"button clicked")
 //           val signInIntent = mGoogleSignInClient.signInIntent
            //MainActivity.startActivityForResult(signInIntent, RC_SIGN_IN);




//            val signInIntent = Intent(requireContext(),mGoogleSignInClient.signInIntent)
//           // Intent signInIntent = mGoogleSignInClient.signInIntent()
//            startActivity(signInIntent, RC_SIGN_IN)
//        }
//        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
//        if (acct != null) {
//            val personName: String? = acct.getDisplayName()
//            val personGivenName: String? = acct.getGivenName()
//            val personFamilyName: String? = acct.getFamilyName()
//            val personEmail: String? = acct.getEmail()
//            val personId: String? = acct.getId()
//            val personPhoto: Uri? = acct.getPhotoUrl()
//        }


        fAuth = Firebase.auth
        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
            //getActivity()?.getSupportFragmentManager()?.beginTransaction()?.replace(R.id.container,RegisterFragment())?.commit()
//            var navRegister = activity as FragmentNavigation
//            navRegister.navigateFrag(RegisterFragment(), false )
        }

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
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
                                val newIntent = Intent(requireContext(), HomeActivity::class.java)

                                newIntent.putExtra("USERNAME", username_pass)
                                //finish()

                                startActivity(newIntent)
                                super.onStop()
                                //getActivity()?.getSupportFragmentManager()?.beginTransaction()?.replace(R.id.container, BlankFragment())?.commit()

                                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "Verify your email", Toast.LENGTH_SHORT).show()
                            }

                            break

                        }

                    }
                }




        }


        return view
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            view?.findViewById<Button>(R.id.sign_in_button)?.visibility = View.GONE
//            view?.findViewById<TextView>(R.id.reg_username)?.text = account.displayName
//
//            // Signed in successfully, show authenticated UI.
//            //updateUI(account)
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//            view?.findViewById<Button>(R.id.sign_in_button)?.visibility = View.VISIBLE
//            // updateUI(null)
//        }
//    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}