package tddcourse.basket.restclient;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Service
public final class RestClient {

    private final RestTemplate restTemplate;
    private final HttpHeaders defaultHeaders;

    public RestClient() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        this.restTemplate = builder.errorHandler(new RestTemplateResponseErrorHandling()).build();
        this.defaultHeaders = new HttpHeaders();
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        defaultHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON_UTF8));
    }

    public <R> R get(URI uri, Class<R> returnType) {
        final HttpEntity<Void> httpEntity = new HttpEntity<>(null, defaultHeaders);
        final ResponseEntity<R> exchange = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, returnType);
        assertThat(exchange
                .getStatusCode()
                .is2xxSuccessful())
                .as("Successful return code for " + uri.getPath() + " GET")
                .isEqualTo(true);
        return exchange.getBody();
    }

    public <R> R get(URI uri, ParameterizedTypeReference<R> returnType) {
        final HttpEntity<Void> httpEntity = new HttpEntity<>(null, defaultHeaders);
        final ResponseEntity<R> exchange = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, returnType);
        assertThat(exchange
                .getStatusCode()
                .is2xxSuccessful())
                .as("Successful return code for " + uri.getPath() + " POST")
                .isEqualTo(true);
        return exchange.getBody();
    }

    public <R> R postToUri(URI uri, Class<R> returnType) {
        return postToUri(uri, null, ParameterizedTypeReference.forType(returnType));
    }

    public <R, T> R postToUri(URI uri, T objectToPost, ParameterizedTypeReference<R> returnType) {
        final HttpEntity<T> httpEntity = new HttpEntity<>(objectToPost, defaultHeaders);
        final ResponseEntity<R> exchange = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, returnType);
        assertThat(exchange
                .getStatusCode()
                .is2xxSuccessful())
                .as("Successful return code for " + uri.getPath() + " POST")
                .isEqualTo(true);
        return exchange.getBody();
    }

}
