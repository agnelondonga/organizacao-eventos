const express = require('express');
const taskRouter = express.Router();
const pool = require('../database');

taskRouter.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM task');
    res.json(result.rows);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

taskRouter.post('/', async (req, res) => {
  const { name, description } = req.body;
  try {
    const result = await pool.query('INSERT INTO task (name, description) VALUES ($1, $2) RETURNING *', [name, description]);
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

taskRouter.put('/:id', async (req, res) => {
  const { id } = req.params;
  const { name, description } = req.body;
  try {
    const result = await pool.query('UPDATE task SET name = $1, description = $2 WHERE id = $3 RETURNING *', [name, description, id]);
    res.json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

taskRouter.delete('/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM task WHERE id = $1', [id]);
    res.status(204).send();
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = taskRouter;
