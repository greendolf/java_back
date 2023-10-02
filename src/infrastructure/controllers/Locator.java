package infrastructure.controllers;

import infrastructure.controllers.endpoint.IFactory;

public class Locator {
    public static IFactory[] locate() {
        return new IFactory[]{
                new infrastructure.controllers.endpoint.calculation.Factory(),
                new infrastructure.controllers.endpoint.auth.Factory(),
                new infrastructure.controllers.endpoint.register.Factory(),
                new infrastructure.controllers.endpoint.task.Factory(),
                new infrastructure.controllers.endpoint.tasks.Factory()
        };
    }
}
