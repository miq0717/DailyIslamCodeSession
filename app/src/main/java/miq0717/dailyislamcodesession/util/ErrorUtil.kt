package miq0717.dailyislamcodesession.util

import miq0717.dailyislamcodesession.network.ApiClient
import miq0717.dailyislamcodesession.networkmodel.Error
import miq0717.dailyislamcodesession.networkmodel.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

object ErrorUtil {

    fun parseError(response: Response<*>): ErrorResponse {
        val converter: Converter<ResponseBody?, ErrorResponse> = ApiClient.getRetrofit()
            .responseBodyConverter(ErrorResponse::class.java, arrayOfNulls<Annotation>(0))
        return try {
            converter.convert(response.errorBody()!!)!!
        } catch (e: IOException) {
            return ErrorResponse(Error(520,"Unknown Error"))
        }
    }

}