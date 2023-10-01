package infrastructure.builder;

import app.*;

public class Builder {
    public static ICalcService buildCalcService() {
        return app.Factory.createCalcService();
    }

    public static ILoginService buildLoginService() {

        IStorage storage = infrastructure.storage.Factory.createTextStorage();

        ILoginService service = app.Factory.createLoginService();
        ((IStorageUsing) service).useStorage(storage);

        return service;
    }

    public static IRegisterService buildRegisterService() {

        IStorage storage = infrastructure.storage.Factory.createTextStorage();

        IRegisterService service = app.Factory.createRegisterService();
        ((IStorageUsing) service).useStorage(storage);

        return service;
    }
}
