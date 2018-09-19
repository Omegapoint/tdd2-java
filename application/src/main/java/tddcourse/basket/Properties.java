package tddcourse.basket;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@ConfigurationProperties(prefix = "tdd2", ignoreInvalidFields = true)
public class Properties {

    private URI productHost;

    public URI getProductHost() {
        return productHost;
    }

    public void setProductHost(String productHost) {
        this.productHost = URI.create(productHost);
    }
}
