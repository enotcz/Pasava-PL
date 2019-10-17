package parser.elements.variables;

import exceptions.AtributException;
import parser.elements.identifiers.Identifier;

public class Constant  extends Attribute {

    public Constant(Identifier identifier, double value) {
        super(identifier, value);
    }

    @Override
    public void setValue(double value) throws AtributException {
        throw new AtributException("Attribute " + getName() + " is constant");
    }
}
