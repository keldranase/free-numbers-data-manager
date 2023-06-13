package dev.alex.klepov.client;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import dev.alex.klepov.client.view.FreeCountryClientView;
import dev.alex.klepov.client.view.FreeNumberClientView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// sadly there's no openapi spec to codegenerate a client, so we have to write our own manually
public class OnlinesimApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnlinesimApiClient.class);

    private static final int SUCCESS_STATUS_CODE = 1;

    private final HttpClient onlinesimHttpClient;
    private final String onlinesimBaseUrl;
    private final long defaultResponseTimeout;

    public OnlinesimApiClient(HttpClient onlinesimHttpClient, String onlinesimBaseUrl, long defaultResponseTimeout) {
        this.onlinesimHttpClient = onlinesimHttpClient;
        this.onlinesimBaseUrl = onlinesimBaseUrl;
        this.defaultResponseTimeout = defaultResponseTimeout;
    }

    public List<FreeCountryClientView> getFreeCountryList() {
        // todo: creating url like this might be fine for functional tests, but for actual production code I'd use some lib for composing it
        URI uri = URI.create("https://onlinesim.ru/api/getFreeCountryList");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.ofMillis(defaultResponseTimeout))
                .build();

        return onlinesimHttpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> OnlinesimResponseMapper.mapFreeCountries(response.body()))
                .thenApply(responsePojo -> {
                    if (responsePojo.getStatusCode() != SUCCESS_STATUS_CODE) {
                        throw new RuntimeException("bad response");
                    }

                    return responsePojo.getCountries();
                })
                .join();
    }

    public List<FreeNumberClientView> getFreePhoneList(long countryId) {
        URI uri = URI.create("https://onlinesim.ru/api/getFreePhoneList?country=" + countryId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.ofMillis(defaultResponseTimeout))
                .build();

        return onlinesimHttpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> OnlinesimResponseMapper.mapFreeNumbers(response.body()))
                .thenApply(responsePojo -> {
                    if (responsePojo.getStatusCode() != SUCCESS_STATUS_CODE) {
                        throw new RuntimeException("bad response");
                    }

                    return responsePojo.getCountries();
                })
                .join();
    }
}
