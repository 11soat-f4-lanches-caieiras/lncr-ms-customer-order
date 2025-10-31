package br.com.tp.lncr.customerorder.datasource.postgres;

import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderCustomerDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderFoodItemDTO;
import br.com.tp.lncr.core.enums.CustomerOrderStatus;
import org.springframework.stereotype.Component;


@Component
public class JpaCustomerOrderMapper {

    public CustomerOrderDTO jpaCustomerOrderToDTO(JpaCustomerOrderEntity entity) {
        if (entity == null) return null;
        CustomerOrderDTO dto = new CustomerOrderDTO();
        dto.setId(entity.getId());
        dto.setTotalCost(entity.getTotalCost());
        dto.setStatus(CustomerOrderStatus.fromId(entity.getStatusId()).getDescription());
        dto.setCustomer(new CustomerOrderCustomerDTO(entity.getCustomerId(), entity.getCustomerName()));
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        return dto;
    }

    public CustomerOrderFoodItemDTO jpaCustomerOrderFoodItemToDTO(JpaCustomerOrderFoodItemEntity entity) {
        if (entity == null) return null;
        return new CustomerOrderFoodItemDTO(
            entity.getFoodItemId(),
            entity.getOrderId(),
            entity.getName(),
            entity.getDescription(),
            entity.getPrice(),
            entity.getNotes()
        );
    }

    public JpaCustomerOrderEntity customerOrderDtoToJpa(CustomerOrderDTO dto) {
        if (dto == null) return null;
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity();
        entity.setId(dto.getId());
        entity.setTotalCost(dto.getTotalCost());
        entity.setStatusId(CustomerOrderStatus.fromDescription(dto.getStatus()).getId());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        if (dto.getCustomer() != null){
            entity.setCustomerId(dto.getCustomer().getId());
            entity.setCustomerName(dto.getCustomer().getName());
        }
        return entity;
    }

    public JpaCustomerOrderFoodItemEntity customerOrderFoodItemDtoToJpa(CustomerOrderFoodItemDTO dto) {
        if (dto == null) return null;
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity();
        entity.setOrderId(dto.getOrderId());
        entity.setFoodItemId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setNotes(dto.getNotes());
        return entity;
    }
}
