package br.com.tp.lncr.customerorder.datasource.postgres;


import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaCustomerOrderRepositoryImpl {

    public CustomerOrderDTO save(CustomerOrderDTO customerOrderDTO, JpaCustomerOrderRepository jpaCustomerOrderRepository, JpaCustomerOrderMapper jpaCustomerOrderMapper){
        JpaCustomerOrderEntity jpaCustomerOrderEntity = jpaCustomerOrderMapper.customerOrderDtoToJpa(customerOrderDTO);
        return jpaCustomerOrderMapper.jpaCustomerOrderToDTO(jpaCustomerOrderRepository.save(jpaCustomerOrderEntity));
    }

    public CustomerOrderDTO findById(Integer customerOrderId, JpaCustomerOrderRepository jpaCustomerOrderRepository, JpaCustomerOrderMapper jpaCustomerOrderMapper) {
        return jpaCustomerOrderRepository.findById(customerOrderId)
                .map(jpaCustomerOrderMapper::jpaCustomerOrderToDTO)
                .orElse(null);
    }

    public List<CustomerOrderDTO> findByStatusList(List<Integer> statusList, JpaCustomerOrderRepository jpaCustomerOrderRepository, JpaCustomerOrderMapper jpaCustomerOrderMapper) {
       List<JpaCustomerOrderEntity> jpaCustomerOrderList = jpaCustomerOrderRepository.findByStatusListIds(statusList);
       return jpaCustomerOrderList.stream().map(jpaCustomerOrderMapper::jpaCustomerOrderToDTO).toList();

    }
}
