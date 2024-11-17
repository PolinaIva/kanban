package entity;

import enums.Status;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void checkUpdateStatus() {
        Epic epic = new Epic("Эпик", "Тест");
        List<Subtask> subtaskList = new ArrayList<>();
        epic.addSubtasks(subtaskList);
        assertEquals(epic.getStatus(), Status.NEW);

        Subtask subtask1 = new Subtask("Сабтаска 1", "Тест", Status.NEW, epic.getId());
        subtaskList.add(subtask1);
        epic.addSubtasks(subtaskList);
        assertEquals(epic.getStatus(), Status.NEW);

        Subtask subtask2 = new Subtask("Сабтаска 2", "Тест", Status.IN_PROGRESS, epic.getId());
        subtaskList.add(subtask2);
        epic.addSubtasks(subtaskList);
        assertEquals(epic.getStatus(), Status.IN_PROGRESS);

        Subtask subtask3 = new Subtask("Сабтаска 3", "Тест", Status.DONE, epic.getId());
        subtaskList.add(subtask3);
        epic.addSubtasks(subtaskList);
        assertEquals(epic.getStatus(), Status.IN_PROGRESS);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        Subtask subtask4 = new Subtask("Сабтаска 4", "Тест", Status.DONE, epic.getId());
        subtaskList.add(subtask4);
        epic.addSubtasks(subtaskList);
        assertEquals(epic.getStatus(), Status.DONE);
    }

}