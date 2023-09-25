package app;

import java.util.Map;

public class loginServiceFactory implements IFactory {
    private final String purpose;

    loginServiceFactory() {
        this.purpose = "/login";
    }

    @Override
    public IService create() {
        return new loginService();
    }

    @Override
    public String getPurpose() {
        return this.purpose;
    }
}

class loginService implements IService {
    @Override
    public String handle(Map<String, String> args) {
        System.out.println("login controller");
        if (args.get("login").equals("admin") && args.get("password").equals("admin")) return "token";
        return "error";
    }
}
