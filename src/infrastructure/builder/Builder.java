package infrastructure.builder;

import app.*;

public class Builder {
    public static ICalculationService buildCalculationService() {
        IStorage storage = infrastructure.storage.mongoStorage.Factory.createInstance();

        ICalculationService service = app.Factory.createCalculationService();
        ((IStorageUsing) service).useStorage(storage);

        return service;
    }

    public static IAuthService buildAuthService() {

        IStorage storage = infrastructure.storage.mongoStorage.Factory.createInstance();

        IAuthService service = app.Factory.createAuthService();
        ((IStorageUsing) service).useStorage(storage);

        return service;
    }

    public static IRegisterService buildRegisterService() {

        IStorage storage = infrastructure.storage.mongoStorage.Factory.createInstance();

        IRegisterService service = app.Factory.createRegisterService();
        ((IStorageUsing) service).useStorage(storage);

        return service;
    }
    public static ITaskService buildTaskService() {

        IStorage storage = infrastructure.storage.mongoStorage.Factory.createInstance();

        ITaskService service = app.Factory.createTaskService();
        ((IStorageUsing) service).useStorage(storage);

        return service;
    }
    public static ITasksService buildTasksService() {

        IStorage storage = infrastructure.storage.mongoStorage.Factory.createInstance();

        ITasksService service = app.Factory.createTasksService();
        ((IStorageUsing) service).useStorage(storage);

        return service;
    }
}
