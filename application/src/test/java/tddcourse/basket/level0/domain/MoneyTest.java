package tddcourse.basket.level0.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Tag;

import org.junit.jupiter.api.Test;
import tddcourse.basket.domain.Money;

@Tag("level0")
class MoneyTest {

  @Test
  void shouldEqualToZero() {
    assertThat(Money.ZERO("SEK")).isEqualTo(Money.valueOfSEK(0));

  }

  @Test
  void shouldBeZero() {
    assertThat(Money.ZERO("SEK").isZero()).isTrue();
  }

  @Test
  void shouldDisplayValueWithCorrectCurrencyPrefix() {
    assertThat(Money.valueOfSEK(11.42).toString()).isEqualTo("11,42 kr");
    assertThat(Money.valueOfCurrency(26.36, "EUR").toString()).isEqualTo("26,36 €");
    assertThat(Money.valueOfCurrency(12.53, "JPY").toString()).isEqualTo("￥13");
  }

  @Test
  void shouldEqualNoneToZero(){
    assertThat(Money.NONE).isEqualTo(Money.ZERO("SEK"));
  }

  @Test
  void shouldNotEqualNoneToNonZero(){
    assertThat(Money.NONE).isNotEqualTo(Money.valueOfSEK(1));
  }

  @Test
  void shouldSumNoneToOtherNone(){
    assertThat(Money.NONE).isEqualTo(Money.NONE.plus(Money.NONE));
  }

  @Test
  void shouldSumNoneToOtherCurrency(){
    Money money = Money.valueOfCurrency(12.34, "SEK");
    assertThat(Money.NONE.plus(money)).isEqualTo(money);
    assertThat(Money.NONE.plus(Money.ZERO("SEK"))).isEqualTo(Money.ZERO("SEK"));
  }

  @Test
  void shouldSubstractNoneToOtherNone(){
    assertThat(Money.NONE).isEqualTo(Money.NONE.minus(Money.NONE));
  }

  @Test
  void shouldSubstractNoneToOtherCurrency(){
    Money money = Money.valueOfCurrency(12.34, "SEK");
    assertThat(money).isEqualTo(money.minus(Money.NONE));
    assertThat(Money.ZERO("SEK")).isEqualTo(Money.ZERO("SEK").minus(Money.NONE));
  }

  @Test
  void shouldMultiplyNoneToNone(){
    assertThat(Money.NONE).isEqualTo(Money.NONE.multiply(42));
  }

  @Test
  void shouldSumTwoValues() {
    assertThat(Money.valueOfSEK(11.42).plus(Money.valueOfSEK(0.92))).isEqualTo(Money.valueOfSEK(12.34));
  }

  @Test
  void shouldSubtractTwoValues() {
    assertThat(Money.valueOfSEK(123).minus(Money.valueOfSEK(15.34))).isEqualTo(Money.valueOfSEK(107.66));
  }

  @Test
  void shouldMultiplyTwoValues() {
    assertThat(Money.valueOfSEK(12.55).multiply(4.5)).isEqualTo(Money.valueOfSEK(56.48));
  }

  @Test
  void shouldRoundDown(){
    Money expected = Money.valueOfSEK(5.52);
    assertThat(Money.valueOfSEK(5.521)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.522)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.523)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.524)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.525)).isEqualTo(expected);
  }

  @Test
  void shouldRoundUp() {
    Money expected = Money.valueOfSEK(5.53);
    assertThat(Money.valueOfSEK(5.526)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.527)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.528)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.529)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(5.530)).isEqualTo(expected);

    assertThat(Money.valueOfSEK(5.535)).isEqualTo(Money.valueOfSEK(5.54));
  }

  @Test
  void shouldEqual(){
    assertThat(Money.valueOfSEK(0)).isEqualTo(Money.valueOfSEK(0));
    Money expected = Money.valueOfSEK(1.0);
    assertThat(Money.valueOfSEK(1.0)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(1.001)).isEqualTo(expected);
    assertThat(Money.valueOfSEK(1.0049999999)).isEqualTo(expected);
  }

  @Test
  void shouldNotEqual(){

    //assertThat(Money.valueOfSEK(0)).isNotEqualTo(Money.valueOfSEK(1));
    Money expected = Money.valueOfSEK(1.0);
    assertThat(Money.valueOfSEK(1.01)).isNotEqualTo(expected);
    assertThat(Money.valueOfSEK(1.006)).isNotEqualTo(expected);
  }


  @Test
  void shouldHaveCorrectCurrencySymbol() {
    assertThat(Money.valueOfSEK(12.12).getCurrencySymbol()).isEqualTo("kr");
    assertThat(Money.valueOfCurrency(15.42, "EUR").getCurrencySymbol()).isEqualTo("€");
  }

  @Test
  void shouldThrowExceptionIfNegativeValues() {
    assertThrows(IllegalArgumentException.class, () -> Money.valueOfSEK(-11.32));
  }

  @Test
  void shouldThrowExceptionIfSubtractionIsNegative() {

    Money big = Money.valueOfSEK(6.2);
    Money small = Money.valueOfSEK(3.1);

    assertThrows(IllegalArgumentException.class, () -> small.minus(big));

  }

  @Test
  void shouldRoundToLocaleSpecificDecimals() {
    Money moneyInSEK = Money.valueOfSEK(11.1111);
    assertThat(moneyInSEK).isEqualTo(Money.valueOfSEK(11.11));
    assertThat(moneyInSEK.toNumericValue()).isEqualTo(11.11);

    Money moneyInYEN = Money.valueOfCurrency(43.12, "JPY");
    assertThat(moneyInYEN).isEqualTo(Money.valueOfCurrency(43, "JPY"));
    assertThat(moneyInYEN.toNumericValue()).isEqualTo(43);
  }



  //ShouldSumNoneToOtherNone
  //ShouldSumNoneToOtherCurrency
  //ShouldSubstractNoneToOtherNone
  //ShouldSubstractNoneToOtherCurrency
  //ShouldMultiplyNoneToNone
  //ShouldSumTwoValues
  //ShouldSubstractTwoValues
  //ShouldMultiply
  //ShouldRoundDown
  //ShouldRoundUp
  //ShouldEqual
  //ShouldNotEqual
}
