package parser.elements.factors;

import parser.elements.identifiers.Identifier;
import parser.elements.variables.Attribute;

public class IdentifierFactor implements IFactor {
    private Attribute attribute;

    public IdentifierFactor(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public double getValue() {
        return attribute.getValue();
    }

    @Override
    public String toString() {
        return attribute.getName();
    }
}
