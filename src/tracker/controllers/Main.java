package tracker;

import tracker.model.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        //Создание задач, эпиков и подзадач
        Task task1 = new Task("Покупка машины", "Выбрать варианты и осмотреть");
        Task task2 = new Task("Учеба", "Дописать проект 4 спринта");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Путешествие", "Подготовиться к путешествию");
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Выбрать авиабилеты", "Посмотреть и выбрать самое выгодное", epic1.getId());
        Subtask subtask2 = new Subtask("Выбрать отель", "Посмотреть на букинге", epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Семейный праздник", "Организовать день рождения");
        taskManager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Купить подарок", "Выбрать и заказать", epic2.getId());
        taskManager.createSubtask(subtask3);

        //Вывод всех задач, эпиков и подзадач
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        //Проверка логики на обновление статуса эпика, после обновления подзадач
        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        System.out.println(taskManager.getEpicById(epic1.getId()));

        subtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask2);
        System.out.println(taskManager.getEpicById(epic1.getId()));

        //Удаление задачи и эпика по идентификатору и проверка
        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic2.getId());

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getHistory());
    }
}
