package com.accenture.lambda.functions;

import com.accenture.lambda.clients.HttpClient;
import com.accenture.lambda.exception.RequestProcessingException;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventBridgeFunction implements Consumer<ScheduledEvent> {

    @Override
    public void accept(final ScheduledEvent event) {
        log.info("Function triggered with: {}", event);

        final HttpClient client = new HttpClient();
        client.sendHttpGet(System.getenv("HEALTHCHECK_PATH"));

        final Payload payload = new Payload("30 Fenchurch Street, London, EC3M 3BD", 100, true);
        try {
            client.sendHttpPost(System.getenv("GEOCODE_PATH"), new ObjectMapper().writeValueAsString(payload));
        } catch (JsonProcessingException e) {
            final String message = String.format("Error serialising request payload: %s", e.getMessage());
            log.error(message, e);
            throw new RequestProcessingException(message, e);
        }

        log.info("Function call ends");
    }

    private record Payload(String address, Integer maxResults, Boolean fuzzy) {}
}
