package com.rekyb.jyro.domain.use_case.local

import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.data.repository.FavouritesRepositoryImpl
import javax.inject.Inject

class AddToFavUseCase @Inject constructor(
    private val repositoryImpl: FavouritesRepositoryImpl,
) {

    suspend operator fun invoke(user: UserDetailsModel) = repositoryImpl.addUserToFavList(user)
}
