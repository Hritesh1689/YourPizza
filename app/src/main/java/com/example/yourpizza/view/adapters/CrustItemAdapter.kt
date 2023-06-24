package com.example.yourpizza.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.R
import com.example.yourpizza.databinding.CrustItemLayoutBinding
import com.example.yourpizza.models.Crust
import com.example.yourpizza.viewmodels.MainViewModel

class CrustItemAdapter(var activity: FragmentActivity?,var crustList : ArrayList<Crust>,var preSelectedId :Int) : RecyclerView.Adapter<CrustItemAdapter.CrustItemViewHolder>() {
    var mainViewModel : MainViewModel?=null
    var selectedPos=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrustItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        mainViewModel= ViewModelProvider(activity!!).get(MainViewModel::class.java)
        val binding: CrustItemLayoutBinding = CrustItemLayoutBinding.inflate(layoutInflater, parent, false)
        return CrustItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return crustList.size
    }


    override fun onBindViewHolder(holder: CrustItemViewHolder, position: Int) {
       holder.crustItemLayoutBinding.crust=crustList.get(position)
        if(crustList.get(position).id==preSelectedId || position== selectedPos){
            if(crustList.get(position).id==preSelectedId){
                val defaultSizeId= crustList.get(position).defaultSize
                mainViewModel?.setDefaultSize(crustList.get(position))
                for(sizeItem in crustList.get(position).sizes){
                    if(sizeItem.id==defaultSizeId){
                        mainViewModel?.setCurrentPrice(sizeItem)
                    }
                }
            }
            holder.crustItemLayoutBinding.mainLayout.setBackgroundColor(activity!!.resources.getColor(R.color.grey))
        }
        else holder.crustItemLayoutBinding.mainLayout.setBackgroundColor(activity!!.resources.getColor(R.color.white))

        holder.crustItemLayoutBinding.mainLayout.setOnClickListener {
            selectedPos=position
            preSelectedId=-1
            notifyDataSetChanged()
            mainViewModel?.openSizeSelector(crustList.get(position))
        }
    }

    class CrustItemViewHolder(val crustItemLayoutBinding : CrustItemLayoutBinding) : RecyclerView.ViewHolder(crustItemLayoutBinding.root) {
    }
}