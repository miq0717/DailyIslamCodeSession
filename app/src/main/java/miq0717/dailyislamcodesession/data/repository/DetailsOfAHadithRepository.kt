package miq0717.dailyislamcodesession.data.repository

import miq0717.dailyislamcodesession.data.api.ApiHelper
import javax.inject.Inject

class DetailsOfAHadithRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getDetailsOfAHadith(
        collectionName: String,
        hadithNumber: Int
    ) = apiHelper.getDetailsOfAHadith(
        collectionName = collectionName,
        hadithNumber = hadithNumber
    )
}