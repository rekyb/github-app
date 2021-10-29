package com.rekyb.jyro.data.remote.mapper

import com.rekyb.jyro.data.remote.response.UserItemsDto
import com.rekyb.jyro.domain.model.UserItems
import com.rekyb.jyro.utils.EntityMapper

class UserItemsMapper : EntityMapper<UserItemsDto, UserItems> {
    override fun mapFromEntity(entity: UserItemsDto): UserItems {
        return UserItems(
            userName = entity.login,
            userId = entity.id,
            userAvatarUrl = entity.avatarUrl,
            userProfileUrl = entity.url
        )
    }

    override fun mapToEntity(domainModel: UserItems): UserItemsDto {
        return UserItemsDto(
            login = domainModel.userName,
            id = domainModel.userId,
            avatarUrl = domainModel.userAvatarUrl,
            url = domainModel.userProfileUrl
        )
    }

    fun toDomainList(initial: List<UserItems>): List<UserItemsDto> {
        return initial.map { mapToEntity(it) }
    }

    fun fromDomainList(initial: List<UserItemsDto>): List<UserItems> {
        return initial.map { mapFromEntity(it) }
    }
}
