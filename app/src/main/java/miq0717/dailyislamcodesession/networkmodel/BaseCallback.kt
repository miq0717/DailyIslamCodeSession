package miq0717.dailyislamcodesession.networkmodel

import android.content.Context
import android.widget.Toast
import miq0717.dailyislamcodesession.util.ErrorUtil.parseError
import miq0717.dailyislamcodesession.util.ToastUtil.showShortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

abstract class BaseCallback<T>(private val context: Context) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!response.isSuccessful) {
            val (error) = parseError(response)
            context.showShortToast(message = "${response.code()} -  ${error.details}")
            handleError()
        } else {
            handleSuccess(response)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Timber.d(t)
    }

    abstract fun handleSuccess(response: Response<T>?)
    abstract fun handleError()
}