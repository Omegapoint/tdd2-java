package tddcourse.basket.level2.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@SuppressWarnings("unused")
public class MoneyDTO {
  private long units;
  private int decimalPlaces;
  private String currencyCode;

  private MoneyDTO(long units, int decimalPlaces, String currencyCode) {
    this.units = units;
    this.decimalPlaces = decimalPlaces;
    this.currencyCode = currencyCode;
  }

  @JsonCreator
  public static MoneyDTO valueOf(@JsonProperty("long") long units, @JsonProperty("decimalPlaces") int decimalPlaces,
      @JsonProperty("regionInfoCode") String regionInfoCode) {
    return new MoneyDTO(units, decimalPlaces, regionInfoCode);
  }

  public long getUnits() {
    return units;
  }

  public void setUnits(long units) {
    this.units = units;
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  public void setDecimalPlaces(int decimalPlaces) {
    this.decimalPlaces = decimalPlaces;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  @Override
  public String toString() {
    return "MoneyDTO{" +
        "units=" + units +
        ", decimalPlaces=" + decimalPlaces +
        ", currencyCode='" + currencyCode + '\'' +
        '}';
  }

  @Override
  public int hashCode() {

    return Objects.hash(units, decimalPlaces, currencyCode);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MoneyDTO moneyDTO = (MoneyDTO) o;
    return units == moneyDTO.units &&
        decimalPlaces == moneyDTO.decimalPlaces &&
        Objects.equals(currencyCode, moneyDTO.currencyCode);
  }

}
