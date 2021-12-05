package me.kcybulski.navis.web.config;

import me.kcybulski.barentswatch.BarentsWatchProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ShipProvidersConfig {

    private final BarentsWatchProvider provider;

    public ShipProvidersConfig(BarentsWatchProvider provider) {
        this.provider = provider;
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduleProvider() {
        provider.provideShips();
    }

}
