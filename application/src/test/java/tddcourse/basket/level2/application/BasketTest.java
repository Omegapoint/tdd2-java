package tddcourse.basket.level2.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.util.UriComponentsBuilder;
import tddcourse.basket.level2.application.dto.BasketDTO;
import tddcourse.basket.level2.application.dto.ProductDTO;
import tddcourse.basket.level2.util.PropertySourcesConfig;
import tddcourse.basket.restclient.RestClient;

import java.net.URI;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(PropertySourcesConfig.class)
@Tag("level2")
@Tag("level3")
class BasketTest {

    @Autowired
    RestClient restClient;

    @Value("${tdd2.basePath}")
    private String propertyBasePath;

    private URI basketPath;

    private URI productPath;

    @BeforeEach
    void setUp() {
        URI basePath = URI.create(this.propertyBasePath);
        this.basketPath = basePath.resolve("/api/basket/");
        this.productPath = basePath.resolve("/api/products/");
    }

    private BasketDTO createBasket() {
        //Example on how to do request with restClient. Method takes uri and expected return type.
        //return restClient.postToUri(basketPath, BasketDTO.class);
        return null;
    }

    private ProductDTO getRandomProduct() {
        //Example on how to do request with restClient where return type is a list.
        // List<ProductDTO> productDTOS = restClient.get(productPath, new ParameterizedTypeReference<List<ProductDTO>>() {});

        return null;
    }

}
