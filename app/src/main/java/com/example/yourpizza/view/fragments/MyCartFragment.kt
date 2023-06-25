package com.example.yourpizza.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.R
import com.example.yourpizza.databinding.MyCartFragmentBinding
import com.example.yourpizza.models.PizzaInCart
import com.example.yourpizza.view.adapters.CartItemAdapter
import com.example.yourpizza.view.adapters.listeners.CartItemListener
import com.example.yourpizza.viewmodels.MainViewModel

class MyCartFragment : Fragment() , CartItemListener {
    var myCartFragmentBinding : MyCartFragmentBinding?=null
    var mainViewModel : MainViewModel?=null
    var cartItemAdapter : CartItemAdapter?=null

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
        myCartFragmentBinding= DataBindingUtil.inflate(inflater, R.layout.my_cart_fragment, container, false);
        mainViewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        myCartFragmentBinding!!.viewModel=mainViewModel
        myCartFragmentBinding!!.lifecycleOwner=this
        init()
        return myCartFragmentBinding?.root
    }

    private fun init() {
        cartItemAdapter= CartItemAdapter(requireContext(),this)
        myCartFragmentBinding?.cartRecycler?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        myCartFragmentBinding?.cartRecycler?.adapter=cartItemAdapter


        getListOfCartItems()
    }

    private fun getListOfCartItems() {
        mainViewModel?.getPizzaAddedInCart()?.observe(viewLifecycleOwner, Observer {
            cartItemAdapter?.setPizzaList(it as ArrayList<PizzaInCart>)
            cartItemAdapter?.notifyDataSetChanged()
        })
    }

    override fun removePizzaFromCart(name: String, price: String, sizeName: String) {
        mainViewModel?.removePizzaFromCart(name, price, sizeName)
    }

}