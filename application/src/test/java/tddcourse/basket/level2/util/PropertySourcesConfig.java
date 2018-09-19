package tddcourse.basket.level2.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan(basePackages = {"tddcourse.basket.level2", "tddcourse.basket.restclient"})
public class PropertySourcesConfig {

  private static final Resource[] LEVEL2_PROPERTIES = new ClassPathResource[] {
                                                                               new ClassPathResource("application-level2.properties"),
  };

  private static final Resource[] LEVEL3_PROPERTIES = new ClassPathResource[] {
                                                                               new ClassPathResource("application-level3.properties"),
  };

  @ActiveProfiles("level2")
  public static class Level2Config {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
      PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
      pspc.setLocations(LEVEL2_PROPERTIES);
      return pspc;
    }
  }

  @ActiveProfiles("level3")
  public static class Level3Config {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
      PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
      pspc.setLocations(LEVEL3_PROPERTIES);
      return pspc;
    }
  }

}
