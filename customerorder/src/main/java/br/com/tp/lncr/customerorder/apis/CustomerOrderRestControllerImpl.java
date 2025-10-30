package br.com.tp.lncr.customerorder.apis;

import br.com.tp.lncr.commons.model.ResponseListModel;
import br.com.tp.lncr.commons.model.ResponseModel;
import br.com.tp.lncr.commons.utils.ResponseEntityModelUtil;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderController;
import br.com.tp.lncr.customerorder.configs.CustomerOrderConfig;
import br.com.tp.lncr.customerorder.dataproxy.CustomerOrderDataProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerOrders")
public class CustomerOrderRestControllerImpl implements CustomerOrderRestController {

    public final CustomerOrderConfig customerOrderConfig;
    public final CustomerOrderDataProxy customerOrderDatabase;
    public final CustomerOrderController customerOrderController;

    public CustomerOrderRestControllerImpl(CustomerOrderConfig customerOrderConfig, CustomerOrderDataProxy customerOrderDatabase, CustomerOrderController customerOrderController) {
        this.customerOrderConfig = customerOrderConfig;
        this.customerOrderDatabase = customerOrderDatabase;
        this.customerOrderController = customerOrderController;
    }

    @Override
    @PostMapping()
    public ResponseEntity<ResponseModel<CustomerOrderDTO>> createCustomerOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
        customerOrderDTO = this.customerOrderController.create(customerOrderDatabase, customerOrderDTO);
        return ResponseEntityModelUtil.created(customerOrderDTO,customerOrderConfig.getLocationPrefix() + "/" + customerOrderDTO.getId());

    }


    @Override
    @GetMapping("/{customerOrderId}")
    public ResponseEntity<ResponseModel<CustomerOrderDTO>> getCustomerOrderById(@PathVariable(name="customerOrderId") Integer customerOrderId,
                                                                                @RequestParam(name = "includeFoodItems", required = false, defaultValue = "false") Boolean includeFoodItems) {
        CustomerOrderDTO customerOrderDTO = this.customerOrderController.getById(customerOrderDatabase, customerOrderId, includeFoodItems);
        return ResponseEntityModelUtil.ok(customerOrderDTO);
    }

    @Override
    @GetMapping("/status/{statusList}")
    public ResponseEntity<ResponseListModel<CustomerOrderDTO>>
    getCustomerOrderByStatus(@PathVariable(name = "statusList") List<String> statusList,
                             @RequestParam(name = "includeFoodItems", required = false, defaultValue = "false") Boolean includeFoodItems) {
        List<CustomerOrderDTO> customerOrderDTOList = this.customerOrderController.getByStatusList(customerOrderDatabase, statusList, includeFoodItems);
        return ResponseEntityModelUtil.listOK(customerOrderDTOList);
    }

    @Override
    @PatchMapping("/{customerOrderId}/updateStatus/{newStatus}")
    public ResponseEntity<ResponseModel<CustomerOrderDTO>> updateOrderStatusById(@PathVariable(name = "customerOrderId") Integer customerOrderId,
                                                                                 @PathVariable(name = "newStatus") String newStatus,
                                                                                 @RequestParam(name = "forceUpdate", required = false, defaultValue = "false") Boolean forceUpdate){
        CustomerOrderDTO updatedCustomerOrderDTO = this.customerOrderController.updateStatusById(customerOrderDatabase,customerOrderId,newStatus,forceUpdate);
        return ResponseEntityModelUtil.ok(updatedCustomerOrderDTO);
    }
}
