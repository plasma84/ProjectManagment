package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import java.util.List;

public interface IProjectRepository {
    boolean createEmployee(Employee emp);
    boolean createProject(Project pj);
    boolean createTask(Task tk);
    boolean assignProjectToEmployee(int projectId, int employeeId);
    boolean assignTaskInProjectToEmployee(int taskId, int projectId, int employeeId);
    boolean deleteEmployee(int userId);
    boolean deleteProject(int projectId);
    boolean deleteTask(int taskId); // Add this line
    List<Task> getAllTasks(int empId, int projectId);
    List<Project> searchProjectsByEmployeeId(int employeeId);
    List<Task> searchTasksByEmployeeId(int employeeId);

}
