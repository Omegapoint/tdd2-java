package tddcourse.basket.level0.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import tddcourse.basket.domain.Basket;
import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.Product;
import tddcourse.basket.domain.mappers.BasketMapper;
import tddcourse.basket.domain.mappers.ProductMapper;
import tddcourse.basket.exceptions.NotFoundException;
import tddcourse.basket.restclient.RestClient;
import tddcourse.basket.services.BasketService;
import tddcourse.basket.services.ProductService;
import tddcourse.basket.storage.entities.BasketEntity;
import tddcourse.basket.storage.repositories.BasketRepository;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("level0")
class ServiceTests {

    private BasketRepository basketRepositoryMock;
    private BasketService basketService;
    private ProductService productService;

    @BeforeEach
    private void setUp() {
        basketRepositoryMock = Mockito.mock(BasketRepository.class);
        basketService = new BasketService(basketRepositoryMock, productService);

        productService = Mockito.spy(new ProductService(URI.create("http://localhost:8081/products"), new RestClient()));
        Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(9.24));
        Product pear = Product.valueOf(0, "Pear", Money.valueOfSEK(7.52));
        Product banana = Product.valueOf(0, "Apple", Money.valueOfSEK(3.15));
        Product watermelon = Product.valueOf(0, "Apple", Money.valueOfSEK(36.24));
        Mockito.doReturn(ProductMapper.productsToDTOs(Arrays.asList(apple, pear, banana, watermelon))).when(productService).getAllProducts();
    }

    @Test
    @DisplayName("Empty basket should be created")
    void shouldCreateEmptyBasket() {
        Mockito.when(basketRepositoryMock
                .save(Mockito.any(BasketEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Basket basket = basketService.createBasket();
        assertThat(basket.getProducts()).isEmpty();
    }

    @Test
    @DisplayName("Get basket method should return the correct basket")
    void shouldGetCorrectBasket() {
        Mockito.when(basketRepositoryMock
                .save(Mockito.any(BasketEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Basket basket = basketService.createBasket();

        Mockito.when(basketRepositoryMock
                .findById(basket.getId()))
                .thenReturn(Optional.of(BasketMapper.basketToEntity(basket)));

        Basket storedBasket = basketService.getBasket(basket.getId());
        assertThat(storedBasket).isEqualTo(basket);
    }

    @Test
    void shouldGetExistingProduct() {
        Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(9.24));
        Product expected = productService.findById(apple.getId());
        assertThat(expected).isEqualTo(apple);
    }

    @Test
    void shouldThrowForNonExistingBasket() {
        UUID id = UUID.randomUUID();
        Mockito.when(basketRepositoryMock.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> basketService.getBasket(id));
    }

    @Test
    void shouldThrowForNonExistingProduct() {
        Product faultyProduct = Product.valueOf(-1, "Fail", Money.ZERO("SEK"));
        assertThrows(NotFoundException.class, () -> productService.findById(faultyProduct.getId()));
    }

    @Test
    void shouldThrowForAddNonExistingProductToBasket() {
        Mockito.when(basketRepositoryMock.save(Mockito.any(BasketEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Basket basket = basketService.createBasket();
        assertThrows(NotFoundException.class, () -> basketService.addProductToBasket(basket.getId(), -1));
    }

    @Test
    void shouldThrowForAddProductToNonExistingBasket() {
        Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(24.2));
        assertThrows(NotFoundException.class, () -> basketService.addProductToBasket(null, apple.getId()));
    }

    //ShouldAddProductToBasket


}
