package com.example.yourpizza.network


import com.example.yourpizza.models.PizzaResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiResponseInterface {

    //bankread
    // @FormUrlEncoded
    // @GET("users/otherdetail/")
    // Call<Object> getBankDetails(@FieldMap HashMap<String, String> params);

    @GET("v1/pizza/1")
    fun getPizzaList(): Call<PizzaResponse>
}