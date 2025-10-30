package br.com.tp.lncr.customerorder.bdd;

import br.com.tp.lncr.core.adapters.customerorder.CustomerOrderMapper;
import br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderController;
import br.com.tp.lncr.customerorder.configs.CustomerOrderConfig;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerOrderConfigSteps {

    private CustomerOrderConfig config;
    private CustomerOrderController controller;
    private CustomerOrderMapper mapper;

    @Dado("que tenho a configuração com location prefix {string}")
    public void queTenhoAConfiguracaoComLocationPrefix(String prefix) {
        config = new CustomerOrderConfig();
        config.setLocationPrefix(prefix);
    }

    @Quando("eu consultar o location prefix")
    public void euConsultarOLocationPrefix() {
        assertNotNull(config.getLocationPrefix());
    }

    @Então("o valor deve ser {string}")
    public void oValorDeveSer(String valor) {
        assertEquals(valor, config.getLocationPrefix());
    }

    @Dado("que tenho a configuração do CustomerOrderConfig")
    public void queTenhoAConfiguracaoDoCustomerOrderConfig() {
        config = new CustomerOrderConfig();
    }

    @Quando("eu criar o bean CustomerOrderController")
    public void euCriarOBeanCustomerOrderController() {
        mapper = new CustomerOrderMapper();
        controller = config.customerOrderController(Mockito.mock(br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderDatabase.class), mapper);
    }

    @Então("o bean deve ser criado com sucesso")
    public void oBeanDeveSerCriadoComSucesso() {
        assertNotNull(controller);
    }

    @Quando("eu criar o bean CustomerOrderMapper")
    public void euCriarOBeanCustomerOrderMapper() {
        mapper = config.customerOrderMapper();
    }
}