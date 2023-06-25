package com.example.yourpizza.view.adapters.listeners

import com.example.yourpizza.models.Size

interface SizeItemListener {
    fun closeSizeSelector()
    fun setCurrentPrice(size: Size)
}