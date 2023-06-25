package com.example.yourpizza.view.adapters.listeners

import com.example.yourpizza.models.Crust
import com.example.yourpizza.models.Size

interface CrustItemListener {
     fun setDefaultSize(crust: Crust)
     fun setCurrentPrice(sizeItem: Size)
     fun openSizeSelector(crust: Crust)
}