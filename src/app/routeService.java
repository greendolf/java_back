package app;


import java.util.HashMap;
import java.util.Map;

public class routeService implements IService {
    Map<String, IFactory> factories;
    public routeService() {
        System.out.println("registration started");
        factories = new HashMap<>();
        IRegister reg = new register();
        IFactory[] arr = reg.getFactories();
        for (IFactory factory : arr) {
            factories.put(factory.getPurpose(), factory);
        }
        System.out.println("registration ended");
    }
    private IService getService(String purpose) {
        return factories.get(purpose).create();
    }

    public String handle(Map<String, String> args) {
        IService IS = getService(args.get("purpose"));
        return IS.handle(args);
    }
}
