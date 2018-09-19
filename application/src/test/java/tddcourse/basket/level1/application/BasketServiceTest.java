package tddcourse.basket.level1.application;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tddcourse.basket.domain.Basket;
import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.Product;
import tddcourse.basket.services.BasketService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "tdd2.productHost=http://localhost:8081/products")
@Tag("level1")
class BasketServiceTest {

    @Autowired
    private BasketService basketService;

    @Test
    void shouldPersistEmptyBasketWhenCreated() {

        Basket basket = basketService.createBasket();

        Basket storedBasket = basketService.getBasket(basket.getId());

        assertThat(storedBasket).isNotNull();
        assertThat(storedBasket.productCount()).isEqualTo(0);
        assertThat(storedBasket.getTotal()).isEqualTo(Money.ZERO("SEK"));
        assertThat(storedBasket.getTotalWithVat()).isEqualTo(Money.ZERO("SEK"));
    }

    @Test
    void shouldAddAppleToBasket() {
        Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(6.28));

        Basket basket = basketService.createBasket();

        basketService.addProductToBasket(basket.getId(), apple.getId());

        Basket storedBasket = basketService.getBasket(basket.getId());

        assertThat(storedBasket)
                .isNotNull()
                .isNotEqualTo(basket)
                .isNotSameAs(basket);

        assertThat(storedBasket.getId())
                .isEqualTo(basket.getId());

        assertThat(storedBasket.productCount()).isEqualTo(1);
        assertThat(storedBasket.getTotal()).isEqualTo(apple.getCost());
        assertThat(storedBasket.getTotalWithVat()).isEqualTo(apple.getCost().multiply(1.25));
    }

}
