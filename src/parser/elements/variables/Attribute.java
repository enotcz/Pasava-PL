package parser.elements.variables;

import exceptions.AtributException;
import parser.elements.identifiers.Identifier;

public abstract class Attribute {
    private Identifier identifier;
    private double value;

    public Attribute(Identifier identifier, double value) {
        this.identifier = identifier;
        this.value = value;
    }

    public Attribute(Identifier identifier) {
        this.identifier = identifier;
        this.value = 0;
    }



    public String getName(){
        return identifier.getName();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) throws AtributException {
        this.value = value;
    }
}
