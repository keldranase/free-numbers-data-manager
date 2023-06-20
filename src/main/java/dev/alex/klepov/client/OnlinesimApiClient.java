package dev.alex.klepov.client;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import dev.alex.klepov.client.exception.OnlinesimClientException;
import dev.alex.klepov.client.view.FreeCountryClientView;
import dev.alex.klepov.client.view.FreeNumberClientView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.util.UriComponentsBuilder;

// sadly there's no openapi or similar spec to codegenerate a client, so we have to write our own manually
public class OnlinesimApiClient {

    private static final Logger LOGGER = LogManager.getLogger(OnlinesimApiClient.class);

    private static final int SUCCESS_STATUS_CODE = 1;

    private final HttpClient onlinesimHttpClient;
    private final String onlinesimBaseUrl;
    private final long defaultResponseTimeout;

    public OnlinesimApiClient(HttpClient onlinesimHttpClient, String onlinesimBaseUrl, long defaultResponseTimeout) {
        this.onlinesimHttpClient = onlinesimHttpClient;
        this.onlinesimBaseUrl = onlinesimBaseUrl;
        this.defaultResponseTimeout = defaultResponseTimeout;
    }

    // This thing is like really important, cause sometimes api gives broken responses for a couple of secs
    // And retries do remedy this problem
    // We'll retry failed requests, with the pause of 1000ms, doubling it with each new retry
    // till we get a parseable response, or exhaust retry count
    @Retryable(
            retryFor = OnlinesimClientException.class,
            maxAttemptsExpression = "${onlinesim.data.manager.client.max.retry}",
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 2))
    public List<FreeCountryClientView> getFreeCountryList() {
        try {
            return getFreeCountryListE();
        } catch (Exception e) {
            LOGGER.error("Error occurred during requesting free numbers from api: " + e.getMessage());
            throw new OnlinesimClientException("Couldn't get list of free countries" + e.getMessage());
        }
    }

    private List<FreeCountryClientView> getFreeCountryListE() {
        URI uri = UriComponentsBuilder
                .fromUriString(onlinesimBaseUrl + "/api/getFreeCountryList")
                .build()
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.ofMillis(defaultResponseTimeout))
                .build();

        List<FreeCountryClientView> freeCountries = onlinesimHttpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> OnlinesimResponseMapper.mapFreeCountries(response.body()))
                .thenApply(responsePojo -> {
                    if (responsePojo.getStatusCode() != SUCCESS_STATUS_CODE) {
                        throw new RuntimeException("bad response");
                    }

                    return responsePojo.getCountries();
                })
                .join();

        LOGGER.debug("Countries with free numbers were found: " + freeCountries.stream()
                .map(FreeCountryClientView::getCountryName)
                .toList());

        return freeCountries;
    }

    @Retryable(
            retryFor = OnlinesimClientException.class,
            maxAttemptsExpression = "${onlinesim.data.manager.client.max.retry}",
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 2))
    public List<FreeNumberClientView> getFreePhoneList(long countryId) {
        try {
            return getFreePhoneListE(countryId);
        } catch (Exception e) {
            LOGGER.error("Error occurred during requesting free numbers from api" + e.getMessage());
            throw new OnlinesimClientException("Couldn't get list of free phone numbers" + e.getMessage());
        }
    }

    private List<FreeNumberClientView> getFreePhoneListE(long countryId) {
        URI uri = UriComponentsBuilder
                .fromUriString(onlinesimBaseUrl + "/api/getFreePhoneList")
                .queryParam("country", countryId)
                .build()
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.ofMillis(defaultResponseTimeout))
                .build();

        List<FreeNumberClientView> numbersByCountry = onlinesimHttpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> OnlinesimResponseMapper.mapFreeNumbers(response.body()))
                .thenApply(responsePojo -> {
                    if (responsePojo.getStatusCode() != SUCCESS_STATUS_CODE) {
                        throw new RuntimeException("bad response");
                    }

                    return responsePojo.getNumbers();
                })
                .join();

        LOGGER.debug("Free numbers were found: " + numbersByCountry.stream()
                .map(FreeNumberClientView::getFullNumber)
                .toList());

        return numbersByCountry;
    }
}
