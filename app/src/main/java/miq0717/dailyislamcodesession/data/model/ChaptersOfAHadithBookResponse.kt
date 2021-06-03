package miq0717.dailyislamcodesession.data.model

import com.squareup.moshi.Json

data class ChaptersOfAHadithBookResponse(
    @Json(name = "data") val chaptersDatum: List<ChapterOfAHadithBookDatum>,
    val total: Int,
    val limit: Int,
    val previous: Int?,
    val next: Int?
)

data class ChapterOfAHadithBookDatum(
    val bookNumber: String,
    @Json(name = "book") val chapterInfoByLanguage: List<ChapterInfoByLanguage>,
    val hadithStartNumber: Int,
    val hadithEndNumber: Int,
    val numberOfHadith: Int
)

data class ChapterInfoByLanguage(
    val lang: String,
    val name: String
)