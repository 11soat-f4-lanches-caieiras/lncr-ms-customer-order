# language: pt

Funcionalidade: API REST de Pedidos de Cliente - CustomerOrderRestController
  Como um cliente do sistema
  Eu quero utilizar a API de pedidos
  Para criar e gerenciar pedidos de clientes

  Cenário: Criar pedido através da API
    Dado que tenho um pedido válido com status "CHECKOUT"
    Quando eu enviar uma requisição POST para criar o pedido
    Então a resposta deve ter código de status 201
    E o pedido deve ser retornado com id gerado

  Cenário: Buscar pedido por id
    Dado que existe um pedido com id 1 no sistema
    Quando eu enviar uma requisição GET para buscar o pedido por id
    Então a resposta deve ter código de status 200
    E o pedido retornado deve ter id 1

  Cenário: Buscar pedidos por status
    Dado que existem pedidos com status "RECEIVED" no sistema
    Quando eu enviar uma requisição GET para buscar pedidos por status
    Então a resposta deve ter código de status 200
    E a lista de pedidos não deve estar vazia

  Cenário: Atualizar status do pedido
    Dado que existe um pedido com id 1 e status "RECEIVED" no sistema
    Quando eu enviar uma requisição PATCH para atualizar o status para "PREPARING"
    Então a resposta deve ter código de status 200
    E o status do pedido deve ser atualizado para "PREPARING"
# language: pt

Funcionalidade: Gerenciamento de Pedidos de Cliente - CustomerOrderDTO
  Como um sistema de gerenciamento de pedidos
  Eu quero validar as operações com CustomerOrderDTO
  Para garantir que os pedidos sejam criados e gerenciados corretamente

  Cenário: Criar um pedido de cliente válido
    Dado que tenho um pedido com id 1
    E o status do pedido é "CHECKOUT"
    E o custo total é 50.00
    Quando eu criar o pedido
    Então o pedido deve ser criado com sucesso
    E o id do pedido deve ser 1
    E o status deve ser "CHECKOUT"

  Cenário: Atualizar o status de um pedido
    Dado que existe um pedido com id 1 e status "RECEIVED"
    Quando eu atualizar o status para "PREPARING"
    Então o status do pedido deve ser "PREPARING"

  Cenário: Validar dados do pedido
    Dado que tenho um pedido com id 2
    E o custo total é 100.50
    Quando eu consultar o custo total
    Então o custo total deve ser 100.50

  Cenário: Associar cliente ao pedido
    Dado que tenho um pedido com id 3
    E o pedido tem um cliente com id 5
    Quando eu consultar o cliente do pedido
    Então o cliente deve ter id 5

