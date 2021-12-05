package me.kcybulski.navis.web.api;

import me.kcybulski.navis.eventstore.Event;
import me.kcybulski.navis.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("events")
class EventsController {

    private final EventStore eventStore;

    EventsController(EventStore eventStore) {
        this.eventStore = eventStore;
    }


    @GetMapping("/{businessKey}")
    List<Event> getPositions(@PathVariable String businessKey) {
        return eventStore.eventStream(businessKey);
    }

}
