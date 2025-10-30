# language: pt

Funcionalidade: Tratamento de Exceções - CustomerOrderInboundHandler
  Como um sistema de gerenciamento de pedidos
  Eu quero tratar exceções de forma adequada
  Para fornecer respostas claras aos usuários

  Cenário: Tratar exceção de pedido com código 400
    Dado que ocorreu uma CustomerOrderException com mensagem "Pedido inválido" e código 400
    Quando o handler processar a exceção
    Então a resposta deve ter código de status 400
    E a mensagem de erro deve ser "Pedido inválido"

  Cenário: Tratar exceção de pedido não encontrado com código 404
    Dado que ocorreu uma CustomerOrderException com mensagem "Pedido não encontrado" e código 404
    Quando o handler processar a exceção
    Então a resposta deve ter código de status 404
    E a mensagem de erro deve ser "Pedido não encontrado"

  Cenário: Tratar exceção de status inválido
    Dado que ocorreu uma CustomerOrderException com mensagem "Status inválido" e código 400
    Quando o handler processar a exceção
    Então a resposta deve ter código de status 400
    E a mensagem de erro deve ser "Status inválido"

