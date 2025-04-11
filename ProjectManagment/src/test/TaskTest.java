package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dao.ProjectRepositoryImpl;
import entity.Task;

public class TaskTest {

    @Test
    public void testCreateTaskSuccess() {
        ProjectRepositoryImpl repo = new ProjectRepositoryImpl();
        Task task = new Task("Design UI", 1, 1, "ASSIGNED");
        boolean result = repo.createTask(task);
        assertTrue(result, "Task should be created successfully");
    }
}
