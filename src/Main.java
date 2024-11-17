import entity.Epic;
import entity.Subtask;
import entity.Task;
import enums.Status;
import manager.util.Managers;
import manager.TaskManager;

import javax.naming.directory.NoSuchAttributeException;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Тестовая задача 1", Status.NEW);
        manager.createTask(task1);
        Task task2 = new Task("Задача 2", "Тестовая задача 2", Status.NEW);
        manager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Тестовый эпик 1");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Сабтаска 1", "Тестовая сабтаска 1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Сабтаска 2", "Тестовая сабтаска 2", Status.NEW, epic1.getId());

        try {
            manager.createSubtask(subtask1);
            manager.createSubtask(subtask2);
        } catch (NoSuchAttributeException e) {
            System.out.println("Вы пытаетесь создать сабтаску, связанную с несуществующим эпиком!");
        }


        Epic epic2 = new Epic("Эпик 2", "Тестовый эпик 2");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Сабтаска 3", "Тестовая сабтаска 3", Status.NEW, epic2.getId());
        try {
            manager.createSubtask(subtask3);
        } catch (NoSuchAttributeException e) {
            System.out.println("Вы пытаетесь создать сабтаску, связанную с несуществующим эпиком!");
        }
        System.out.println(manager.getEpicList());
        System.out.println(manager.getTaskList());
        System.out.println(manager.getSubtaskList());

        task1.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task1);
        task2.setStatus(Status.DONE);
        manager.updateTask(task2);

        subtask1.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask1);
        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);
        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        System.out.println(manager.getEpicList());
        System.out.println(manager.getTaskList());
        System.out.println(manager.getSubtaskList());

        manager.deleteTask(task1.getId());
        manager.deleteEpic(epic1.getId());

        manager.getTask(task2.getId());
        manager.getSubtask(subtask3.getId());
        System.out.println(manager.getHistory());


    }


}
