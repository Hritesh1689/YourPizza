package com.example.yourpizza.models

data class PizzaResponse(var name: String,var isVeg : Boolean, var description : String,
                          var defaultCrust : Int, var crusts : ArrayList<Crust>)
