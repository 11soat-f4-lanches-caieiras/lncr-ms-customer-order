package br.com.tp.lncr.customerorder.datasource.postgres;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaCustomerOrderRepository extends JpaRepository<JpaCustomerOrderEntity, Integer> {

    @Query(value = "select * from customer_order where status_id = :statusId", nativeQuery = true)
    List<JpaCustomerOrderEntity> findByStatusId(@Param("statusId") Integer statusId);

    @Query(value = "select * from customer_order where status_id in(:statusListIds)", nativeQuery = true)
    List<JpaCustomerOrderEntity> findByStatusListIds(@Param("statusListIds") List<Integer> statusListIds);

}
