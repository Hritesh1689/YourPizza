package com.example.yourpizza.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.databinding.PizzaItemLayoutBinding
import com.example.yourpizza.models.Crust
import com.example.yourpizza.viewmodels.MainViewModel

class PizzaItemAdapter(var activity: FragmentActivity?) : RecyclerView.Adapter<PizzaItemAdapter.PizzaItemViewHolder>() {
    var mainViewModel : MainViewModel?=null
    var pizzasList : ArrayList<Crust> =ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        mainViewModel= ViewModelProvider(activity!!).get(MainViewModel::class.java)
        val binding: PizzaItemLayoutBinding = PizzaItemLayoutBinding.inflate(layoutInflater, parent, false)
        return PizzaItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pizzasList.size
    }

    fun setPizzaList(pizzaList:ArrayList<Crust>){
        pizzasList=pizzaList
    }


    override fun onBindViewHolder(holder: PizzaItemViewHolder, position: Int) {
       holder.pizzaItemLayoutBinding.setPizza(pizzasList.get(position))
        holder.pizzaItemLayoutBinding.executePendingBindings()
        holder.pizzaItemLayoutBinding.parentLayout.setOnClickListener(View.OnClickListener {
            mainViewModel?.navigateToFragmentB(position)
        })
    }

    class PizzaItemViewHolder(val pizzaItemLayoutBinding : PizzaItemLayoutBinding) : RecyclerView.ViewHolder(pizzaItemLayoutBinding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(product: Crust, position: Int) {
            with(pizzaItemLayoutBinding){

            }
        }
    }
}