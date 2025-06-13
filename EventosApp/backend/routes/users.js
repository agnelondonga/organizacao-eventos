const express = require('express');
const router = express.Router();
const pool = require('../database');

router.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM user_account');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/', async (req, res) => {
  const { username, password } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO user_account (username, password, created_at) VALUES ($1, $2, NOW()) RETURNING *',
      [username, password]
    );
    res.status(201).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.put('/:id', async (req, res) => {
  const { id } = req.params;
  const { username, password } = req.body;
  try {
    const result = await pool.query(
      'UPDATE user_account SET username = $1, password = $2 WHERE id = $3 RETURNING *',
      [username, password, id]
    );
    res.json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.delete('/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM user_account WHERE id = $1', [id]);
    res.json({ message: 'User deleted' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
