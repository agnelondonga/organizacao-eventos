const pool = require('./database');
(async () => {
  try {
    const res = await pool.query("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
    console.log('Tables in the database:');
    res.rows.forEach(row => console.log(row.table_name));
  } catch (err) {
    console.error('Error querying tables:', err);
  } finally {
    await pool.end();
  }
})();