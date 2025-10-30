package br.com.tp.lncr.customerorder.datasource.postgres;

import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderCustomerDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderFoodItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JpaCustomerOrderMapperTest {

    private JpaCustomerOrderMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new JpaCustomerOrderMapper();
    }

    @Test
    void shouldMapJpaCustomerOrderEntityToDTOSuccessfully() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity(1, 50.0, 2, 10, created, updated, "João Silva");

        CustomerOrderDTO result = mapper.jpaCustomerOrderToDTO(entity);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(50.0, result.getTotalCost());
        assertEquals("Received", result.getStatus());
        assertEquals(created, result.getCreated());
        assertEquals(updated, result.getUpdated());
        assertNotNull(result.getCustomer());
        assertEquals(10, result.getCustomer().getId());
        assertEquals("João Silva", result.getCustomer().getName());
    }

    @Test
    void shouldMapJpaCustomerOrderEntityWithNullCustomerToDTOSuccessfully() {
        LocalDateTime created = LocalDateTime.now();
        JpaCustomerOrderEntity entity = new JpaCustomerOrderEntity(2, 75.0, 1, null, created, null, null);

        CustomerOrderDTO result = mapper.jpaCustomerOrderToDTO(entity);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(75.0, result.getTotalCost());
        assertEquals("Checkout", result.getStatus());
        assertEquals(created, result.getCreated());
        assertNull(result.getUpdated());
        assertNotNull(result.getCustomer());
        assertNull(result.getCustomer().getId());
        assertNull(result.getCustomer().getName());
    }

    @Test
    void shouldReturnNullWhenJpaCustomerOrderEntityIsNull() {
        CustomerOrderDTO result = mapper.jpaCustomerOrderToDTO(null);

        assertNull(result);
    }

    @Test
    void shouldMapCustomerOrderDTOToJpaEntitySuccessfully() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        CustomerOrderCustomerDTO customer = new CustomerOrderCustomerDTO(5, "Maria Santos");
        CustomerOrderDTO dto = createCustomerOrderDTO(3, "Preparing", 100.0, created, updated, customer);

        JpaCustomerOrderEntity result = mapper.customerOrderDtoToJpa(dto);

        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals(100.0, result.getTotalCost());
        assertEquals(3, result.getStatusId());
        assertEquals(created, result.getCreated());
        assertEquals(updated, result.getUpdated());
        assertEquals(5, result.getCustomerId());
        assertEquals("Maria Santos", result.getCustomerName());
    }

    @Test
    void shouldMapCustomerOrderDTOWithNullCustomerToJpaEntitySuccessfully() {
        LocalDateTime created = LocalDateTime.now();
        CustomerOrderDTO dto = createCustomerOrderDTO(4, "Ready", 80.0, created, null, null);

        JpaCustomerOrderEntity result = mapper.customerOrderDtoToJpa(dto);

        assertNotNull(result);
        assertEquals(4, result.getId());
        assertEquals(80.0, result.getTotalCost());
        assertEquals(4, result.getStatusId());
        assertEquals(created, result.getCreated());
        assertNull(result.getUpdated());
        assertNull(result.getCustomerId());
        assertNull(result.getCustomerName());
    }

    @Test
    void shouldReturnNullWhenCustomerOrderDTOIsNull() {
        JpaCustomerOrderEntity result = mapper.customerOrderDtoToJpa(null);

        assertNull(result);
    }

    @Test
    void shouldMapJpaCustomerOrderFoodItemEntityToDTOSuccessfully() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity(1, 10, 5, 25.0, "Sem cebola", "Hambúrguer", "Hambúrguer artesanal");

        CustomerOrderFoodItemDTO result = mapper.jpaCustomerOrderFoodItemToDTO(entity);

        assertNotNull(result);
        assertEquals(5, result.getId());
        assertEquals(10, result.getOrderId());
        assertEquals("Hambúrguer", result.getName());
        assertEquals("Hambúrguer artesanal", result.getDescription());
        assertEquals(25.0, result.getPrice());
        assertEquals("Sem cebola", result.getNotes());
    }

    @Test
    void shouldMapJpaCustomerOrderFoodItemEntityWithNullNotesToDTOSuccessfully() {
        JpaCustomerOrderFoodItemEntity entity = new JpaCustomerOrderFoodItemEntity(2, 15, 8, 18.0, null, "Batata Frita", "Batata crocante");

        CustomerOrderFoodItemDTO result = mapper.jpaCustomerOrderFoodItemToDTO(entity);

        assertNotNull(result);
        assertEquals(8, result.getId());
        assertEquals(15, result.getOrderId());
        assertEquals("Batata Frita", result.getName());
        assertEquals("Batata crocante", result.getDescription());
        assertEquals(18.0, result.getPrice());
        assertNull(result.getNotes());
    }

    @Test
    void shouldReturnNullWhenJpaCustomerOrderFoodItemEntityIsNull() {
        CustomerOrderFoodItemDTO result = mapper.jpaCustomerOrderFoodItemToDTO(null);

        assertNull(result);
    }

    @Test
    void shouldMapCustomerOrderFoodItemDTOToJpaEntitySuccessfully() {
        CustomerOrderFoodItemDTO dto = new CustomerOrderFoodItemDTO(3, 20, "Refrigerante", "Coca-Cola 350ml", 5.0, "Gelado");

        JpaCustomerOrderFoodItemEntity result = mapper.customerOrderFoodItemDtoToJpa(dto);

        assertNotNull(result);
        assertEquals(20, result.getOrderId());
        assertEquals(3, result.getFoodItemId());
        assertEquals("Refrigerante", result.getName());
        assertEquals("Coca-Cola 350ml", result.getDescription());
        assertEquals(5.0, result.getPrice());
        assertEquals("Gelado", result.getNotes());
        assertNull(result.getId());
    }

    @Test
    void shouldMapCustomerOrderFoodItemDTOWithNullNotesToJpaEntitySuccessfully() {
        CustomerOrderFoodItemDTO dto = new CustomerOrderFoodItemDTO(7, 25, "Sorvete", "Sorvete de baunilha", 12.0, null);

        JpaCustomerOrderFoodItemEntity result = mapper.customerOrderFoodItemDtoToJpa(dto);

        assertNotNull(result);
        assertEquals(25, result.getOrderId());
        assertEquals(7, result.getFoodItemId());
        assertEquals("Sorvete", result.getName());
        assertEquals("Sorvete de baunilha", result.getDescription());
        assertEquals(12.0, result.getPrice());
        assertNull(result.getNotes());
        assertNull(result.getId());
    }

    @Test
    void shouldReturnNullWhenCustomerOrderFoodItemDTOIsNull() {
        JpaCustomerOrderFoodItemEntity result = mapper.customerOrderFoodItemDtoToJpa(null);

        assertNull(result);
    }

    @Test
    void shouldHandleAllCustomerOrderStatusesCorrectly() {
        assertEquals("Checkout", mapper.jpaCustomerOrderToDTO(createEntityWithStatus(1)).getStatus());
        assertEquals("Received", mapper.jpaCustomerOrderToDTO(createEntityWithStatus(2)).getStatus());
        assertEquals("Preparing", mapper.jpaCustomerOrderToDTO(createEntityWithStatus(3)).getStatus());
        assertEquals("Ready", mapper.jpaCustomerOrderToDTO(createEntityWithStatus(4)).getStatus());
        assertEquals("Finished", mapper.jpaCustomerOrderToDTO(createEntityWithStatus(5)).getStatus());
        assertEquals("Cancelled", mapper.jpaCustomerOrderToDTO(createEntityWithStatus(6)).getStatus());
    }

    @Test
    void shouldMapStatusDescriptionToIdCorrectly() {
        assertEquals(1, mapper.customerOrderDtoToJpa(createDTOWithStatus("Checkout")).getStatusId());
        assertEquals(2, mapper.customerOrderDtoToJpa(createDTOWithStatus("Received")).getStatusId());
        assertEquals(3, mapper.customerOrderDtoToJpa(createDTOWithStatus("Preparing")).getStatusId());
        assertEquals(4, mapper.customerOrderDtoToJpa(createDTOWithStatus("Ready")).getStatusId());
        assertEquals(5, mapper.customerOrderDtoToJpa(createDTOWithStatus("Finished")).getStatusId());
        assertEquals(6, mapper.customerOrderDtoToJpa(createDTOWithStatus("Cancelled")).getStatusId());
    }

    private CustomerOrderDTO createCustomerOrderDTO(Integer id, String status, Double totalCost, LocalDateTime created, LocalDateTime updated, CustomerOrderCustomerDTO customer) {
        CustomerOrderDTO dto = new CustomerOrderDTO();
        dto.setId(id);
        dto.setStatus(status);
        dto.setTotalCost(totalCost);
        dto.setCreated(created);
        dto.setUpdated(updated);
        dto.setCustomer(customer);
        return dto;
    }

    private JpaCustomerOrderEntity createEntityWithStatus(Integer statusId) {
        return new JpaCustomerOrderEntity(1, 50.0, statusId, 1, LocalDateTime.now(), null, "Test User");
    }

    private CustomerOrderDTO createDTOWithStatus(String status) {
        CustomerOrderDTO dto = new CustomerOrderDTO();
        dto.setStatus(status);
        return dto;
    }
}
