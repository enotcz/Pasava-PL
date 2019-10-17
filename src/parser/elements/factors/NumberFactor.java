package parser.elements.factors;

public class NumberFactor implements IFactor {
    private double number;

    public NumberFactor(double number) {
        this.number = number;
    }

    @Override
    public double getValue() {
        return number;
    }
    @Override
    public String toString() {
        return number + "";
    }
}
