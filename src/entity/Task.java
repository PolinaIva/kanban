package entity;

import enums.Status;
import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
public class Task {

    private static int taskCounter;

    private final int id;
    private String name;
    private String description;
    private Status status;

    public Task(){
        this.id = incrementCounter();
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = incrementCounter();
    }

    private static int incrementCounter() {

        return taskCounter++;
    }

    @Override
    public String toString() {
        return "Task {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}