package com.rekyb.jyro.data.remote.mapper

import com.rekyb.jyro.data.remote.response.SearchResultsDto
import com.rekyb.jyro.domain.model.SearchResultsModel
import com.rekyb.jyro.utils.EntityMapper

class SearchResultsMapper : EntityMapper<SearchResultsDto, SearchResultsModel> {
    private val mapper = UserItemsMapper()

    override fun mapFromEntity(entity: SearchResultsDto): SearchResultsModel {
        return SearchResultsModel(
            totalCount = entity.totalCount,
            incompleteResults = entity.incompleteResults,
            userItems = mapper.fromDomainList(entity.items)
        )
    }

    override fun mapToEntity(model: SearchResultsModel): SearchResultsDto {
        return SearchResultsDto(
            totalCount = model.totalCount,
            incompleteResults = model.incompleteResults,
            items = mapper.toDomainList(model.userItems)
        )
    }
}
