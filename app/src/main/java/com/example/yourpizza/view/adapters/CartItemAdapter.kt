package com.example.yourpizza.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourpizza.databinding.CartItemLayoutBinding
import com.example.yourpizza.models.PizzaInCart
import com.example.yourpizza.view.adapters.listeners.CartItemListener

class CartItemAdapter(val context: Context, val cartItemListener: CartItemListener) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {
    var pizzasList : ArrayList<PizzaInCart> =ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: CartItemLayoutBinding = CartItemLayoutBinding.inflate(layoutInflater, parent, false)
        return CartItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pizzasList.size
    }

    fun setPizzaList(pizzaList:ArrayList<PizzaInCart>){
        pizzasList=pizzaList
    }


    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.cartItemLayoutBinding.setPizza(pizzasList.get(position))
        holder.cartItemLayoutBinding.removeButton.setOnClickListener(View.OnClickListener {
            cartItemListener.removePizzaFromCart(pizzasList.get(position).name, pizzasList.get(position).price.toString() ,pizzasList.get(position).sizeName)
        })
    }

    class CartItemViewHolder(val cartItemLayoutBinding : CartItemLayoutBinding) : RecyclerView.ViewHolder(cartItemLayoutBinding.root) {
    }
}