const express = require('express');
const scheduleRouter = express.Router();
const pool = require('../database');

scheduleRouter.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM schedule');
    res.json(result.rows);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

scheduleRouter.post('/', async (req, res) => {
  const { date, description } = req.body;
  try {
    const result = await pool.query('INSERT INTO schedule (date, description) VALUES ($1, $2) RETURNING *', [date, description]);
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

scheduleRouter.put('/:id', async (req, res) => {
  const { id } = req.params;
  const { date, description } = req.body;
  try {
    const result = await pool.query('UPDATE schedule SET date = $1, description = $2 WHERE id = $3 RETURNING *', [date, description, id]);
    res.json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

scheduleRouter.delete('/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM schedule WHERE id = $1', [id]);
    res.status(204).send();
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = scheduleRouter;
