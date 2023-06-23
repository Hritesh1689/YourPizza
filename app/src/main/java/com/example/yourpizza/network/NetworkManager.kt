package com.example.yourpizza.network

import android.content.Context
import com.example.yourpizza.models.PizzaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkManager(nmc: NetworkManagerCallback, context: Context) {

    private val apiJSONService = ApiClient.getClient()!!.create(
        ApiResponseInterface::class.java
    )
     private var nmc: NetworkManagerCallback
     private var context : Context ?=null

     init {
        this.nmc = nmc
        this.context = context
    }

       fun getPizzaList(){
             val call : Call<PizzaResponse> =apiJSONService.getPizzaList()
             getJSONResponseCallbackGenric(call)
       }

        private fun <T> getJSONResponseCallbackGenric(call: Call<T>) {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    nmc.onSuccessCallback(response)
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    nmc.onFailureCallback()
                }
            })
        }


}