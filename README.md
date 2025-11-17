# 📦 Sistema de Gestão de Estoque e Vendas — Back-End

Este repositório contém o **Back-End** do sistema de Gestão de Estoque e Vendas, desenvolvido com **Spring Boot**.  
A aplicação fornece APIs REST para gerenciamento de produtos, usuários, vendas, movimentação de estoque e auditoria das operações.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot v3.2.12**
- Spring Web
- Spring Data JPA
- Hibernate
- Banco de Dados **H2** (ambiente de desenvolvimento)
- Maven
- Jackson (JSON)
- Auditoria manual (com interceptors)

---

## 📁 Estrutura do Projeto

src/main/java/com/gestao/back/
|
|   Back.java
|
+---controllers
|       AuthController.java
|       ProdutoController.java
|       UsuarioController.java
|       VendaController.java
|
+---dto
|       ErroDTO.java
|       ItemVendaRequestDTO.java
|       ItemVendaResponseDTO.java
|       LoginRequestDTO.java
|       MovimentoEstoqueRequestDTO.java
|       ProdutoRequestDTO.java
|       ProdutoResponseDTO.java
|       UsuarioRequestDTO.java
|       UsuarioResponseDTO.java
|       VendaRequestDTO.java
|       VendaResponseDTO.java
|
+---model
|   +---config
|   |       WebConfig.java
|   |
|   +---context
|   |       UsuarioContext.java
|   |
|   +---entities
|   |       Auditoria.java
|   |       ItemVenda.java
|   |       MovimentoEstoque.java
|   |       Produto.java
|   |       Usuario.java
|   |       Venda.java
|   |
|   +---enums
|   |       Perfil.java
|   |       TipoMovimento.java
|   |
|   +---exceptions
|   |       BadRequestException.java
|   |       BusinessException.java
|   |       ConflictException.java
|   |       ForbiddenException.java
|   |       GlobalExceptionHandler.java
|   |       NotFoundException.java
|   |
|   +---interceptors
|   |       UsuarioInterceptor.java
|   |
|   \---repositories
|           AuditoriaRepository.java
|           ItemVendaRepository.java
|           MovimentoEstoqueRepository.java
|           ProdutoRepository.java
|           UsuarioRepository.java
|           VendaRepository.java
|
\---service
        AuditoriaService.java
        ProdutoServiceImpl.java
        UsuarioServiceImpl.java
        VendaServiceImpl.java

---

## 🔧 Como Rodar o Projeto

### ✔ 1. Clonar o repositório
git clone https://github.com/GabrielGRCheim/Sistema_de_Gest-o_de_Estoque_e_Caixa.git
cd back/src/main/java/com/gestao/back/
executar arquivo Back.java
## ✔ 2. Rodar via Maven
mvn spring-boot:run
## ✔ 3. Acessar o H2 Console
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/db-api;DB_CLOSE_ON_EXIT=FALSE
jdbc:h2:file:./data/db-api

---

## 🛠 Funcionalidades

# 🧑‍💼 Gestão de Usuários
Cadastro de usuário
Login
Controle de ativo/inativo
Identificação automática do usuário nas auditorias

# 📦 Gestão de Produtos
Criar, listar, editar e excluir produtos
Validações:
Código único
Preço não negativo
Estoque não negativo
Desativação antes de permitir exclusão
Registro automático de auditorias

# 🔄 Movimentação de Estoque
Entrada
Ajuste positivo/negativo
Validação de quantidade
Bloqueio para evitar estoque negativo
Registro do usuário responsável
Registro de motivo da movimentação

# 🧾 Vendas
Registrar venda
Itens de venda vinculados ao produto
Atualização automática do estoque
Validações de quantidade disponível
Auditoria completa (antes/depois)

# 📝 Auditoria (LOG Completo)
Auditamos automaticamente:
Quem realizou a ação (via cabeçalho X-Usuario)
O que foi alterado
Estado antes e depois
Data/Hora
Operação: CREATE, UPDATE, DELETE
Como funciona:
O Angular envia em todas as requisições o cabeçalho:
X-Usuario: "Todas as informações do Usuario"
O Interceptor do Spring captura esse valor:
Armazena no ThreadLocal → UsuarioContext
O AuditoriaService salva tudo automaticamente no banco.

# 🔐 Tratamento Global de Exceções
O projeto utiliza um @RestControllerAdvice global com classes genéricas:
BadRequestException → 400
NotFoundException → 404
ConflictException → 409
ForbiddenException → 403

---

## 👤 Usuário – /api/usuarios
🔹 GET /api/usuarios/{id}

Retorna os dados de um usuário específico com base no seu ID.
Uso típico: busca de perfil, exibição de informações de um usuário específico.

