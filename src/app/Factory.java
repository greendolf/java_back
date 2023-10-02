package app;


import app.services.calc.CalcService;
import app.services.login.LoginService;
import app.services.register.RegisterService;
import app.services.task.TaskService;
import app.services.tasks.TasksService;

public class Factory {
    public static ICalcService createCalcService() {
        return new CalcService();
    }
    public static ILoginService createLoginService() {
        return new LoginService();
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