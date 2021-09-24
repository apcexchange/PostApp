package com.example.weekten.room

import com.example.weekten.model.Post
import com.example.weekten.utils.EntityMapper
import javax.inject.Inject


class CachedPostMapper @Inject constructor() :EntityMapper<PostEntity,Post> {
    override fun mapFromEntity(entity: PostEntity): Post {
        return Post(
            userId = entity.userId,
            id = entity.id,
            title = entity.title,
            body = entity.body
        )
    }

    override fun mapToEntity(domainModel: Post): PostEntity {
        return PostEntity(
            userId = domainModel.userId,
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun mapFromEntityList(entities:List<PostEntity>):List<Post>{
        return entities.map { mapFromEntity(it) }
    }
}