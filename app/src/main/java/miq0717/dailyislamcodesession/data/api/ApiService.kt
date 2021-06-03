package miq0717.dailyislamcodesession.data.api

import miq0717.dailyislamcodesession.data.model.ChaptersOfAHadithBookResponse
import miq0717.dailyislamcodesession.data.model.HadithBooksResponse
import miq0717.dailyislamcodesession.data.model.HadithInfoDatum
import miq0717.dailyislamcodesession.data.model.HadithsOfAChapterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("collections")
    suspend fun getHadithBooks(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<HadithBooksResponse>

    @GET("collections/{collectionName}/books")
    suspend fun getChaptersOfAHadithBook(
        @Path("collectionName") collectionName: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<ChaptersOfAHadithBookResponse>

    @GET("collections/{collectionName}/books/{bookNumber}/hadiths")
    suspend fun getHadithsOfAChapter(
        @Path("collectionName") collectionName: String,
        @Path("bookNumber") bookNumber: Int,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<HadithsOfAChapterResponse>

    @GET("collections/{collectionName}/hadiths/{hadithNumber}")
    suspend fun getDetailsOfAHadith(
        @Path("collectionName") collectionName: String,
        @Path("hadithNumber") hadithNumber: Int,
    ): Response<HadithInfoDatum>
}