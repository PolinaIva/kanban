package entity;

import enums.Status;


import java.util.*;

public class Epic extends Task {

    private List<Subtask> subtaskList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public Epic(String name, String description, List<Subtask> subtaskList) {

        super(name, description, createStatus(subtaskList));
        this.subtaskList = subtaskList;

    }

    public List<Subtask> getSubtaskList() {
        return subtaskList;
    }

    public void setSubtaskList(List<Subtask> subtaskList) {
        this.subtaskList = subtaskList;
        this.updateStatus();
    }

    public void addSubtask(Subtask subtask) {
        subtaskList.add(subtask);
        this.updateStatus();
    }

    public void addSubtasks(List<Subtask> subtaskList) {
        this.subtaskList.addAll(subtaskList);
        this.updateStatus();
    }

    public void updateStatus() {
        setStatus(createStatus(this.subtaskList));
    }

    private static Status createStatus(List<Subtask> subtaskList) {
        if (Objects.isNull(subtaskList) || subtaskList.isEmpty()) return Status.NEW;
        int countNew = (int) subtaskList.stream()
                .filter(s -> s.getStatus() == Status.NEW)
                .count();
        int countDone = (int) subtaskList.stream()
                .filter(s -> s.getStatus() == Status.DONE)
                .count();
        if (countNew != 0) {
            if (countNew < subtaskList.size()) {
                return Status.IN_PROGRESS;
            }
            return Status.NEW;
        }
        if (countDone != 0) {
            if (countDone < subtaskList.size()) {
                return Status.IN_PROGRESS;
            }
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }

    @Override
    public String toString() {
        return "Epic " + super.toString();
    }
}
