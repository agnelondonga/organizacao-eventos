const express = require('express');
const router = express.Router();
const pool = require('../database');

router.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM menu');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/', async (req, res) => {
  const { title } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO menu (title) VALUES ($1) RETURNING *',
      [title]
    );
    res.status(201).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.put('/:id', async (req, res) => {
  const { id } = req.params;
  const { title } = req.body;
  try {
    const result = await pool.query(
      'UPDATE menu SET title = $1 WHERE id = $2 RETURNING *',
      [title, id]
    );
    res.json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.delete('/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM menu WHERE id = $1', [id]);
    res.json({ message: 'Menu deleted' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;