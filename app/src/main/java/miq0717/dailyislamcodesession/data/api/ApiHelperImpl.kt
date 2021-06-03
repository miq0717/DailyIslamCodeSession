package miq0717.dailyislamcodesession.data.api

import miq0717.dailyislamcodesession.data.model.ChaptersOfAHadithBookResponse
import miq0717.dailyislamcodesession.data.model.HadithBooksResponse
import miq0717.dailyislamcodesession.data.model.HadithInfoDatum
import miq0717.dailyislamcodesession.data.model.HadithsOfAChapterResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getHadithBooks(
        limit: Int,
        page: Int
    ): Response<HadithBooksResponse> = apiService.getHadithBooks(
        limit = limit,
        page = page
    )

    override suspend fun getChaptersOfAHadithBook(
        collectionName: String,
        limit: Int,
        page: Int
    ): Response<ChaptersOfAHadithBookResponse> =
        apiService.getChaptersOfAHadithBook(
            collectionName = collectionName,
            limit = limit,
            page = page
        )

    override suspend fun getHadithsOfAChapter(
        collectionName: String,
        bookNumber: Int,
        limit: Int,
        page: Int
    ): Response<HadithsOfAChapterResponse> =
        apiService.getHadithsOfAChapter(
            collectionName = collectionName,
            bookNumber = bookNumber,
            limit = limit,
            page = page
        )

    override suspend fun getDetailsOfAHadith(
        collectionName: String,
        hadithNumber: Int
    ): Response<HadithInfoDatum> = apiService.getDetailsOfAHadith(
        collectionName = collectionName,
        hadithNumber = hadithNumber
    )
}