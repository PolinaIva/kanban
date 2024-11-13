package entity;

import enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Epic extends Task {

    private Set<Subtask> subtaskSet;

    public Epic(String name, String description) {
        super(name,description, Status.NEW);
    }
    public Epic(String name, String description, Set<Subtask> subtaskSet) {

        super(name,description,createStatus(subtaskSet));
        this.subtaskSet = subtaskSet;

    }

    public void setSubtaskSet(Set<Subtask> subtaskSet) {
        this.subtaskSet = subtaskSet;
        setStatus(createStatus(this.subtaskSet));
    }

    public void addSubtask(Subtask subtask) {
        subtaskSet.add(subtask);
        setStatus(createStatus(subtaskSet));
    }
    public void addSubtasks(Set<Subtask> subtaskSet) {
        this.subtaskSet.addAll(subtaskSet);
        setStatus(createStatus(this.subtaskSet));
    }

    public void updateStatus() {
        createStatus(this.subtaskSet);
    }

    private static Status createStatus(Set<Subtask> subtaskList) {
        if(subtaskList.isEmpty()) return Status.NEW;
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


}
