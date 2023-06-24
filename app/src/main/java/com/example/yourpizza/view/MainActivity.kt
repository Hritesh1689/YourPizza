package com.example.yourpizza.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.yourpizza.R
import com.example.yourpizza.view.fragments.HomeScreenFragment
import com.example.yourpizza.view.fragments.MyCartFragment
import com.example.yourpizza.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    var mainViewModel : MainViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel= ViewModelProvider(this).get(MainViewModel::class.java)

        setObservers()
        openHomeScreenFragment()
    }

    private fun setObservers() {
        mainViewModel?.getNavigateToMyCartFragment()
            ?.observe(this, Observer { it ->
                navigateToMyCartFragment()
            })
    }

    private fun navigateToMyCartFragment() {
        val myCartFrag = MyCartFragment()
        val fragManager = supportFragmentManager
        val transaction = fragManager.beginTransaction()
        transaction.add(
            R.id.content_frame, myCartFrag)
        transaction.addToBackStack("openCartFragment")
        transaction.commit()
    }

    private fun openHomeScreenFragment() {
        val fragManager = supportFragmentManager
        val transaction = fragManager.beginTransaction()
        transaction.replace(R.id.content_frame, HomeScreenFragment(), "PizzaListingScreen")
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}