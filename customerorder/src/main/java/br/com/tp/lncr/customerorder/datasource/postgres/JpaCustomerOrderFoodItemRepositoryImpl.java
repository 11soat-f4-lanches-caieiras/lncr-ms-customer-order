package br.com.tp.lncr.customerorder.datasource.postgres;

import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderFoodItemDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaCustomerOrderFoodItemRepositoryImpl {

    
    public List<CustomerOrderFoodItemDTO> saveAll(List<CustomerOrderFoodItemDTO> foodItems, JpaCustomerOrderFoodItemRepository jpaCustomerOrderFoodItemRepository, JpaCustomerOrderMapper jpaCustomerOrderMapper) {
        List<JpaCustomerOrderFoodItemEntity> jpaItemList = foodItems.stream().map(jpaCustomerOrderMapper::customerOrderFoodItemDtoToJpa).toList();
        jpaItemList = jpaCustomerOrderFoodItemRepository.saveAll(jpaItemList);
        return jpaItemList.stream().map(jpaCustomerOrderMapper::jpaCustomerOrderFoodItemToDTO).toList();
    }

    public List<CustomerOrderFoodItemDTO> findByCustomerOrderId(Integer customerOrderId, JpaCustomerOrderFoodItemRepository jpaCustomerOrderFoodItemRepository, JpaCustomerOrderMapper jpaCustomerOrderMapper) {
        return jpaCustomerOrderFoodItemRepository.findByCustomerOrderId(customerOrderId)
                .stream()
                .map(jpaCustomerOrderMapper::jpaCustomerOrderFoodItemToDTO)
                .toList();
    }

    public List<CustomerOrderFoodItemDTO> findByCustomerOrderIdList(List<Integer> customerOrdersIdsList, JpaCustomerOrderFoodItemRepository jpaCustomerOrderFoodItemRepository, JpaCustomerOrderMapper jpaCustomerOrderMapper) {
        List<JpaCustomerOrderFoodItemEntity> jpaCustomerOrderFoodItemList = jpaCustomerOrderFoodItemRepository.findByCustomerOrderIdList(customerOrdersIdsList);
        return jpaCustomerOrderFoodItemList.stream().map(jpaCustomerOrderMapper::jpaCustomerOrderFoodItemToDTO).toList();
    }
}
