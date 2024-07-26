package cc.spie.bangumix.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PagedData<T>(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val data: List<T>
)