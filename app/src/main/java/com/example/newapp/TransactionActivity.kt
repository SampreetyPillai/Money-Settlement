package com.example.newapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime


class TransactionActivity : AppCompatActivity() {
    private var newlayoutmanager: RecyclerView.LayoutManager?= null
    private lateinit var userArrayList: ArrayList<UserData>
    private var myadapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>? = null
    public val che: MutableList<Boolean> = mutableListOf()
    public var all_users:String = ""

    public var myusername:String = ""
    public var selected_users:Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        val newdb = Firebase.firestore
        val transaction_details = newdb.collection("transactions")

        var newintent = getIntent()
        val USERNAME = newintent.getStringExtra("USERNAME").toString()
        myusername = USERNAME
//
//
//        val the_recipient: EditText = findViewById(R.id.recipient_mail)
        val the_amount: EditText =findViewById(R.id.amount)
       // val AMOUNT:Number =Integer.parseInt(the_amount.toString())
       // val RECIPIENT:String = the_recipient.toString()
        val the_status: EditText = findViewById(R.id.status)
//        val STATUS:String  = the_status.toString()
        val submission: Button = findViewById(R.id.split_button)
        val occasion: EditText = findViewById(R.id.occasion)
//        val RECEIVER: String =the_recipient.text.toString()
//        val AMOUNT:String = the_amount.text.toString()

        userArrayList = arrayListOf()

        newlayoutmanager = LinearLayoutManager(this)

        val newrec:RecyclerView = findViewById(R.id.userrecycle)

        newrec.layoutManager = newlayoutmanager
        myadapter = UserAdapter(this, userArrayList)

        newrec.adapter = myadapter
        EventChangeListener()


        submission.setOnClickListener(){
            var position:Int = 0
            Log.d(TAG,"button clicked ${position}")
            var total_selections:Int = 0
            var item:Int = 0
            while(item<selected_users){
                if (che[item]==true) {
                    total_selections += 1
                }
                item+=1
            }
            while(position <selected_users){



                val thecurrent = userArrayList[position]
                if(che[position]==true) {
                    Log.d(TAG, "current position is ${thecurrent.username}")

                    transaction_details.add(
                        hashMapOf(
                            "USERNAME" to USERNAME,
                            "RECIPIENT" to thecurrent.username.toString(),
                            "Amount" to ((the_amount.getText().toString().toInt())/total_selections).toString(),
                            "Settled" to false,
                            "Time" to LocalDateTime.now().toString(),
                            "Occasion" to occasion.getText().toString()
                        )
                    )
                    all_users+="("
                    all_users+=thecurrent.username.toString()
                    all_users+=") "
                    Log.d(TAG, "${selected_users}")
//                if(thecurrent.SELECTED==true){
//                    Log.d(TAG,"selected ${thecurrent.username} from ${USERNAME}")
//                }
                }
                position+=1
            }
            finish()
            val recIntent = Intent(this, ReceiptActivity::class.java)
            recIntent.putExtra("RECEIVER",all_users)
            recIntent.putExtra("TIMING",LocalDateTime.now().toString())
            recIntent.putExtra("AMOUNT",((the_amount.getText().toString().toInt())/total_selections).toString()+" each")
            recIntent.putExtra("USERNAME",USERNAME)
            startActivity(recIntent)
//            Log.d(TAG,"${USERNAME}= ${the_recipient.getText().toString()}")
//
//            val current_trans = hashMapOf("USERNAME" to USERNAME, "RECIPIENT" to the_recipient.getText().toString(),"Status" to the_status.getText().toString(),"Amount" to the_amount.getText().toString(),"Time" to LocalDateTime.now().toString(),"Occasion" to occasion.getText().toString(),"Settled" to false )
//
//            Log.d(TAG, "time is ${LocalDateTime.now().toString()}")
//            transaction_details.add(current_trans)
//            val intent = Intent(this,ReceiptActivity::class.java)
//            intent.putExtra("USERNAME",USERNAME)
//            intent.putExtra("RECEIVER",the_recipient.getText().toString())
//            intent.putExtra("AMOUNT",the_amount.getText().toString())
//            intent.putExtra("TIMING",LocalDateTime.now().toString())
//            intent.putExtra("OCCASION",occasion.getText().toString())
//            //finish()
//            startActivity(intent)
//            finish()




        }
    }
    fun EventChangeListener(){

        val user_db = Firebase.firestore
        user_db.collection("users")
            .addSnapshotListener(object: EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {

                                userArrayList.add(dc.document.toObject(UserData::class.java))
                            selected_users +=1
                            che.add(false)


                        }
                    }
                    myadapter?.notifyDataSetChanged()

                }

            })
    }
}


