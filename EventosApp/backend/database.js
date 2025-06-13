require('dotenv').config();
const { Pool } = require('pg');

const connectionString = process.env.NEOTECH_CONNECTION_STRING;

if (!connectionString) {
  throw new Error('NEOTECH_CONNECTION_STRING is not set in environment variables');
}

const pool = new Pool({ connectionString });

module.exports = pool;