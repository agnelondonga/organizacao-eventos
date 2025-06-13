const express = require('express');
const router = express.Router();
const pool = require('../database');

// Example: GET /api/events
router.get('/', async (req, res) => {
  let client;
  try {
    client = await pool.connect();
    const result = await client.query('SELECT * FROM event');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  } finally {
    if (client) client.release();
  }
});

module.exports = router;