package me.kcybulski.barentswatch

import me.kcybulski.navis.ships.ShipsFacade
import java.lang.System.getenv

@JvmOverloads
fun barentsWatch(
    ships: ShipsFacade,
    clientId: String = getenv("BARENTS_WATCH_CLIENT_ID"),
    clientSecret: String = getenv("BARENTS_WATCH_CLIENT_SECRET")
): BarentsWatchProvider = BarentsWatchProvider(
    ships,
    BarentsWatchClient(
        oauthProvider = OauthProvider(
            clientId = clientId,
            clientSecret = clientSecret
        )
    )
)
