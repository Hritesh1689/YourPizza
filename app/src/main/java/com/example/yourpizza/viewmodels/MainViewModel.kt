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

    // to handle the quantity of same custom pizza
    private var pizzHashMap : HashMap<String,PizzaInCart> = HashMap()

    // to show all added pizza in my cart fragment
    private val pizzaAddedInCart :  MutableLiveData<List<PizzaInCart>> = MutableLiveData()

    private val backPressed : MutableLiveData<Boolean> = MutableLiveData()
    private val isButtonsEnabled : MutableLiveData<Boolean> = MutableLiveData()

    private val closeCrustDialog : MutableLiveData<Boolean> = MutableLiveData()
    private val openCrustSelector :  MutableLiveData<List<Crust>> = MutableLiveData()
    private val openSizeSelector :  MutableLiveData<Crust> = MutableLiveData()

    //to handle price of current choice
    private val currentPrice : MutableLiveData<Size> = MutableLiveData()

    //to handle total pizza count in cart
    private val totalPizzaCountInCart : MutableLiveData<String> = MutableLiveData<String>().apply { value="0" }

    //to handle total order cost on cart page
    private val totalCost : MutableLiveData<String> = MutableLiveData<String>().apply { value="0" }

    private val navigateToMyCart :  MutableLiveData<Int> = MutableLiveData()
    private var defaultCrust : Crust ?=null
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


    fun setCurrentPrice(size: Size){
        currentPrice.value=size
    }

    fun getCurrentPrice() : LiveData<Size>{
        return currentPrice
    }

    fun getTotalPizzaCount() : LiveData<String>{
        return totalPizzaCountInCart
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

    fun getBackPressed(): LiveData<Boolean> {
        return backPressed
    }



    fun getCloseCrustDialog(): LiveData<Boolean> {
        return closeCrustDialog
    }

    fun getNavigateToMyCartFragment(): LiveData<Int> {
        return navigateToMyCart
    }


    fun openCrustSelector() {
        openCrustSelector.value = pizzaResponseLiveData?.value?.crusts
        openSizeSelector.value=null
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

    fun onBackPressed(){
        backPressed.value=true
    }

    fun setButtonEnableTrue() {
         isButtonsEnabled.value=true
    }

    fun closeCrustDialog(){
        closeCrustDialog.value=true
    }

    fun removePizzaFromCart(name :String,price : String,sizeName : String){
        val key = java.lang.StringBuilder()
        key.append(name).append("_").append(sizeName)
        if(pizzHashMap.containsKey(key.toString())){
            val alreadyAddedPizza= pizzHashMap.get(key.toString())
            alreadyAddedPizza?.quantity=alreadyAddedPizza?.quantity!!.minus(1)
            if(alreadyAddedPizza.quantity==0)
                pizzHashMap.remove(key.toString())

            // updated pizza list to show at cart page
            pizzaAddedInCart.value= ArrayList<PizzaInCart>(pizzHashMap.values)

            updatePizzaCountInCart(false)
            updateTotalCostInCart(false,price.toDouble())
        }
    }

    fun setTotalPizzaCounts(){
        updatePizzaCountInCart(true)
        updateTotalCostInCart(true,currentPrice.value?.price!!)

        val key = java.lang.StringBuilder()
        if(openSizeSelector.value!=null)
            key.append(openSizeSelector.value?.name).append("_").append(currentPrice.value?.name)
        else
            key.append(defaultCrust?.name).append("_").append(currentPrice.value?.name)

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

        closeCrustDialog()
    }

    fun updatePizzaCountInCart(toIncrease : Boolean){
        var alreadyCount= totalPizzaCountInCart.value?.toInt()
        if (alreadyCount != null) {
            if(toIncrease) alreadyCount=alreadyCount+1
            else alreadyCount=alreadyCount-1
        }
        totalPizzaCountInCart.value=alreadyCount.toString()
    }

    fun updateTotalCostInCart(toIncrease: Boolean, price: Double){
        var prevTotalCost= totalCost.value?.toDouble()
        if (prevTotalCost != null) {
            if(toIncrease) prevTotalCost=prevTotalCost + price
            else prevTotalCost=prevTotalCost - price
        }
        totalCost.value=prevTotalCost.toString()
    }

    fun isEnable() : LiveData<Boolean>{
        return isButtonsEnabled
    }

}