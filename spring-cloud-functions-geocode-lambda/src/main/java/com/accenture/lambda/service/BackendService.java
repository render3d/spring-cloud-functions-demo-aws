package com.accenture.lambda.service;

import static java.time.temporal.ChronoUnit.SECONDS;

import com.accenture.lambda.exception.BackendResponseException;
import com.accenture.lambda.exception.RequestProcessingException;
import com.accenture.lambda.rest.request.FindAddressesPayload;
import com.accenture.lambda.service.models.FindAddressCandidatesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class BackendService {
    private static final String ESRI_URI = "https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer";
    private static final ObjectMapper mapper = new ObjectMapper();

    public FindAddressCandidatesResponse findAddressCandidates(final FindAddressesPayload findAddressesPayload) {
        try {
            log.info("Payload: {}", mapper.writeValueAsString(findAddressesPayload));
        } catch (JsonProcessingException e) {
            log.error("Error serialising payload:", e);
            throw new RequestProcessingException(e.getMessage());
        }

        final String queryString = String.format(
                "/findAddressCandidates?outSR=4326&outFields=%s&Fuzzy=%s&maxLocations=%s&f=json&Address=%s",
                encodeValue("*"),
                findAddressesPayload.fuzzy(),
                findAddressesPayload.maxResults(),
                encodeValue(findAddressesPayload.address()));

        final String uri = String.format("%s%s", ESRI_URI, queryString);
        final HttpResponse<String> response = sendHttpGet(uri);

        try {
            log.info("Response: {}", mapper.writeValueAsString(response.body()));
            return mapper.readValue(response.body(), FindAddressCandidatesResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error deserialising GET /findAddressCandidates response:", e);
            throw new BackendResponseException(e.getMessage());
        }
    }

    @SneakyThrows
    private static HttpResponse<String> sendHttpGet(final String uri) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.of(30, SECONDS))
                .GET()
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}