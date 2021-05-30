package miq0717.dailyislamcodesession

import android.app.Application
import miq0717.dailyislamcodesession.network.ApiClient
import timber.log.Timber
import timber.log.Timber.DebugTree


class InitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiClient.init()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    var logPrefix: String = String.format(
                        "(%s:%s)#%s",
                        element.fileName,
                        element.lineNumber,
                        element.methodName
                    )
                    logPrefix = "$logPrefix [ DailyIslamCodingSessionLog:  ] "
                    return logPrefix
                }
            })
        }
    }
}