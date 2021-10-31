package com.rekyb.jyro.utils

/**
 * An interface to facilitate data transfer from Data Layer into Domain Layer.
 * Implementation can be seen at Mapper package
 */
interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
}
