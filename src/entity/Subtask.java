package entity;

import enums.Status;
import lombok.*;

@Getter
@Setter
public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name,description,status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask " + super.toString();
    }

}
