const express = require('express');
const router = express.Router();
const pool = require('../database');

// GET all teams
router.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM team');
    res.json(result.rows);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// POST new team
router.post('/', async (req, res) => {
  const { name } = req.body;
  try {
    const result = await pool.query('INSERT INTO team (name) VALUES ($1) RETURNING *', [name]);
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// PUT update team
router.put('/:id', async (req, res) => {
  const { id } = req.params;
  const { name } = req.body;
  try {
    const result = await pool.query('UPDATE team SET name = $1 WHERE id = $2 RETURNING *', [name, id]);
    res.json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// DELETE team
router.delete('/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM team WHERE id = $1', [id]);
    res.status(204).send();
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;