package com.rekyb.jyro.domain.use_case.local

import com.rekyb.jyro.repository.FavouritesRepositoryImpl
import javax.inject.Inject

class ClearFavListUseCase @Inject constructor(
    private val repo: FavouritesRepositoryImpl
) {
    suspend operator fun invoke() = repo.clearFavList()
}
