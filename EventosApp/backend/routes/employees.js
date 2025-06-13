const express = require('express');
const router = express.Router();
const pool = require('../database');

// GET all employees
router.get('/', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM employee');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// POST new employee
router.post('/', async (req, res) => {
  const { name, role, hourly_wage, is_available, icon_reference } = req.body;
  try {
    const result = await pool.query(
      `INSERT INTO employee (name, role, hourly_wage, is_available, icon_reference)
       VALUES ($1, $2, $3, $4, $5) RETURNING *`,
      [name, role, hourly_wage, is_available, icon_reference]
    );
    res.status(201).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
