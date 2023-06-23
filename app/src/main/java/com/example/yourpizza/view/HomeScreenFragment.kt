package com.example.yourpizza.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.R
import com.example.yourpizza.databinding.HomeScreenBinding
import com.example.yourpizza.viewmodels.MainViewModel


class HomeScreenFragment : Fragment() {

    var homeScreenBinding : HomeScreenBinding?=null
    var mainViewModel : MainViewModel?=null
    var pizzaItemAdapter : PizzaItemAdapter?=null
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
        homeScreenBinding= DataBindingUtil.inflate(inflater, R.layout.home_screen, container, false);
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        init()
        return homeScreenBinding?.root
    }

    private fun init() {
        pizzaItemAdapter= PizzaItemAdapter(activity)
        mLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        homeScreenBinding?.pizzaRecycler?.layoutManager = mLinearLayoutManager
        homeScreenBinding?.pizzaRecycler?.adapter=pizzaItemAdapter

        mainViewModel?.getNavigateToFragmentB()
            ?.observe(viewLifecycleOwner, Observer { it ->
              navigateToFragmentB(it)
            })

        getPizzaListFromServer()
    }

    private fun navigateToFragmentB(pos : Int) {
        val pizzaDetailFrag = PizzaDetailsFragment()
        val args = Bundle()
        args.putInt("pos", pos)
        pizzaDetailFrag.setArguments(args)

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(
            R.id.content_frame, pizzaDetailFrag)
        fragmentTransaction.addToBackStack("openPizzaDetail")
        fragmentTransaction.commit()
    }

    private fun getPizzaListFromServer() {
        mainViewModel?.getPizzaListFromServer()?.observe(viewLifecycleOwner, Observer {
            pizzaItemAdapter?.setPizzaList(it.crusts)
            pizzaItemAdapter?.notifyItemRangeInserted(0,it.crusts.size)
        })
    }

}