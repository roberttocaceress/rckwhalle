# RCKSISTEMAS-APP

Aplicativo Android para gestão de vendas externas, relacionamento com clientes, emissão de pedidos, controle financeiro e cobrança em campo.

---

## 📱 Visão Geral

O **RCKSISTEMAS-APP** é uma solução Android desenvolvida para apoiar equipes de vendas no campo. O sistema opera com suporte completo a funcionalidades offline-first (usando banco de dados SQLite local) e sincronização automática com o servidor backend via Retrofit e Firebase.

### Principais Recursos
* 🛒 **Gestão de Pedidos e Catálogo**: Emissão, acompanhamento e exportação de pedidos com fotos e tabelas de preços.
* 👤 **Cadastro de Clientes e Prospectos**: Wizard completo para cadastro com geolocalização e fotos.
* 💰 **Módulo Financeiro e Cobrança**: Acompanhamento de títulos, contas a receber e cobrança em campo.
* 📊 **Dashboards e Relatórios**: Gráficos de vendas, acompanhamento de metas e histórico financeiro.
* 🔄 **Sincronização Offline/Online**: Persistência local segura em SQLite com rotinas de sincronia em lote.

---

## 🛠️ Tecnologias e Requisitos

* **Linguagem**: Java (Android)
* **SDK Mínimo**: API 21 (Android 5.0)
* **SDK Alvo**: API 33 (Android 13) / Compile SDK 36
* **Banco de Dados Local**: SQLite (`RCKWhalle.db`) via DAO Pattern
* **Comunicação / API**: Retrofit 2, Gson Converter
* **Serviços Cloud**: Firebase Firestore, Database, Messaging e Google Location Services
* **Interface**: AndroidX, Material Design (M3)

---

## 🤖 Desenvolvimento Assistido por IA (Agentes & Skills)

Este repositório possui diretrizes e automações padronizadas para assistentes de IA no Android Studio:

* **`AGENTS.md`**: Guia de arquitetura do projeto, convenções de código, estrutura de dados e regras gerais que a IA deve seguir.
* **`.agents/skills/`**: Habilidades e templates reutilizáveis pelo agente de IA:
  * **`gerador-dao`**: Padrão obrigatório para criação e refatoração de classes DAO SQLite (mapeamento seguro de `Cursor`, transações em lote com `SQLiteStatement` e auxiliares null-safe).
  * **`auditor-java`**: Análise e auditoria de código Java e layouts XML.

### Como utilizar na equipe:
1. O assistente de IA usará automaticamente o arquivo `AGENTS.md` como contexto de arquitetura do projeto.
2. Durante o desenvolvimento, invoque as automações citando as skills (exemplo: `@gerador-dao`).

---

## 🚀 Como Executar o Projeto

1. **Clonar o Repositório**:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```

2. **Abrir no Android Studio**:
   * Abra o Android Studio e selecione **Open**.
   * Escolha a pasta raiz do projeto (`RCKSISTEMAS-APP`).

3. **Sincronizar e Compilar**:
   * Aguarde o término do **Gradle Sync**.
   * Execute o aplicativo em um dispositivo físico ou emulador Android (API 21+).

---

## 🤝 Contribuição e Padrões da Equipe

Ao desenvolver novas funcionalidades ou alterar o código-fonte:
1. Respeite a arquitetura em camadas do projeto (`activity/`, `fragment/`, `BO/`, `DAO/`, `model/`, `Helper/`).
2. Siga as orientações contidas no `AGENTS.md` e utilize as skills da pasta `.agents/skills/`.
3. Ao adicionar novas colunas ou tabelas no SQLite, lembre-se de atualizar as definições em `DBHelper.java`.
