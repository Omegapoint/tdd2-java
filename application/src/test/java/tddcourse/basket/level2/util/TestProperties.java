package tddcourse.basket.level2.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "tdd2", ignoreInvalidFields = true)
public class TestProperties {

  private URI basePath;

  public URI getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    this.basePath = URI.create(basePath);
  }
}
