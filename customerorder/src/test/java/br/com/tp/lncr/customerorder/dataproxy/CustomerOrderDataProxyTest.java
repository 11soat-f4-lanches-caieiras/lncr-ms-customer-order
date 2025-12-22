package br.com.tp.lncr.customerorder.dataproxy;

import br.com.tp.lncr.commons.integrations.IntegrationMapper;
import br.com.tp.lncr.commons.integrations.customer.CustomerIntegrationImpl;
import br.com.tp.lncr.commons.integrations.fooditem.FoodItemIntegrationImpl;
import br.com.tp.lncr.commons.integrations.kitchenorder.KitchenOrderIntegrationImpl;
import br.com.tp.lncr.commons.integrations.notifcation.NotificationIntegraionImpl;
import br.com.tp.lncr.commons.integrations.payment.PaymentIntegrationImpl;
import br.com.tp.lncr.core.dtos.customer.CustomerDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderCustomerDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderFoodItemDTO;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import br.com.tp.lncr.core.dtos.payment.PaymentMercadopagoQrDTO;
import br.com.tp.lncr.customerorder.datasource.postgres.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerOrderDataProxyTest {

    @Mock
    private JpaCustomerOrderRepositoryImpl jpaCustomerOrderRepositoryImpl;
    @Mock
    private JpaCustomerOrderRepository jpaCustomerOrderRepository;
    @Mock
    private JpaCustomerOrderFoodItemRepositoryImpl jpaCustomerOrderFoodItemRepositoryImpl;
    @Mock
    private JpaCustomerOrderFoodItemRepository jpaCustomerOrderFoodItemRepository;
    @Mock
    private CustomerIntegrationImpl customerIntegration;
    @Mock
    private FoodItemIntegrationImpl foodItemIntegration;
    @Mock
    private PaymentIntegrationImpl paymentIntegration;
    @Mock
    private KitchenOrderIntegrationImpl kitchenOrderIntegration;
    @Mock
    private NotificationIntegraionImpl notificationIntegration;
    @Mock
    private JpaCustomerOrderMapper jpaCustomerOrderMapper;

    private CustomerOrderDataProxy customerOrderDataProxy;
    private IntegrationMapper integrationMapper;

    @BeforeEach
    void setUp() {
        customerOrderDataProxy = new CustomerOrderDataProxy(
                jpaCustomerOrderRepositoryImpl,
                jpaCustomerOrderRepository,
                jpaCustomerOrderFoodItemRepositoryImpl,
                jpaCustomerOrderFoodItemRepository,
                customerIntegration,
                foodItemIntegration,
                paymentIntegration,
                kitchenOrderIntegration,
                notificationIntegration,
                jpaCustomerOrderMapper,
                integrationMapper
        );
    }

    @Test
    void shouldReturnFoodItemDetailsListWhenValidIds() {
        List<Integer> foodItemIds = Arrays.asList(1, 2, 3);
        List<CustomerOrderFoodItemDTO> expectedItems = Arrays.asList(
                new CustomerOrderFoodItemDTO(),
                new CustomerOrderFoodItemDTO()
        );

        Mockito.when(foodItemIntegration.getFoodItemDetailList(foodItemIds)).thenReturn(expectedItems);

        List<CustomerOrderFoodItemDTO> result = customerOrderDataProxy.findFoodItemsDetailsList(foodItemIds);

        Assertions.assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(foodItemIntegration).getFoodItemDetailList(foodItemIds);
    }

    @Test
    void shouldReturnEmptyListWhenNoFoodItemIdsProvided() {
        List<Integer> emptyIds = List.of();
        List<CustomerOrderFoodItemDTO> expectedItems = List.of();

        Mockito.when(foodItemIntegration.getFoodItemDetailList(emptyIds)).thenReturn(expectedItems);

        List<CustomerOrderFoodItemDTO> result = customerOrderDataProxy.findFoodItemsDetailsList(emptyIds);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(foodItemIntegration).getFoodItemDetailList(emptyIds);
    }

    @Test
    void shouldReturnCustomerDetailsListWhenValidIds() {
        List<Integer> customerIds = Arrays.asList(1, 2);
        List<CustomerOrderCustomerDTO> expectedCustomers = Arrays.asList(
                new CustomerOrderCustomerDTO(),
                new CustomerOrderCustomerDTO()
        );

        Mockito.when(customerIntegration.getCustomerDetailsList(customerIds)).thenReturn(expectedCustomers);

        List<CustomerOrderCustomerDTO> result = customerOrderDataProxy.findCustomerDetailsList(customerIds);

        Assertions.assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(customerIntegration).getCustomerDetailsList(customerIds);
    }

    @Test
    void shouldReturnCustomerDetailsWhenValidId() {
        Integer customerId = 1;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setName("Test Customer");

        Mockito.when(customerIntegration.getCustomerDetails(customerId)).thenReturn(customerDTO);

        CustomerOrderCustomerDTO result = customerOrderDataProxy.findCustomerDetails(customerId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(customerId, result.getId());
        Assertions.assertEquals("Test Customer", result.getName());
        Mockito.verify(customerIntegration).getCustomerDetails(customerId);
    }

    @Test
    void shouldSaveCustomerOrderWithFoodItems() {
        CustomerOrderDTO orderDTO = new CustomerOrderDTO();
        orderDTO.setId(1);
        CustomerOrderFoodItemDTO foodItem = new CustomerOrderFoodItemDTO();
        orderDTO.setFoodItems(List.of(foodItem));

        CustomerOrderDTO savedOrder = new CustomerOrderDTO();
        savedOrder.setId(1);

        CustomerOrderFoodItemDTO savedFoodItem = new CustomerOrderFoodItemDTO();
        savedFoodItem.setOrderId(1);

        Mockito.when(jpaCustomerOrderRepositoryImpl.save(ArgumentMatchers.any(), ArgumentMatchers.eq(jpaCustomerOrderRepository), ArgumentMatchers.eq(jpaCustomerOrderMapper)))
                .thenReturn(savedOrder);
        Mockito.when(jpaCustomerOrderFoodItemRepositoryImpl.saveAll(ArgumentMatchers.any(), ArgumentMatchers.eq(jpaCustomerOrderFoodItemRepository), ArgumentMatchers.eq(jpaCustomerOrderMapper)))
                .thenReturn(List.of(savedFoodItem));

        CustomerOrderDTO result = customerOrderDataProxy.save(orderDTO);

        Assertions.assertNotNull(result);
        assertEquals(1, result.getId());
        Assertions.assertNotNull(result.getFoodItems());
        assertEquals(1, result.getFoodItems().getFirst().getOrderId());
        Mockito.verify(jpaCustomerOrderRepositoryImpl).save(ArgumentMatchers.any(), ArgumentMatchers.eq(jpaCustomerOrderRepository), ArgumentMatchers.eq(jpaCustomerOrderMapper));
        Mockito.verify(jpaCustomerOrderFoodItemRepositoryImpl).saveAll(ArgumentMatchers.any(), ArgumentMatchers.eq(jpaCustomerOrderFoodItemRepository), ArgumentMatchers.eq(jpaCustomerOrderMapper));
    }

    @Test
    void shouldCreatePaymentChargeWithValidParameters() {
        Integer customerOrderId = 1;
        Double totalCost = 25.50;

        customerOrderDataProxy.createPaymentCharge(customerOrderId, totalCost);

        Mockito.verify(paymentIntegration).createPayment(customerOrderId, totalCost);
    }

    @Test
    void shouldSendNotificationWithValidParameters() {
        String notificationSource = "ORDER_UPDATE";
        Integer artefactId = 1;
        String message = "Order status updated";

        customerOrderDataProxy.sendNotification(notificationSource, artefactId, message);

        Mockito.verify(notificationIntegration).sendNotification(notificationSource, artefactId, message);
    }

    @Test
    void shouldFindCustomerOrderByIdWithoutFoodItems() {
        Integer customerOrderId = 1;
        Boolean includeFoodItems = false;
        CustomerOrderDTO expectedOrder = new CustomerOrderDTO();
        expectedOrder.setId(customerOrderId);

        Mockito.when(jpaCustomerOrderRepositoryImpl.findById(customerOrderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper))
                .thenReturn(expectedOrder);

        CustomerOrderDTO result = customerOrderDataProxy.findCustomerOrderById(customerOrderId, includeFoodItems);

        Assertions.assertNotNull(result);
        assertEquals(customerOrderId, result.getId());
        Mockito.verify(jpaCustomerOrderRepositoryImpl).findById(customerOrderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper);
        Mockito.verify(jpaCustomerOrderFoodItemRepositoryImpl, Mockito.never()).findByCustomerOrderId(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void shouldFindCustomerOrderByIdWithFoodItems() {
        Integer customerOrderId = 1;
        Boolean includeFoodItems = true;
        CustomerOrderDTO expectedOrder = new CustomerOrderDTO();
        expectedOrder.setId(customerOrderId);
        List<CustomerOrderFoodItemDTO> foodItems = List.of(new CustomerOrderFoodItemDTO());

        Mockito.when(jpaCustomerOrderRepositoryImpl.findById(customerOrderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper))
                .thenReturn(expectedOrder);
        Mockito.when(jpaCustomerOrderFoodItemRepositoryImpl.findByCustomerOrderId(customerOrderId, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper))
                .thenReturn(foodItems);

        CustomerOrderDTO result = customerOrderDataProxy.findCustomerOrderById(customerOrderId, includeFoodItems);

        Assertions.assertNotNull(result);
        assertEquals(customerOrderId, result.getId());
        Assertions.assertNotNull(result.getFoodItems());
        assertEquals(1, result.getFoodItems().size());
        Mockito.verify(jpaCustomerOrderRepositoryImpl).findById(customerOrderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper);
        Mockito.verify(jpaCustomerOrderFoodItemRepositoryImpl).findByCustomerOrderId(customerOrderId, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);
    }

    @Test
    void shouldReturnNullWhenCustomerOrderNotFound() {
        Integer customerOrderId = 999;
        Boolean includeFoodItems = true;

        Mockito.when(jpaCustomerOrderRepositoryImpl.findById(customerOrderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper))
                .thenReturn(null);

        CustomerOrderDTO result = customerOrderDataProxy.findCustomerOrderById(customerOrderId, includeFoodItems);

        Assertions.assertNull(result);
        Mockito.verify(jpaCustomerOrderRepositoryImpl).findById(customerOrderId, jpaCustomerOrderRepository, jpaCustomerOrderMapper);
        Mockito.verify(jpaCustomerOrderFoodItemRepositoryImpl, Mockito.never()).findByCustomerOrderId(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void shouldFindCustomerOrdersByStatusListWithFoodItems() {
        List<Integer> statusIds = Arrays.asList(1, 2);
        Boolean includeFoodItems = true;

        CustomerOrderDTO order1 = new CustomerOrderDTO();
        order1.setId(1);
        CustomerOrderDTO order2 = new CustomerOrderDTO();
        order2.setId(2);
        List<CustomerOrderDTO> orders = Arrays.asList(order1, order2);

        CustomerOrderFoodItemDTO foodItem1 = new CustomerOrderFoodItemDTO();
        foodItem1.setOrderId(1);
        CustomerOrderFoodItemDTO foodItem2 = new CustomerOrderFoodItemDTO();
        foodItem2.setOrderId(2);
        List<CustomerOrderFoodItemDTO> foodItems = Arrays.asList(foodItem1, foodItem2);

        Mockito.when(jpaCustomerOrderRepositoryImpl.findByStatusList(statusIds, jpaCustomerOrderRepository, jpaCustomerOrderMapper))
                .thenReturn(orders);
        Mockito.when(jpaCustomerOrderFoodItemRepositoryImpl.findByCustomerOrderIdList(ArgumentMatchers.anyList(), ArgumentMatchers.eq(jpaCustomerOrderFoodItemRepository), ArgumentMatchers.eq(jpaCustomerOrderMapper)))
                .thenReturn(foodItems);

        List<CustomerOrderDTO> result = customerOrderDataProxy.findCustomerOrderByStatusList(statusIds, includeFoodItems);

        Assertions.assertNotNull(result);
        assertEquals(2, result.size());
        Assertions.assertNotNull(result.get(0).getFoodItems());
        Assertions.assertNotNull(result.get(1).getFoodItems());
        Mockito.verify(jpaCustomerOrderRepositoryImpl).findByStatusList(statusIds, jpaCustomerOrderRepository, jpaCustomerOrderMapper);
        Mockito.verify(jpaCustomerOrderFoodItemRepositoryImpl).findByCustomerOrderIdList(ArgumentMatchers.anyList(), ArgumentMatchers.eq(jpaCustomerOrderFoodItemRepository), ArgumentMatchers.eq(jpaCustomerOrderMapper));
    }

    @Test
    void shouldUpdateCustomerOrderSuccessfully() {
        CustomerOrderDTO updateOrderDTO = new CustomerOrderDTO();
        updateOrderDTO.setId(1);
        CustomerOrderDTO updatedOrder = new CustomerOrderDTO();
        updatedOrder.setId(1);

        Mockito.when(jpaCustomerOrderRepositoryImpl.save(updateOrderDTO, jpaCustomerOrderRepository, jpaCustomerOrderMapper))
                .thenReturn(updatedOrder);

        CustomerOrderDTO result = customerOrderDataProxy.updateCustomerOrder(updateOrderDTO);

        Assertions.assertNotNull(result);
        assertEquals(1, result.getId());
        Mockito.verify(jpaCustomerOrderRepositoryImpl).save(updateOrderDTO, jpaCustomerOrderRepository, jpaCustomerOrderMapper);
    }

    @Test
    void shouldCreateKitchenOrderSuccessfully() {
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();

        customerOrderDataProxy.createKitchenOrder(customerOrderDTO);

        Mockito.verify(kitchenOrderIntegration).createKitchenOrder(customerOrderDTO);
    }

    @Test
    void shouldCancelPaymentChargeByCustomerOrderId() {
        Integer customerOrderId = 1;

        customerOrderDataProxy.cancelPaymentChargeByCustomerOrderId(customerOrderId);

        Mockito.verify(paymentIntegration).cancelPaymentChargeByCustomerOrderId(customerOrderId);
    }

    @Test
    void shouldFindPaymentByCustomerOrderId() {
        Integer customerOrderId = 1;
        PaymentMercadopagoQrDTO expectedPayment = new PaymentMercadopagoQrDTO();

        Mockito.when(paymentIntegration.getPaymentByCustomerOrderId(customerOrderId)).thenReturn(expectedPayment);

        PaymentMercadopagoQrDTO result = customerOrderDataProxy.findPaymentByCustomerOrderId(customerOrderId);

        Assertions.assertNotNull(result);
        Mockito.verify(paymentIntegration).getPaymentByCustomerOrderId(customerOrderId);
    }

    @Test
    void shouldFindKitchenOrderByCustomerOrderId() {
        Integer customerOrderId = 1;
        KitchenOrderDTO expectedKitchenOrder = new KitchenOrderDTO();

        Mockito.when(kitchenOrderIntegration.getKitchenOrderByCustomerOrderId(customerOrderId)).thenReturn(expectedKitchenOrder);

        KitchenOrderDTO result = customerOrderDataProxy.findKitchenOrderByCustomerOrderId(customerOrderId);

        Assertions.assertNotNull(result);
        Mockito.verify(kitchenOrderIntegration).getKitchenOrderByCustomerOrderId(customerOrderId);
    }

    @Test
    void shouldCancelKitchenOrderById() {
        Integer kitchenOrderId = 1;

        customerOrderDataProxy.cancelKitchenOrderById(kitchenOrderId);

        Mockito.verify(kitchenOrderIntegration).cancelKitchenOrderById(kitchenOrderId);
    }
}
