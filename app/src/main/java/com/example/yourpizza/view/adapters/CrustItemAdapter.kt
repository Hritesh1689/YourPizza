package com.example.yourpizza.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.R
import com.example.yourpizza.databinding.CrustItemLayoutBinding
import com.example.yourpizza.models.Crust
import com.example.yourpizza.view.adapters.listeners.CrustItemListener

class CrustItemAdapter(val context: Context, val crustItemListener: CrustItemListener, var crustList : ArrayList<Crust>, var preSelectedId :Int) : RecyclerView.Adapter<CrustItemAdapter.CrustItemViewHolder>() {
    var selectedPos=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrustItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
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
                crustItemListener.setDefaultSize(crustList.get(position))
                for(sizeItem in crustList.get(position).sizes){
                    if(sizeItem.id==defaultSizeId){
                        crustItemListener.setCurrentPrice(sizeItem)
                    }
                }
            }
            holder.crustItemLayoutBinding.mainLayout.setBackgroundColor(context.resources.getColor(R.color.grey))
        }
        else holder.crustItemLayoutBinding.mainLayout.setBackgroundColor(context.resources.getColor(R.color.white))

        holder.crustItemLayoutBinding.mainLayout.setOnClickListener {
            selectedPos=position
            preSelectedId=-1
            notifyDataSetChanged()
            crustItemListener.openSizeSelector(crustList.get(position))
        }
    }

    class CrustItemViewHolder(val crustItemLayoutBinding : CrustItemLayoutBinding) : RecyclerView.ViewHolder(crustItemLayoutBinding.root) {
    }
}