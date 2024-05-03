package app.services;


import app.api.ICalculationService;
import app.api.IStorage;
import app.api.IStorageUsing;
import domain.ICalculator;

import java.util.Map;


public class CalculationService implements ICalculationService, IStorageUsing {
    private IStorage storage;

    @Override
    public double[][] calculate(int id) {
        Map<String, Double> values = storage.getTaskValues(id);
        ICalculator calculator = domain.model.Factory.createCalculator(values);
        double[][] res = calculator.calculate();
        System.out.println("sum successful");
        storage.modifyTask(id, res, "complete");
        return res;
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }

}
