package miq0717.dailyislamcodesession.data.repository

import miq0717.dailyislamcodesession.data.api.ApiHelper
import javax.inject.Inject

class ChaptersOfAHadithBookRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getChaptersOfAHadithBook(
        collectionName: String,
        limit: Int,
        page: Int
    ) = apiHelper.getChaptersOfAHadithBook(
        collectionName = collectionName,
        limit = limit,
        page = page
    )

    suspend fun getHadithsOfAChapter(
        collectionName: String,
        bookNumber: Int,
        limit: Int,
        page: Int
    ) = apiHelper.getHadithsOfAChapter(
        collectionName = collectionName,
        bookNumber = bookNumber,
        limit = limit,
        page = page
    )

    suspend fun getDetailsOfAHadith(
        collectionName: String,
        hadithNumber: Int
    ) = apiHelper.getDetailsOfAHadith(
        collectionName = collectionName,
        hadithNumber = hadithNumber
    )
}