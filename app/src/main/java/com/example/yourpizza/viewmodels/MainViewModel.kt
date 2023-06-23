package com.example.yourpizza.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourpizza.models.Crust
import com.example.yourpizza.models.PizzaResponse
import com.example.yourpizza.repositories.PizzaReposiory
import com.example.yourpizza.toOpenPizzaDetail

class MainViewModel() : ViewModel() {

    private val navigateToFragmentB :  MutableLiveData<Int> = MutableLiveData()
    var pizzaRepository : PizzaReposiory?= null
    var pizzaResponseLiveData : LiveData<PizzaResponse>?= null

    init {
        pizzaRepository= PizzaReposiory.getInstance()
        pizzaRepository?.init()
    }

    fun getPizzaListFromServer() : LiveData<PizzaResponse>? {
        if(pizzaResponseLiveData==null) {
             pizzaResponseLiveData =  pizzaRepository?.getPizzaListfromServer()
        }
        return pizzaResponseLiveData
    }

    fun getParticularPizza(pos: Int) : Crust? {
        return pizzaResponseLiveData?.value?.crusts?.get(pos)
    }


    fun getNavigateToFragmentB(): LiveData<Int> {
        return navigateToFragmentB
    }

    fun navigateToFragmentB( pos : Int) {
        navigateToFragmentB.value = pos
    }

}