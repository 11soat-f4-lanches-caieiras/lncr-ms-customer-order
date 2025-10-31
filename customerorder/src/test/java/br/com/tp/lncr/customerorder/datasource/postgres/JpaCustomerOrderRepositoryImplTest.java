package br.com.tp.lncr.customerorder.datasource.postgres;

import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderCustomerDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaCustomerOrderRepositoryImplTest {

    @Mock
    private JpaCustomerOrderRepository jpaCustomerOrderRepository;
    @Mock
    private JpaCustomerOrderMapper jpaCustomerOrderMapper;

    private JpaCustomerOrderRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new JpaCustomerOrderRepositoryImpl();
    }

    @Test
    void shouldSaveCustomerOrderSuccessfully() {
        CustomerOrderCustomerDTO customer = new CustomerOrderCustomerDTO(1, "João Silva");
        CustomerOrderDTO inputDto = createCustomerOrderDTO(null, 50.0, "PENDING", customer, LocalDateTime.now());
        JpaCustomerOrderEntity mappedEntity = createJpaCustomerOrderEntity(null, 50.0, 1, 1, LocalDateTime.now(), "João Silva");
        JpaCustomerOrderEntity savedEntity = createJpaCustomerOrderEntity(1, 50.0, 1, 1, LocalDateTime.now(), "João Silva");
        CustomerOrderDTO expectedDto = createCustomerOrderDTO(1, 50.0, "PENDING", customer, LocalDateTime.now());

        when(jpaCustomerOrderMapper.customerOrderDtoToJpa(inputDto)).thenReturn(mappedEntity);
        when(jpaCustomerOrderRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(jpaCustomerOrderMapper.jpaCustomerOrderToDTO(savedEntity)).thenReturn(expectedDto);

        CustomerOrderDTO result = repository.save(inputDto, jpaCustomerOrderRepository, jpaCustomerOrderMapper);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(50.0, result.getTotalCost());
        verify(jpaCustomerOrderMapper).customerOrderDtoToJpa(inputDto);
        verify(jpaCustomerOrderRepository).save(mappedEntity);
        verify(jpaCustomerOrderMapper).jpaCustomerOrderToDTO(savedEntity);
    }

    @Test
    void shouldSaveCustomerOrderWithNullCustomer() {
        CustomerOrderDTO inputDto = createCustomerOrderDTO(null, 25.0, "PENDING", null, LocalDateTime.now());
        JpaCustomerOrderEntity mappedEntity = createJpaCustomerOrderEntity(null, 25.0, 1, null, LocalDateTime.now(), null);
        JpaCustomerOrderEntity savedEntity = createJpaCustomerOrderEntity(1, 25.0, 1, null, LocalDateTime.now(), null);
        CustomerOrderDTO expectedDto = createCustomerOrderDTO(1, 25.0, "PENDING", null, LocalDateTime.now());

        when(jpaCustomerOrderMapper.customerOrderDtoToJpa(inputDto)).thenReturn(mappedEntity);
        when(jpaCustomerOrderRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(jpaCustomerOrderMapper.jpaCustomerOrderToDTO(savedEntity)).thenReturn(expectedDto);

        CustomerOrderDTO result = repository.save(inputDto, jpaCustomerOrderRepository, jpaCustomerOrderMapper);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNull(result.getCustomer());
        verify(jpaCustomerOrderMapper).customerOrderDtoToJpa(inputDto);
        verify(jpaCustomerOrderRepository).save(mappedEntity);
        verify(jpaCustomerOrderMapper).jpaCustomerOrderToDTO(savedEntity);
    }

    @Test
    void shouldFindCustomerOrderByIdWhenExists() {
        Integer orderId = 1;
        CustomerOrderCustomerDTO customer = new CustomerOrderCustomerDTO(1, "João Silva");
        JpaCustomerOrderEntity entity = createJpaCustomerOrderEntity(1, 75.0, 2, 1, LocalDateTime.now(), "João Silva");
        CustomerOrderDTO expectedDto = createCustomerOrderDTO(1, 75.0, "PREPARING", customer, LocalDateTime.now());

        when(jpaCustomerOrderRepository.findById(orderId)).thenReturn(Optional.of(entity));
        when(jpaCustomerOrderMapper.jpaCustomerOrderToDTO(entity)).thenReturn(expectedDto);

        CustomerOrderDTO result = repository.findById(orderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(75.0, result.getTotalCost());
        assertEquals("PREPARING", result.getStatus());
        verify(jpaCustomerOrderRepository).findById(orderId);
        verify(jpaCustomerOrderMapper).jpaCustomerOrderToDTO(entity);
    }

    @Test
    void shouldReturnNullWhenCustomerOrderNotFoundById() {
        Integer orderId = 999;

        when(jpaCustomerOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        CustomerOrderDTO result = repository.findById(orderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper);

        assertNull(result);
        verify(jpaCustomerOrderRepository).findById(orderId);
        verifyNoInteractions(jpaCustomerOrderMapper);
    }

    @Test
    void shouldFindCustomerOrdersByStatusList() {
        List<Integer> statusList = Arrays.asList(1, 2);
        CustomerOrderCustomerDTO customer1 = new CustomerOrderCustomerDTO(1, "João Silva");
        CustomerOrderCustomerDTO customer2 = new CustomerOrderCustomerDTO(2, "Maria Santos");

        List<JpaCustomerOrderEntity> entities = Arrays.asList(
                createJpaCustomerOrderEntity(1, 50.0, 1, 1, LocalDateTime.now(), "João Silva"),
                createJpaCustomerOrderEntity(2, 75.0, 2, 2, LocalDateTime.now(), "Maria Santos")
        );

        CustomerOrderDTO dto1 = createCustomerOrderDTO(1, 50.0, "PENDING", customer1, LocalDateTime.now());
        CustomerOrderDTO dto2 = createCustomerOrderDTO(2, 75.0, "PREPARING", customer2, LocalDateTime.now());

        when(jpaCustomerOrderRepository.findByStatusListIds(statusList)).thenReturn(entities);
        when(jpaCustomerOrderMapper.jpaCustomerOrderToDTO(entities.get(0))).thenReturn(dto1);
        when(jpaCustomerOrderMapper.jpaCustomerOrderToDTO(entities.get(1))).thenReturn(dto2);

        List<CustomerOrderDTO> result = repository.findByStatusList(statusList, jpaCustomerOrderRepository, jpaCustomerOrderMapper);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
        verify(jpaCustomerOrderRepository).findByStatusListIds(statusList);
    }

    @Test
    void shouldReturnEmptyListWhenNoOrdersFoundByStatusList() {
        List<Integer> statusList = List.of(99);

        when(jpaCustomerOrderRepository.findByStatusListIds(statusList)).thenReturn(Collections.emptyList());

        List<CustomerOrderDTO> result = repository.findByStatusList(statusList, jpaCustomerOrderRepository, jpaCustomerOrderMapper);

        assertTrue(result.isEmpty());
        verify(jpaCustomerOrderRepository).findByStatusListIds(statusList);
        verifyNoInteractions(jpaCustomerOrderMapper);
    }

    @Test
    void shouldFindCustomerOrdersByEmptyStatusList() {
        List<Integer> emptyStatusList = Collections.emptyList();

        when(jpaCustomerOrderRepository.findByStatusListIds(emptyStatusList)).thenReturn(Collections.emptyList());

        List<CustomerOrderDTO> result = repository.findByStatusList(emptyStatusList, jpaCustomerOrderRepository, jpaCustomerOrderMapper);

        assertTrue(result.isEmpty());
        verify(jpaCustomerOrderRepository).findByStatusListIds(emptyStatusList);
        verifyNoInteractions(jpaCustomerOrderMapper);
    }

    private CustomerOrderDTO createCustomerOrderDTO(Integer id, Double totalCost, String status, CustomerOrderCustomerDTO customer, LocalDateTime created) {
        CustomerOrderDTO dto = new CustomerOrderDTO();
        dto.setId(id);
        dto.setTotalCost(totalCost);
        dto.setStatus(status);
        dto.setCustomer(customer);
        dto.setCreated(created);
        dto.setUpdated(null);
        return dto;
    }

    private JpaCustomerOrderEntity createJpaCustomerOrderEntity(Integer id, Double totalCost, Integer statusId, Integer customerId, LocalDateTime created, String customerName) {
        return new JpaCustomerOrderEntity(id, totalCost, statusId, customerId, created, null, customerName);
    }
}
