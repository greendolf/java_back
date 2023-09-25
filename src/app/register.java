package app;

public class register implements IRegister {

    @Override
    public IFactory[] getFactories() {
        return new IFactory[] {new calcServiceFactory(), new loginServiceFactory()};
    }
}
