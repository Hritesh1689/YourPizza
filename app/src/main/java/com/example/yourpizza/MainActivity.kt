package com.example.yourpizza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragManager = supportFragmentManager
        val transaction = fragManager.beginTransaction()
        transaction.replace(R.id.content_frame, HomeScreen(), "bubble_conversation")
        transaction.commit()
    }
}