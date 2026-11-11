# SaúdePerto — Solução CitizenTech para Acesso à Saúde

> **Global Solution 2026 — 2º Semestre**  
> **Disciplina:** Android Kotlin Developer  
> **Tema:** Desafio CitizenTech — Tecnologia que melhora a vida das pessoas  

---

##  Integrantes da Equipe
Guilherme Buglioli — 555273 
Lucas Pasqualini - 555169

---
<img width="387" height="799" alt="image" src="https://github.com/user-attachments/assets/dd97c457-01e2-4f6c-8359-5f7194f37acc" />
<img width="411" height="814" alt="image" src="https://github.com/user-attachments/assets/e21d819c-4259-4dad-bcbc-e8dabe2e86f6" />
<img width="407" height="802" alt="image" src="https://github.com/user-attachments/assets/48a80205-a7a3-4fc1-8191-ae406bb74e8d" />
<img width="400" height="782" alt="image" src="https://github.com/user-attachments/assets/5a39d618-567c-4337-8810-7dc301af40f7" />



##  Contextualização e Problema Real
Em diversas comunidades e bairros, os cidadãos enfrentam dificuldades para obter informações claras e centralizadas sobre o funcionamento das **Unidades Básicas de Saúde (UBS)**, postos de vacinação comunitária e especialidades médicas disponíveis. Isso frequentemente gera deslocamentos desnecessários, filas excessivas e falta de acompanhamento preventivo.

O **SaúdePerto** foi desenvolvido como um protótipo funcional para resolver essa dor do cotidiano urbano e rural, permitindo que a população:
1. Localize postos de saúde e pontos de atendimento no seu bairro ou região.
2. Verifique horários, telefones, especialidades e status de atendimento (Disponível, Lotado, Manutenção).
3. Cadastre e atualize informações comunitárias de novas unidades de saúde.
4. Salve unidades favoritas para acesso rápido no dia a dia.

---

##  Público Beneficiado
* **Cidadãos em geral** buscando atendimento primário e informações sobre vacinação.
* **Idosos e gestantes** que necessitam verificar acessibilidade e horários de atendimento prioritário.
* **Agentes comunitários e moradores locais** interessados em cadastrar novos pontos de atendimento ou serviços de saúde no bairro.

---

##  Conexão com os Objetivos de Desenvolvimento Sustentável (ODS)
O aplicativo **SaúdePerto** está diretamente conectado ao **ODS 3: Saúde e Bem-Estar** da Organização das Nações Unidas (ONU).

* **Objetivo ODS 3:** *"Garantir uma vida saudável e promover o bem-estar para todas e todos, em todas as idades."*
* **Justificativa da Conexão:** Ao facilitar o mapeamento, o cadastro e a consulta comunitária de postos de saúde e vacinação local, o **SaúdePerto** reduz barreiras de acesso à saúde primária, incentiva a prevenção de doenças e promove o bem-estar social por meio da tecnologia cidadã (*CitizenTech*).

---

##  Funcionalidades do Aplicativo

### 1. Tela Inicial (Contextualização e ODS)
* Apresentação da identidade visual do app.
* Explicação do desafio CitizenTech e destaque para o **ODS 3 — Saúde e Bem-Estar**.
* Botões de navegação rápida para consulta e cadastro de unidades de saúde.

### 2. Navegação entre Telas (Navigation Compose)
* Navegação fluida e sem travamentos entre três telas principais:
  * `HomeScreen`: Tela inicial informativa e institucional.
  * `ListScreen`: Listagem de unidades com busca dinâmica e filtros.
  * `FormScreen`: Formulário de cadastro e edição de registros.

