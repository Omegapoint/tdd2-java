package tddcourse.basket.level2.application;

import static org.apache.commons.lang3.Validate.notNull;
import static org.assertj.core.api.Assertions.assertThat;

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

import java.net.URI;
import java.util.List;
import java.util.Random;

import tddcourse.basket.level2.application.dto.BasketDTO;
import tddcourse.basket.level2.application.dto.ProductDTO;
import tddcourse.basket.level2.util.PropertySourcesConfig;
import tddcourse.basket.restclient.RestClient;

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
    return restClient.postToUri(basketPath, BasketDTO.class);
  }

  private ProductDTO getRandomProduct() {
    List<ProductDTO> productDTOS = restClient.get(productPath, new ParameterizedTypeReference<List<ProductDTO>>() {});

    Random random = new Random();
    return productDTOS.get(random.nextInt(productDTOS.size()));
  }

  private BasketDTO addProductToBasket(BasketDTO basketDTO, ProductDTO productDTO) {

    URI addToBasketURI = basketPath.resolve(basketDTO.getId() + "/product/" + productDTO.getId());
    return restClient.postToUri(addToBasketURI, BasketDTO.class);
  }

  @Test
  void shouldCreateBasket() {

    BasketDTO basketDTO = createBasket();
    assertThat(basketDTO).isNotNull();
    assertThat(basketDTO.getProducts()).isNotNull().hasSize(0);
  }

  @Test
  void shouldFetchCreatedBasket() {

    BasketDTO basketDTO = createBasket();
    URI getCreatedBasketURI = basketPath.resolve(basketDTO.getId().toString());
    BasketDTO storedBasketDTO = restClient.get(getCreatedBasketURI, BasketDTO.class);
    assertThat(basketDTO).isEqualTo(storedBasketDTO);

  }

  @Test
  void shouldAddRandomProductToBasket() {
    BasketDTO basketDTO = createBasket();
    List<ProductDTO> products = basketDTO.getProducts();
    assertThat(basketDTO).isNotNull();
    assertThat(products).isNotNull().hasSize(0);

    ProductDTO randomProduct = getRandomProduct();
    BasketDTO basketWithProduct = addProductToBasket(basketDTO, randomProduct);
    List<ProductDTO> basketProducts = basketWithProduct.getProducts();
    assertThat(basketDTO).isNotNull();
    assertThat(basketProducts).isNotNull().hasSize(1);
    assertThat(basketProducts).contains(randomProduct);
  }

  @Test
  void shouldCheckoutBasket() {
    BasketDTO basketDTO = createBasket();
    ProductDTO randomProduct = getRandomProduct();
    BasketDTO basketWithProduct = addProductToBasket(basketDTO, randomProduct);
    assertThat(basketDTO.getId()).isEqualTo(basketWithProduct.getId());

    URI checkoutPath = UriComponentsBuilder.fromUri(basketPath.resolve(basketWithProduct.getId() + "/checkout"))
        .queryParam("cost", basketWithProduct.getTotal())
        .build()
        .toUri();
    restClient.postToUri(checkoutPath, null);

  }

}
