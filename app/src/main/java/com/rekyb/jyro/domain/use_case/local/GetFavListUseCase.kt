package com.rekyb.jyro.domain.use_case.local

import android.content.Context
import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.repository.FavouritesRepositoryImpl
import com.rekyb.jyro.utils.ExceptionHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetFavListUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repositoryImpl: FavouritesRepositoryImpl,
) {

    operator fun invoke(): Flow<Resources<List<UserDetailsModel>>> {
        return flow {
            try {
                emit(Resources.Loading)
                repositoryImpl.getFavouritesList()
                    .collect {
                        emit(Resources.Success(it))
                    }
            } catch (error: Exception) {
                emit(ExceptionHandler(context).handleError(error))
            }
        }
    }
}
