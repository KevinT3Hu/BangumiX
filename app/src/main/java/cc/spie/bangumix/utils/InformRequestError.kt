package cc.spie.bangumix.utils

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun Context.informError(
    e: Throwable?,
    onHttpError: (HttpException) -> Boolean = { false }
) {
    withContext(Dispatchers.Main) {
        val toastMsg = when (e) {
            is IOException -> {
                "Network error"
            }

            is HttpException -> {
                val blocked = onHttpError(e)
                if (blocked) {
                    return@withContext
                }
                val errorCode = e.code()
                if (errorCode == 400) {
                    "Invalid code"
                } else {
                    "Unknown error"
                }
            }

            else -> {
                "Unknown error"
            }
        }

        Toast.makeText(this@informError, toastMsg, Toast.LENGTH_SHORT).show()
    }
}