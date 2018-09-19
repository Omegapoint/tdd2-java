package tddcourse.basket.domain.mappers;

import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.Product;
import tddcourse.basket.domain.dto.MoneyDTO;
import tddcourse.basket.domain.dto.ProductDTO;
import tddcourse.basket.storage.entities.MoneyEntity;
import tddcourse.basket.storage.entities.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
        throw new AssertionError("Class should not be instantiated");
    }

    public static Product entityToProduct(ProductEntity productEntity) {
        Money cost = MoneyMapper.entityToMoney(productEntity.getCost());
        return Product.valueOf(productEntity.getId(), productEntity.getName(), cost);
    }

    public static List<Product> entitiesToProducts(List<ProductEntity> productEntities) {
        return productEntities.stream().map(ProductMapper::entityToProduct).collect(Collectors.toList());
    }

    public static List<ProductEntity> productsToEntities(List<Product> products) {
        return products.stream().map(ProductMapper::productToEntity).collect(Collectors.toList());
    }

    public static ProductEntity productToEntity(Product product) {
        MoneyEntity moneyEntity = MoneyMapper.moneyToEntity(product.getCost());
        return ProductEntity.valueOf(product.getId(), product.getName(), moneyEntity);
    }

    public static ProductDTO productToDTO(Product product) {
        long id = product.getId();
        String name = product.getName();
        MoneyDTO moneyDTO = MoneyMapper.moneyToDTO(product.getCost());
        return ProductDTO.valueOf(id, name, moneyDTO);
    }

    public static List<ProductDTO> productsToDTOs(List<Product> items) {
        return items.stream().map(ProductMapper::productToDTO).collect(Collectors.toList());

    }

    public static Product dtoToProduct(ProductDTO productDTO) {
        return Product.valueOf(productDTO.getId(), productDTO.getName(), MoneyMapper.dtoToMoney(productDTO.getCost()));
    }

}
