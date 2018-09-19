package tddcourse.basket.level0.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import org.junit.jupiter.api.Test;
import tddcourse.basket.domain.Basket;
import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.Product;

@Tag("level0")
class BasketTest {

  @Test
  @DisplayName("Basket should be empty when created")
  void shouldBeEmptyWhenCreated() {
    Basket basket = Basket.valueOf();

    assertThat(basket.getProducts()).isEmpty();
  }

  @Test
  @DisplayName("Sum of basket should be zero when basket has been created")
  void sumShouldTotalToZeroWhenCreated() {
    Basket basket = Basket.valueOf();

    assertThat(basket.getTotal()).isEqualTo(Money.ZERO("SEK"));
    assertThat(basket.getTotalWithVat()).isEqualTo(Money.ZERO("SEK"));
  }

  @Test
  @DisplayName("Basket should contain exactly one product after adding exactly one product")
  void shouldContainAnProductWhenAddingOne() {
    Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(12.72));
    Basket basket = Basket.valueOf();

    basket = basket.add(apple);
    assertThat(basket.getProducts()).contains(apple);
    assertThat(basket.productCount()).isEqualTo(1);
  }

  @Test
  @DisplayName("Basket should have the same total as product after adding one product")
  void shouldTotalToProductForOne() {
    Product product = Product.valueOf(0, "Pear", Money.valueOfSEK(8.64));
    Basket basket = Basket.valueOf();

    basket = basket.add(product);

    assertThat(basket.getTotal()).isEqualTo(product.getCost());
  }

  @Test
  @DisplayName("Basket should contain multiple products of the same type")
  void shouldContainMultipleProductsWhenAddingManyOfOneProduct() {
    Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(13.5));
    Basket basket = Basket.valueOf();

    basket = basket.add(apple);
    basket = basket.add(apple);

    assertThat(basket.productCount()).isEqualTo(2);
  }

  @Test
  @DisplayName("Total sum of basket should be correct for many product")
  void shouldTotalForManyProducts() {
    int count = 100;
    Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(24.2));
    Money totalCost = apple.getCost().multiply(count);

    Basket basket = Basket.valueOf();
    for (int i = 0; i < count; i++) {
      basket = basket.add(apple);
    }
    assertThat(basket.getTotal()).isEqualTo(totalCost);
  }

  @Test
  @DisplayName("Basket should contain multiple ")
  void shouldAddMultipleProductsToBasket() {
    Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(14.5));
    Product pear = Product.valueOf(1, "Pear", Money.valueOfSEK(13.8));
    Product banana = Product.valueOf(2, "Banana", Money.valueOfSEK(7.2));

    int nrOfApples = 3, nrOfPears = 18, nrOfBananas = 23;

    Money ProductsTotal = apple.getCost()
        .multiply(nrOfApples)
        .plus(
            pear.getCost()
                .multiply(nrOfPears)
                .plus(
                    banana.getCost()
                        .multiply(nrOfBananas)));

    Basket basket = Basket.valueOf();

    for (int i = 0; i < nrOfApples; i++) {
      basket = basket.add(apple);
    }
    for (int j = 0; j < nrOfPears; j++) {
      basket = basket.add(pear);
    }
    for (int k = 0; k < nrOfBananas; k++) {
      basket = basket.add(banana);
    }
    assertThat(basket.productCount()).isEqualTo(nrOfApples + nrOfPears + nrOfBananas);
    assertThat(basket.getTotal()).isEqualTo(ProductsTotal);
    assertThat(basket.getTotalWithVat()).isEqualTo(ProductsTotal.multiply(1.25));
  }

  @Test
  void shouldTotalWithVat() {
    Product apple = Product.valueOf(0, "Apple", Money.valueOfSEK(24.2));
    Product banana = Product.valueOf(1, "Banana", Money.valueOfSEK(18.79));
    Money totalWithVat = apple.getCost().plus(banana.getCost()).multiply(1.25);
    Basket basket = Basket.valueOf();

    basket = basket.add(apple);
    basket = basket.add(banana);

    assertThat(basket.getTotalWithVat()).isEqualTo(totalWithVat);
  }


}
