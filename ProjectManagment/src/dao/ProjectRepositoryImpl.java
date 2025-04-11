package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import util.DBConnUtil;
import myexceptions.EmployeeNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl implements IProjectRepository {

    private final Connection connection;

    public ProjectRepositoryImpl() {
        this.connection = DBConnUtil.getConnection("db.properties");
    }

    @Override
    public boolean createEmployee(Employee emp) {
        String sql = "INSERT INTO Employee (name, designation, gender, salary, project_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, emp.getName());
            statement.setString(2, emp.getDesignation());
            statement.setString(3, emp.getGender());
            statement.setDouble(4, emp.getSalary());
            statement.setObject(5, emp.getProjectId(), Types.INTEGER);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createProject(Project pj) {
        String sql = "INSERT INTO Project (project_name, description, start_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pj.getProjectName());
            statement.setString(2, pj.getDescription());
            statement.setString(3, pj.getStartDate());
            statement.setString(4, pj.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createTask(Task tk) {
        String sql = "INSERT INTO Task (task_name, project_id, employee_id, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tk.getTaskName());
            statement.setInt(2, tk.getProjectId());
            statement.setInt(3, tk.getEmployeeId());
            statement.setString(4, tk.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean assignProjectToEmployee(int projectId, int employeeId) {
        String sql = "UPDATE Employee SET project_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            statement.setInt(2, employeeId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean assignTaskInProjectToEmployee(int taskId, int projectId, int employeeId) {
        String sql = "UPDATE Task SET employee_id = ? WHERE task_id = ? AND project_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.setInt(2, taskId);
            statement.setInt(3, projectId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(int userId) {
        String sql = "DELETE FROM Employee WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM Project WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM Task WHERE task_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Task> getAllTasks(int empId, int projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE employee_id = ? AND project_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            statement.setInt(2, projectId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_name"),
                        rs.getInt("project_id"),
                        rs.getInt("employee_id"),
                        rs.getString("status")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<Project> searchProjectsByEmployeeId(int empId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.* FROM Project p JOIN Employee e ON p.id = e.project_id WHERE e.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Project project = new Project(
                        rs.getInt("id"),
                        rs.getString("project_name"),
                        rs.getString("description"),
                        rs.getString("start_date"),
                        rs.getString("status")
                );
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (projects.isEmpty()) {
        }
        return projects;
    }

    @Override
    public List<Task> searchTasksByEmployeeId(int empId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE employee_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_name"),
                        rs.getInt("project_id"),
                        rs.getInt("employee_id"),
                        rs.getString("status")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (tasks.isEmpty()) {
        }
        return tasks;
    }
}
