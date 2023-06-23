package com.example.yourpizza.models

import com.example.yourpizza.models.Size

data class Crust(var id : Int, var name : String, var defaultSize : Int,
                 var sizes : ArrayList<Size>)
