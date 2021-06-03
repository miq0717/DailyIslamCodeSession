package miq0717.dailyislamcodesession.data.repository

import miq0717.dailyislamcodesession.data.api.ApiHelper
import javax.inject.Inject

class HadithsOfAChapterRepository @Inject constructor(private val apiHelper: ApiHelper) {

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
}