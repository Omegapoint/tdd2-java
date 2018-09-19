package tddcourse.basket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tddcourse.basket.domain.Basket;
import tddcourse.basket.domain.Money;
import tddcourse.basket.domain.Product;
import tddcourse.basket.domain.mappers.BasketMapper;
import tddcourse.basket.exceptions.NotFoundException;
import tddcourse.basket.storage.entities.BasketEntity;
import tddcourse.basket.storage.repositories.BasketRepository;

import java.util.UUID;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    @Autowired
    public BasketService(BasketRepository basketRepository, ProductService productService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
    }

    public Basket createBasket() {
        Basket newBasket = Basket.valueOf();
        BasketEntity basketEntity = BasketMapper.basketToEntity(newBasket);
        BasketEntity storedBasket = basketRepository.save(basketEntity);
        return BasketMapper.basketEntityToBasket(storedBasket);
    }

    public Basket getBasket(UUID id) {
        BasketEntity basketEntity = basketRepository
                .findById(id)
                .orElseThrow(
                        () -> NotFoundException
                                .withMsg("Basket with id" + id + " was not found"));
        return BasketMapper.basketEntityToBasket(basketEntity);
    }

    public Basket addProductToBasket(UUID id, long productId) {
        Basket basket = getBasket(id);
        Product storedProduct = productService.findById(productId);

        BasketEntity basketEntity = BasketMapper.basketToEntity(basket.add(storedProduct));
        BasketEntity storedBasket = basketRepository.save(basketEntity);
        return BasketMapper.basketEntityToBasket(storedBasket);
    }

    public void checkout(Money cost) {

    }
}
