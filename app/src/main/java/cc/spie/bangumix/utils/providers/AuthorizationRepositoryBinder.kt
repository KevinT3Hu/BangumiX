package cc.spie.bangumix.utils.providers

import cc.spie.bangumix.data.repositories.AuthorizationRepository
import cc.spie.bangumix.data.repositories.impl.AuthorizationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthorizationRepositoryBinder {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindAuthorizationRepository(
        authorizationRepositoryImpl: AuthorizationRepositoryImpl
    ): AuthorizationRepository
}