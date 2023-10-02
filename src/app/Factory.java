package app;


import app.services.calculation.Calculation;
import app.services.auth.AuthService;
import app.services.register.RegisterService;
import app.services.task.TaskService;
import app.services.tasks.TasksService;

public class Factory {
    public static ICalculationService createCalculationService() {
        return new Calculation();
    }
    public static IAuthService createAuthService() {
        return new AuthService();
    }
    public static IRegisterService createRegisterService() {
        return new RegisterService();
    }
    public static ITaskService createTaskService() {
        return new TaskService();
    }
    public static ITasksService createTasksService() {
        return new TasksService();
    }
}