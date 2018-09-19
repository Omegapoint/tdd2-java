package tddcourse.basket.level0.domain.mappers;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tddcourse.basket.domain.Basket;
import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.Product;
import tddcourse.basket.domain.dto.BasketDTO;
import tddcourse.basket.domain.mappers.BasketMapper;
import tddcourse.basket.domain.mappers.ProductMapper;
import tddcourse.basket.storage.entities.BasketEntity;
import tddcourse.basket.storage.entities.MoneyEntity;
import tddcourse.basket.storage.entities.ProductEntity;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Tag("level0")
class BasketMapperTest {

    private static BasketEntity validBasketEntity;
    private static Basket validBasket;

    @BeforeAll
    static void setUp() {

        Currency swedishKrona = Currency.getInstance(new Locale("sv", "SE"));
        ProductEntity productEntity1 = ProductEntity.valueOf(0, "Apple", MoneyEntity.valueOf(723, 2, swedishKrona));
        ProductEntity productEntity2 = ProductEntity.valueOf(1, "Pear", MoneyEntity.valueOf(723, 2, swedishKrona));
        List<ProductEntity> validproductEntities = ImmutableList.of(productEntity1, productEntity2);

        Product product1 = Product.valueOf(0, "Apple", Money.valueOfSEK(223));
        Product product2 = Product.valueOf(1, "Ananans", Money.valueOfSEK(671));
        List<Product> validProducts = ImmutableList.of(product1, product2);

        validBasketEntity = BasketEntity.valueOf(UUID.randomUUID(), validproductEntities);
        validBasket = Basket.valueOf(UUID.randomUUID(), validProducts);

    }

    @Test
    void shouldConvertEntityToBasket() {
        Basket basket = BasketMapper.basketEntityToBasket(validBasketEntity);
        assertThat(basket).isEqualToIgnoringGivenFields(validBasketEntity, "products");
        List<Product> products = basket.getProducts();
        List<ProductEntity> productEntities = validBasketEntity.getProductEntities();

        assertThat(products.size()).isEqualTo(productEntities.size());
        for (Product product : products) {
            assertThat(productEntities).usingFieldByFieldElementComparator().contains(ProductMapper.productToEntity(product));
        }

        BasketEntity basketEntity = BasketMapper.basketToEntity(basket);
        assertThat(basketEntity).isEqualToIgnoringGivenFields(validBasketEntity, "productEntities");
        for (ProductEntity productEntity : validBasketEntity.getProductEntities()) {
            assertThat(basketEntity.getProductEntities()).usingFieldByFieldElementComparator().contains(productEntity);
        }
    }

    @Test
    void shouldConvertBasketToDTO() {
        BasketDTO basketDTO = BasketMapper.basketToDTO(validBasket);
        assertThat(basketDTO).isEqualToIgnoringGivenFields(validBasket, "products");
        List<Product> products = validBasket.getProducts();
        for (Product product : products) {
            assertThat(basketDTO.getProducts()).usingFieldByFieldElementComparator().contains(ProductMapper.productToDTO(product));
        }

    }

}
