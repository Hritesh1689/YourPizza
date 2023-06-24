package com.example.yourpizza.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.R
import com.example.yourpizza.databinding.SizeItemLayoutBinding
import com.example.yourpizza.models.Size
import com.example.yourpizza.viewmodels.MainViewModel

class SizeItemAdapter(var activity: FragmentActivity?, var sizeList : ArrayList<Size>, var preSelectedId :Int) : RecyclerView.Adapter<SizeItemAdapter.SizeItemViewHolder>() {
    var mainViewModel : MainViewModel?=null
    var selectedPos =-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        mainViewModel= ViewModelProvider(activity!!).get(MainViewModel::class.java)
        val binding: SizeItemLayoutBinding = SizeItemLayoutBinding.inflate(layoutInflater, parent, false)
        return SizeItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sizeList.size
    }


    override fun onBindViewHolder(holder: SizeItemViewHolder, position: Int) {
        holder.sizeItemLayoutBinding.size=sizeList.get(position)
        if(sizeList.get(position).id==preSelectedId || position== selectedPos) holder.sizeItemLayoutBinding.mainLayout.setBackgroundColor(activity!!.resources.getColor(
            R.color.grey))
        else holder.sizeItemLayoutBinding.mainLayout.setBackgroundColor(activity!!.resources.getColor(
            R.color.white))

        holder.sizeItemLayoutBinding.mainLayout.setOnClickListener {
            selectedPos=position
            preSelectedId=-1
            notifyDataSetChanged()
            mainViewModel?.setCurrentPrice(sizeList.get(position))
            mainViewModel?.closeSizeSelector()
        }
    }

    class SizeItemViewHolder(val sizeItemLayoutBinding : SizeItemLayoutBinding) : RecyclerView.ViewHolder(sizeItemLayoutBinding.root) {
    }
}