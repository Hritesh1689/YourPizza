package com.example.yourpizza

import android.content.Context

class PizzaReposiory private constructor(){

    companion object {

        @Volatile
        private var instance: PizzaReposiory? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PizzaReposiory().also { instance = it }
            }
    }




    fun init(context: Context) {

    }

    fun getPizzaListfromServer() {
        TODO("Not yet implemented")
    }
}