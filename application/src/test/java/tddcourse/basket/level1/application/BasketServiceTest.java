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


}
