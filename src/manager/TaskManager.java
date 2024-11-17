package manager;

import entity.Epic;
import entity.Subtask;
import entity.Task;

import javax.naming.directory.NoSuchAttributeException;
import java.util.List;

public interface TaskManager {
    List<Task> getTaskList();

    List<Epic> getEpicList();

    List<Subtask> getSubtaskList();

    void deleteTaskList();

    void deleteEpicList();

    void deleteSubtaskList();

    void deleteEpic(int id);

    void deleteSubtask(int id);

    void deleteTask(int id);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask) throws NoSuchAttributeException;

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Subtask> getEpicSubtasks(Epic epic);

    List<Task> getHistory();
}
