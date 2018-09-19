package tddcourse.basket.storage.entities;

import java.util.Currency;
import java.util.Objects;

public class MoneyEntity {

  private long units;
  private int decimalPlaces;
  private Currency currency;

  private MoneyEntity(long units, int decimalPlaces, Currency currency) {
    this.units = units;
    this.decimalPlaces = decimalPlaces;
    this.currency = currency;
  }

  public static MoneyEntity valueOf(long units, int decimalPlaces, Currency currency) {
    return new MoneyEntity(units, decimalPlaces, currency);
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  public Currency getCurrency() {
    return currency;
  }

  public long getUnits() {
    return units;
  }

  @Override
  public int hashCode() {

    return Objects.hash(units, decimalPlaces, currency);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MoneyEntity that = (MoneyEntity) o;
    return units == that.units &&
        decimalPlaces == that.decimalPlaces &&
        Objects.equals(currency, that.currency);
  }
}
