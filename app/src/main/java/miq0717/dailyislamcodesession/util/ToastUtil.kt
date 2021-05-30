package miq0717.dailyislamcodesession.util

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT

/*Created by MiQ0717 on 07-Jun-2020.*/

object ToastUtil {

    fun Context.showShortToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()

    fun Context.showLongToast(message: String) = Toast.makeText(this, message, LENGTH_LONG).show()
}

