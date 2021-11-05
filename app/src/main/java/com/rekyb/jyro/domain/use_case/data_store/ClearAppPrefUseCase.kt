package com.rekyb.jyro.domain.use_case.data_store

import com.rekyb.jyro.repository.DataStoreRepositoryImpl
import javax.inject.Inject

class ClearAppPrefUseCase @Inject constructor(
    private val repositoryImpl: DataStoreRepositoryImpl,
) {

    suspend operator fun invoke() = repositoryImpl.clearAppPreferenceCache()
}