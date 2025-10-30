package br.com.tp.lncr.customerorder.bdd;

import br.com.tp.lncr.commons.model.ResponseListModel;
import br.com.tp.lncr.commons.model.ResponseModel;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderController;
import br.com.tp.lncr.customerorder.apis.CustomerOrderRestControllerImpl;
import br.com.tp.lncr.customerorder.configs.CustomerOrderConfig;
import br.com.tp.lncr.customerorder.dataproxy.CustomerOrderDataProxy;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerOrderRestControllerSteps {

    private CustomerOrderConfig customerOrderConfig;
    private CustomerOrderDataProxy customerOrderDatabase;
    private CustomerOrderController customerOrderController;
    private CustomerOrderRestControllerImpl restController;
    private CustomerOrderDTO customerOrderDTO;
    private ResponseEntity<?> response;
    private List<String> statusList;

    @Dado("que tenho um pedido válido com status {string}")
    public void queTenhoUmPedidoValidoComStatus(String status) {
        customerOrderConfig = mock(CustomerOrderConfig.class);
        customerOrderDatabase = mock(CustomerOrderDataProxy.class);
        customerOrderController = mock(CustomerOrderController.class);
        restController = new CustomerOrderRestControllerImpl(customerOrderConfig, customerOrderDatabase, customerOrderController);

        customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(1);
        customerOrderDTO.setStatus(status);
        customerOrderDTO.setTotalCost(50.0);
    }

    @Quando("eu enviar uma requisição POST para criar o pedido")
    public void euEnviarUmaRequisicaoPostParaCriarOPedido() {
        when(customerOrderConfig.getLocationPrefix()).thenReturn("/customerOrders");
        when(customerOrderController.create(any(), any())).thenReturn(customerOrderDTO);
        response = restController.createCustomerOrder(customerOrderDTO);
    }

    @Então("a resposta deve ter código de status {int}")
    public void aRespostaDeveTerCodigoDeStatus(Integer statusCode) {
        assertEquals(statusCode, response.getStatusCode().value());
    }

    @Então("o pedido deve ser retornado com id gerado")
    public void oPedidoDeveSerRetornadoComIdGerado() {
        assertNotNull(response.getBody());
        if (response.getBody() instanceof ResponseModel) {
            ResponseModel<CustomerOrderDTO> body = (ResponseModel<CustomerOrderDTO>) response.getBody();
            assertNotNull(body.getContent());
            assertNotNull(body.getContent().getId());
        }
    }

    @Dado("que existe um pedido com id {int} no sistema")
    public void queExisteUmPedidoComIdNoSistema(Integer id) {
        customerOrderConfig = mock(CustomerOrderConfig.class);
        customerOrderDatabase = mock(CustomerOrderDataProxy.class);
        customerOrderController = mock(CustomerOrderController.class);
        restController = new CustomerOrderRestControllerImpl(customerOrderConfig, customerOrderDatabase, customerOrderController);

        customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(id);
        customerOrderDTO.setStatus("RECEIVED");
    }

    @Quando("eu enviar uma requisição GET para buscar o pedido por id")
    public void euEnviarUmaRequisicaoGetParaBuscarOPedidoPorId() {
        when(customerOrderController.getById(any(), anyInt(), anyBoolean())).thenReturn(customerOrderDTO);
        response = restController.getCustomerOrderById(customerOrderDTO.getId(), false);
    }

    @Então("o pedido retornado deve ter id {int}")
    public void oPedidoRetornadoDeveTerId(Integer id) {
        assertNotNull(response.getBody());
        if (response.getBody() instanceof ResponseModel) {
            ResponseModel<CustomerOrderDTO> body = (ResponseModel<CustomerOrderDTO>) response.getBody();
            assertEquals(id, body.getContent().getId());
        }
    }

    @Dado("que existem pedidos com status {string} no sistema")
    public void queExistemPedidosComStatusNoSistema(String status) {
        customerOrderConfig = mock(CustomerOrderConfig.class);
        customerOrderDatabase = mock(CustomerOrderDataProxy.class);
        customerOrderController = mock(CustomerOrderController.class);
        restController = new CustomerOrderRestControllerImpl(customerOrderConfig, customerOrderDatabase, customerOrderController);

        customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(1);
        customerOrderDTO.setStatus(status);
        statusList = Collections.singletonList(status);
    }

    @Quando("eu enviar uma requisição GET para buscar pedidos por status")
    public void euEnviarUmaRequisicaoGetParaBuscarPedidosPorStatus() {
        when(customerOrderController.getByStatusList(any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(customerOrderDTO));
        response = restController.getCustomerOrderByStatus(statusList, false);
    }

    @Então("a lista de pedidos não deve estar vazia")
    public void aListaDePedidosNaoDeveEstarVazia() {
        assertNotNull(response.getBody());
        if (response.getBody() instanceof ResponseListModel) {
            ResponseListModel<CustomerOrderDTO> body = (ResponseListModel<CustomerOrderDTO>) response.getBody();
            assertFalse(body.getContent().isEmpty());
        }
    }

    @Dado("que existe um pedido com id {int} e status {string} no sistema")
    public void queExisteUmPedidoComIdEStatusNoSistema(Integer id, String status) {
        customerOrderConfig = mock(CustomerOrderConfig.class);
        customerOrderDatabase = mock(CustomerOrderDataProxy.class);
        customerOrderController = mock(CustomerOrderController.class);
        restController = new CustomerOrderRestControllerImpl(customerOrderConfig, customerOrderDatabase, customerOrderController);

        customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(id);
        customerOrderDTO.setStatus(status);
    }

    @Quando("eu enviar uma requisição PATCH para atualizar o status para {string}")
    public void euEnviarUmaRequisicaoPatchParaAtualizarOStatusPara(String novoStatus) {
        CustomerOrderDTO updatedOrder = new CustomerOrderDTO();
        updatedOrder.setId(customerOrderDTO.getId());
        updatedOrder.setStatus(novoStatus);

        when(customerOrderController.updateStatusById(any(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(updatedOrder);
        response = restController.updateOrderStatusById(customerOrderDTO.getId(), novoStatus, false);
    }

    @Então("o status do pedido deve ser atualizado para {string}")
    public void oStatusDoPedidoDeveSerAtualizadoPara(String status) {
        assertNotNull(response.getBody());
        if (response.getBody() instanceof ResponseModel) {
            ResponseModel<CustomerOrderDTO> body = (ResponseModel<CustomerOrderDTO>) response.getBody();
            assertEquals(status, body.getContent().getStatus());
        }
    }
}


