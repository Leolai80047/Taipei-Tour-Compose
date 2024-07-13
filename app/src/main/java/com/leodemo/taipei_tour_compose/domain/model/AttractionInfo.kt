package com.leodemo.taipei_tour_compose.domain.model

import android.os.Parcelable
import com.leodemo.taipei_tour.data.api.AttractionResponse
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AttractionInfo(
    val id: String,
    val name: String,
    val introduction: String,
    val address: String,
    val url: String,
    val modified: String,
    val imageUrl: String
) : Parcelable

fun AttractionResponse.Data.toDomain(): AttractionInfo {
    return AttractionInfo(
        id = this.id,
        name = this.name,
        introduction = this.introduction,
        address = this.address,
        url = this.url,
        modified = this.modified,
        imageUrl = this.getImage() ?: ""
    )
}