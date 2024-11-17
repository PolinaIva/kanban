package manager.util;

import manager.impl.InMemoryHistoryManager;
import manager.impl.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagersTest {

    @Test
    public void checkGetDefault() {
        assertEquals(InMemoryTaskManager.class, Managers.getDefault().getClass());
    }

    @Test
    public void checkGetDefaultHistory() {
        assertEquals(InMemoryHistoryManager.class, Managers.getDefaultHistory().getClass());
    }
}
