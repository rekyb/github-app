package com.rekyb.jyro.domain.use_case

import android.content.Context
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.SearchResponse
import com.rekyb.jyro.repository.UserRepositoryImpl
import com.rekyb.jyro.utils.ExceptionHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: UserRepositoryImpl,
) {

    operator fun invoke(query: String): Flow<DataState<SearchResponse>> {
        return flow {
            try {
                emit(DataState.Loading)
                emit(DataState.Success(repo.search(query)))
            } catch (error: Exception) {
                emit(ExceptionHandler(context).handleError(error))
            }
        }
    }
}
