const express = require('express');
const router = express.Router();
const pool = require('../database');

router.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM menu_item');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/', async (req, res) => {
  const { name, description, price, menu_id } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO menu_item (name, description, price, menu_id) VALUES ($1, $2, $3, $4) RETURNING *',
      [name, description, price, menu_id]
    );
    res.status(201).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.put('/:id', async (req, res) => {
  const { id } = req.params;
  const { name, description, price, menu_id } = req.body;
  try {
    const result = await pool.query(
      'UPDATE menu_item SET name = $1, description = $2, price = $3, menu_id = $4 WHERE id = $5 RETURNING *',
      [name, description, price, menu_id, id]
    );
    res.json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.delete('/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM menu_item WHERE id = $1', [id]);
    res.json({ message: 'Menu item deleted' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;