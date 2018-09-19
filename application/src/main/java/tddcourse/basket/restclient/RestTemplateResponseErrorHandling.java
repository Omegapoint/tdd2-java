package tddcourse.basket.restclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import tddcourse.basket.exceptions.NotFoundException;
import tddcourse.basket.exceptions.UnexpectedException;

@Component
public final class RestTemplateResponseErrorHandling
    implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse)
      throws IOException {

    return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
  }

  @Override
  @SuppressWarnings("NullableProblems")
  public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
    // Generic case without url or method information.
    handleError(null, null, clientHttpResponse);
  }

  @Override
  public void handleError(URI url, HttpMethod method, ClientHttpResponse httpResponse)
      throws IOException {

    if (httpResponse.getStatusCode()
        .series() == HttpStatus.Series.SERVER_ERROR) {
      final ObjectMapper objectMapper = new ObjectMapper();
      List<String> exceptionLines = objectMapper.readValue(httpResponse.getBody(), new TypeReference<List<String>>() {});
      throw UnexpectedException.withMsgAndStacktrace(
          "Got unexpected status code: " + httpResponse.getStatusCode() + ". Check services logs for full stacktrace.", exceptionLines);
    } else if (httpResponse.getStatusCode()
        .series() == HttpStatus.Series.CLIENT_ERROR) {
      // handle CLIENT_ERROR
      if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
        if (url != null && method != null) {
          throw NotFoundException.withMsg(
              "Could not find a " + method.name() + " endpoint with path " + url.toASCIIString() + ". Please verify that the request path is correct.");
        } else {
          throw NotFoundException.withMsg("Could not find correct endpoint and method. Please verify that the request path is correct");
        }
      } else {
        throw UnexpectedException
            .withMsg("Got unexpected status code: " + httpResponse.getStatusCode() + ". Please check that the request as a whole is correct.");
      }
    }
  }

}
