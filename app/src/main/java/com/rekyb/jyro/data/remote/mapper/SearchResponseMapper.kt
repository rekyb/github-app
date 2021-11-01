package com.rekyb.jyro.data.remote.mapper

import com.rekyb.jyro.data.remote.response.SearchResponseDto
import com.rekyb.jyro.domain.model.SearchResultsModel
import com.rekyb.jyro.utils.EntityMapper

class SearchResponseMapper : EntityMapper<SearchResponseDto, SearchResultsModel> {
    private val mapper = UserItemsMapper()

    override fun mapFromEntity(entity: SearchResponseDto): SearchResultsModel {
        return SearchResultsModel(
            totalCount = entity.totalCount,
            incompleteResults = entity.incompleteResults,
            userItems = mapper.fromDomainList(entity.items)
        )
    }

    override fun mapToEntity(domainModel: SearchResultsModel): SearchResponseDto {
        return SearchResponseDto(
            totalCount = domainModel.totalCount,
            incompleteResults = domainModel.incompleteResults,
            items = mapper.toDomainList(domainModel.userItems)
        )
    }
}
