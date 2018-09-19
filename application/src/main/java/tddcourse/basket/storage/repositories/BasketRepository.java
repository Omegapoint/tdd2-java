package tddcourse.basket.storage.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tddcourse.basket.storage.entities.BasketEntity;

import java.util.UUID;

@Repository
public interface BasketRepository extends CrudRepository<BasketEntity, UUID> {
}
