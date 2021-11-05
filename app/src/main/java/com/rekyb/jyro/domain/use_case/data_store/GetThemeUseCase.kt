package com.rekyb.jyro.domain.use_case.data_store

import com.rekyb.jyro.repository.DataStoreRepositoryImpl
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val repositoryImpl: DataStoreRepositoryImpl
) {

    suspend operator fun invoke(): String? = repositoryImpl.themeSelection.firstOrNull()
}
