package cc.spie.bangumix.utils.providers

import cc.spie.bangumix.data.repositories.BangumiRepository
import cc.spie.bangumix.data.repositories.impl.BangumiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BangumiRepositoryBinder {

    @Binds
    abstract fun bindBangumiRepositoryImpl(
        bangumiRepositoryImpl: BangumiRepositoryImpl
    ): BangumiRepository
}