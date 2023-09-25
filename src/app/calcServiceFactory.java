package app;

import domain.Model;

import java.util.Map;


public class calcServiceFactory implements IFactory {

    private final String purpose;

    calcServiceFactory() {
        this.purpose = "/calc";
    }

    @Override
    public IService create() {
        return new calcService();
    }

    @Override
    public String getPurpose() {
        return this.purpose;
    }
}

class calcService implements IService {
    @Override
    public String handle(Map<String, String> args) {
        int a = Integer.parseInt(args.get("a"));
        int b = Integer.parseInt(args.get("b"));
        return Integer.toString(Model.calc(a, b));
    }
}
