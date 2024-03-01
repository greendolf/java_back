package infrastructure.builder;

import app.*;
import infrastructure.security.ITokenManager;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;


public class Builder {
    @Inject
    @Default
    private IAuthService IAS;
    @Inject
    @Default
    private ICalculationService ICS;
    @Inject
    @Default
    private IRegisterService IRS;
    @Inject
    @Default
    private ITaskService ITS;
    @Inject
    @Default
    private ITasksService ITsS;
    @Inject
    @Production
    private IStorage storage;
    @Inject
    @Default
    private ITokenManager tokenManager;

    @Produces
    @Built
    public IAuthService buildAuthService() {
        ((IStorageUsing) IAS).useStorage(storage);
        ((ITokenManagerUsing) IAS).useTokenManager(tokenManager);
        return IAS;
    }

    @Produces
    @Built
    public ICalculationService buildCalcService() {
        ((IStorageUsing) ICS).useStorage(storage);
        return ICS;
    }

    @Produces
    @Built
    public IRegisterService buildRegisterService() {
        ((IStorageUsing) IRS).useStorage(storage);
        ((ITokenManagerUsing) IRS).useTokenManager(tokenManager);
        return IRS;
    }

    @Produces
    @Built
    public ITaskService buildTaskService() {
        ((IStorageUsing) ITS).useStorage(storage);
        return ITS;
    }

    @Produces
    @Built
    public ITasksService buildTasksService() {
        ((IStorageUsing) ITsS).useStorage(storage);
        return ITsS;
    }
}
