package parser.elements.statements;

import exceptions.AtributException;
import parser.elements.expressions.IExpression;
import exceptions.ParserException;
import parser.elements.identifiers.Identifier;
import parser.elements.variables.Attribute;

public class SetStatement implements IStatement {
    private Attribute attribute;
    private IExpression expression;

    public SetStatement(Attribute attribute, IExpression expression) {
        this.attribute = attribute;
        this.expression = expression;
    }

    @Override
    public void execute() throws ParserException, AtributException {
        attribute.setValue(expression.getResult());
    }

    @Override
    public String toString() {
        return attribute.getName() + " := " + expression.getResult() + "\n";
    }
}
