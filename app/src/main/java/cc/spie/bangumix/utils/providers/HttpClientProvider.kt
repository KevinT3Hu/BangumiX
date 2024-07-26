package cc.spie.bangumix.utils.providers

import android.util.Log
import cc.spie.bangumix.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HttpClientProvider {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                // log request body
                val request = it.request()
                val requestBody = request.body
                val buffer = if (requestBody != null) {
                    val buf = okio.Buffer()
                    requestBody.writeTo(buf)
                    buf
                } else {
                    null
                }
                Log.d(
                    "HttpClientProvider",
                    "Request: ${request.method} ${request.url} ${buffer?.readUtf8()}\n Headers: ${request.headers}"
                )
                it.proceed(
                    request.newBuilder()
                        .header("User-Agent", Constants.USER_AGENT)
                        .build()
                )
            }.build()
    }
}