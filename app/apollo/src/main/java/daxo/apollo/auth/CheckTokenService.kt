package daxo.apollo.auth

import com.apollographql.apollo.ApolloClient
import daxo.core.api.ITokenRepo
import daxo.the.apollo.SimpleTokenCheckQuery
import daxo.the.data.api.auth.ICheckTokenService
import javax.inject.Inject

class CheckTokenService @Inject constructor(
    private val apolloClient: ApolloClient,
    private val tokenRepo: ITokenRepo
) : ICheckTokenService {

    override suspend fun checkToken(clearIfFailure: Boolean): Result<Unit> {
        val query = SimpleTokenCheckQuery()

        val response = apolloClient.query(query).execute()

        val result =
            if (response.exception == null && response.errors?.isEmpty() != false && response.data?.Viewer?.id != null)
                Result.success(Unit)
            else
                Result.failure(Exception("validating error"))

        if (result.isFailure && clearIfFailure) tokenRepo.clearToken()
        return result
    }
}