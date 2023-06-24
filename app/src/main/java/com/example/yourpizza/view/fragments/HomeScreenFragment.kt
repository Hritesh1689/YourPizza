package com.example.yourpizza.view.fragments

import android.app.AlertDialog
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
import com.example.yourpizza.databinding.CrustsSelectorDialogBinding
import com.example.yourpizza.databinding.HomeScreenBinding
import com.example.yourpizza.databinding.SizeSelectorDialogBinding
import com.example.yourpizza.models.Crust
import com.example.yourpizza.view.adapters.CrustItemAdapter
import com.example.yourpizza.view.adapters.SizeItemAdapter
import com.example.yourpizza.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomeScreenFragment : Fragment() {

    var homeScreenBinding : HomeScreenBinding?=null
    var bottomSheetDialog : BottomSheetDialog?=null
    var alertDialog : AlertDialog ?=null
    var mainViewModel : MainViewModel?=null
//    var pizzaItemAdapter : PizzaItemAdapter?=null
//    private var mLinearLayoutManager : LinearLayoutManager? = null
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
        homeScreenBinding!!.mainViewModel=mainViewModel
        homeScreenBinding!!.lifecycleOwner=this
        init()
        setObservers()
        return homeScreenBinding?.root
    }

    private fun setObservers() {
        mainViewModel?.getOpenCrustSelector()?.observe(viewLifecycleOwner, Observer {
            openCrustSelector(it)
        })

        mainViewModel?.getOpenSizeSelector()?.observe(viewLifecycleOwner, Observer {
                 openSizeSeletor(it)
        })

        mainViewModel?.getCloseSizeSelector()?.observe(viewLifecycleOwner, Observer {
            bottomSheetDialog!!.cancel()
        })
    }

    private fun openSizeSeletor(list: Crust) {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        val sizeSelectorDialogBinding : SizeSelectorDialogBinding = SizeSelectorDialogBinding.inflate(LayoutInflater.from(context),null,false)
        bottomSheetDialog!!.setContentView(sizeSelectorDialogBinding.root)

        val layoutManager = LinearLayoutManager(context)
        sizeSelectorDialogBinding.sizeRecyclerView.layoutManager = layoutManager

        val adapter = SizeItemAdapter(activity, list.sizes, list.defaultSize)
        sizeSelectorDialogBinding.sizeRecyclerView.adapter = adapter

        bottomSheetDialog!!.show()
    }

    private fun openCrustSelector(crusts : List<Crust>) {
        val dialogBuilder = AlertDialog.Builder(context)
        val crustSelectorDialogBinding : CrustsSelectorDialogBinding = CrustsSelectorDialogBinding.inflate(LayoutInflater.from(context),null,false)
        crustSelectorDialogBinding.viewModel=mainViewModel
        crustSelectorDialogBinding.lifecycleOwner=this

        crustSelectorDialogBinding.done.setOnClickListener(View.OnClickListener {
             mainViewModel?.setTotalPizzaCounts()
             alertDialog!!.cancel()
        })

        val dialogView = crustSelectorDialogBinding.root
        dialogBuilder.setView(dialogView)
        val recyclerView: RecyclerView = dialogView.findViewById(R.id.crust_recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val adapter = CrustItemAdapter(activity,mainViewModel?.getPizzaList()?.value?.crusts!!, mainViewModel?.getPizzaList()?.value?.defaultCrust!!)
        recyclerView.adapter = adapter

        alertDialog = dialogBuilder.create()
        alertDialog!!.show()
    }

    private fun init() {
        getPizzaListFromServer()
    }

    private fun getPizzaListFromServer() {
        mainViewModel?.getPizzaList()
    }

}