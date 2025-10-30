package br.com.tp.lncr.customerorder.bdd;

import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderCustomerDTO;
import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerOrderDTOSteps {

    private CustomerOrderDTO customerOrderDTO;

    @Dado("que tenho um pedido com id {int}")
    public void queTenhoUmPedidoComId(Integer id) {
        customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(id);
    }

    @Dado("o status do pedido é {string}")
    public void oStatusDoPedidoE(String status) {
        customerOrderDTO.setStatus(status);
    }

    @Dado("o custo total é {double}")
    public void oCustoTotalE(Double custo) {
        customerOrderDTO.setTotalCost(custo);
    }

    @Quando("eu criar o pedido")
    public void euCriarOPedido() {
        assertNotNull(customerOrderDTO);
    }

    @Então("o pedido deve ser criado com sucesso")
    public void oPedidoDeveSerCriadoComSucesso() {
        assertNotNull(customerOrderDTO);
    }

    @Então("o id do pedido deve ser {int}")
    public void oIdDoPedidoDeveSer(Integer id) {
        assertEquals(id, customerOrderDTO.getId());
    }

    @Então("o status deve ser {string}")
    public void oStatusDeveSer(String status) {
        assertEquals(status, customerOrderDTO.getStatus());
    }

    @Dado("que existe um pedido com id {int} e status {string}")
    public void queExisteUmPedidoComIdEStatus(Integer id, String status) {
        customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(id);
        customerOrderDTO.setStatus(status);
    }

    @Quando("eu atualizar o status para {string}")
    public void euAtualizarOStatusPara(String novoStatus) {
        customerOrderDTO.setStatus(novoStatus);
    }

    @Então("o status do pedido deve ser {string}")
    public void oStatusDoPedidoDeveSer(String status) {
        assertEquals(status, customerOrderDTO.getStatus());
    }

    @Quando("eu consultar o custo total")
    public void euConsultarOCustoTotal() {
        assertNotNull(customerOrderDTO.getTotalCost());
    }

    @Então("o custo total deve ser {double}")
    public void oCustoTotalDeveSer(Double custo) {
        assertEquals(custo, customerOrderDTO.getTotalCost());
    }

    @Dado("o pedido tem um cliente com id {int}")
    public void oPedidoTemUmClienteComId(Integer clienteId) {
        CustomerOrderCustomerDTO customer = new CustomerOrderCustomerDTO();
        customer.setId(clienteId);
        customerOrderDTO.setCustomer(customer);
    }

    @Quando("eu consultar o cliente do pedido")
    public void euConsultarOClienteDoPedido() {
        assertNotNull(customerOrderDTO.getCustomer());
    }

    @Então("o cliente deve ter id {int}")
    public void oClienteDeveTerId(Integer id) {
        assertEquals(id, customerOrderDTO.getCustomer().getId());
    }
}