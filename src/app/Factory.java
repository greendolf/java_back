package app;


import app.services.calc.CalcService;
import app.services.login.LoginService;
import app.services.register.RegisterService;

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
}