package tddcourse.basket.storage.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import tddcourse.basket.storage.entities.BasketEntity;

@Repository
public interface BasketRepository extends CrudRepository<BasketEntity, UUID> {}
