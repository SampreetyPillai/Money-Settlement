package com.example.newapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView
import com.example.newapp.R.*


class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_receipt)

        var newintent = getIntent()
        val USERNAME = newintent.getStringExtra("USERNAME").toString()
        val RECEIVER = newintent.getStringExtra("RECEIVER").toString()
        val AMOUNT = newintent.getStringExtra("AMOUNT").toString()
        val TIMING = newintent.getStringExtra("TIMING").toString()



        val rec_from:TextView = findViewById(R.id.receipt_from)
        val rec_to:TextView = findViewById(R.id.receipt_to)
        val rec_amount:TextView = findViewById(R.id.receipt_amount)
        val rec_date:TextView = findViewById(R.id.receipt_date)


        rec_from.setText("From: "+USERNAME)
        rec_to.setText("To: "+RECEIVER)
        rec_amount.setText("Transaction amount: "+AMOUNT)
        rec_date.setText("ON: "+TIMING)

    }
}