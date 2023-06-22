package com.example.yourpizza

import android.app.Application
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var pizzaRepository : PizzaReposiory = PizzaReposiory

    fun MainViewModel(application: Application) {
        pizzaRepository.init(application)
    }

    fun getPizzaListFromServer() {
        pizzaRepository.getPizzaListfromServer()
    }

}