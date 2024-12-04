package daxo.the.data.interfaces.profile

import daxo.core.model.profile.MainProfileData
import kotlinx.coroutines.flow.Flow

interface IMainProfileDataRepo {
    suspend fun loadProfile(): Result<MainProfileData>
    suspend fun loadProfileFlow(): Flow<MainProfileData>
}