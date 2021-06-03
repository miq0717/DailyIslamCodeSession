package miq0717.dailyislamcodesession.data.repository

import miq0717.dailyislamcodesession.data.api.ApiHelper
import javax.inject.Inject

class HadithBooksRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getHadithBooks(
        limit: Int,
        page: Int
    ) = apiHelper.getHadithBooks(
        limit = limit,
        page = page
    )
}