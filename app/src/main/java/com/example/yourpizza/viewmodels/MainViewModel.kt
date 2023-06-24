package com.example.yourpizza.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourpizza.models.Crust
import com.example.yourpizza.models.PizzaInCart
import com.example.yourpizza.models.PizzaResponse
import com.example.yourpizza.models.Size
import com.example.yourpizza.repositories.PizzaReposiory

class MainViewModel() : ViewModel() {

    private var pizzHashMap : HashMap<String,PizzaInCart> = HashMap()

    private val pizzaAddedInCart :  MutableLiveData<List<PizzaInCart>> = MutableLiveData()
    private val openCrustSelector :  MutableLiveData<List<Crust>> = MutableLiveData()
    private val openSizeSelector :  MutableLiveData<Crust> = MutableLiveData()
    private val closeSizeSelector :  MutableLiveData<Int> = MutableLiveData()

    private val currentPrice : MutableLiveData<Size> = MutableLiveData()
    private val totalPizzaInCart : MutableLiveData<String> = MutableLiveData<String>().apply { value="0" }
    private val totalCost : MutableLiveData<String> = MutableLiveData<String>().apply { value="0" }

    private val navigateToMyCart :  MutableLiveData<Int> = MutableLiveData()
    private val selectedPizza :  MutableLiveData<Crust> = MutableLiveData()
     var defaultCrust : Crust ?=null


    var pizzaRepository : PizzaReposiory?= null
    var pizzaResponseLiveData : LiveData<PizzaResponse>?= null

    init {
        pizzaRepository= PizzaReposiory.getInstance()
        pizzaRepository?.init()
    }

    fun getPizzaList() : LiveData<PizzaResponse>? {
        if(pizzaResponseLiveData==null) {
             pizzaResponseLiveData =  pizzaRepository?.getPizzaListfromServer()
        }
        return pizzaResponseLiveData
    }

    fun getParticularPizza(pos: Int) : Crust? {
        return pizzaResponseLiveData?.value?.crusts?.get(pos)
    }

    fun setCurrentPrice(size: Size){
        currentPrice.value=size
    }

    fun getCurrentPrice() : LiveData<Size>{
        return currentPrice
    }

     fun setTotalPizzaCounts(){
        var alreadyCount= totalPizzaInCart.value?.toInt()
        if (alreadyCount != null) {
            alreadyCount=alreadyCount+1
        }
         totalPizzaInCart.value=alreadyCount.toString()

         var prevTotalCost= totalCost.value?.toDouble()
         if (prevTotalCost != null) {
             prevTotalCost=prevTotalCost+currentPrice.value?.price!!
         }
         totalCost.value=prevTotalCost.toString()

         val key = java.lang.StringBuilder()
         key.append(openSizeSelector.value?.name).append("_").append(currentPrice.value?.name).append("_").append(currentPrice.value?.price)
         if(pizzHashMap.containsKey(key.toString())){
             val alreadyAddedPizza= pizzHashMap.get(key.toString())
             alreadyAddedPizza?.quantity=alreadyAddedPizza?.quantity!!.plus(1)
             pizzHashMap.put(key.toString(),alreadyAddedPizza)
         }else{
             lateinit var newAddedPizza : PizzaInCart
             if(openSizeSelector.value!=null)
                newAddedPizza= PizzaInCart(openSizeSelector.value?.id!!,openSizeSelector.value?.name!!,currentPrice.value?.name!!,currentPrice.value?.price!!,1)
             else
                 newAddedPizza= PizzaInCart(defaultCrust!!.id,defaultCrust!!.name,currentPrice.value?.name!!,currentPrice.value?.price!!,1)
             pizzHashMap.put(key.toString(),newAddedPizza)
         }
    }

    fun getTotalPizzaCount() : LiveData<String>{
        return totalPizzaInCart
    }

    fun getTotalCost() : LiveData<String>{
        return totalCost
    }

    fun getOpenCrustSelector(): LiveData<List<Crust>> {
        return openCrustSelector
    }

    fun getPizzaAddedInCart(): LiveData<List<PizzaInCart>> {
        return pizzaAddedInCart
    }

    fun getOpenSizeSelector(): LiveData<Crust> {
        return openSizeSelector
    }

    fun getCloseSizeSelector(): LiveData<Int> {
        return closeSizeSelector
    }

    fun getNavigateToMyCartFragment(): LiveData<Int> {
        return navigateToMyCart
    }


    fun getSelectedPizza(): LiveData<Crust> {
        return selectedPizza
    }

    fun openCrustSelector() {
        openCrustSelector.value = pizzaResponseLiveData?.value?.crusts
    }

    fun addPizzaInCart() {
        openCrustSelector.value = pizzaResponseLiveData?.value?.crusts
    }

    fun  openSizeSelector(sizes: Crust) {
        openSizeSelector.value = sizes
    }

    fun  setDefaultSize(crust: Crust) {
        defaultCrust=crust
    }


    fun navigateToMyCartFragment(){
        navigateToMyCart.postValue(1)
        pizzaAddedInCart.value= ArrayList<PizzaInCart>(pizzHashMap.values)
    }

    fun closeSizeSelector() {
       closeSizeSelector.value=1
    }

    fun removePizzaFromCart(name :String,price : String,sizeName : String){
        val key = java.lang.StringBuilder()
        key.append(name).append("_").append(sizeName).append("_").append(price)
        if(pizzHashMap.containsKey(key.toString())){
            val alreadyAddedPizza= pizzHashMap.get(key.toString())
            alreadyAddedPizza?.quantity=alreadyAddedPizza?.quantity!!.minus(1)
            if(alreadyAddedPizza.quantity==0)
              pizzHashMap.remove(key.toString())

            pizzaAddedInCart.value= ArrayList<PizzaInCart>(pizzHashMap.values)

            var alreadyCount= totalPizzaInCart.value?.toInt()
            if (alreadyCount != null) {
                alreadyCount=alreadyCount-1
            }
            totalPizzaInCart.value=alreadyCount.toString()

            var prevTotalCost= totalCost.value?.toDouble()
            if (prevTotalCost != null) {
                prevTotalCost=prevTotalCost - price.toDouble()
            }
            totalCost.value=prevTotalCost.toString()
        }
    }

}