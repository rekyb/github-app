package com.rekyb.jyro.domain.use_case

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.SearchResponse
import com.rekyb.jyro.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val repo: UserRepositoryImpl,
) {

    operator fun invoke(query: String): Flow<DataState<SearchResponse>> {
        return flow {
            try {
                val response = repo.search(query)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.message.toString()))
            }
        }
    }

}