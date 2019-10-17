package parser.elements.variables;

import parser.elements.identifiers.Identifier;

public class Variable extends Attribute {
    public Variable(Identifier identifier) {
        super(identifier);
    }

    public Variable(Identifier identifier, double value) {
        super(identifier, value);
    }
}
