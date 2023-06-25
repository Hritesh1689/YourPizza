package com.example.yourpizza.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.R
import com.example.yourpizza.databinding.SizeItemLayoutBinding
import com.example.yourpizza.models.Size
import com.example.yourpizza.view.adapters.listeners.SizeItemListener

class SizeItemAdapter(var context : Context, val sizeItemListener: SizeItemListener, var sizeList : ArrayList<Size>, var preSelectedId :Int) : RecyclerView.Adapter<SizeItemAdapter.SizeItemViewHolder>() {
    var selectedPos =-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: SizeItemLayoutBinding = SizeItemLayoutBinding.inflate(layoutInflater, parent, false)
        return SizeItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sizeList.size
    }


    override fun onBindViewHolder(holder: SizeItemViewHolder, position: Int) {
        holder.sizeItemLayoutBinding.size="${sizeList.get(position).name} - (${sizeList.get(position).price} rupee)"
        if(sizeList.get(position).id==preSelectedId || position== selectedPos) holder.sizeItemLayoutBinding.mainLayout.setBackgroundColor(context.resources.getColor(
            R.color.grey))
        else holder.sizeItemLayoutBinding.mainLayout.setBackgroundColor(context.resources.getColor(
            R.color.white))

        holder.sizeItemLayoutBinding.mainLayout.setOnClickListener {
            selectedPos=position
            preSelectedId=-1
            notifyDataSetChanged()
            sizeItemListener.setCurrentPrice(sizeList.get(position))
            sizeItemListener.closeSizeSelector()
        }
    }

    class SizeItemViewHolder(val sizeItemLayoutBinding : SizeItemLayoutBinding) : RecyclerView.ViewHolder(sizeItemLayoutBinding.root) {
    }
}