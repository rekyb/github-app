package com.rekyb.jyro.data.remote.mapper

import com.rekyb.jyro.data.remote.response.UserDetailsDto
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.utils.EntityMapper

class UserDetailsMapper: EntityMapper<UserDetailsDto, UserDetailsModel> {
    override fun mapFromEntity(entity: UserDetailsDto): UserDetailsModel {
        return UserDetailsModel(
            id = entity.id,
            fullName = entity.name,
            userName = entity.login,
            userAvatarUrl = entity.userAvatarUrl,
            userCompany = entity.company,
            userLocation = entity.location,
            followersUrl = entity.followersUrl,
            followingUrl = entity.followingUrl
        )
    }

    override fun mapToEntity(model: UserDetailsModel): UserDetailsDto {
        return UserDetailsDto(
            id = model.id,
            name = model.fullName,
            login = model.userName,
            userAvatarUrl = model.userAvatarUrl,
            company = model.userCompany,
            location = model.userLocation,
            followersUrl = model.followersUrl,
            followingUrl = model.followingUrl
        )
    }
}