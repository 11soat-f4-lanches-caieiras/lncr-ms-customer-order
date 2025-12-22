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
import br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderDatabase;
import br.com.tp.lncr.customerorder.datasource.postgres.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CustomerOrderDataProxy implements CustomerOrderDatabase {

    private final JpaCustomerOrderRepositoryImpl jpaCustomerOrderRepositoryImpl;
    private final JpaCustomerOrderRepository jpaCustomerOrderRepository;
    private final JpaCustomerOrderFoodItemRepositoryImpl jpaCustomerOrderFoodItemRepositoryImpl;
    private final JpaCustomerOrderFoodItemRepository jpaCustomerOrderFoodItemRepository;
    private final CustomerIntegrationImpl customerIntegration;
    private final FoodItemIntegrationImpl foodItemIntegration;
    private final PaymentIntegrationImpl paymentIntegration;
    private final KitchenOrderIntegrationImpl kitchenOrderIntegration;
    private final NotificationIntegraionImpl notificationIntegration;
    private final JpaCustomerOrderMapper jpaCustomerOrderMapper;
    private final IntegrationMapper integrationMapper;

    public CustomerOrderDataProxy(JpaCustomerOrderRepositoryImpl jpaCustomerOrderRepositoryImpl,
                                  JpaCustomerOrderRepository jpaCustomerOrderRepository,
                                  JpaCustomerOrderFoodItemRepositoryImpl jpaCustomerOrderFoodItemRepositoryImpl,
                                  JpaCustomerOrderFoodItemRepository jpaCustomerOrderFoodItemRepository,
                                  CustomerIntegrationImpl customerIntegration,
                                  FoodItemIntegrationImpl foodItemIntegration,
                                  PaymentIntegrationImpl paymentIntegration,
                                  KitchenOrderIntegrationImpl kitchenOrderIntegration,
                                  NotificationIntegraionImpl notificationIntegration,
                                  JpaCustomerOrderMapper jpaCustomerOrderMapper,
                                  IntegrationMapper integrationMapper) {
        this.jpaCustomerOrderRepositoryImpl = jpaCustomerOrderRepositoryImpl;
        this.jpaCustomerOrderRepository = jpaCustomerOrderRepository;
        this.jpaCustomerOrderFoodItemRepositoryImpl = jpaCustomerOrderFoodItemRepositoryImpl;
        this.jpaCustomerOrderFoodItemRepository = jpaCustomerOrderFoodItemRepository;
        this.customerIntegration = customerIntegration;
        this.foodItemIntegration = foodItemIntegration;
        this.paymentIntegration = paymentIntegration;
        this.kitchenOrderIntegration = kitchenOrderIntegration;
        this.notificationIntegration = notificationIntegration;
        this.jpaCustomerOrderMapper = jpaCustomerOrderMapper;
        this.integrationMapper = integrationMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerOrderFoodItemDTO> findFoodItemsDetailsList(List<Integer> foodItemListIds) {
        return this.foodItemIntegration.getFoodItemDetailList(foodItemListIds);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerOrderCustomerDTO> findCustomerDetailsList(List<Integer> customerIdList) {
        return this.customerIntegration.getCustomerDetailsList(customerIdList);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerOrderCustomerDTO findCustomerDetails(Integer customerId) {
        CustomerDTO customerDTO = this.customerIntegration.getCustomerDetails(customerId);
        if (customerDTO == null) return null;
        return new CustomerOrderCustomerDTO(customerDTO.getId(), customerDTO.getName());
    }

    @Override
    public CustomerOrderDTO save(CustomerOrderDTO customerOrderDTO) {
        CustomerOrderDTO newCustomerDTO = this.jpaCustomerOrderRepositoryImpl.save(customerOrderDTO,jpaCustomerOrderRepository,jpaCustomerOrderMapper);
        newCustomerDTO.setFoodItems(customerOrderDTO.getFoodItems());
        for (CustomerOrderFoodItemDTO item : newCustomerDTO.getFoodItems()){
            item.setOrderId(newCustomerDTO.getId());
        }
        newCustomerDTO.setFoodItems(this.jpaCustomerOrderFoodItemRepositoryImpl.saveAll(newCustomerDTO.getFoodItems(),jpaCustomerOrderFoodItemRepository,jpaCustomerOrderMapper));
        return newCustomerDTO;
    }

    @Override
    public void createPaymentCharge(Integer customerOrderId, Double totalCost) {
        this.paymentIntegration.createPayment(customerOrderId,totalCost);
    }

    @Override
    public void sendNotification(String notificationSource, Integer artefactId, String message) {
        this.notificationIntegration.sendNotification(notificationSource, artefactId, message);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerOrderDTO findCustomerOrderById(Integer customerOrderId, Boolean includFoodItems) {
        CustomerOrderDTO customerOrderDTO = this.jpaCustomerOrderRepositoryImpl.findById(customerOrderId,jpaCustomerOrderRepository,jpaCustomerOrderMapper);
        if ((customerOrderDTO != null) && (includFoodItems)) {
            customerOrderDTO.setFoodItems(this.jpaCustomerOrderFoodItemRepositoryImpl.findByCustomerOrderId(customerOrderDTO.getId(),jpaCustomerOrderFoodItemRepository,jpaCustomerOrderMapper));
        }
        return customerOrderDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerOrderDTO> findCustomerOrderByStatusList(List<Integer> statusListIds, Boolean includeFoodItems) {
        List<CustomerOrderDTO> customerOrderDTOList = this.jpaCustomerOrderRepositoryImpl.findByStatusList(statusListIds,jpaCustomerOrderRepository,jpaCustomerOrderMapper);
        if ((customerOrderDTOList != null) && (includeFoodItems)){
            List<CustomerOrderFoodItemDTO> customerOrderFoodItemDTOList = getFoodItemsInCustomerOrdersIdList(customerOrderDTOList);
            setFoodItemsInCustomerOrder(customerOrderDTOList, customerOrderFoodItemDTOList);
        }
        return customerOrderDTOList;
    }

    @Override
    public CustomerOrderDTO updateCustomerOrder(CustomerOrderDTO updateCustomerOrderDTO) {
        return this.jpaCustomerOrderRepositoryImpl.save(updateCustomerOrderDTO, jpaCustomerOrderRepository, jpaCustomerOrderMapper);
    }

    @Override
    public void createKitchenOrder(CustomerOrderDTO customerOrderDTO) {
        this.kitchenOrderIntegration.createKitchenOrder(customerOrderDTO);
    }

    @Override
    public void cancelPaymentChargeByCustomerOrderId(Integer customerOrderId) {
        this.paymentIntegration.cancelPaymentChargeByCustomerOrderId(customerOrderId);
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentMercadopagoQrDTO findPaymentByCustomerOrderId(Integer id) {
        return this.paymentIntegration.getPaymentByCustomerOrderId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public KitchenOrderDTO findKitchenOrderByCustomerOrderId(Integer customerOrderId) {
        return this.kitchenOrderIntegration.getKitchenOrderByCustomerOrderId(customerOrderId);
    }

    @Transactional(readOnly = true)
    @Override
    public void cancelKitchenOrderById(Integer kitchenOrderOrderId) {
        this.kitchenOrderIntegration.cancelKitchenOrderById(kitchenOrderOrderId);
    }

    private List<CustomerOrderFoodItemDTO> getFoodItemsInCustomerOrdersIdList(List<CustomerOrderDTO> customerOrderDTOList){
            List<Integer> customerOrdersIdsList = customerOrderDTOList.stream()
                    .map(CustomerOrderDTO::getId)
                    .toList();
            return this.jpaCustomerOrderFoodItemRepositoryImpl.findByCustomerOrderIdList(customerOrdersIdsList,jpaCustomerOrderFoodItemRepository,jpaCustomerOrderMapper);
    }

    private void setFoodItemsInCustomerOrder(List<CustomerOrderDTO> customerOrderDTOList, List<CustomerOrderFoodItemDTO> customerOrderFoodItemDTOList){
        for (CustomerOrderDTO customerOrder : customerOrderDTOList){
            customerOrder.setFoodItems(customerOrderFoodItemDTOList.stream()
                    .filter(fooditem -> fooditem.getOrderId().equals(customerOrder.getId()))
                    .toList());
        }
    }
}
