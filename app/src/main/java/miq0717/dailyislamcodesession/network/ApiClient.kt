package miq0717.dailyislamcodesession.network

import miq0717.dailyislamcodesession.util.ApiUtil
import miq0717.dailyislamcodesession.util.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private lateinit var retrofitInstance: Retrofit

    fun init() {
        retrofitInstance = Retrofit.Builder().run {
            baseUrl(AppConstants.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(ApiUtil.getGsonBuilder()))
            client(ApiUtil.getClient())
            build()
        }
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofitInstance.create(serviceClass)
    }

    fun getRetrofit(): Retrofit {
        return retrofitInstance
    }
}