### 3. Interface e Experiência de Uso (Material 3)
* Componentes nativos do Jetpack Compose: `Scaffold`, `TopAppBar`, `Card`, `LazyColumn`, `OutlinedTextField`, `Button`, `FilterChip`, `SnackbarHost` e `AlertDialog`.
* Indicadores visuais claros de status (badges coloridos para *Disponível*, *Lotado*, *Manutenção*).
* Mensagens de feedback (*Snackbars*) ao salvar, favoritar ou excluir registros.
* Estado de lista vazia (*Empty State*) quando nenhum registro atende aos filtros de busca.

### 4. Gestão de Dados e Interações Funcionais
* Modelo de Dados `UnidadeSaudeEntity` com os seguintes atributos:
  * `id`: Identificador único numérico (Chave Primária autogerada).
  * `nome`: Nome da Unidade (ex: *UBS Central Vila Saúde*).
  * `endereco`: Endereço completo ou bairro.
  * `especialidade`: Serviços oferecidos (ex: *Clínica Geral, Vacinação, Pediatria*).
  * `telefone`: Contato telefônico.
  * `horarioFuncionamento`: Horário de atendimento.
  * `status`: Condição atual da unidade (*Disponível*, *Lotado*, *Manutenção*).
  * `isFavorito`: Indicador booleano de unidade favoritada.
  * `observacao`: Informações adicionais de acessibilidade ou orientação.
* **Validação do Formulário:** Impedimento de envio com campos obrigatórios em branco (`Nome` e `Endereço`), exibindo feedback de erro inline.
* **Ações Adicionais:**
  * **Editar:** Alteração de qualquer informação da unidade existente.
  * **Favoritar:** Marcar/desmarcar unidades preferidas diretamente na lista.
  * **Excluir:** Remoção permanente com diálogo de confirmação.

### 5. Persistência Local com Room
* Utilização do **Room Database** para armazenamento persistente no dispositivo SQLite local.
* Inserção automática de dados iniciais (*Seed Data*) no primeiro acesso para demonstração imediata.
* Garantia de que todos os cadastros, edições, exclusões e marcações de favorito permanecem armazenados mesmo após fechar e reabrir o aplicativo.

---

##  Tecnologias Utilizadas
* **Linguagem:** Kotlin
* **UI Framework:** Jetpack Compose (Material 3)
* **Arquitetura:** MVVM (Model-View-ViewModel) + Repository Pattern
* **Navegação:** Navigation Compose (`androidx.navigation:navigation-compose`)
* **Gerenciamento de Estado:** `ViewModel` + Kotlin `Flow` / `StateFlow`
* **Persistência de Dados:** Room Database + KSP (`androidx.room`)
* **Concorrência:** Kotlin Coroutines & `withContext(Dispatchers.IO)`

---

##  Instruções para Executar o Projeto

1. **Clone ou extraia** o projeto no seu computador.
2. Abra o **Android Studio** (versão Ladybug / Jellyfish ou superior).
3. Selecione **File > Open** e escolha a pasta raiz `SaudePerto`.
4. Aguarde a sincronização automática do Gradle (*Gradle Sync*).
5. Selecione um emulador Android (API 24+) ou conecte um dispositivo físico via USB Debugging.
6. Clique no botão **Run 'app'** (SHIFT + F10) para compilar e executar o aplicativo.

---

##  Evidências de Funcionamento e Demonstração

*(Utilize os espaços abaixo para anexar as capturas de tela reais do seu aplicativo em execução)*

### 1. Tela Inicial e Contextualização do ODS 3
`[ Inserir Print da Tela Inicial aqui ]`

### 2. Listagem de Unidades, Busca e Filtro de Favoritos
`[ Inserir Print da Listagem aqui ]`

### 3. Cadastro de Registro e Validação de Formulário
`[ Inserir Print do Formulário de Cadastro aqui ]`

### 4. Ação Adicional (Edição / Favoritar / Exclusão)
`[ Inserir Print da Ação Adicional aqui ]`

### 5. Comprovação de Persistência no Room (Dados mantidos após fechar/reabrir o app)
`[ Inserir Print provando que os dados continuam salvos após fechar e reabrir o app ]`
