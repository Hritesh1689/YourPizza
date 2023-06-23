package com.example.yourpizza.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourpizza.R
import com.example.yourpizza.databinding.PizzaDetailFragmentBinding
import com.example.yourpizza.models.Crust
import com.example.yourpizza.viewmodels.MainViewModel


class PizzaDetailsFragment: Fragment() {

    var orderFragmentBinding : PizzaDetailFragmentBinding ?=null
    var selectedFromDropdown =false
    var sizeAdapter: ArrayAdapter<String>?=null
    var specificPizza  : Crust? =null
    lateinit var mContext :Context
    var mainViewModel : MainViewModel?=null

    override fun onAttach(context: Context) {
        this.mContext=context
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
        orderFragmentBinding= DataBindingUtil.inflate(inflater,
            R.layout.pizza_detail_fragment, container, false);
        mainViewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        return orderFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        val posPizza: Int = requireArguments().get("pos") as Int
        specificPizza = mainViewModel?.getParticularPizza(posPizza) as Crust
        val yourData : ArrayList<String> = ArrayList()
        for(size in specificPizza!!.sizes) yourData.add(size.name+" (${size.price} rupees)")
        sizeAdapter = ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, yourData)
        orderFragmentBinding?.setPizzasName(specificPizza!!.name+" "+context?.resources?.getString(R.string.pizza))
        orderFragmentBinding?.setSizeName(specificPizza!!.sizes[0].name)
        orderFragmentBinding?.setSizePrice(specificPizza!!.sizes[0].price.toString())

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        orderFragmentBinding?.sizeSuggestionTextview?.setAdapter(sizeAdapter)
        orderFragmentBinding?.sizeSuggestionTextview?.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            selectedFromDropdown=true
            val selectedText : List<String> = (parent.getItemAtPosition(position) as String).split(" ")
            orderFragmentBinding?.setSizeName( selectedText[0])
            orderFragmentBinding?.setSizePrice( selectedText[1].substring(1))
        })
    }
}