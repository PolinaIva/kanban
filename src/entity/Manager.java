package entity;

import java.util.HashMap;
import java.util.List;


public class Manager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;
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
    }
    public void deleteSubtaskList() {
        subtasks.clear();
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
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(),task);
    }
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(),epic);
    }
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(),subtask);
        epics.get(subtask.getEpicId()).updateStatus();
    }
    public List<Subtask> getEpicSubtasks(Epic epic) {
        return epics.get(epic.getId()).getSubtaskSet().stream().toList();
    }
}
