package com.rekyb.jyro.domain.use_case.local

import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.repository.FavouritesRepositoryImpl
import javax.inject.Inject

class RemoveFromFavUseCase @Inject constructor(
    private val repositoryImpl: FavouritesRepositoryImpl,
) {

    suspend operator fun invoke(user: UserDetailsModel) = repositoryImpl.removeUserFromFavList(user)
}
