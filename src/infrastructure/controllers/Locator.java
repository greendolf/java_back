package infrastructure.controllers;

import infrastructure.controllers.endpoint.IFactory;

public class Locator {
    public static IFactory[] locate() {
        IFactory[] factories = {
                new infrastructure.controllers.endpoint.calc.Factory()
                ,
                new infrastructure.controllers.endpoint.login.Factory()
                ,
                new infrastructure.controllers.endpoint.register.Factory()
        };
        return factories;
    }
}
