package tddcourse.basket.domain.mappers;

import java.util.List;

import tddcourse.basket.domain.Basket;
import tddcourse.basket.domain.Product;
import tddcourse.basket.domain.dto.BasketDTO;
import tddcourse.basket.domain.dto.ProductDTO;
import tddcourse.basket.storage.entities.BasketEntity;
import tddcourse.basket.storage.entities.ProductEntity;

public class BasketMapper {

  private BasketMapper() {
    throw new AssertionError("Class should not be instantiated");
  }

  public static Basket basketEntityToBasket(BasketEntity basket) {
    List<Product> products = ProductMapper.entitiesToProducts(basket.getProductEntities());
    return Basket.valueOf(basket.getId(), products);
  }

  public static BasketEntity basketToEntity(Basket basket) {
    List<ProductEntity> productEntities = ProductMapper.productsToEntities(basket.getProducts());
    return BasketEntity.valueOf(basket.getId(), productEntities);
  }

  public static BasketDTO basketToDTO(Basket basket) {
    List<ProductDTO> productDTOS = ProductMapper.productsToDTOs(basket.getProducts());
    return BasketDTO.valueOf(basket.getId(), productDTOS);

  }
}
