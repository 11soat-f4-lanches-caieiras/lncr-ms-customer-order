package br.com.tp.lncr.customerorder.apis;

import br.com.tp.lncr.commons.model.ResponseListModel;
import br.com.tp.lncr.commons.model.ResponseModel;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CustomerOrderRestController {

    ResponseEntity<ResponseModel<CustomerOrderDTO>> createCustomerOrder(@RequestBody CustomerOrderDTO customerOrderDTO);

    ResponseEntity<ResponseModel<CustomerOrderDTO>> getCustomerOrderById(@PathVariable(name="customerOrderId") Integer customerOrderId,
                                                                         @RequestParam(name = "includeFoodItems", required = false, defaultValue = "false") Boolean includeFoodItems);

    ResponseEntity<ResponseListModel<CustomerOrderDTO>>getCustomerOrderByStatus(@PathVariable(name = "statusList") List<String> statusList,
                                                                                @RequestParam(name = "includeFoodItems", required = false, defaultValue = "false") Boolean includeFoodItems);

    ResponseEntity<ResponseModel<CustomerOrderDTO>> updateOrderStatusById(@PathVariable(name="customerOrderId") Integer customerOrderId,
                                                                @PathVariable(name = "newStatus") String newStatus,
                                                                @RequestParam(name="forceUpdate", required = false, defaultValue = "false") Boolean forceUpdate);

}
