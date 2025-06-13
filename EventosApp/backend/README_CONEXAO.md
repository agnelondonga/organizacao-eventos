# API de Conexão – EventosApp Backend

Este ficheiro documenta exclusivamente a API de conexão do backend do EventosApp, responsável por testar a ligação ao banco de dados PostgreSQL.

## Objetivo
A API de conexão permite verificar se o backend consegue conectar-se corretamente ao banco de dados PostgreSQL configurado no ambiente.

## Arquivos Relacionados
- `database.js`: Configura o pool de conexões PostgreSQL usando a variável de ambiente `NEOTECH_CONNECTION_STRING`.
- `api/connection.js`: Exporta a função `getClient` para obter um cliente do pool e executar queries.
- `api/testConnection.js`: Define o endpoint `/api/test-connection` que utiliza a função `getClient` para testar a conexão.

## Variáveis de Ambiente
No ficheiro `.env` ou `.env.example`, configure:
```
NEOTECH_CONNECTION_STRING=postgresql://usuario:senha@endereco:porta/banco?sslmode=require
```
Substitua pelos dados do seu ambiente.

## Como funciona o endpoint
- **Rota:** `GET /api/test-connection`
- **Descrição:** Executa a query `SELECT 1` no banco de dados.
- **Resposta de sucesso:**
  ```json
  { "message": "Conexão bem-sucedida com o banco de dados!" }
  ```
- **Resposta de erro:**
  ```json
  { "error": "Mensagem de erro detalhada" }
  ```

## Como testar
1. Certifique-se de que o `.env` está configurado corretamente.
2. Inicie o backend com:
   ```bash
   npm start
   ```
3. Faça uma requisição GET para `http://localhost:3001/api/test-connection`.

## Boas práticas
- Sempre libere o cliente após o uso com `client.release()`.
- Nunca exponha credenciais do banco de dados; mantenha-as apenas no `.env`.

---
Esta documentação refere-se apenas à API de conexão do backend do EventosApp.