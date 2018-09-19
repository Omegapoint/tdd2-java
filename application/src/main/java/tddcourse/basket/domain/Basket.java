package tddcourse.basket.domain;

import java.util.*;

public final class Basket {

    private final UUID id;
    private final List<Product> products;

    private Basket(UUID id, List<Product> products) {
        this.id = id;
        this.products = Collections.unmodifiableList(new ArrayList<>(products));
    }

    public static Basket valueOf() {
        return new Basket(UUID.randomUUID(), new ArrayList<>());
    }

    public static Basket valueOf(UUID id, List<Product> products) {
        return new Basket(id, products);
    }

    public Basket add(Product product) {
        ArrayList<Product> basketProducts = new ArrayList<>(this.products);
        basketProducts.add(product);
        return valueOf(this.id, basketProducts);
    }

    public int productCount() {
        return this.products.size();
    }

    public Money getTotal() {
        return (this.products.isEmpty()) ? Money.ZERO("SEK")
                : products.stream()
                .map(Product::getCost)
                .reduce(Money::plus)
                .orElseThrow(() -> new Error("Could not sum the cost of all products in the basket"));

    }

    public Money getTotalWithVat() {
        return this.getTotal().multiply(1.25);
    }

    public UUID getId() {
        return id;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", products=" + products +
                '}';
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
        Basket basket = (Basket) o;
        return Objects.equals(id, basket.id) &&
                Objects.equals(products, basket.products);
    }
}
