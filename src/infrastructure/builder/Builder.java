package infrastructure.builder;

import app.*;

public class Builder {
    public static ICalcService buildCalcService() {
        return app.Factory.createCalcService();
    }

    public static ILoginService buildLoginService() {

        IStorage storage = infrastructure.storage.mongoStorage.Factory.createInstance();

        ILoginService service = app.Factory.createLoginService();
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
