package com.example.yourpizza

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourpizza.databinding.HomeScreenBinding
import com.example.yourpizza.databinding.OrderFragmentBinding

class HomeScreen : Fragment() {

    var homeScreenBinding : HomeScreenBinding?=null
    var mainViewModel : MainViewModel?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeScreenBinding= DataBindingUtil.inflate(inflater, R.layout.home_screen, container, false);
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        init()
        return homeScreenBinding?.root
    }

    private fun init() {
        getPizzaListFromServer()
    }

    private fun getPizzaListFromServer() {
        mainViewModel?.getPizzaListFromServer()
    }

}