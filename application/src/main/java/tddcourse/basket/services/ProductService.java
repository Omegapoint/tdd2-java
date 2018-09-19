package tddcourse.basket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

import tddcourse.basket.domain.Product;
import tddcourse.basket.domain.dto.ProductDTO;
import tddcourse.basket.domain.mappers.ProductMapper;
import tddcourse.basket.exceptions.NotFoundException;
import tddcourse.basket.restclient.RestClient;

@Service
public class ProductService {

  private RestClient restClient;

  private URI productHost;

  @Autowired
  public ProductService(URI productHost, RestClient restClient) {
    this.restClient = restClient;
    this.productHost = productHost;
  }

  public Product findById(long id) {
    List<ProductDTO> products = getAllProducts();
    ProductDTO productDTO = products.stream().filter(dto -> dto.getId() == id).findFirst().orElseThrow(
        () -> NotFoundException.withMsg("Product with id: " + id + " was not found"));
    return ProductMapper.dtoToProduct(productDTO);
  }

  public List<ProductDTO> getAllProducts() {
    return restClient.get(productHost, new ParameterizedTypeReference<List<ProductDTO>>() {});
  }
}
