package miq0717.dailyislamcodesession.data.api

import miq0717.dailyislamcodesession.data.model.ChaptersOfAHadithBookResponse
import miq0717.dailyislamcodesession.data.model.HadithBooksResponse
import miq0717.dailyislamcodesession.data.model.HadithInfoDatum
import miq0717.dailyislamcodesession.data.model.HadithsOfAChapterResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getHadithBooks(
        limit: Int,
        page: Int
    ): Response<HadithBooksResponse>

    suspend fun getChaptersOfAHadithBook(
        collectionName: String,
        limit: Int,
        page: Int
    ): Response<ChaptersOfAHadithBookResponse>

    suspend fun getHadithsOfAChapter(
        collectionName: String,
        bookNumber: Int,
        limit: Int,
        page: Int
    ): Response<HadithsOfAChapterResponse>

    suspend fun getDetailsOfAHadith(
        collectionName: String,
        hadithNumber: Int
    ): Response<HadithInfoDatum>
}