package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author
 **/
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

}
