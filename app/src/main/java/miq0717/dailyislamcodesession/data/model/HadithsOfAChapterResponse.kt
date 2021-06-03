package miq0717.dailyislamcodesession.data.model

import com.squareup.moshi.Json

data class HadithsOfAChapterResponse(
    @Json(name = "data") val hadithDatum: List<HadithInfoDatum>,
    val total: Int,
    val limit: Int,
    val previous: Int?,
    val next: Int?
)

data class HadithInfoDatum(
    val collection: String,
    val bookNumber: Int,
    val chapterId: String,
    val hadithNumber: Int,
    @Json(name = "hadith") val hadithDetailDataByLanguage: List<HadithDetailDataByLanguage>,
)

data class HadithDetailDataByLanguage(
    val lang: String,
    val chapterNumber: String,
    val chapterTitle: String,
    val urn: Long,
    @Json(name = "body") val hadithBody: String,
    @Json(name = "grades") val hadithGradeData: List<HadithGradeData>
)

data class HadithGradeData(
    @Json(name = "graded_by") val gradedBy: String?,
    val grade: String
)