package cc.spie.bangumix.utils.providers

import cc.spie.bangumix.data.apis.BangumiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object BangumiApiProvider {

    @Singleton
    @Provides
    fun provideBangumiApi(json: Json, httpClient: OkHttpClient): BangumiApi = Retrofit.Builder()
        .baseUrl("https://api.bgm.tv")
        .addConverterFactory(
            json.asConverterFactory("application/json; charset=UTF8".toMediaTypeOrNull()!!)
        )
        .client(httpClient)
        .build()
        .create(BangumiApi::class.java)
}