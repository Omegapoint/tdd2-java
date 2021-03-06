package tddcourse.basket.domain.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BasketDTO {
    private UUID id;
    private List<ProductDTO> products;

    public BasketDTO() {
        super();
    }

    private BasketDTO(UUID id, List<ProductDTO> products) {
        this.id = id;
        this.products = products;
    }

    public static BasketDTO valueOf(UUID id, List<ProductDTO> itemDTOS) {
        return new BasketDTO(id, itemDTOS);
    }

    public UUID getId() {
        return id;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BasketDTO basketDTO = (BasketDTO) o;
        return Objects.equals(id, basketDTO.id) &&
                Objects.equals(products, basketDTO.products);
    }
}
