package domain.model;

import domain.ICalculator;

public class Factory {
    public static ICalculator createCalculator() {
        return new Model();
    }
}
class Model implements ICalculator{
    public int sum(int a, int b) {
        return a + b;
    }
    public int sub(int a, int b) {
        return a - b;
    }
}