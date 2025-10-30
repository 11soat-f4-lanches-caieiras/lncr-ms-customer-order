package br.com.tp.lncr.customerorder.bdd;

import br.com.tp.lncr.core.enums.CustomerOrderStatus;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerOrderStatusSteps {

    private CustomerOrderStatus status;
    private Integer statusId;
    private String statusDescription;

    @Dado("que tenho o status {string}")
    public void queTenhoOStatus(String statusName) {
        status = CustomerOrderStatus.valueOf(statusName);
    }

    @Quando("eu validar o status")
    public void euValidarOStatus() {
        // Validação implícita - o status já foi carregado
    }

    @Então("o id do status deve ser {int}")
    public void oIdDoStatusDeveSer(Integer id) {
        assertEquals(id, status.getId());
    }

    @Então("a descrição deve ser {string}")
    public void aDescricaoDeveSer(String description) {
        assertEquals(description, status.getDescription());
    }

    @Dado("que tenho o id de status {int}")
    public void queTenhoOIdDeStatus(Integer id) {
        this.statusId = id;
    }

    @Quando("eu buscar o status por id")
    public void euBuscarOStatusPorId() {
        status = CustomerOrderStatus.fromId(statusId);
    }

    @Então("o status retornado deve ser {string}")
    public void oStatusRetornadoDeveSer(String statusName) {
        assertEquals(statusName, status.name());
    }

    @Dado("que tenho a descrição {string}")
    public void queTenhoADescricao(String description) {
        this.statusDescription = description;
    }

    @Quando("eu buscar o status por descrição")
    public void euBuscarOStatusPorDescricao() {
        status = CustomerOrderStatus.fromDescription(statusDescription);
    }
}

