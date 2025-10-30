# language: pt

Funcionalidade: Validação de Status de Pedidos - CustomerOrderStatus
  Como um sistema de gerenciamento de pedidos
  Eu quero validar os status dos pedidos
  Para garantir que apenas status válidos sejam utilizados

  Cenário: Validar status CHECKOUT
    Dado que tenho o status "CHECKOUT"
    Quando eu validar o status
    Então o id do status deve ser 1
    E a descrição deve ser "Checkout"

  Cenário: Validar status RECEIVED
    Dado que tenho o status "RECEIVED"
    Quando eu validar o status
    Então o id do status deve ser 2
    E a descrição deve ser "Received"

  Cenário: Validar status PREPARING
    Dado que tenho o status "PREPARING"
    Quando eu validar o status
    Então o id do status deve ser 3
    E a descrição deve ser "Preparing"

  Cenário: Validar status READY
    Dado que tenho o status "READY"
    Quando eu validar o status
    Então o id do status deve ser 4
    E a descrição deve ser "Ready"

  Cenário: Validar status FINISHED
    Dado que tenho o status "FINISHED"
    Quando eu validar o status
    Então o id do status deve ser 5
    E a descrição deve ser "Finished"

  Cenário: Validar status CANCELLED
    Dado que tenho o status "CANCELLED"
    Quando eu validar o status
    Então o id do status deve ser 6
    E a descrição deve ser "Cancelled"

  Cenário: Obter status por id
    Dado que tenho o id de status 3
    Quando eu buscar o status por id
    Então o status retornado deve ser "PREPARING"

  Cenário: Obter status por descrição
    Dado que tenho a descrição "Ready"
    Quando eu buscar o status por descrição
    Então o status retornado deve ser "READY"

