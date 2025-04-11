package test;

import static org.junit.jupiter.api.Assertions.*;

import dao.ProjectRepositoryImpl;
import myexceptions.EmployeeNotFoundException;
import myexceptions.ProjectNotFoundException;
import org.junit.jupiter.api.Test;

public class ExceptionTest {

    @Test
    public void testEmployeeNotFoundExceptionForProjects() {
        ProjectRepositoryImpl repo = new ProjectRepositoryImpl();
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            repo.searchProjectsByEmployeeId(999); // assuming 999 is invalid
        });
        assertEquals("Employee not found with id: 999", exception.getMessage());
    }

    @Test
    public void testEmployeeNotFoundExceptionForTasks() {
        ProjectRepositoryImpl repo = new ProjectRepositoryImpl();
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            repo.searchTasksByEmployeeId(999); 
        });
        assertEquals("Employee not found with id: 999", exception.getMessage());
    }
}