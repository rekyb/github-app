package com.rekyb.jyro.domain.use_case.local

import com.rekyb.jyro.data.repository.FavouritesRepositoryImpl
import javax.inject.Inject

class ClearFavListUseCase @Inject constructor(
    private val repositoryImpl: FavouritesRepositoryImpl
) {

    suspend operator fun invoke() = repositoryImpl.clearFavList()
}
