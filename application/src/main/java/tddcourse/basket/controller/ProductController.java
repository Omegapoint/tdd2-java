package tddcourse.basket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import tddcourse.basket.domain.dto.ProductDTO;
import tddcourse.basket.services.ProductService;

@RestController
@RequestMapping(path = "/products",
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {

  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<ProductDTO> getProducts() {
    return productService.getAllProducts();
  }
}