🔹 PUT /api/usuarios/{id}

Atualiza os dados de um usuário já existente.
Corpo da requisição: JSON com os campos que podem ser alterados.
Uso típico: edição do perfil ou atualização administrativa.

🔹 DELETE /api/usuarios/{id}

Remove um usuário do sistema pelo ID.
Uso típico: desativação/remoção de usuários.

🔹 GET /api/usuarios

Retorna uma lista paginada de usuários ou todos, dependendo da implementação.
Uso típico: listagem no painel administrativo.

🔹 POST /api/usuarios

Cria um novo usuário.
Corpo da requisição: JSON com os dados obrigatórios para cadastro.
Uso típico: criação de contas no sistema.

## 📦 Produto – /api/produtos
🔹 GET /api/produtos/{id}

Busca um produto específico pelo ID.
Uso típico: exibir detalhes de um item no estoque.

🔹 PUT /api/produtos/{id}

Atualiza os dados de um produto existente.
Uso típico: alteração de preço, nome, categoria, etc.

🔹 DELETE /api/produtos/{id}

Remove um produto específico.
Uso típico: exclusão de itens obsoletos.

🔹 GET /api/produtos

Lista todos os produtos ou de forma paginada.
Uso típico: exibição do estoque no painel.

🔹 POST /api/produtos

Cria um novo produto no sistema.
Uso típico: cadastrar novos itens no estoque.

🔹 POST /api/produtos/movimentar/{id}

Realiza uma movimentação de estoque (entrada/saída) para o produto informado.
Corpo da requisição: JSON contendo quantidade, tipo da movimentação e justificativa.
Uso típico: controle de estoque após vendas ou reposições.

## 🔐 Autenticação – /login
🔹 POST /login

Realiza autenticação de um usuário e retorna um token JWT.
Corpo da requisição: { "username": "...", "password": "..." }
Uso típico: login no sistema e obtenção de credenciais.

## 💰 Vendas – /api/vendas
🔹 GET /api/vendas

Retorna uma lista de vendas registradas.
Uso típico: exibir histórico de vendas no painel.

🔹 POST /api/vendas

Registra uma nova venda no sistema.
Ao realizar a venda:

desconta itens do estoque automaticamente;

registra os itens vendidos e seus valores;

salva a data e o responsável.

Corpo da requisição: JSON contendo os produtos e quantidades.

### 📌 Tabela de Endpoints

| **Módulo**       | **Método** | **Endpoint**                    | **Descrição Resumida**            |
| ---------------- | ---------- | ------------------------------- | --------------------------------- |
| **Usuários**     | GET        | `/api/usuarios/{id}`            | Busca usuário por ID              |
| Usuários         | PUT        | `/api/usuarios/{id}`            | Atualiza usuário                  |
| Usuários         | DELETE     | `/api/usuarios/{id}`            | Remove usuário                    |
| Usuários         | GET        | `/api/usuarios`                 | Lista todos os usuários           |
| Usuários         | POST       | `/api/usuarios`                 | Cria novo usuário                 |
| **Produtos**     | GET        | `/api/produtos/{id}`            | Busca produto por ID              |
| Produtos         | PUT        | `/api/produtos/{id}`            | Atualiza produto                  |
| Produtos         | DELETE     | `/api/produtos/{id}`            | Remove produto                    |
| Produtos         | GET        | `/api/produtos`                 | Lista produtos                    |
| Produtos         | POST       | `/api/produtos`                 | Cadastra novo produto             |
| Produtos         | POST       | `/api/produtos/movimentar/{id}` | Movimenta estoque (entrada/saída) |
| **Autenticação** | POST       | `/login`                        | Autentica usuário e retorna JWT   |
| **Vendas**       | GET        | `/api/vendas`                   | Lista vendas realizadas           |
| Vendas           | POST       | `/api/vendas`                   | Registra nova venda               |

### Exemplo em uma requisição Swagger:

<img width="1602" height="693" alt="image" src="https://github.com/user-attachments/assets/249b1f30-3acf-45c9-bc1f-9dafed29c4dc" />

---

## 🧪 Banco H2 (Dados de Teste)
O back-end utiliza um script SQL automático para:
Inserir usuários iniciais

# Exemplo da criação de tabelas e valores ja registrados:

<img width="1869" height="492" alt="image" src="https://github.com/user-attachments/assets/570c5364-57bf-4dd9-88a7-af8a8aaaf50c" />

---

## 📝 Licença
Projeto livre para fins educacionais e uso pessoal.

---

## 📬 Contato
Autor: Gabriel Gomes Rodrigues Cheim Cheim
