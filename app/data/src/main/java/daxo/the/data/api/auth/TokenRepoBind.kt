package daxo.the.data.api.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daxo.core.api.ITokenRepo

@Module
@InstallIn(SingletonComponent::class)
interface TokenRepoBind {
    @Binds
    fun tokenRepoEncrPrefsImplBind(tokenRepo: ApiTokenTokenRepoPrefsImpl): ITokenRepo
}