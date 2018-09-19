package tddcourse.basket.storage.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProductEntity {

    @Id
    private long id;
    private String name;
    private MoneyEntity cost;

    private ProductEntity(long id, String name, MoneyEntity cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;

    }

    public static ProductEntity valueOf(long id, String name, MoneyEntity cost) {
        return new ProductEntity(id, name, cost);
    }

    public MoneyEntity getCost() {
        return cost;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
