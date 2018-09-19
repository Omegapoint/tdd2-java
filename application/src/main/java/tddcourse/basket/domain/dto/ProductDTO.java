package tddcourse.basket.domain.dto;

import java.util.Objects;

@SuppressWarnings("unused")
public class ProductDTO {

  private long id;
  private String name;
  private MoneyDTO cost;

  public ProductDTO() {
    super();
  }

  private ProductDTO(long id, String name, MoneyDTO cost) {
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

  public static ProductDTO valueOf(long id, String name, MoneyDTO moneyDTO) {
    return new ProductDTO(id, name, moneyDTO);
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MoneyDTO getCost() {
    return this.cost;
  }

  public void setCost(MoneyDTO cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "ProductDTO{" +
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
    ProductDTO productDTO = (ProductDTO) o;
    return id == productDTO.id &&
        Objects.equals(name, productDTO.name) &&
        Objects.equals(cost, productDTO.cost);
  }
}
