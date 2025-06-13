const express = require('express');
const router = express.Router();
const { getClient } = require('./connection');

// GET /api/test-connection
router.get('/', async (req, res) => {
  let client;
  try {
    client = await getClient();
    await client.query('SELECT 1');
    res.json({ success: true, message: 'PostgreSQL connection successful.' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  } finally {
    if (client) client.release();
  }
});

module.exports = router;