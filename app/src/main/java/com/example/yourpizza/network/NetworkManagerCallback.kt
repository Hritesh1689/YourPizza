package com.example.yourpizza.network

interface NetworkManagerCallback {
    fun onSuccessCallback(response: Any?)

    fun onFailureCallback()
}