package parser.elements.factors;

import parser.elements.expressions.IExpression;

public class ExpressionFactor implements IFactor {
    private IExpression expression;

    public ExpressionFactor(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public double getValue() {
        return expression.getResult();
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
