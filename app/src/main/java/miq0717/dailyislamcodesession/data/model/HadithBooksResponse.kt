package miq0717.dailyislamcodesession.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class HadithBooksResponse(
    @Json(name = "data") val hadithBookDatum: List<HadithBookDatum>,
    val total: Int,
    val limit: Int,
    val previous: Int?,
    val next: Int?
)

@JsonClass(generateAdapter = true)
data class HadithBookDatum(
    val name: String,
    val hasBooks: Boolean,
    val hasChapters: Boolean,
    @Json(name = "collection") val hadithBookCollectionDataByLanguage: List<HadithBookCollectionData>,
    val totalHadith: Int,
    val totalAvailableHadith: Int
)

@JsonClass(generateAdapter = true)
data class HadithBookCollectionData(
    val lang: String,
    val title: String,
    val shortIntro: String
)