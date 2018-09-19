package tddcourse.basket.domain;

import static org.apache.commons.lang3.Validate.isTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public final class Money {
  public static final Money NONE;
  private static final String SEK = "SEK";
  private static RoundingMode roundingMode;

  static {
    roundingMode = RoundingMode.HALF_EVEN;
    NONE = new Money(BigDecimal.ZERO, "None");
  }

  private BigDecimal units;
  private CurrencyInfo currencyInfo;

  private Money(BigDecimal units, String currencyCode) {
    this(units, CurrencyInfo.valueOf(currencyCode));
  }

  private Money(BigDecimal units, CurrencyInfo currencyInfo) {
    isTrue(units.compareTo(BigDecimal.ZERO) >= 0, "Units of money must be positive");
    this.currencyInfo = currencyInfo;
    this.units = units.setScale(currencyInfo.getDecimalPlaces(), roundingMode);
  }

  public static Money ZERO(String currencyCode) {
    return new Money(BigDecimal.ZERO, currencyCode);
  }

  public static Money valueOfSEK(double units) {
    return valueOfCurrency(units, SEK);
  }

  public static Money valueOfCurrency(double units, String currencyCode) {
    return new Money(BigDecimal.valueOf(units), currencyCode);
  }

  public static Money valueOf(long units, Currency currency){
    BigDecimal unit = BigDecimal.valueOf(units/Math.pow(10, currency.getDefaultFractionDigits()));
    return new Money(unit, currency.getCurrencyCode());

  }

  public Money plus(Money money) {
    assertCompatible(money);
    return new Money(this.units.add(money.units), this.currencyInfo);
  }

  public Money minus(Money money) {
    assertCompatible(money);
    return new Money(this.units.subtract(money.units), this.currencyInfo);
  }

  public Money multiply(double value) {
    BigDecimal product = this.units.multiply(BigDecimal.valueOf(value));
    return new Money(product, this.currencyInfo);

  }

  public boolean isZero() {
    return units.compareTo(BigDecimal.ZERO) == 0;
  }

  public double toNumericValue() {
    return getNumericValue();
  }

  private double getNumericValue() {
    return units.setScale(getDecimalPlaces(), roundingMode).doubleValue();
  }

  public String getCurrencySymbol() {
    return this.currencyInfo.getSymbol();
  }

  public long getUnits() {
    return currencyInfo.getNumericValue(units);
  }

  public int getDecimalPlaces() {
    return currencyInfo.getDecimalPlaces();
  }

  private void assertCompatible(Money compare) {

    if (this.currencyInfo == null || this.currencyInfo.getCurrency() == null || compare.currencyInfo == null || compare.currencyInfo.getCurrency() == null) {
      return;
    }
    if (!this.currencyInfo.getCurrencyCode()
        .equals(compare.currencyInfo.getCurrencyCode())) {
      throw new IllegalArgumentException(
          "Operands have difference currency codes: " + this.currencyInfo.getCurrencyCode() + " does not match: " + compare.currencyInfo.getCurrencyCode());
    }
  }

  public String toString() {
    return this.currencyInfo.format(units);
  }

  @Override
  public int hashCode() {
    return Objects.hash(units, currencyInfo);
  }

  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    if (this == object) {
      return true;
    }
    if (!(object instanceof Money)) {
      return false;
    }
    Money money = (Money) object;
    if (this.currencyInfo == null) {
      return money.currencyInfo == null || money.getUnits() == 0;
    }
    if (money.currencyInfo == null) {
      return this.getUnits() == 0;
    }
    if (this.units.equals(BigDecimal.ZERO) && money.units.equals(BigDecimal.ZERO)) {
      return true;
    }
    return (this.units.equals(money.units)) && this.currencyInfo.equals(money.currencyInfo);
  }

  public Currency getCurrency() {
    return this.currencyInfo.getCurrency();
  }
}
