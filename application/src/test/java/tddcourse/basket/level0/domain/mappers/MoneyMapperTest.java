package tddcourse.basket.level0.domain.mappers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.dto.MoneyDTO;
import tddcourse.basket.domain.mappers.MoneyMapper;
import tddcourse.basket.storage.entities.MoneyEntity;

import java.util.Currency;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Tag("level0")
class MoneyMapperTest {

    private static MoneyEntity validMoneyEntity;
    private static Money validMoney;
    private static MoneyDTO validMoneyDTO;

    @BeforeAll
    static void setUp() {
        validMoneyEntity = MoneyEntity.valueOf(124, 2, Currency.getInstance("SEK"));
        validMoney = Money.valueOfSEK(12.45);
        validMoneyDTO = MoneyDTO.valueOf(432, 2, "SEK");
    }

    @Test
    void shouldConvertEntityToMoney() {
        Money money = MoneyMapper.entityToMoney(validMoneyEntity);
        assertThat(money).isEqualToIgnoringGivenFields(validMoneyEntity, "currencyInfo");
        assertThat(money.getCurrency()).isEqualTo(validMoneyEntity.getCurrency());

        MoneyEntity moneyEntityConverted = MoneyMapper.moneyToEntity(money);
        assertThat(validMoneyEntity).isEqualTo(moneyEntityConverted);
    }

    @Test
    void shouldConvertMoneyToEntity() {
        MoneyEntity moneyEntity = MoneyMapper.moneyToEntity(validMoney);
        assertThat(moneyEntity).isEqualToComparingFieldByField(validMoney);

        Money moneyConverted = MoneyMapper.entityToMoney(moneyEntity);
        assertThat(moneyConverted).isEqualTo(validMoney);
    }

    @Test
    void shouldConvertDTOToMoney() {
        Money money = MoneyMapper.dtoToMoney(validMoneyDTO);
        assertThat(money).isEqualToIgnoringGivenFields(validMoneyDTO, "currencyInfo");
        assertThat(money.getCurrency().getCurrencyCode()).isEqualTo(validMoneyDTO.getCurrencyCode());

        MoneyDTO moneyDTOConverted = MoneyMapper.moneyToDTO(money);
        assertThat(moneyDTOConverted).isEqualTo(validMoneyDTO);

    }

    @Test
    void shouldConvertMoneyToDTO() {
        MoneyDTO moneyDTO = MoneyMapper.moneyToDTO(validMoney);
        assertThat(moneyDTO).isEqualToIgnoringGivenFields(validMoney, "currencyCode");

        Money moneyConverted = MoneyMapper.dtoToMoney(moneyDTO);
        assertThat(moneyConverted).isEqualTo(validMoney);
    }
}
