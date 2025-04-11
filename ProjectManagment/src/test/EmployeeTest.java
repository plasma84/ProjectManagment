package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dao.ProjectRepositoryImpl;
import entity.Employee;

public class EmployeeTest {

    @Test
    public void testCreateEmployeeSuccess() {
        ProjectRepositoryImpl repo = new ProjectRepositoryImpl();
        Employee emp = new Employee("Alice", "Developer", "Female", 60000, null);
        boolean result = repo.createEmployee(emp);
        assertTrue(result, "Employee should be created successfully");
    }
}