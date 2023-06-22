package com.example.yourpizza

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.yourpizza.databinding.OrderFragmentBinding

class OrderFragment: Fragment() {

    var orderFragmentBinding : OrderFragmentBinding ?=null

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
        orderFragmentBinding= DataBindingUtil.inflate(inflater, R.layout.order_fragment, container, false);
        return orderFragmentBinding?.root
    }
}