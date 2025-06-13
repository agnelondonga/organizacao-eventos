const express = require('express');
const router = express.Router();
const pool = require('../database');

// GET /api/database
router.get('/', async (req, res) => {
  try {
    const result = await pool.query(`
      SELECT table_name
      FROM information_schema.tables
      WHERE table_schema = 'public'
      ORDER BY table_name
    `);
    res.json({ tables: result.rows.map(row => row.table_name) });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
