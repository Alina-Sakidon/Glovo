package com.company.glovo.repository.order;

import com.company.glovo.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {
}
