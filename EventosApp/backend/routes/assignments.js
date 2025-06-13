const express = require('express');
const assignmentRouter = express.Router();
const pool = require('../database');

assignmentRouter.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM assignment');
    res.json(result.rows);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

assignmentRouter.post('/', async (req, res) => {
  const { task_id, user_id } = req.body;
  try {
    const result = await pool.query('INSERT INTO assignment (task_id, user_id) VALUES ($1, $2) RETURNING *', [task_id, user_id]);
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

assignmentRouter.put('/:id', async (req, res) => {
  const { id } = req.params;
  const { task_id, user_id } = req.body;
  try {
    const result = await pool.query('UPDATE assignment SET task_id = $1, user_id = $2 WHERE id = $3 RETURNING *', [task_id, user_id, id]);
    res.json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

assignmentRouter.delete('/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM assignment WHERE id = $1', [id]);
    res.status(204).send();
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = assignmentRouter;