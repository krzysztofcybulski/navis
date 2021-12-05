package me.kcybulski.barentswatch

import com.github.kittinunf.fuel.core.Headers.Companion.AUTHORIZATION
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.getOrElse
import com.github.kittinunf.result.onError
import java.time.Instant

internal class BarentsWatchClient(
    private val api: String = "https://www.barentswatch.no/bwapi/v2",
    private val oauthProvider: OauthProvider
) {

    fun getAllShips(): List<Response> =
        "${api}/geodata/ais/openpositions"
            .httpGet()
            .header(AUTHORIZATION, "Bearer ${oauthProvider.getToken().raw}")
            .responseObject<List<Response>>()
            .third
            .onError { println(it) }
            .getOrElse { emptyList() }

}

internal data class Response(
    val timeStamp: String,
    val sog: Double,
    val mmsi: String,
    val cog: Double,
    val geometry: Geometry,
    val shipType: Int,
    val name: String?,
    val country: String,
    val destination: String?
)

internal data class Geometry(
    val coordinates: List<Double>
)
