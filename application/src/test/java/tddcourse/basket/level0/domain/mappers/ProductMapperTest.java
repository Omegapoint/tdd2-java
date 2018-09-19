package tddcourse.basket.level0.domain.mappers;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.Product;
import tddcourse.basket.domain.dto.MoneyDTO;
import tddcourse.basket.domain.dto.ProductDTO;
import tddcourse.basket.domain.mappers.ProductMapper;
import tddcourse.basket.storage.entities.MoneyEntity;
import tddcourse.basket.storage.entities.ProductEntity;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Tag("level0")
class ProductMapperTest {

    private static ProductDTO validProductDTO;
    private static ProductEntity validProductEntity;
    private static List<ProductEntity> validProductEntities;
    private static Product validProduct;
    private static List<Product> validProducts;

    @BeforeAll
    static void setUp() {
        MoneyDTO moneyDTO = new MoneyDTO();
        moneyDTO.setUnits(123);
        moneyDTO.setDecimalPlaces(2);
        moneyDTO.setCurrencyCode("SEK");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Apple");
        productDTO.setCost(moneyDTO);
        productDTO.setId(1);
        validProductDTO = productDTO;

        MoneyEntity moneyEntity1 = MoneyEntity.valueOf(432, 2, Currency.getInstance(new Locale("sv", "SE")));
        validProductEntity = ProductEntity.valueOf(1, "Pear", moneyEntity1);

        MoneyEntity moneyEntity2 = MoneyEntity.valueOf(196, 2, Currency.getInstance(new Locale("sv", "SE")));
        ProductEntity productEntity = ProductEntity.valueOf(2, "Banana", moneyEntity2);

        validProductEntities = ImmutableList.of(validProductEntity, productEntity);

        validProduct = Product.valueOf(3, "Pineapple", Money.valueOfSEK(54.13));
        Product product = Product.valueOf(3, "Pineapple", Money.valueOfSEK(54.13));
        validProducts = ImmutableList.of(validProduct, product);

    }


    @Test
    void shouldConvertProductDTOToEntity() {
        Product product = ProductMapper.dtoToProduct(validProductDTO);
        assertThat(product).isEqualToIgnoringGivenFields(validProductDTO, "cost");
        assertThat(product.getCost().getUnits()).isEqualTo(validProductDTO.getCost().getUnits());
    }

    @Test
    void shouldConvertToSameProductDTO() {
        Product product = ProductMapper.dtoToProduct(validProductDTO);
        ProductDTO actualProductDTO = ProductMapper.productToDTO(product);
        assertThat(validProductDTO).isEqualTo(actualProductDTO);
    }

    @Test
    void shouldConvertEntityToProduct() {
        Product product = ProductMapper.entityToProduct(validProductEntity);
        assertThat(product).isEqualToIgnoringGivenFields(validProductEntity, "cost");
        assertThat(product.getCost().getUnits()).isEqualTo(validProductEntity.getCost().getUnits());
    }

    @Test
    void shouldConvertEntitiesToProducts() {
        List<Product> products = ProductMapper.entitiesToProducts(validProductEntities);

        assertThat(products.size()).isEqualTo(validProductEntities.size());
        for (Product product : products) {
            assertThat(validProductEntities).usingFieldByFieldElementComparator().contains(ProductMapper.productToEntity(product));
        }

    }

    @Test
    void shouldConvertProductToEntity() {
        ProductEntity productEntity = ProductMapper.productToEntity(validProduct);
        assertThat(productEntity).isEqualToIgnoringGivenFields(validProduct, "cost");
        assertThat(productEntity.getCost().getUnits()).isEqualTo(validProduct.getCost().getUnits());

    }

    @Test
    void shouldConvertProductsToEntities() {
        List<ProductEntity> productEntities = ProductMapper.productsToEntities(validProducts);

        assertThat(productEntities.size()).isEqualTo(validProducts.size());
        for (ProductEntity productEntity : productEntities) {
            assertThat(validProducts).usingFieldByFieldElementComparator().contains(ProductMapper.entityToProduct(productEntity));
        }

    }

    @Test
    void shouldConvertProductToDTO() {
        ProductDTO productDTO = ProductMapper.productToDTO(validProduct);
        assertThat(productDTO).isEqualToIgnoringGivenFields(validProduct, "cost");
        assertThat(productDTO.getCost().getUnits()).isEqualTo(validProduct.getCost().getUnits());
    }

    @Test
    void shouldConvertProductsToDTOs() {
        List<ProductDTO> productDTOS = ProductMapper.productsToDTOs(validProducts);

        assertThat(productDTOS.size()).isEqualTo(validProducts.size());
        for (ProductDTO productDTO : productDTOS) {
            assertThat(validProducts).usingFieldByFieldElementComparator().contains(ProductMapper.dtoToProduct(productDTO));
        }
    }

    @Test
    void shouldConvertDTOToProduct() {
        Product product = ProductMapper.dtoToProduct(validProductDTO);
        assertThat(product).isEqualToIgnoringGivenFields(validProductDTO, "cost");
        assertThat(product.getCost().getUnits()).isEqualTo(validProductDTO.getCost().getUnits());
    }

}
