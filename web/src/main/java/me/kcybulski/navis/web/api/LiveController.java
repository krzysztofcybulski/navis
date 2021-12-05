package me.kcybulski.navis.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kcybulski.navis.web.movements.MovementReadModel;
import me.kcybulski.navis.web.movements.ShipMovement;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class LiveController implements WebSocketHandler {

    private final Flux<ShipMovement> movements;
    private final ObjectMapper objectMapper;

    public LiveController(MovementReadModel movements, ObjectMapper objectMapper) {
        this.movements = movements.getShipMovements();
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                movements
                        .map(this::movementToString)
                        .map(session::textMessage)
        );
    }

    private String movementToString(ShipMovement movement) {
        try {
            return objectMapper.writeValueAsString(movement);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

}
