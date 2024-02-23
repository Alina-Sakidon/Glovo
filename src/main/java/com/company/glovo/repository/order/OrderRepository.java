package com.company.glovo.repository.order;

import com.company.glovo.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {

    Page<Order> findAll(Pageable pageable);
}
