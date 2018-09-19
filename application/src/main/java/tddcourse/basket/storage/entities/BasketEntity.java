package tddcourse.basket.storage.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
public class BasketEntity {

    @Id
    private UUID id;
    private List<ProductEntity> productEntities;

    private BasketEntity(UUID id, List<ProductEntity> productEntities) {
        this.id = id;
        this.productEntities = productEntities;
    }

    public static BasketEntity valueOf(UUID id, List<ProductEntity> productEntities) {
        return new BasketEntity(id, productEntities);
    }

    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public UUID getId() {
        return id;
    }
}
