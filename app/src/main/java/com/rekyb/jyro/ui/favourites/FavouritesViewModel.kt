package com.rekyb.jyro.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.domain.use_case.local.ClearFavListUseCase
import com.rekyb.jyro.domain.use_case.local.GetFavListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavList: GetFavListUseCase,
    private val clearFavList: ClearFavListUseCase
): ViewModel() {

    private val _favouritesState: MutableLiveData<DataState<List<UserDetailsModel>>> = MutableLiveData()
    val favouritesState: LiveData<DataState<List<UserDetailsModel>>> = _favouritesState

    init {
        getFavouritesList()
    }

    private fun getFavouritesList() {
        viewModelScope.launch {
            getFavList().collect { _favouritesState.postValue(it) }
        }
    }

}