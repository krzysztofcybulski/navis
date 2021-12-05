package me.kcybulski.barentswatch

import com.github.kittinunf.fuel.core.Headers.Companion.CONTENT_TYPE
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.getOrElse
import com.github.kittinunf.result.map

internal class OauthProvider(
    private val api: String = "https://id.barentswatch.no",
    clientId: String,
    clientSecret: String
) {

    private val parameters = listOf(
        "client_id" to clientId,
        "client_secret" to clientSecret,
        "scope" to "api",
        "grant_type" to "client_credentials"
    )

    fun getToken(): AccessToken =
        "${api}/connect/token"
            .httpPost(parameters)
            .header(CONTENT_TYPE, "application/x-www-form-urlencoded")
            .responseObject<TokenResponse>()
            .third
            .map { AccessToken(it.access_token) }
            .getOrElse { throw IllegalStateException("Couldn't get oauth token") }

    private class TokenResponse(
        val access_token: String
    )
}

internal data class AccessToken(val raw: String)
