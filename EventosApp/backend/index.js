require('dotenv').config();
const express = require('express');
const cors = require('cors');

// Importar rotas
const eventsRouter = require('./routes/events');
const menusRouter = require('./routes/menus');
const menuItemsRouter = require('./routes/menuItems');
const employeesRouter = require('./routes/employees');
const locationsRouter = require('./routes/locations');
const usersRouter = require('./routes/users');
const teamsRouter = require('./routes/teams');
const tasksRouter = require('./routes/tasks');
const assignmentsRouter = require('./routes/assignments');
const schedulesRouter = require('./routes/schedules');
const testConnectionRouter = require('./api/testConnection');

const app = express();
const PORT = process.env.PORT || 3001;

// Middlewares
app.use(cors());
app.use(express.json());

// Rota raiz
app.get('/', (req, res) => {
  res.send('EventosApp Backend is running');
});

// Usar rotas
app.use('/api/events', eventsRouter);
app.use('/api/menus', menusRouter);
app.use('/api/menu-items', menuItemsRouter);
app.use('/api/employees', employeesRouter);
app.use('/api/locations', locationsRouter);
app.use('/api/users', usersRouter);
app.use('/api/teams', teamsRouter);
app.use('/api/tasks', tasksRouter);
app.use('/api/assignments', assignmentsRouter);
app.use('/api/schedules', schedulesRouter);
app.use('/api/test-connection', testConnectionRouter);

// Iniciar servidor
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
