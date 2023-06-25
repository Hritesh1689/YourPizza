package com.example.yourpizza.view.adapters.listeners

interface CartItemListener {
     fun removePizzaFromCart(name: String, price: String, sizeName: String)
}