package tddcourse.basket.domain.mappers;

import com.google.common.collect.ImmutableMap;

import java.util.Currency;
import java.util.Locale;

public class CurrencyMapper {


    private static final Locale defaultLocale = new Locale("sv", "SE");
    private static ImmutableMap supportedLocales =
            new ImmutableMap.Builder()
                    .put("SEK", new Locale("sv", "SE"))
                    .put("JPY", Locale.JAPAN)
                    .build();

    public static Currency fromCurrencyCode(String currencyCode) {
        Locale currentLocale = (Locale) supportedLocales.get(currencyCode);
        Locale locale = currentLocale != null ? currentLocale : defaultLocale;
        Locale.setDefault(locale);
        if (currencyCode.equals("None")) {
            return null;
        }

        return Currency.getInstance(currencyCode);
    }

    /*
     * Map<Currency, String> supportedCurrencies = { // Arbitrary subset of ISO 4217 currencies { "AED", new CurrencyInfo("AED", 2, "د.إ") }, // United Arab
     * Emirates dirham { "AFN", new CurrencyInfo("AFN", 2, "؋") }, // Afghan afghani { "DKK", new CurrencyInfo("DKK", 2, "kr.") }, // Danish krone { "EUR", new
     * CurrencyInfo("EUR", 2, "€") }, // Euro { "GBP", new CurrencyInfo("GBP", 2, "£") }, // Pound sterling { "SEK", new CurrencyInfo("SEK", 2, "kr") }, //
     * Swedish krona { "USD", new CurrencyInfo("USD", 2, "$") }, // United States dollar { "JPY", new CurrencyInfo("JPY", 0, "¥") }, // Japanese yen { "JOD", new
     * CurrencyInfo("JOD", 3, "د.ا.‏") }, // Jordanian dinar { "VND", new CurrencyInfo("VND", 0, "₫") } // Vietnamese dong }; }
     */

}
