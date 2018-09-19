package tddcourse.basket;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;

@Configuration
@Profile("mock-integration")
public class ProductMockConfiguration {

  private static Logger log = LoggerFactory.getLogger(Application.class);

  @Bean
  ApplicationRunner initializeProductBean(Properties properties) {
    return new InitializeProductBean(properties.getProductHost());
  }

  private static class InitializeProductBean implements ApplicationRunner {

    private URI productHost;

    InitializeProductBean(URI productHost) {
      this.productHost = productHost;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

      if (productHost.toString().contains("localhost")) {
        log.warn("NOT REAL INTEGRATION");
        File productsFile = ResourceUtils.getFile("classpath:seeds/products.json");
        String products = new String(Files.readAllBytes(productsFile.toPath()));

        String host = productHost.getHost();
        int port = productHost.getPort();
        WireMockServer wireMockServer = new WireMockServer(options().port(port).containerThreads(20));
        wireMockServer.start();
        configureFor(host, port);
        stubFor(get(urlEqualTo("/products")).willReturn(okJson(products)));
      }
    }
  }

}
