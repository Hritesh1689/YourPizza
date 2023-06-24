package com.example.yourpizza.view

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
import com.example.yourpizza.viewmodels.MainViewModel

class MyCartFragment : Fragment() {
    var myCartFragmentBinding : MyCartFragmentBinding?=null
    var mainViewModel : MainViewModel?=null
    var cartItemAdapter : CartItemAdapter?=null
    private var mLinearLayoutManager : LinearLayoutManager? = null
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
        cartItemAdapter= CartItemAdapter(activity)
        mLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        myCartFragmentBinding?.cartRecycler?.layoutManager = mLinearLayoutManager
        myCartFragmentBinding?.cartRecycler?.adapter=cartItemAdapter


        getListOfCartItems()
    }

    private fun getListOfCartItems() {
        mainViewModel?.getPizzaAddedInCart()?.observe(viewLifecycleOwner, Observer {
            cartItemAdapter?.setPizzaList(it as ArrayList<PizzaInCart>)
            cartItemAdapter?.notifyDataSetChanged()
        })
    }

}