package com.example.newapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.oAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.time.LocalDateTime

class UserAdapter( private val context: TransactionActivity,private val user_list:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    val mybtn:Button = context.findViewById(R.id.split_button)
    val splitting = context.findViewById<TextView>(R.id.amount)

    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        val thename:TextView = v.findViewById(R.id.sub_recip)
//        val theamnt:TextView = v.findViewById(R.id.amnt)
        val thecheck:CheckBox = v.findViewById(R.id.ischeck)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.add_user,parent, false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return user_list.size
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder:UserAdapter.UserViewHolder, position: Int){

        val newList = user_list[position]
        holder.thename.text = newList.username
        holder.thecheck.isChecked = newList.SELECTED

        holder.thecheck.setOnClickListener{
            if (context.che[position]==false){
                context.che[position] = true
            }else{
                context.che[position]==false
            }

        }




    }




}