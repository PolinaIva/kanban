package manager.util.impl;

import entity.Epic;
import entity.Subtask;
import entity.Task;
import enums.Status;
import manager.impl.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.directory.NoSuchAttributeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class InMemoryTaskManagerTest {

    public static InMemoryTaskManager manager;
    public static Task task1;
    public static Subtask subtask1;
    public static Subtask subtask2;
    public static Subtask subtask3;
    public static Epic epic1;
    public static Epic epic2;

    @BeforeAll
    public static void init() {
        manager = new InMemoryTaskManager();
        task1 = new Task("Задача 1", "Тестовая задача 1", Status.NEW);
        // task2 = new Task("Задача 2", "Тестовая задача 2", Status.NEW);

        epic1 = new Epic("Эпик 1", "Тестовый эпик 1");

        subtask1 = new Subtask("Сабтаска 1", "Тестовая сабтаска 1", Status.NEW, epic1.getId());
        subtask2 = new Subtask("Сабтаска 2", "Тестовая сабтаска 2", Status.NEW, 1000);

        epic2 = new Epic("Эпик 2", "Тестовый эпик 2");

        subtask3 = new Subtask("Сабтаска 3", "Тестовая сабтаска 3", Status.NEW, epic2.getId());

    }

    @Test
    public void checkCreateTask() {
        manager.createTask(task1);
        assertNotNull(manager.getTaskList());
        assertEquals(manager.getTaskList().size(), 1);
        assertEquals(manager.getTaskList().get(0).getId(), task1.getId());
        assertEquals(manager.getTaskList().get(0).getName(), task1.getName());
        assertEquals(manager.getTaskList().get(0).getDescription(), task1.getDescription());
        assertEquals(manager.getTaskList().get(0).getStatus(), task1.getStatus());
    }

    @Test
    public void checkCreateEpic() {
        epic1.addSubtask(subtask1);
        manager.createEpic(epic1);
        assertNotNull(manager.getEpicList());
        assertEquals(manager.getEpicList().size(), 1);
        assertEquals(manager.getEpicList().get(0).getId(), epic1.getId());
        assertEquals(manager.getEpicList().get(0).getName(), epic1.getName());
        assertEquals(manager.getEpicList().get(0).getDescription(), epic1.getDescription());
        assertEquals(manager.getEpicList().get(0).getStatus(), epic1.getStatus());
        assertEquals(manager.getEpicList().get(0).getSubtaskList(), epic1.getSubtaskList());
        assertFalse(manager.getSubtaskList().isEmpty());
    }

    @Test
    public void checkCreateSubtask() throws NoSuchAttributeException {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        assertNotNull(manager.getSubtaskList());
        assertEquals(manager.getSubtaskList().size(), 1);
        assertEquals(manager.getSubtaskList().get(0).getId(), subtask1.getId());
        assertEquals(manager.getSubtaskList().get(0).getName(), subtask1.getName());
        assertEquals(manager.getSubtaskList().get(0).getDescription(), subtask1.getDescription());
        assertEquals(manager.getSubtaskList().get(0).getStatus(), subtask1.getStatus());
        assertEquals(manager.getSubtaskList().get(0).getEpicId(), subtask1.getEpicId());
        assertEquals(manager.getEpicList().get(0).getSubtaskList().get(0), subtask1);
        assertThrows(NoSuchAttributeException.class, () -> manager.createSubtask(subtask2));
    }

    @Test
    public void checkDeleteEpicList() throws NoSuchAttributeException {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.deleteEpicList();
        assertTrue(manager.getEpicList().isEmpty());
        assertTrue(manager.getSubtaskList().isEmpty());
    }

    @Test
    public void checkDeleteSubtaskList() throws NoSuchAttributeException {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.deleteSubtaskList();
        assertTrue(manager.getSubtaskList().isEmpty());
        assertTrue(epic1.getSubtaskList().isEmpty());
    }

    @Test
    public void checkDeleteSubtask() throws NoSuchAttributeException {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createEpic(epic2);
        manager.createSubtask(subtask3);
        manager.deleteSubtask(subtask3.getId());
        assertFalse(manager.getEpicList().isEmpty());
        assertFalse(manager.getSubtaskList().isEmpty());
        assertTrue(epic2.getSubtaskList().isEmpty());
    }

    @Test
    public void checkUpdateSubtask() throws NoSuchAttributeException {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        subtask1.setDescription("Лала");
        manager.updateSubtask(subtask1);
        assertEquals(manager.getEpicList().get(0).getSubtaskList().get(0).getDescription(),
                manager.getSubtaskList().get(0).getDescription());
    }

    @Test
    public void checkAddToHistoryList() {
        manager.createTask(task1);
        manager.getTask(task1.getId());
        assertEquals(manager.getHistory().get(0), task1);

    }
    

}
