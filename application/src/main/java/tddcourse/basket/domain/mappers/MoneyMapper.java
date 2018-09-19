package tddcourse.basket.domain.mappers;

import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.dto.MoneyDTO;
import tddcourse.basket.storage.entities.MoneyEntity;

import java.util.Currency;

public class MoneyMapper {

    private MoneyMapper() {
        throw new AssertionError("Class should not be instantiated!");
    }

    public static Money entityToMoney(MoneyEntity cost) {
        return Money.valueOf(cost.getUnits(), cost.getCurrency());
    }

    public static MoneyEntity moneyToEntity(Money cost) {
        return MoneyEntity.valueOf(cost.getUnits(), cost.getDecimalPlaces(), cost.getCurrency());
    }

    public static MoneyDTO moneyToDTO(Money cost) {
        return MoneyDTO.valueOf(cost.getUnits(), cost.getDecimalPlaces(), cost.getCurrency().toString());
    }

    public static Money dtoToMoney(MoneyDTO moneyDTO) {
        String currencyCode = moneyDTO.getCurrencyCode();
        Currency currency = Currency.getInstance(currencyCode);
        return Money.valueOf(moneyDTO.getUnits(), currency);

    }
}
