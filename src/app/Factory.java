package app;


import app.api.*;
import app.services.CalculationService;
import app.services.AuthService;
import app.services.RegisterService;
import app.services.TaskService;
import app.services.TasksService;

public class Factory {
    public static ICalculationService createCalculationService() {
        return new CalculationService();
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