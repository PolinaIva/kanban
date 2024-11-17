package manager.impl;

import entity.Epic;
import entity.Subtask;
import entity.Task;
import manager.HistoryManager;
import manager.TaskManager;
import manager.util.Managers;

import javax.naming.directory.NoSuchAttributeException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getTaskList() {
        return tasks.values().stream().toList();
    }

    @Override
    public List<Epic> getEpicList() {
        return epics.values().stream().toList();
    }

    @Override
    public List<Subtask> getSubtaskList() {
        return subtasks.values().stream().toList();
    }

    @Override
    public void deleteTaskList() {
        tasks.clear();
    }

    @Override
    public void deleteEpicList() {
        epics.clear();
        deleteSubtaskList();
    }

    @Override
    public void deleteSubtaskList() {
        subtasks.clear();
        if (!epics.isEmpty()) {
            for (Epic e : epics.values()) {
                e.getSubtaskList().clear();
                e.updateStatus();
            }
        }
    }

    @Override
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

    @Override
    public void deleteSubtask(int id) {
        Subtask deletedSubtask = subtasks.remove(id);
        if (Objects.nonNull(deletedSubtask)) {
            epics.get(deletedSubtask.getEpicId()).getSubtaskList().remove(deletedSubtask);
            epics.get(deletedSubtask.getEpicId()).updateStatus();
        }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        if (Objects.nonNull(epic.getSubtaskList()) && !epic.getSubtaskList().isEmpty()) {
            List<Subtask> subtaskList = epic.getSubtaskList().stream()
                    .filter(s -> !subtasks.containsKey(s.getId()))
                    .toList();
            for (Subtask s : subtaskList) {
                subtasks.put(s.getId(), s);
            }
        }

    }

    @Override
    public void createSubtask(Subtask subtask) throws NoSuchAttributeException {
        if (Objects.isNull(epics.get(subtask.getEpicId()))) {
            throw new NoSuchAttributeException(String.format("Эпика %d, с которым связана сабтаска %d, не "
                    + "существует!", subtask.getEpicId(), subtask.getId()));
        } else {
            subtasks.put(subtask.getId(), subtask);
        }
        if (!epics.get(subtask.getEpicId()).getSubtaskList().contains(subtask)) {
            epics.get(subtask.getEpicId()).addSubtask(subtask);
        }
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.updateStatus();
        updateEpic(epic);
    }

    @Override
    public List<Subtask> getEpicSubtasks(Epic epic) {
        return epics.get(epic.getId()).getSubtaskList();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
