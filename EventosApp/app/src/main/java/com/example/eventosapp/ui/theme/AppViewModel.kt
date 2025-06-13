package com.example.eventosapp.ui.theme

import androidx.lifecycle.ViewModel
import com.example.eventosapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {
    // Lista de tarefas
    private val _tasks = MutableStateFlow<List<Task>>(initialTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // Lista de eventos
    private val _events = MutableStateFlow<List<Event>>(initialEvents)
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    // Lista de empregados
    private val _employees = MutableStateFlow<List<Employee>>(initialEmployees)
    val employees: StateFlow<List<Employee>> = _employees.asStateFlow()

    // Lista de ementas
    private val _menus = MutableStateFlow<List<Menu>>(initialMenus)
    val menus: StateFlow<List<Menu>> = _menus.asStateFlow()

    // Funções para tarefas
    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    fun updateTask(oldTask: Task, newTask: Task) {
        _tasks.value = _tasks.value.map { if (it == oldTask) newTask else it }
    }

    fun deleteTask(task: Task) {
        _tasks.value = _tasks.value.filter { it != task }
    }

    // Funções para eventos
    fun addEvent(event: Event) {
        _events.value = _events.value + event
    }

    fun updateEvent(oldEvent: Event, newEvent: Event) {
        _events.value = _events.value.map { if (it.name == oldEvent.name) newEvent else it }
    }

    // Funções para empregados
    fun addEmployee(employee: Employee) {
        _employees.value = _employees.value + employee
    }

    fun updateEmployee(oldEmployee: Employee, newEmployee: Employee) {
        _employees.value = _employees.value.map { if (it.name == oldEmployee.name) newEmployee else it }
    }

    fun deleteEmployee(employee: Employee) {
        _employees.value = _employees.value.filter { it != employee }
    }

    // Funções para associar funcionários a eventos
    fun addEmployeeToEvent(eventName: String, employeeName: String) {
        _events.value = _events.value.map {
            if (it.name == eventName) {
                it.copy(employees = it.employees + employeeName)
            } else {
                it
            }
        }
    }

    fun removeEmployeeFromEvent(eventName: String, employeeName: String) {
        _events.value = _events.value.map {
            if (it.name == eventName) {
                it.copy(employees = it.employees - employeeName)
            } else {
                it
            }
        }
    }

    // Funções para ementas
    fun addMenu(menu: Menu) {
        _menus.value = _menus.value + menu
    }

    fun updateMenu(oldMenu: Menu, newMenu: Menu) {
        _menus.value = _menus.value.map { if (it.eventName == oldMenu.eventName) newMenu else it }
    }

    fun getMenuForEvent(eventName: String): Menu? {
        return _menus.value.find { it.eventName == eventName }
    }

    // Função para calcular o custo estimado
    fun calculateEstimatedCost(attendees: Int, menu: String): String {
        val costPerPerson = 500 // 500 KZS por pessoa
        val menuCost = when (menu) {
            "Ementa 1" -> 1000 // Custo fixo da Ementa 1
            "Ementa 2" -> 800  // Custo fixo da Ementa 2
            "Ementa 3" -> 600  // Custo fixo da Ementa 3
            else -> 0
        }
        val totalCost = (attendees * costPerPerson) + menuCost
        return "$totalCost KZS"
    }
}

// Modelos de dados
data class Task(
    val name: String,
    val status: TaskStatus,
    val icon: Int,
    val description: String = ""
)

enum class TaskStatus {
    DONE, IN_PROGRESS, PAUSED, NOT_STARTED
}

data class Event(
    val name: String,
    val attendees: Int,
    val date: String,
    val time: String,
    val location: String,
    val menu: String, // Nome da ementa (ex.: "Ementa 1")
    val estimatedCost: String, // Custo estimado em KZS
    val employees: List<String> = emptyList() // Lista de nomes de funcionários
)

data class Employee(
    val name: String,
    val role: String,
    val isAvailable: Boolean,
    val icon: Int
)

data class Menu(
    val eventName: String, // Nome do evento associado
    val name: String, // Nome da ementa (ex.: "Ementa 1")
    val appetizers: String,
    val mainCourse: String,
    val dessert: String,
    val drinks: String
)

// Dados iniciais
private val initialTasks = listOf(
    Task("Localização", TaskStatus.DONE, R.drawable.ic_meeting, "Escolher salão de festas"),
    Task("Lista de Convidados", TaskStatus.IN_PROGRESS, R.drawable.ic_other, "Confirmar RSVPs"),
    Task("Comida", TaskStatus.PAUSED, R.drawable.ic_birthday, "Organizar cardápio com canapés e bolo"),
    Task("Presentes", TaskStatus.IN_PROGRESS, R.drawable.ic_graduation, "Planejar lista de presentes"),
    Task("Fotógrafo", TaskStatus.DONE, R.drawable.ic_baptism, "Contratar fotógrafo profissional"),
    Task("Decoração", TaskStatus.NOT_STARTED, R.drawable.ic_wedding, "Planejar decoração do evento")
)

private val initialEvents = listOf(
    Event(
        name = "Casamento",
        attendees = 50,
        date = "15/06/2025",
        time = "18:00 - 22:00",
        location = "Salão de Festas XYZ",
        menu = "Ementa 1",
        estimatedCost = "26000 KZS",
        employees = listOf("João Silva", "Ana Costa")
    ),
    Event(
        name = "Aniversário",
        attendees = 30,
        date = "20/07/2025",
        time = "14:00 - 18:00",
        location = "Buffet Infantil ABC",
        menu = "Ementa 2",
        estimatedCost = "15800 KZS",
        employees = listOf("Pedro Santos")
    ),
    Event(
        name = "Batismo",
        attendees = 20,
        date = "10/08/2025",
        time = "10:00 - 12:00",
        location = "Igreja São Paulo",
        menu = "Ementa 3",
        estimatedCost = "10600 KZS",
        employees = listOf("Maria Souza")
    ),
    Event(
        name = "Reunião de Empresas",
        attendees = 40,
        date = "25/09/2025",
        time = "09:00 - 13:00",
        location = "Centro de Convenções DEF",
        menu = "Ementa 1",
        estimatedCost = "21000 KZS",
        employees = listOf("João Silva")
    ),
    Event(
        name = "Graduação",
        attendees = 60,
        date = "05/12/2025",
        time = "19:00 - 23:00",
        location = "Auditório Universitário",
        menu = "Ementa 2",
        estimatedCost = "30800 KZS",
        employees = listOf("Ana Costa", "Pedro Santos")
    ),
    Event(
        name = "Outros",
        attendees = 25,
        date = "15/10/2025",
        time = "16:00 - 20:00",
        location = "Espaço Cultural GHI",
        menu = "Ementa 3",
        estimatedCost = "13100 KZS",
        employees = emptyList()
    )
)

private val initialEmployees = listOf(
    Employee("João Silva", "Garçom", true, R.drawable.ic_other),
    Employee("Maria Souza", "Fotógrafa", false, R.drawable.ic_baptism),
    Employee("Pedro Santos", "Cozinheiro", true, R.drawable.ic_birthday),
    Employee("Ana Costa", "Decoradora", true, R.drawable.ic_wedding),
    Employee("Irina", "Fotógrafa", true, R.drawable.ic_irina),
    Employee("Maria", "Cozinheira", true, R.drawable.ic_maria)
)

private val initialMenus = listOf(
    Menu(
        eventName = "Casamento",
        name = "Ementa 1",
        appetizers = "Canapés, brusquetas",
        mainCourse = "Frango assado, massa",
        dessert = "Bolo de chocolate",
        drinks = "Vinho, suco, água"
    ),
    Menu(
        eventName = "Aniversário",
        name = "Ementa 2",
        appetizers = "Salgadinhos, pizza",
        mainCourse = "Nenhum",
        dessert = "Bolo de morango",
        drinks = "Refrigerante, suco"
    ),
    Menu(
        eventName = "Batismo",
        name = "Ementa 3",
        appetizers = "Bolos, salgados",
        mainCourse = "Nenhum",
        dessert = "Torta de limão",
        drinks = "Suco, água"
    )
)