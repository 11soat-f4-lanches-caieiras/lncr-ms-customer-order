package br.com.tp.lncr.customerorder.apis;

import br.com.tp.lncr.commons.model.ResponseListModel;
import br.com.tp.lncr.commons.model.ResponseModel;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderCustomerDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderController;
import br.com.tp.lncr.customerorder.configs.CustomerOrderConfig;
import br.com.tp.lncr.customerorder.dataproxy.CustomerOrderDataProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerOrderRestControllerImplTest {

    @Mock
    private CustomerOrderConfig customerOrderConfig;

    @Mock
    private CustomerOrderDataProxy customerOrderDatabase;

    @Mock
    private CustomerOrderController customerOrderController;

    @InjectMocks
    private CustomerOrderRestControllerImpl customerOrderRestController;

    private CustomerOrderDTO customerOrderDTO;

    @BeforeEach
    void setUp() {
        customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(1);
        customerOrderDTO.setCustomer(new CustomerOrderCustomerDTO());
        customerOrderDTO.getCustomer().setId(1);
        customerOrderDTO.setStatus("PENDING");
    }

    @Test
    void deveCriarCustomerOrderComSucesso() {
        Mockito.when(customerOrderController.create(customerOrderDatabase, customerOrderDTO)).thenReturn(customerOrderDTO);
        Mockito.when(customerOrderConfig.getLocationPrefix()).thenReturn("/customerOrders");

        ResponseEntity<ResponseModel<CustomerOrderDTO>> response = customerOrderRestController.createCustomerOrder(customerOrderDTO);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(customerOrderDTO, response.getBody().getContent());
        Assertions.assertNotNull(response.getHeaders().getLocation());
        Assertions.assertTrue(response.getHeaders().getLocation().toString().contains("/customerOrders/1"));
        Mockito.verify(customerOrderController).create(customerOrderDatabase, customerOrderDTO);
    }

    @Test
    void deveRetornarCustomerOrderPorIdSemFoodItems() {
        Mockito.when(customerOrderController.getById(customerOrderDatabase, 1, false)).thenReturn(customerOrderDTO);

        ResponseEntity<ResponseModel<CustomerOrderDTO>> response = customerOrderRestController.getCustomerOrderById(1, false);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(customerOrderDTO, response.getBody().getContent());
        Mockito.verify(customerOrderController).getById(customerOrderDatabase, 1, false);
    }

    @Test
    void deveRetornarCustomerOrderPorIdComFoodItems() {
        Mockito.when(customerOrderController.getById(customerOrderDatabase, 1, true)).thenReturn(customerOrderDTO);

        ResponseEntity<ResponseModel<CustomerOrderDTO>> response = customerOrderRestController.getCustomerOrderById(1, true);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(customerOrderDTO, response.getBody().getContent());
        Mockito.verify(customerOrderController).getById(customerOrderDatabase, 1, true);
    }

    @Test
    void deveRetornarCustomerOrdersPorStatusListSemFoodItems() {
        List<String> statusList = List.of("PENDING", "CONFIRMED");
        List<CustomerOrderDTO> orders = List.of(customerOrderDTO);
        Mockito.when(customerOrderController.getByStatusList(customerOrderDatabase, statusList, false)).thenReturn(orders);

        ResponseEntity<ResponseListModel<CustomerOrderDTO>> response = customerOrderRestController.getCustomerOrderByStatus(statusList, false);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(orders, response.getBody().getContent());
        Mockito.verify(customerOrderController).getByStatusList(customerOrderDatabase, statusList, false);
    }

    @Test
    void deveRetornarCustomerOrdersPorStatusListComFoodItems() {
        List<String> statusList = List.of("PENDING");
        List<CustomerOrderDTO> orders = List.of(customerOrderDTO);
        Mockito.when(customerOrderController.getByStatusList(customerOrderDatabase, statusList, true)).thenReturn(orders);

        ResponseEntity<ResponseListModel<CustomerOrderDTO>> response = customerOrderRestController.getCustomerOrderByStatus(statusList, true);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(orders, response.getBody().getContent());
        Mockito.verify(customerOrderController).getByStatusList(customerOrderDatabase, statusList, true);
    }

    @Test
    void deveAtualizarStatusDoCustomerOrderSemForceUpdate() {
        CustomerOrderDTO updatedOrder = new CustomerOrderDTO();
        updatedOrder.setId(1);
        updatedOrder.setStatus("CONFIRMED");
        Mockito.when(customerOrderController.updateStatusById(customerOrderDatabase, 1, "CONFIRMED", false)).thenReturn(updatedOrder);

        ResponseEntity<ResponseModel<CustomerOrderDTO>> response = customerOrderRestController.updateOrderStatusById(1, "CONFIRMED", false);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(updatedOrder, response.getBody().getContent());
        Mockito.verify(customerOrderController).updateStatusById(customerOrderDatabase, 1, "CONFIRMED", false);
    }

    @Test
    void deveAtualizarStatusDoCustomerOrderComForceUpdate() {
        CustomerOrderDTO updatedOrder = new CustomerOrderDTO();
        updatedOrder.setId(1);
        updatedOrder.setStatus("CANCELLED");
        Mockito.when(customerOrderController.updateStatusById(customerOrderDatabase, 1, "CANCELLED", true)).thenReturn(updatedOrder);

        ResponseEntity<ResponseModel<CustomerOrderDTO>> response = customerOrderRestController.updateOrderStatusById(1, "CANCELLED", true);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(updatedOrder, response.getBody().getContent());
        Mockito.verify(customerOrderController).updateStatusById(customerOrderDatabase, 1, "CANCELLED", true);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverOrdersComStatus() {
        List<String> statusList = List.of("NONEXISTENT");
        List<CustomerOrderDTO> emptyOrders = List.of();
        Mockito.when(customerOrderController.getByStatusList(customerOrderDatabase, statusList, false)).thenReturn(emptyOrders);

        ResponseEntity<ResponseListModel<CustomerOrderDTO>> response = customerOrderRestController.getCustomerOrderByStatus(statusList, false);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().getContent().isEmpty());
        Mockito.verify(customerOrderController).getByStatusList(customerOrderDatabase, statusList, false);
    }
}
