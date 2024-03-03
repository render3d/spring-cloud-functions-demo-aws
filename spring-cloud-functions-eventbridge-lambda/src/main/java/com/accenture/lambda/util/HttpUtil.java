package com.accenture.lambda.util;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import lombok.SneakyThrows;

public class HttpUtil {

    private final HttpClient httpClient;
    private final HttpRequest.Builder httpBuilder;
    private final String apiRootUrl;

    public HttpUtil() {
        this.httpBuilder = HttpRequest.newBuilder().timeout(Duration.of(30, SECONDS));
        this.httpClient = HttpClient.newHttpClient();
        this.apiRootUrl = System.getenv("API_ROOT_URL");
    }

    @SneakyThrows
    public HttpResponse<String> sendHttpGet(final String endpoint) {
        final HttpRequest request = httpBuilder
                .uri(URI.create(String.join("", apiRootUrl, endpoint)))
                .GET()
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @SneakyThrows
    public HttpResponse<String> sendHttpPost(final String endpoint, final String payload) {
        final HttpRequest request = httpBuilder
                .header("Content-Type", "application/json")
                .uri(URI.create(String.join("", apiRootUrl, endpoint)))
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
