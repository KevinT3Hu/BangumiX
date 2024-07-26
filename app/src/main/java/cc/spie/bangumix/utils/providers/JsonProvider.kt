package cc.spie.bangumix.utils.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object JsonProvider {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            explicitNulls = false

//            serializersModule = SerializersModule {
//                polymorphic(Subject.Data::class) {
//                    subclass(Subject.Data.StringValue::class)
//                    subclass(Subject.Data.ListValue::class)
//                }
//            }
        }
    }
}