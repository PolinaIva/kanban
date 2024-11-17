package manager.util.impl;

import entity.Task;
import manager.impl.InMemoryHistoryManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {

    public static InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    public void checkAdd() {
        for (int i = 0; i <= 10; i++) {
            historyManager.add(new Task());
        }
        assertEquals(10, historyManager.getHistory().size());
    }
}
