# Sistema de Cardápio do Restaurante Universitário (RU)

Projeto desenvolvido em **Java** e **JavaFX** para gerenciar o cardápio diário do Restaurante Universitário (RU) da **UFAL – Arapiraca**.

O sistema utiliza o padrão arquitetural **MVC**, segue estritamente os princípios da **Orientação a Objetos**, faz uso de **Padrões de Projeto (Singleton e Repository)** e mantém a persistência dos dados em memória durante a execução.



## Objetivo do Sistema

O sistema tem como objetivo informatizar o fluxo de criação e visualização dos cardápios, permitindo:

- Ao **Administrador**: Configurar pratos do dia (almoço e jantar), categorizar alimentos e definir a publicação.
- Aplicação prática de conceitos de Engenharia de Software e POO, tais como:
    - **MVC** (Model–View–Controller)
    - **Encapsulamento** e **Polimorfismo**
    - **Interfaces** e **Enums**
    - **Padrões de Projeto** (`Singleton` e `Repository`)



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


## Funcionalidades Principais

### Administrador
- **Gestão de Itens:** Adicionar e remover itens do cardápio.
- **Categorização:** Classificar pratos (Principal, Vegetariano, Guarnição, etc.).
- **Controle de Datas:** Navegar entre datas para planejar cardápios futuros.
- **Publicação:** Definir se o cardápio está visível ("Publicado") ou oculto.


## Padrões de Projeto Utilizados

### **Singleton**
* **Onde:** Classe `ConfiguracoesAplicacao`.
* **Por que:** Garante que exista uma **única instância** de configurações globais (como links externos) acessível por toda a aplicação.

### **Repository**
* **Onde:** Interface `RepositorioCardapio` e classe `MemoriaRepositorioCardapio`.
* **Por que:** Abstrai a lógica de acesso aos dados, permitindo trocar a forma de armazenamento (ex: para Banco de Dados SQL) sem quebrar o restante do sistema.



## Estrutura de Pastas

```text
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
└─ View/
   ├─ TelaConfigCardapio.java
   └─ Main.java

```

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

```bash
--module-path "C:\Caminho\Para\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml