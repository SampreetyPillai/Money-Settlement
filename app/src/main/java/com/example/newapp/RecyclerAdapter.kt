package com.example.newapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.oAuthProvider
import org.w3c.dom.Text

var user_data = db.collection("transactions")
//private var giver_array = arrayListOf("Sampreety", "Khushi", "Shanmukha", "Nishita","Mamta","Lahari")



// private val trans_list :ArrayList<newtrans>, private val raycontext:Context
//giver_array :MutableList<String>, val receiver_array :MutableList<String>, val amount_array :MutableList<String>, val date_array :MutableList<String>
class RecyclerAdapter(private val trans_list:ArrayList<newtrans>, private val context: HomeActivity): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder{

        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)



    }

    override fun getItemCount(): Int {
       return trans_list.size
    }

    override fun onBindViewHolder(holder:RecyclerAdapter.ViewHolder, position: Int){


        val currtrans: newtrans = trans_list[position]
        holder.giver_name.text = currtrans.USERNAME
        holder.receiver_name.text = currtrans.RECIPIENT
        holder.amount_trans.text ="Rs. "+ currtrans.Amount.toString()
        holder.the_occasion.text = currtrans.Occasion.toString()
        holder.the_timing.text = ""+currtrans.Time.toString()

        changeSettled(holder.mybutton, position, context)





    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


            var giver_name:TextView = itemView.findViewById(R.id.giver)
            var receiver_name:TextView = itemView.findViewById(R.id.receiver)
            var amount_trans:TextView = itemView.findViewById(R.id.amount_display)
            var the_timing:TextView = itemView.findViewById(R.id.date_time_display)
            var mybutton: Button = itemView.findViewById(R.id.t_button_check)
            var the_occasion: TextView = itemView.findViewById(R.id.occasion)





    }

    fun changeSettled(button: Button,position:Int, acti:Activity){
        button.setOnClickListener(){
            val currtrans: newtrans = trans_list[position]

            val username_access = currtrans.USERNAME.toString()
            val time_access = currtrans.Time.toString()
            val docId: String = ""


//            db.collection("transactions").document(currtrans.USERNAME.toString(),currtrans.USERNAME.toString())
            user_data.get()
                .addOnSuccessListener { result->
                    for (document in result){
                        if (document.data.get("USERNAME")==currtrans.USERNAME && document.data.get("RECIPIENT")==currtrans.RECIPIENT && document.data.get("Time")==currtrans.Time){

                            db.collection("transactions").document(document.id).update(mapOf("Settled" to true))
//                            val another = Intent(context, HomeActivity::class.java)




                            break
                        }
                    }
                    acti.recreate()
                }



            currtrans.Settled = true
            Log.d(TAG,"transaction settled")
        }
    }



}