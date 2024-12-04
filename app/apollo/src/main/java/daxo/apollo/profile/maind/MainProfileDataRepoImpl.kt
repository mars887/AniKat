package daxo.apollo.profile.maind

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicy
import daxo.apollo.QueryTimeController
import daxo.apollo.repo.converters.UserStaffNameLanguageConverter.toDomain
import daxo.apollo.repo.converters.UserTitleLanguageConverter.toDomain
import daxo.core.model.profile.MainProfileData
import daxo.the.apollo.LoadMainProfileDataQuery
import daxo.the.data.interfaces.profile.IMainProfileDataRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainProfileDataRepoImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val queryTimeController: QueryTimeController,
) : IMainProfileDataRepo {

    override suspend fun loadProfile(): Result<MainProfileData> {
        while (queryTimeController.checkTime()) delay(50)

        val query = LoadMainProfileDataQuery()

        val viewer = apolloClient.query(query).execute().apply {
            exception?.let {
                Log.i(TAG, "loadProfile exception: ${it.message}")
            }
            errors?.let {
                Log.i(TAG, "loadProfile errors: ${it.joinToString("\n") { it.message }}")
            }
        }.data?.Viewer ?: return Result.failure(Exception("is null"))

        return Result.success(RC.viewerToDomain(viewer))
    }

    override suspend fun loadProfileFlow(): Flow<MainProfileData> = flow {
        while (queryTimeController.checkTime()) delay(50)

        val query = LoadMainProfileDataQuery()

        apolloClient.query(query).fetchPolicy(FetchPolicy.CacheAndNetwork).toFlow().collect {
            it.data?.Viewer?.let {
                emit(RC.viewerToDomain(it))
            }
            it.errors?.let {
                Log.i(TAG, "loadProfileFlow errors: ${it.joinToString("\n") { it.message }}")
            }
            it.exception?.let {
                Log.i(TAG, "loadProfileFlow exception: ${it.message}")
            }
        }
    }

    object RC {
        fun viewerToDomain(viewer: LoadMainProfileDataQuery.Viewer): MainProfileData {
            return MainProfileData(
                id = viewer.id,
                name = viewer.name,
                about = viewer.about,
                bannerImage = viewer.bannerImage,
                unreadNotificationCount = viewer.unreadNotificationCount,
                siteUrl = viewer.siteUrl,
                donatorTier = viewer.donatorTier,
                donatorBadge = viewer.donatorBadge,
                userOptions = MainProfileData.UserOptions(
                    titleLanguage = viewer.options?.titleLanguage?.toDomain(),
                    displayAdultContent = viewer.options?.displayAdultContent,
                    profileColor = viewer.options?.profileColor,
                    staffNameLanguage = viewer.options?.staffNameLanguage?.toDomain()
                ),
                avatar = MainProfileData.Avatar(
                    medium = viewer.avatar?.medium,
                    large = viewer.avatar?.large
                )
            )
        }
    }

    companion object {
        private const val TAG = "MainProfileDataRepoImpl"
    }
}