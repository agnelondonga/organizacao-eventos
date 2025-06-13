const express = require('express');
const router = express.Router();
const pool = require('../database');

// GET all locations
router.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM location');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// POST new location
router.post('/', async (req, res) => {
  const { name, image_reference } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO location (name, image_reference) VALUES ($1, $2) RETURNING *',
      [name, image_reference]
    );
    res.status(201).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
