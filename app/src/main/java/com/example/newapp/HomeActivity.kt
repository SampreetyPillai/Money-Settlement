package com.example.newapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private var layoutManager:RecyclerView.LayoutManager?= null
    private lateinit var transArrayList: ArrayList<newtrans>
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var my_username:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().remove(LoginFragment()).commit()

        var newintent = getIntent()
        val USERNAME = newintent.getStringExtra("USERNAME").toString()
        my_username = USERNAME


        Log.d(TAG, "USERNAME is ${USERNAME}")
       // var view =  inflater.inflate(R.layout.fragment_login, container, false)
        val DisplayName:TextView = findViewById(R.id.yourname)
        DisplayName.setText(USERNAME)

        val transactionCalc: Button = findViewById(R.id.transaction_calc)
        transactionCalc.setOnClickListener(){
            val intent = Intent(this,TransactionActivity::class.java)
            intent.putExtra("USERNAME",USERNAME)
            //finish()
            startActivity(intent)


        }
        transArrayList = arrayListOf()

        layoutManager = LinearLayoutManager(this)

        val rec:RecyclerView = findViewById(R.id.recyclerView)

        rec.layoutManager = layoutManager

//        var giver_array = mutableListOf<String>()
//        var receiver_array = mutableListOf<String>()
//        var amount_array = mutableListOf<String>()
//        var date_array = mutableListOf<String>()
//
//
//        user_data.get().addOnSuccessListener { result->
//            for(document in result){
//                val giver_array_name = document.data.get("USERNAME").toString()
//                Log.d(TAG, "giver_array_name is ${giver_array_name}")
//                giver_array.add(giver_array_name)
//                val receiver_array_name = document.data.get("RECIPIENT").toString()
////                Log.d(TAG, "receiver_array_name is ${receiver_array_name}")
//                receiver_array.add(receiver_array_name)
//                val amount_array_name = document.data.get("Amount").toString()
//                amount_array.add(amount_array_name)
////                Log.d(TAG, "amount is ${amount_array}")
//                val date_array_name = document.data.get("Time").toString()
//                date_array.add(date_array_name)
////                Log.d(TAG, "date_array_name is ${date_array}")
////                Log.d(TAG, "date_array_name is ${date_array[6]}")
//
//            }
//
//        }
//        for(user in giver_array){
//            Log.d(TAG, "next user ${user} ")
//        }



//        mydb = FirebaseFirestore.getInstance()
       // adapter = RecyclerAdapter(giver_array,receiver_array,amount_array,date_array)
        adapter = RecyclerAdapter(transArrayList,this)

        rec.adapter = adapter



        EventChangeListener()

//        Log.d(TAG, "USERNAME is ${U
    //        SERNAME}")
    }

    fun EventChangeListener(){
        val mydb = Firebase.firestore
        mydb.collection("transactions")
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    for (dc: DocumentChange in value?.documentChanges!!){
                        if(dc.type==DocumentChange.Type.ADDED){
                            //Log.d(TAG, "USERNAME is ${dc.document.data.get("username").toString()} and ${my_username}")
                            if ((dc.document.toObject(newtrans::class.java).USERNAME==my_username || dc.document.toObject(newtrans::class.java).RECIPIENT==my_username) && (dc.document.toObject(newtrans::class.java).Settled==false)){

                                transArrayList.add(dc.document.toObject(newtrans::class.java))

                            }

                        }
                    }
                    adapter?.notifyDataSetChanged()
                }
            })
    }
}


