package miq0717.dailyislamcodesession.di.module

import androidx.room.RoomDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import miq0717.dailyislamcodesession.BuildConfig
import miq0717.dailyislamcodesession.data.api.ApiHelper
import miq0717.dailyislamcodesession.data.api.ApiHelperImpl
import miq0717.dailyislamcodesession.data.api.ApiService
import miq0717.dailyislamcodesession.util.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader(AppConstants.HEADER_PREFIX, AppConstants.API_TOKEN)
            val request = requestBuilder.method(original.method, original.body).build()
            val response = chain.proceed(request)
            if (response.code != 200) {
                Timber.e("Something went wrong")
            }
            response
        }

        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(AppConstants.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .readTimeout(AppConstants.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .writeTimeout(AppConstants.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(AppConstants.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .readTimeout(AppConstants.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .writeTimeout(AppConstants.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .build()
        }
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder().run {
            baseUrl(BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(okHttpClient)
            build()
        }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}