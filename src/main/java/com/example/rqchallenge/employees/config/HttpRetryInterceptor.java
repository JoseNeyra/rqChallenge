package com.example.rqchallenge.employees.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
public class HttpRetryInterceptor implements ClientHttpRequestInterceptor {

    private final int maxAttempts;
    private final long initialInterval;
    private final long maxInterval;
    private final double jitterFactor;
    private final Random random = new Random();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        int attempt = 0;
        long backOffInterval = initialInterval;

        while (attempt < maxAttempts) {
            try {
                ClientHttpResponse response = execution.execute(request, body);
                log.debug("Response status: {}", response.getStatusCode());
                if (response.getStatusCode().value() == 429) { // Too Many Requests
                    attempt++;
                    if (attempt >= maxAttempts) {
                        String message = "Max retry attempts exceeded due to 429 Too Many Requests";
                        log.error(message);
                        throw new IOException(message);
                    }
                    backOffInterval = calculateBackOffInterval(backOffInterval);
                    log.debug("Retry attempt {} after {} ms due to 429 Too Many Requests", attempt, backOffInterval);
                    try {
                        Thread.sleep(backOffInterval);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Interrupted during backoff", ie);
                    }
                } else {
                    return response;
                }
            } catch (IOException e) {
                attempt++;
                if (attempt >= maxAttempts) {
                    throw e;
                }
                backOffInterval = calculateBackOffInterval(backOffInterval);
                log.debug("Retry attempt {} after {} ms", attempt, backOffInterval);
                try {
                    Thread.sleep(backOffInterval);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted during backoff", ie);
                }
            }
        }
        String message = "Max retry attempts exceeded";
        log.error(message);
        throw new IOException(message);
    }

    private long calculateBackOffInterval(long currentInterval) {
        long nextInterval = Math.min(currentInterval * 2, maxInterval);
        long jitter = (long) (nextInterval * jitterFactor * (random.nextDouble() - 0.5));
        return nextInterval + jitter;
    }
}
