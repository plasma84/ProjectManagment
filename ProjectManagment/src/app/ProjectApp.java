package app;

import dao.ProjectRepositoryImpl;
import util.DBConnUtil;
import entity.Employee;
import entity.Project;
import entity.Task;

import java.util.Scanner;

public class ProjectApp {
    public static void main(String[] args) {
        // Initialize DB connection
        DBConnUtil.getConnection("db.properties");

        ProjectRepositoryImpl repo = new ProjectRepositoryImpl();

        try (Scanner sc = new Scanner(System.in)) {
            int choice;

            do {
                System.out.println("\n--- Project Management Menu ---");
                System.out.println("1. Add Employee");
                System.out.println("2. Add Project");
                System.out.println("3. Add Task");
                System.out.println("4. Assign Project to Employee");
                System.out.println("5. Assign Task in Project to Employee");
                System.out.println("6. Delete Employee");
                System.out.println("7. Delete Task");
                System.out.println("8. List All Tasks for Employee in Project");
                System.out.println("9. Exit");

                choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                try {
                    switch (choice) {
                        case 1: // Add Employee
                            System.out.print("Enter employee name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter designation: ");
                            String designation = sc.nextLine();
                            System.out.print("Enter gender (Male/Female/Other): ");
                            String gender = sc.nextLine();
                            System.out.print("Enter salary: ");
                            double salary = sc.nextDouble();
                            System.out.print("Enter project ID (optional, 0 for none): ");
                            int projectId = sc.nextInt();
                            sc.nextLine(); // Consume newline

                            Employee emp = new Employee(name, designation, gender, salary, projectId == 0 ? null : projectId);
                            if (repo.createEmployee(emp)) {
                                System.out.println("Employee added successfully!");
                            } else {
                                System.out.println("Failed to add employee.");
                            }
                            break;

                        case 2: // Add Project
                            System.out.print("Enter project name: ");
                            String projectName = sc.nextLine();
                            System.out.print("Enter project description: ");
                            String description = sc.nextLine();
                            System.out.print("Enter project start date (yyyy-mm-dd): ");
                            String startDate = sc.nextLine();
                            System.out.print("Enter project status (started/dev/build/test/deployed): ");
                            String status = sc.nextLine();

                            Project pj = new Project(projectName, description, startDate, status);
                            if (repo.createProject(pj)) {
                                System.out.println("Project added successfully!");
                            } else {
                                System.out.println("Failed to add project.");
                            }
                            break;

                        case 3: // Add Task
                            System.out.print("Enter task name: ");
                            String taskName = sc.nextLine();
                            System.out.print("Enter project ID: ");
                            int taskProjectId = sc.nextInt();
                            System.out.print("Enter employee ID: ");
                            int taskEmployeeId = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            System.out.print("Enter task status (Assigned/Started/Completed): ");
                            String taskStatus = sc.nextLine();

                            Task tk = new Task(taskName, taskProjectId, taskEmployeeId, taskStatus);
                            if (repo.createTask(tk)) {
                                System.out.println("Task added successfully!");
                            } else {
                                System.out.println("Failed to add task.");
                            }
                            break;

                        case 4: // Assign Project to Employee
                            System.out.print("Enter employee ID: ");
                            int empId = sc.nextInt();
                            System.out.print("Enter project ID: ");
                            int projectID = sc.nextInt();
                            if (repo.assignProjectToEmployee(projectID, empId)) {
                                System.out.println("Project assigned to employee successfully!");
                            } else {
                                System.out.println("Failed to assign project.");
                            }
                            break;

                        case 5: // Assign Task to Employee
                            System.out.print("Enter task ID: ");
                            int taskId = sc.nextInt();
                            System.out.print("Enter project ID: ");
                            int projId = sc.nextInt();
                            System.out.print("Enter employee ID: ");
                            int empID = sc.nextInt();
                            if (repo.assignTaskInProjectToEmployee(taskId, projId, empID)) {
                                System.out.println("Task assigned to employee successfully!");
                            } else {
                                System.out.println("Failed to assign task.");
                            }
                            break;

                        case 6: // Delete Employee
                            System.out.print("Enter employee ID: ");
                            int deleteEmpId = sc.nextInt();
                            if (repo.deleteEmployee(deleteEmpId)) {
                                System.out.println("Employee deleted successfully!");
                            } else {
                                System.out.println("Failed to delete employee.");
                            }
                            break;

                        case 7: // Delete Task
                            System.out.print("Enter task ID: ");
                            int deleteTaskId = sc.nextInt();
                            if (repo.deleteTask(deleteTaskId)) {
                                System.out.println("Task deleted successfully!");
                            } else {
                                System.out.println("Failed to delete task.");
                            }
                            break;

                        case 8: // List all tasks for employee in project
                            System.out.print("Enter employee ID: ");
                            int empIdForTasks = sc.nextInt();
                            System.out.print("Enter project ID: ");
                            int projIdForTasks = sc.nextInt();

                            var tasks = repo.getAllTasks(empIdForTasks, projIdForTasks);
                            tasks.forEach(task -> System.out.println(task.getTaskName()));
                            break;

                        case 9:
                            System.out.println("Exiting...");
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    e.printStackTrace();
                }

            } while (choice != 9);

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnUtil.closeConnection();
            System.out.println("Application terminated. DB connection closed.");
        }
    }
}