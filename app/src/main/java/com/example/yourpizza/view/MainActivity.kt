package com.example.yourpizza.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yourpizza.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragManager = supportFragmentManager
        val transaction = fragManager.beginTransaction()
        transaction.replace(R.id.content_frame, HomeScreenFragment(), "PizzaListingScreen")
        transaction.commit()
    }
}