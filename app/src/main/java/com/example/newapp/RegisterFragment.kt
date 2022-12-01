package com.example.newapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import kotlinx.android.synthetic.main.fragment_register.*
import android.content.Intent

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore


val db0 = Firebase.firestore

var reg_users = db0.collection("users")


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirm_password: EditText
    private lateinit var fAuth: FirebaseAuth


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
        var view = inflater.inflate(R.layout.fragment_register, container, false)

        username = view.findViewById(R.id.reg_username)
        password = view.findViewById(R.id.reg_password)
        confirm_password = view.findViewById(R.id.reg_confirm_password)
        fAuth = Firebase.auth


        view.findViewById<Button>(R.id.btn_login_reg).setOnClickListener {
            //getActivity()?.getSupportFragmentManager()?.beginTransaction()?.replace(R.id.container, LoginFragment())?.commit()
//            var navRegister = activity as FragmentNavigation
//            navRegister.navigateFrag(LoginFragment(), false )
        }

        view.findViewById<Button>(R.id.btn_register_reg).setOnClickListener {
            validateEmptyForm()
        }
        return view
    }

    private fun fireBaseSignUp(){
        fAuth.createUserWithEmailAndPassword(username.text.toString(),password.text.toString()).addOnCompleteListener(){
                task->
            if(task.isSuccessful){

                fAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    Toast.makeText(context, "Registration Successful",Toast.LENGTH_SHORT).show()
                }




                    val users = hashMapOf("username" to username.text.toString(), "password" to password.text.toString())
                    reg_users.add(users)
                val verified = fAuth.currentUser?.isEmailVerified
                if (verified==true){
                    val newIntent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(newIntent)

                }else{
                    Toast.makeText(context, "Verify your mail",Toast.LENGTH_SHORT).show()
                }


                //getActivity()?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit();
                getActivity()?.getSupportFragmentManager()?.popBackStack();

//                val users = hashMapOf("username" to username.text.toString(), "password" to password.text.toString(), "authentication" to true)
//                reg_users.add(users)


            }else{
                Toast.makeText(context,task.exception?.message,Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateEmptyForm(){
        var icon = AppCompatResources.getDrawable(requireContext(),R.drawable.error)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}