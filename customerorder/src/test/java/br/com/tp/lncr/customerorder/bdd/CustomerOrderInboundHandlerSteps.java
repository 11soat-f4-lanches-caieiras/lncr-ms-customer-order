package br.com.tp.lncr.customerorder.bdd;

import br.com.tp.lncr.core.exceptions.CustomerOrderException;
import br.com.tp.lncr.customerorder.handlers.CustomerOrderInboundHandler;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerOrderInboundHandlerSteps {

    private CustomerOrderInboundHandler handler;
    private CustomerOrderException exception;
    private ResponseEntity<Object> response;

    @Dado("que ocorreu uma CustomerOrderException com mensagem {string} e código {int}")
    public void queOcorreuUmaCustomerOrderExceptionComMensagemECodigo(String mensagem, Integer codigo) {
        handler = new CustomerOrderInboundHandler();
        exception = new CustomerOrderException(mensagem, codigo);
    }

    @Quando("o handler processar a exceção")
    public void oHandlerProcessarAExcecao() {
        response = handler.handleCustomerOrderExceptionException(exception);
    }

    @Então("a resposta deve ter código de status {int}")
    public void aRespostaDeveTerCodigoDeStatus(Integer statusCode) {
        assertNotNull(response);
        assertEquals(statusCode, response.getStatusCode().value());
    }

    @Então("a mensagem de erro deve ser {string}")
    public void aMensagemDeErroDeveSer(String mensagem) {
        assertNotNull(response.getBody());
    }
}

