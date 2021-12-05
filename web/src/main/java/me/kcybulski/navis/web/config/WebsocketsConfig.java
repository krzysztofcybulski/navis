package me.kcybulski.navis.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kcybulski.navis.web.api.LiveController;
import me.kcybulski.navis.web.movements.MovementReadModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
public class WebsocketsConfig {

    @Bean
    public HandlerMapping handlerMapping(MovementReadModel movements, ObjectMapper objectMapper) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ships/live", new LiveController(movements, objectMapper));

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(HIGHEST_PRECEDENCE);
        return mapping;
    }
}
