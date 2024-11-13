package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Manager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public List<Task> getTaskList() {
        return tasks.values().stream().toList();
    }
    public List<Epic> getEpicList() {
        return epics.values().stream().toList();
    }
    public List<Subtask> getSubtaskList() {
        return subtasks.values().stream().toList();
    }

    public void deleteTaskList() {
        tasks.clear();
    }
    public void deleteEpicList() {
        epics.clear();
        deleteSubtaskList();
    }
    public void deleteSubtaskList() {
        subtasks.clear();
        if(!epics.isEmpty()) {
            for (Epic e : epics.values()) {
                e.getSubtaskList().clear();
            }
        }
    }

    public void deleteEpic(int id) {
        Epic deletedEpic = epics.remove(id);
        if (Objects.nonNull(deletedEpic) && !subtasks.isEmpty()) {
           List<Integer> subtaskIdList = subtasks.values().stream()
                   .filter(s -> s.getEpicId() == deletedEpic.getId())
                   .map(Task::getId)
                   .toList();
           for (int i : subtaskIdList) {
               subtasks.remove(i);
           }
        }
    }
    public void deleteSubtask(int id) {
        Subtask deletedSubtask = subtasks.remove(id);
        if (Objects.nonNull(deletedSubtask)) {
            epics.get(deletedSubtask.getEpicId()).getSubtaskList().remove(deletedSubtask);
            epics.get(deletedSubtask.getEpicId()).updateStatus();
        }
    }
    public void deleteTask(int id) {
       tasks.remove(id);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }
    public Epic getEpic(int id) {
        return epics.get(id);
    }
    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(),task);
    }
    public void createEpic(Epic epic) {
        epics.put(epic.getId(),epic);
    }
    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(),subtask);
        epics.get(subtask.getEpicId()).addSubtask(subtask);
        epics.get(subtask.getEpicId()).updateStatus();
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(),task);
    }
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(),epic);
    }
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(),subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.updateStatus();
        updateEpic(epic);
    }

    public List<Subtask> getEpicSubtasks(Epic epic) {
        return epics.get(epic.getId()).getSubtaskList();
    }
}
