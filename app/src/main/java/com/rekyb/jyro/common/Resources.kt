package com.rekyb.jyro.common

sealed class Resources<out T> {
    object Loading : Resources<Nothing>()
    data class Success<out T>(val data: T) : Resources<T>()
    data class Error<out T>(val message: String) : Resources<T>()
}
