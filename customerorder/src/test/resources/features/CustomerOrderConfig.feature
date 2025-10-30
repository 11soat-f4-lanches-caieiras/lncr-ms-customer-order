# language: pt

Funcionalidade: Configuração de Beans - CustomerOrderConfig
  Como um sistema de gerenciamento de pedidos
  Eu quero configurar corretamente os beans da aplicação
  Para garantir o funcionamento adequado do sistema

  Cenário: Validar configuração do location prefix
    Dado que tenho a configuração com location prefix "/customerOrders"
    Quando eu consultar o location prefix
    Então o valor deve ser "/customerOrders"

  Cenário: Criar bean CustomerOrderController
    Dado que tenho a configuração do CustomerOrderConfig
    Quando eu criar o bean CustomerOrderController
    Então o bean deve ser criado com sucesso

  Cenário: Criar bean CustomerOrderMapper
    Dado que tenho a configuração do CustomerOrderConfig
    Quando eu criar o bean CustomerOrderMapper
    Então o bean deve ser criado com sucesso
