package com.rekyb.jyro.common

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error<out T>(val message: String) : DataState<T>()
}
