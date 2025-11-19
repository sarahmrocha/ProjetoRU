
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
![Diagrama UML](https://raw.githubusercontent.com/sarahmrocha/ProjetoRU/main/diagrama/Diagrama_UML.png)

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


## Objetivos e Funcionalidades do Sistema

### Fluxo de Uso e Público-Alvo

| Perfil | Ações Principais |
| :--- | :--- |
| **Administrador** | Gerencia o cardápio por data; Adiciona, remove e categoriza itens (`TipoPrato`); Define a publicação (`Publicado/Rascunho`). |
| **Usuário/Aluno** | Consulta e visualiza apenas os cardápios que estão `Publicados`; Navega entre datas. |

---

### Funcionalidades Detalhadas

#### 1. Administrador (Gestão de Cardápios)
* **Gestão de Itens:** Adicionar e remover pratos do cardápio do dia.
* **Controle de Datas:** Planejar cardápios futuros navegando entre datas usando o `DatePicker`.
* **Categorização:** Classificar pratos usando o `TipoPrato` (Principal, Vegetariano, Salada, etc.).
* **Publicação:** Definir o status do cardápio (`Publicado` ou `Rascunho`) via checkbox. O sistema impede a publicação de cardápios vazios.

#### 2. Usuário/Aluno (Consulta Pública)
* **Consulta por Data:** Pesquisar o cardápio (Almoço/Jantar) de qualquer dia, sendo a pesquisa disparada automaticamente pela mudança de data no `DatePicker`.
* **Visibilidade Controlada:** O sistema exibe o aviso "Cardápio não divulgado" se o flag `publicado` estiver como `false`.

---

### Aplicação de Conceitos Técnicos

#### 1. Arquitetura e Paradigma
* **MVC (Model-View-Controller):** Separação estrita das responsabilidades em três pacotes diferentes.
* **Orientação a Objetos (OO):** Uso de classes (`ItemCardapio`, `CardapioDiario`), **Encapsulamento** (atributos privados e métodos de acesso) e **Polimorfismo** (implementação de interfaces).
* **Tipagem Forte:** Uso de **Interfaces** (`ISistemaRU`, `RepositorioCardapio`) e **Enums** (`TipoRefeicao`, `TipoPrato`).

#### 2. Padrões de Projeto (Design Patterns)
* **Repository Pattern:** Utilizado para desacoplar a lógica de negócio do armazenamento de dados.
    * O **`MemoriaRepositorioCardapio`** usa um `Map` **estático** para garantir que o estado da memória seja único e compartilhado entre todas as telas.
* **Singleton Pattern:** Implementado na classe `ConfiguracoesAplicacao` para garantir que as configurações globais (como o link do boleto) possuam uma única instância.


## Arquitetura do Projeto (MVC)

O projeto está organizado em três camadas principais para garantir o desacoplamento e a facilidade de manutenção.

### **Model**
Contém as classes que representam os dados e regras de negócio:

- `CardapioDiario` – Entidade principal (data, estado de publicação, itens do almoço e jantar).
- `ItemCardapio` – Representa cada prato ou alimento individual.
- `TipoRefeicao` – Enum que define se o item é **ALMOCO** ou **JANTAR**.
- `TipoPrato` – Enum para categorização (PRINCIPAL, VEGETARIANO, SALADA, etc.).
- `RepositorioCardapio` – Interface do repositório.
- `MemoriaRepositorioCardapio` – Implementação usando `HashMap` para persistência.
- `ConfiguracoesAplicacao` – Singleton responsável por configurações globais.

### **Controller**
Coordena a comunicação entre View e Model:

- `ISistemaRU` – Interface que define as operações do sistema.
- `ControleRU` – Implementação oficial das regras de negócio e controle.

### **View (JavaFX)**
Interface gráfica com o usuário:

- `TelaConfigCardapio` – Tela administrativa para cadastro e vizualização dos itens.
- `Main` – Inicializador do ciclo de vida da aplicação JavaFX.



## Padrões de Projeto Utilizados

### **Singleton**
* **Onde:** Classe `ConfiguracoesAplicacao`.
* **Por que:** Garante que exista uma **única instância** de configurações globais (como links externos) acessível por toda a aplicação.

### **Repository**
* **Onde:** Interface `RepositorioCardapio` e classe `MemoriaRepositorioCardapio`.
* **Por que:** Abstrai a lógica de acesso aos dados, permitindo trocar a forma de armazenamento (ex: para Banco de Dados SQL) sem quebrar o restante do sistema.



## Estrutura de Pastas

src/  
├─ Model/  
│  ├─ CardapioDiario.java  
│  ├─ ItemCardapio.java  
│  ├─ TipoRefeicao.java  
│  ├─ TipoPrato.java  
│  ├─ RepositorioCardapio.java  
│  ├─ MemoriaRepositorioCardapio.java  
│  └─ ConfiguracoesAplicacao.java  
│  
├─ Controller/  
│  ├─ ISistemaRU.java  
│  └─ ControleRU.java  
│  
├─ View/  
│  ├─ TelaConfigCardapio.java  
│  └─ TelaUsuario.java  
│  
└─ Main.java  



## Como Executar o Projeto

Este projeto utiliza **Java 21** e **JavaFX**. Como o JavaFX foi descontinuado do JDK padrão, é necessário configurar as bibliotecas manualmente para rodar o projeto.

### 1.Pré-requisitos
* **Java JDK 21** instalado.
* **IntelliJ IDEA** (ou IDE de sua preferência).
* **JavaFX SDK 21** (Download necessário).

### 2.Passo a Passo: Baixando o JavaFX
1. Acesse o site oficial da Gluon: [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Na seção "Downloads", filtre por:
    * **Version:** 21 (LTS)
    * **Type:** SDK
    * **OS:** Seu sistema operacional (Windows/Mac/Linux)
3. Baixe e extraia o arquivo `.zip` em uma pasta segura (Ex: `C:\Java\javafx-sdk-21`).

### 3.Configurando na IDE (IntelliJ IDEA)
1. Abra o projeto e vá em **File > Project Structure > Libraries**.
2. Clique no **+** (Java) e selecione a pasta `lib` dentro do SDK que você baixou.
3. Clique em **Apply**.

### 4.Configurando a Execução (VM Options)
>  **Passo Crítico:** Sem isso o projeto não roda (`Runtime components are missing`).

1. Localize a classe `Main.java` (View).
2. Tente rodar uma vez (vai falhar) para criar a configuração.
3. Vá em **Run > Edit Configurations...**
4. Selecione a classe `Main`.
5. Em **Modify Options**, ative **"Add VM Options"**.
6. Cole o comando abaixo no campo que apareceu (ajuste o caminho conforme seu PC):

bash
--module-path "C:\Caminho\Para\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml

## Autores

* **Jamily Barbosa de Oliveira** 
* **Samile Riquele** 
* **Sarah Maria Rocha de Oliveira** 





