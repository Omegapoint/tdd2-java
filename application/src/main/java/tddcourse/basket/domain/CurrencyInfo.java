package tddcourse.basket.domain;

import tddcourse.basket.domain.mappers.CurrencyMapper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Objects;

public final class CurrencyInfo {

    private final Currency currency;
    private final int decimalPlaces;
    private final String symbol;
    private final NumberFormat formatter;

    private CurrencyInfo(String currencyCode) {
        this.currency = CurrencyMapper.fromCurrencyCode(currencyCode);
        this.decimalPlaces = currency != null ? currency.getDefaultFractionDigits() : 2;
        this.symbol = currency != null ? currency.getSymbol() : "";
        this.formatter = NumberFormat.getCurrencyInstance();
        if (currency != null) {
            this.formatter.setCurrency(this.currency);
        }
        this.formatter.setMaximumFractionDigits(this.decimalPlaces);
    }

    public static CurrencyInfo valueOf(String currencyCode) {
        return new CurrencyInfo(currencyCode);
    }

    int getDecimalPlaces() {
        return decimalPlaces;
    }

    String getSymbol() {
        return this.symbol;
    }

    String getCurrencyCode() {
        return currency == null ? "" : currency.getDisplayName();
    }

    Currency getCurrency() {
        return this.currency;
    }

    String format(BigDecimal units) {
        return this.formatter.format(units);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CurrencyInfo that = (CurrencyInfo) o;
        if (this.currency == null || that.currency == null) {
            return true;
        }
        return Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, decimalPlaces, symbol, formatter);
    }

    long getNumericValue(BigDecimal units) {
        return units.multiply(BigDecimal.valueOf(Math.pow(10, decimalPlaces))).longValue();
    }
}
