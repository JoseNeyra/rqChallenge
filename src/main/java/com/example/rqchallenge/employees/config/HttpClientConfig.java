package com.example.rqchallenge.employees.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.util.DefaultUriBuilderFactory;


@Data
@Configuration
@ConfigurationProperties(prefix = "rqchallenge.employees.http.client")
@ConfigurationPropertiesScan
@Slf4j
public class HttpClientConfig {

    private String baseUrl;
    private Integer maxAttempts;
    private Long backOffDurationMillis;
    private Long maxBackOffSeconds;
    private Double jitterFactor;


    @Bean
    RestTemplateBuilder restTemplateBuilder (RestTemplateBuilderConfigurer configurer) {
        assert baseUrl != null;

        RestTemplateBuilder templateBuilder = configurer.configure(new RestTemplateBuilder());
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        return templateBuilder.uriTemplateHandler(uriBuilderFactory);
    }

    @Bean
    public ClientHttpRequestInterceptor retryInterceptor() {
        return new HttpRetryInterceptor(maxAttempts, backOffDurationMillis, maxBackOffSeconds * 1000, jitterFactor);
    }
}
