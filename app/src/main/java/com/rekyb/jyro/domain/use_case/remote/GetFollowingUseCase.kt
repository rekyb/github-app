package com.rekyb.jyro.domain.use_case.remote

import android.content.Context
import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.domain.model.UserItemsModel
import com.rekyb.jyro.data.repository.UserRepositoryImpl
import com.rekyb.jyro.utils.ExceptionHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repositoryImpl: UserRepositoryImpl,
) {

    operator fun invoke(userName: String): Flow<Resources<List<UserItemsModel>>> {
        return flow {
            try {
                emit(Resources.Loading)
                emit(Resources.Success(repositoryImpl.getFollowing(userName)))
            } catch (error: Exception) {
                emit(ExceptionHandler(context).handleError(error))
            }
        }
    }
}
