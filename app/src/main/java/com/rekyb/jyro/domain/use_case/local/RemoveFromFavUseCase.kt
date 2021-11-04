package com.rekyb.jyro.domain.use_case.local

import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.repository.FavouritesRepositoryImpl
import javax.inject.Inject

class RemoveFromFavUseCase @Inject constructor(
    private val repo: FavouritesRepositoryImpl,
) {
    suspend operator fun invoke(user: UserDetailsModel) = repo.removeUserFromFavList(user)
}
