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
import com.example.yourpizza.models.Size
import com.example.yourpizza.view.adapters.*
import com.example.yourpizza.view.adapters.listeners.CrustItemListener
import com.example.yourpizza.view.adapters.listeners.SizeItemListener
import com.example.yourpizza.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomeScreenFragment : Fragment(), SizeItemListener, CrustItemListener {

    var homeScreenBinding : HomeScreenBinding?=null
    var sizeSelectorDialog : BottomSheetDialog?=null
    var crustSelectorDialog : AlertDialog ?=null
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
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        homeScreenBinding!!.mainViewModel=mainViewModel
        homeScreenBinding!!.lifecycleOwner=this
        homeScreenBinding!!.progressBar.visibility=View.VISIBLE
        init()
        setObservers()
        return homeScreenBinding?.root
    }

    private fun setObservers() {
        mainViewModel?.getOpenCrustSelector()?.observe(viewLifecycleOwner, Observer {
            openCrustSelector(it)
        })

        mainViewModel?.getOpenSizeSelector()?.observe(viewLifecycleOwner, Observer {
            if(it!=null)
                 openSizeSeletor(it)
        })

        mainViewModel?.getCloseCrustDialog()?.observe(viewLifecycleOwner, Observer {
            crustSelectorDialog!!.cancel()
        })
    }

    private fun openSizeSeletor(list: Crust) {
        sizeSelectorDialog = BottomSheetDialog(requireContext())
        val sizeSelectorDialogBinding : SizeSelectorDialogBinding = SizeSelectorDialogBinding.inflate(LayoutInflater.from(context),null,false)
        sizeSelectorDialog!!.setContentView(sizeSelectorDialogBinding.root)

        val adapter = SizeItemAdapter(requireContext(),this, list.sizes, list.defaultSize)
        sizeSelectorDialogBinding.sizeRecyclerView.layoutManager = LinearLayoutManager(context)
        sizeSelectorDialogBinding.sizeRecyclerView.adapter = adapter

        sizeSelectorDialog!!.show()
    }

    private fun openCrustSelector(crusts : List<Crust>) {
        val dialogBuilder = AlertDialog.Builder(context)
        val crustSelectorDialogBinding : CrustsSelectorDialogBinding = CrustsSelectorDialogBinding.inflate(LayoutInflater.from(context),null,false)
        crustSelectorDialogBinding.viewModel=mainViewModel
        crustSelectorDialogBinding.lifecycleOwner=this

        val dialogView = crustSelectorDialogBinding.root
        dialogBuilder.setView(dialogView)

        val adapter = CrustItemAdapter(requireContext(),this,
            crusts as ArrayList<Crust>, mainViewModel?.getPizzaList()?.value?.defaultCrust!!)
        crustSelectorDialogBinding.crustRecyclerView.layoutManager = LinearLayoutManager(context)
        crustSelectorDialogBinding.crustRecyclerView.adapter = adapter

        crustSelectorDialog = dialogBuilder.create()
        crustSelectorDialog!!.show()
    }

    private fun init() {
        getPizzaListFromServer()
    }

    private fun getPizzaListFromServer() {
        mainViewModel?.getPizzaList()?.observe(viewLifecycleOwner, Observer {
            homeScreenBinding!!.progressBar.visibility=View.GONE
            mainViewModel?.setButtonEnableTrue()
        })
    }

    override fun closeSizeSelector() {
        sizeSelectorDialog?.cancel()
    }

    override fun setDefaultSize(crust: Crust) {
        mainViewModel?.setDefaultSize(crust)
    }

    override fun setCurrentPrice(size: Size) {
        mainViewModel?.setCurrentPrice(size)
    }

    override fun openSizeSelector(crust: Crust) {
        mainViewModel?.openSizeSelector(crust)
    }

}