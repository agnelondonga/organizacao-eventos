# EventosApp Backend

This is the Node.js backend API for EventosApp, using Express and Neo4j (Neo-Tech).

## Setup

1. Install dependencies:
   ```
   npm install
   ```
2. Copy `.env.example` to `.env` and fill in your Neo-Tech connection string:
   ```
   cp .env.example .env
   # Edit .env and set NEOTECH_CONNECTION_STRING
   ```
3. Start the server:
   ```
   npm start
   ```

## API Endpoints

- `GET /api/events` — Returns all events from the Neo-Tech database.

## Environment Variables
- `PORT`: Port for the server (default: 3001)
- `NEOTECH_CONNECTION_STRING`: Your Neo-Tech (Neo4j) database connection string

## Notes
- Make sure your Neo-Tech database is accessible from this server.
- Extend the API by adding more routes in the `routes/` folder.