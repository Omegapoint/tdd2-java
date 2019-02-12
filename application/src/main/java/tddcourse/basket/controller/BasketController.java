package tddcourse.basket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import tddcourse.basket.domain.dto.BasketDTO;
import tddcourse.basket.domain.mappers.BasketMapper;
import tddcourse.basket.services.BasketService;

@RestController
@RequestMapping(path = "/basket")
public class BasketController {

  private BasketService basketService;

  @Autowired
  public BasketController(BasketService basketService) {
    this.basketService = basketService;
  }

  @GetMapping(path = "/{basketId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public BasketDTO getBasket(@PathVariable("basketId") UUID id) {
    return BasketMapper.basketToDTO(basketService.getBasket(id));
  }

  @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public BasketDTO create() {
    return BasketMapper.basketToDTO(basketService.createBasket());
  }

  @PostMapping(path = "/{basketId}/product/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public BasketDTO addProductToBasket(@PathVariable("basketId") UUID basketId, @PathVariable("productId") long productId) {
    return BasketMapper.basketToDTO(basketService.addProductToBasket(basketId, productId));
  }

  @PostMapping(path = "/{basketId}/checkout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void checkout(@PathVariable("basketId") UUID basketId, @RequestParam("cost") double cost) {

  }
}
