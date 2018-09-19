package tddcourse.basket.domain;

import java.util.Objects;

public final class Product {

    private long id;
    private String name;
    private Money cost;

    private Product(long id, String name, Money cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;

    }

    public static Product valueOf(long id, String name, Money cost) {
        return new Product(id, name, cost);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name) &&
                Objects.equals(cost, product.cost);
    }
}
