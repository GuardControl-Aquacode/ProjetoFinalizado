# AquacodeUpx2 — Java + JDBC / MySQL

Versão de console adaptada ao **DER original de seis tabelas** (`database/01_der_original.sql`).
O nome do projeto Maven é `AquacodeUpx2`. O pacote Java `br.com.guardcontrol` e a
classe principal `GuardControl` foram mantidos para facilitar a continuação do projeto da turma.

## O que o programa faz

- Cadastro, consulta, alteração e exclusão de empresas (`cliente`).
- Cadastro geral de vigilantes (`seguranca`), gestores (`gestor`) e locais (`local`).
- Início, conclusão, consulta e manutenção de rondas (`ronda`).
- Cadastro e consulta de ocorrências vinculadas a rondas (`ocorrencia`).
- Seleção de empresa por ID e seleção de vigilante por matrícula **apenas para demonstração**.

## O que foi removido para respeitar o DER

- `PontoControle` e `Checagem`: tabelas inexistentes no DER.
- Campos de CNPJ, senha, turno, estado e vínculo empresa–vigilante.
- Login por senha, QR Code, checagens, status de ocorrência e alertas automáticos.
- Scripts de migração que acrescentavam colunas ou tabelas ao DER.

**ATENÇÃO:** não há autenticação. Digitar ID/matrícula NÃO comprova identidade e não controla permissões;
use somente em uma demonstração acadêmica local com dados fictícios.

## Banco de dados

O arquivo `database/01_der_original.sql` contém exatamente as seis definições `CREATE TABLE`
do DER original fornecido. Execute-o em um esquema vazio.

**Não execute esse SQL para corrigir um banco já existente:** como o original usa
`CREATE TABLE IF NOT EXISTS`, colunas extras antigas não são removidas. O banco em que você
adicionou `cnpj` e `senha_hash` com `NOT NULL` não é compatível com o novo cadastro de `cliente`.
Para testar sem perder dados, faça backup e use um banco de teste separado:

1. No MySQL Workbench, execute **`database/03_der_banco_teste.sql`**. Ele contém as
   MESMAS seis tabelas, sem adicionar campos ou relacionamentos; só muda o nome do banco.
2. Configure `GUARD_DB_URL=jdbc:mysql://127.0.0.1:3306/aquacode_upx2_teste`.
3. Execute a aplicação e cadastre uma empresa nova de teste.

O arquivo original `01_der_original.sql` continua disponível como referência fiel ao DER.
Não apague as colunas extras do banco antigo sem confirmar os dados e fazer backup.

O arquivo `database/02_conferir_estrutura.sql` apenas mostra as tabelas para comparação;
não modifica seu banco.

## Configurar conexão

O Java usa estas configurações, por variável de ambiente ou propriedade da JVM:

- `GUARD_DB_URL` (padrão: `jdbc:mysql://127.0.0.1:3306/guard_control`)
- `GUARD_DB_USER` (padrão: `root`)
- `GUARD_DB_PASSWORD` (padrão: vazio)

No Windows PowerShell (exemplo, substitua pelos seus dados):

```powershell
$env:GUARD_DB_URL="jdbc:mysql://127.0.0.1:3306/guard_control"
$env:GUARD_DB_USER="root"
$env:GUARD_DB_PASSWORD="SUA_SENHA_LOCAL"
mvn compile exec:java
```

No NetBeans, configure as variáveis de ambiente de execução ou propriedades `-DGUARD_DB_...`.
Não publique a senha em código, ZIP, GitHub ou prints.

## Executar

Requer JDK 17+ e Maven com dependências disponíveis. Na pasta do projeto:

```bash
mvn clean compile
mvn exec:java
```

### Limitações impostas pelo DER

1. A tabela `seguranca` **não** tem `id_cliente`. O cadastro de vigilantes é geral,
   não limitado a uma empresa. Uma ronda pode associar um vigilante a um local da empresa,
   mas isso não cria uma empresa fixa do vigilante.
2. Ocorrências podem ter `id_ronda = NULL` no DER. Os menus por empresa e por vigilante
   mostram apenas ocorrências com ronda, já que não há como atribuir a empresa/vigilante
   de uma ocorrência sem ronda; o DAO continua capaz de ler esses registros.
3. A seleção de empresa ou vigilante permite entrar sem senha, portanto não constitui login.
4. O DER não permite armazenar fotos, apenas o texto `URL_foto`.
5. Como não há conexão com seu MySQL neste ambiente, a integração com banco real deve
   ser testada pelo grupo com uma base compatível.
