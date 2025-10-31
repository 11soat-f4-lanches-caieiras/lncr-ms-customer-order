package br.com.tp.lncr.customerorder.datasource.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JpaCustomerOrderFoodItemRepository extends JpaRepository<JpaCustomerOrderFoodItemEntity, Integer> {

    @Query(value = "select * from customer_order_food_item where order_id = :orderId", nativeQuery = true)
    List<JpaCustomerOrderFoodItemEntity> findByCustomerOrderId(@Param("orderId") Integer orderId);

    @Query(value = "select * from customer_order_food_item where order_id in(:customerOrderIdsList)", nativeQuery = true)
    List<JpaCustomerOrderFoodItemEntity> findByCustomerOrderIdList(@Param("customerOrderIdsList") List<Integer> customerOrderIdsList);
}
