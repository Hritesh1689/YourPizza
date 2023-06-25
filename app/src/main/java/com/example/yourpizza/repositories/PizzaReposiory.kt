package com.example.yourpizza.repositories

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yourpizza.models.PizzaResponse
import com.example.yourpizza.network.NetworkManager
import com.example.yourpizza.network.NetworkManagerCallback
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PizzaReposiory private constructor() : NetworkManagerCallback {
    var pizzaMutableLiveData : MutableLiveData<PizzaResponse> = MutableLiveData()
    var serviceFailueUpdate : MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value= false }
    var application : Application ?=null
    companion object {
        @Volatile
        private var instance: PizzaReposiory? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PizzaReposiory().also { instance = it }
            }
    }

    fun init() {
       application = Application()
    }

    fun getPizzaListfromServer() : LiveData<PizzaResponse> {
        val netManager = NetworkManager(this, application as Context)
        netManager.getPizzaList()

        //to check service failure case
        //                android.os.Handler().postDelayed({
        //                   serviceFailueUpdate.value=true
        //                }, 4000)


       // storeDummyData()
        return pizzaMutableLiveData
    }

    fun getServiceFailureUpdate() : LiveData<Boolean>{
        return serviceFailueUpdate
    }

    fun storeDummyData(){
        val jsonString =
            "{\"crusts\":[{\"defaultSize\":2,\"id\":1,\"name\":\"Hand-tossed\",\"sizes\":[{\"id\":1,\"name\":\"Regular\",\"price\":235.0},{\"id\":2,\"name\":\"Medium\",\"price\":265.0},{\"id\":3,\"name\":\"Large\",\"price\":295.0}]},{\"defaultSize\":1,\"id\":2,\"name\":\"Cheese Burst\",\"sizes\":[{\"id\":1,\"name\":\"Medium\",\"price\":295.0},{\"id\":2,\"name\":\"Large\",\"price\":325.0},{\"id\":3,\"name\":\"Regular\",\"price\":170.0}]}],\"defaultCrust\":1,\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\"isVeg\":false,\"name\":\"Non-Veg Pizza\"}"
        CoroutineScope(Dispatchers.IO).launch {
            val body: PizzaResponse = Gson().fromJson(
                jsonString,
                PizzaResponse::class.java
            )

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    //  if (response.body() != null) {
                    pizzaMutableLiveData.postValue(body)
                    //  }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onSuccessCallback(response: Any?) {
        CoroutineScope(Dispatchers.IO).launch {
            val respon = response as Response<*>
            val jsonString = Gson().toJson(respon.body())
            Log.e("Hritesh_JSON====",jsonString)
            val body: PizzaResponse = Gson().fromJson(
                jsonString,
                PizzaResponse::class.java
            )

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    if (response.body() != null) {
                        pizzaMutableLiveData.postValue(body)
                    }else{
                        serviceFailueUpdate.value=true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    serviceFailueUpdate.value=true
                }
            }
        }


    }

    override fun onFailureCallback() {
          serviceFailueUpdate.value=true
    }
}