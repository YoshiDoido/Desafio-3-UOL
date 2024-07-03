# E-commerce API

Este é um projeto de uma API para um sistema de e-commerce, desenvolvida com Java 17 e Spring Boot 3.3.1. O sistema utiliza um banco de dados relacional (PostgreSQL ou MySQL) e segue a arquitetura Layered Architecture.

## Funcionalidades

- **Produto**
    - Permitir que os usuários criem, leiam, atualizem e excluam produtos.
    - Criar validações para os dados de entrada na criação de um produto (ex.: preço deve ser positivo).
    - Um produto não pode ser deletado após ser incluído em uma venda, mas pode ser inativado.
    - Controlar o estoque do produto para evitar vendas com estoque insuficiente.

- **Vendas**
    - Permitir que os usuários criem, leiam, atualizem e excluam vendas.
    - Criar método para filtrar vendas por data.
    - Criar métodos de relatório de vendas (mensal e semanal).

- **Cache**
    - As leituras na API salvam as informações no cache para melhorar a performance.
    - O cache é invalidado ao inserir uma nova venda para garantir dados atualizados.

- **Tratamento de Exceções**
    - Todas as exceções são tratadas e seguem o mesmo padrão de resposta.

## Estrutura do Projeto

O projeto segue a arquitetura Layered Architecture, organizado nas seguintes camadas:
- **Controllers:** Controladores REST para lidar com as requisições HTTP.
- **Services:** Camada de serviços onde a lógica de negócio é implementada.
- **Repositories:** Interfaces JPA para interagir com o banco de dados.
- **Entities:** Classes que representam as tabelas do banco de dados.

## Modelo ER
O modelo entidade relacionamento foi criado usando a ferramenta **PlantUML**.

Para poder visualizar corretamente o diagrama, é necessário instalar a extensão PlantUML na sua IDE de preferência e também instalar o **Graphviz**.
Para instalar o Graphviz no seu SO, siga as instruções no site oficial: [Graphviz](https://graphviz.org/download/)

Aqui está o diagrama Entidade-Relacionamento (ER) do sistema:

```plaintext
@startuml
!theme toy

entity "Produto" {
    * id: Long
    --
    nome: String
    preco: Double
    estoque: int
    ativo: boolean
    --
    adicionarProduto()
    verProduto()
    atualizarProduto()
    excluirProduto()
}

entity "Venda" {
    * id: Long
    --
    data: Date
    total: Double
    --
    adicionarVendas()
    verVendas()
    atualizarVendas()
    excluirVendas()
    relatorioVendas()
}

entity "VendaProduto" {
    * vendaId: Long
    * produtoId: Long
    --
    quantidade: int
}

Produto ||--o{ VendaProduto: contém
Venda ||--o{ VendaProduto: contém
@enduml
```

