package cc.spie.bangumix.utils.providers

import cc.spie.bangumix.data.apis.AuthorizationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@InstallIn(ActivityRetainedComponent::class)
@Module
object AuthorizationApiProvider {

    @ActivityRetainedScoped
    @Provides
    fun provideAuthorizationApi(httpClient: OkHttpClient, json: Json): AuthorizationApi =
        Retrofit.Builder()
            .baseUrl("https://bgm.tv/oauth/")
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaTypeOrNull()!!)
            )
            .client(httpClient)
            .build()
            .create(AuthorizationApi::class.java)
}