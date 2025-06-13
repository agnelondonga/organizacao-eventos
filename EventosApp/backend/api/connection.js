const pool = require('../database');

// Example function to get a PostgreSQL client
async function getClient() {
  return await pool.connect();
}

module.exports = { getClient };