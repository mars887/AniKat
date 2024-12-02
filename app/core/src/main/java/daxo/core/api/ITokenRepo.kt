package daxo.core.api

interface ITokenRepo {
    fun putToken(token: ApiToken)
    fun getToken(): ApiToken?
    fun tokenAvailable(): Boolean
    fun clearToken()
}