package app.services.calculation;


import app.ICalculationService;
import app.IStorage;
import app.IStorageUsing;
import app.ITokenManagerUsing;
import domain.ICalculator;
import infrastructure.security.ITokenManager;

import java.util.Map;


public class Calculation implements ICalculationService, IStorageUsing {
    private IStorage storage;

    @Override
    public String sum(int id) {
        Map<String, Integer> values = storage.getTaskValues(id);
        System.out.println(values.get("value1"));
        System.out.println(values.get("value2"));
        ICalculator calculator = domain.model.Factory.createCalculator();
        int res = calculator.sum(values.get("value1"), values.get("value2"));
        System.out.println("sum successful");
        storage.modifyTask(id, res, "complete");
        return Integer.toString(res);
    }

    @Override
    public String sub(int id) {
        return null;
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }

}
