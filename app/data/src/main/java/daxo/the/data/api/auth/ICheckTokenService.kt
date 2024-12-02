package daxo.the.data.api.auth

interface ICheckTokenService {
    suspend fun checkToken(clearIfFailure: Boolean = false): Result<Unit>
}