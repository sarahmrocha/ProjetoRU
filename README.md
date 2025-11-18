
# Sistema de Gestão do Restaurante Universitário (RU)

Este projeto é um Sistema de Informação desenvolvido para gerenciar e disponibilizar o cardápio diário do Restaurante Universitário. O sistema permite o cadastro de refeições (almoço e jantar), gerenciamento de itens categorizados e publicação do cardápio para visualização.

Projeto desenvolvido como requisito avaliativo para a disciplina de **Programação Orientada a Objetos** do curso de **Ciência da Computação**.



## Funcionalidades

* **Gestão de Cardápios:** Criação de cardápios ao adicionar itens em novas datas.
* **Controle de Itens:** Adição, classificação e remoção de itens.
* **Categorização:** Classificação dos itens via **Enums** (Prato Principal, Vegetariano, Salada, Sobremesa, etc.).
* **Publicação:** Controle de visibilidade do cardápio ("Publicado" ou "Rascunho").
* **Persistência em Memória:** Armazenamento volátil de dados durante a execução da aplicação.


## Arquitetura e Tecnologias

O projeto segue estritamente o padrão arquitetural **MVC (Model-View-Controller)** e os princípios da Orientação a Objetos.

* **Linguagem:** Java (JDK 21)
* **Interface Gráfica:** JavaFX (Construção programática de UI)
* **Paradigma:** Orientação a Objetos (Encapsulamento, Herança, Polimorfismo).

### Estrutura do Projeto (MVC)

* **`Model`**: Contém as Regras de Negócio e Entidades.
    * Principais classes: `CardapioDiario`, `ItemCardapio`.
    * Enums: `TipoPrato`, `TipoRefeicao`.
* **`View`**: Camada de apresentação.
    * `TelaConfigCardapio`: Interface administrativa construída com componentes JavaFX (`BorderPane`, `ListView`, `DatePicker`).
* **`Controller`**: Intermediário que processa as ações do usuário.
    * `ControleRU`: Gerencia a lógica de ID único, validação de dados e chamadas ao repositório.



## Padrões de Projeto Aplicados

Para atender aos requisitos exigidos na disciplina, foram implementados os seguintes padrões:

### 1. Singleton
* **Onde:** Classe `ConfiguracoesAplicacao`.
* **Objetivo:** Garantir que as configurações globais do sistema (como o link do boleto) tenham uma **única instância** acessível por toda a aplicação.

### 2. Repository
* **Onde:** Interface `RepositorioCardapio` e classe `MemoriaRepositorioCardapio`.
* **Objetivo:** Abstrair a camada de acesso a dados. Isso desacopla o `Controller` da forma como os dados são salvos (neste caso, em um `HashMap`), facilitando a migração futura para um banco de dados real (SQL).

## Diagrama UML
![Diagrama UML](https://raw.githubusercontent.com/sarahmrocha/ProjetoRU/main/diagrama/Diagrama-UML.png)

## Atendimento aos Requisitos

| Requisito | Implementação no Código                                                      |
| :--- |:-----------------------------------------------------------------------------|
| **Sistema de Informação** | Gestão completa de fluxo de cardápios (CRUD).                                |
| **Armazenamento de Dados** | Implementado via `HashMap<LocalDate, CardapioDiario>`.                       |
| **Classes e Interfaces** | Interface `ISistemaRU` definindo o contrato do sistema.                      |
| **Tipagem Forte** | Uso de `Enum` para `TipoPrato` (Principal, Vegetariano...) e `TipoRefeicao`. |
| **Encapsulamento** | Atributos privados protegidos e modificados apenas via métodos de negócio.   |
| **Tratamento de Erros** |  Uso de `Objects.requireNonNull` e alertas visuais na interface.             |

---

## Autores

* **Jamily Barbosa de Oliveira** 
* **Samile Riquele** 
* **Sarah Maria Rocha de Oliveira** 




