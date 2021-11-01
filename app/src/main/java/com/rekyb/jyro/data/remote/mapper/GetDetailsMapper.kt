package com.rekyb.jyro.data.remote.mapper

import com.rekyb.jyro.data.remote.response.GetDetailsResponseDto
import com.rekyb.jyro.domain.model.GetDetailsModel
import com.rekyb.jyro.utils.EntityMapper

class GetDetailsMapper: EntityMapper<GetDetailsResponseDto, GetDetailsModel> {
    override fun mapFromEntity(entity: GetDetailsResponseDto): GetDetailsModel {
        return GetDetailsModel(
            fullName = entity.name,
            userName = entity.login,
            userAvatarUrl = entity.userAvatarUrl,
            userCompany = entity.company,
            userLocation = entity.location,
            followersUrl = entity.followersUrl,
            followingUrl = entity.followingUrl
        )
    }

    override fun mapToEntity(domainModel: GetDetailsModel): GetDetailsResponseDto {
        return GetDetailsResponseDto(
            name = domainModel.fullName,
            login = domainModel.userName,
            userAvatarUrl = domainModel.userAvatarUrl,
            company = domainModel.userCompany,
            location = domainModel.userLocation,
            followersUrl = domainModel.followersUrl,
            followingUrl = domainModel.followingUrl
        )
    }
}