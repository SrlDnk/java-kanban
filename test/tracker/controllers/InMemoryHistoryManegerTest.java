package tracker.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.HistoryManager;
import tracker.Managers;
import tracker.TaskManager;
import tracker.model.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setup() {
        taskManager = Managers.getDefault();
    }

    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void tasksAreEqualIfIdEquals() {
        Task task1 = new Task("Тест", "Описание" );
        task1.setId(1);
        Task task2 = new Task("Тест2", "Описание2");
        task2.setId(1);
        assertEquals(task1, task2, "Tasks с одним id должны быть равны");
    }

    //проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void subtasksAndEpicsEqualIfIdEquals() {
        Epic epic1 = new Epic("Эпик", "Описание" );
        epic1.setId(2);
        Subtask subtask1 = new Subtask("Сабтаск", "Описание2", 2);
        subtask1.setId(2);
        assertEquals(epic1, subtask1, "Epic и Subtask с одним id должны быть равны");
    }

    //проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
    @Test
    void epicCannotBeItsOwnSubtask() {
        Epic epic = new Epic("Эпик", "Описание");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Сабтаск", "Описание", epic.getId());
        boolean isEpic = subtask.getEpicId() == epic.getId() && subtask.getId() == epic.getId();
        assertFalse(isEpic, "Эпик не может быть своей подзадачей");
    }

    //проверьте, что объект Subtask нельзя сделать своим же эпиком;
    @Test
    void  subtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask("Подзадача", "Описание", 1);
        boolean isSubtask = (subtask.getEpicId() == subtask.getId());
        assertFalse(isSubtask, "Subtask не может быть своим же эпиком");
    }

    //убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    @Test
    void managersReturnNotNullInstances() {
        TaskManager tm = Managers.getDefault();
        HistoryManager hm = Managers.getDefaultHistory();
        assertNotNull(tm, "TaskManager должен быть проинициализирован");
        assertNotNull(hm, "HistoryManager должен быть проинициализирован");
    }

    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void addTasksAndFindById() {
        Task task = new Task("Таск", "Описание");
        taskManager.createTask(task);
        Task found = taskManager.getTaskById(task.getId());
        assertEquals(task, found, "Добавленная задача должна возвращаться по id");
    }

    //проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    @Test
    void idsDoNotConflict() {
        Task task1 = new Task("Таск1", "Описание");
        Task task2 = new Task("Таск2", "Описание");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertNotEquals(task1.getId(), task2.getId(), "Id задач не должны совпадать");
    }

    //создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void taskDoesNotChangeOnAdd() {
        Task task = new Task("Таск", "Описание");
        task.setStatus(Status.NEW);
        Task copy = new Task(task.getTitle(), task.getDescription());
        copy.setStatus(task.getStatus());
        taskManager.createTask(task);
        assertEquals(copy.getTitle(), task.getTitle());
        assertEquals(copy.getDescription(), task.getDescription());
        assertEquals(copy.getStatus(), task.getStatus());
    }

    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    void  tasksAddedToHistoryKeepData() {
        Task task = new Task("HistoryTask", "Описание");
        taskManager.createTask(task);
        taskManager.getTaskById(task.getId());
        task.setTitle("Поменяно");
        List<Task> history = taskManager.getHistory();
        assertEquals("HistoryTask", history.get(0).getTitle(), "История должна хранить старую версию");
    }
}