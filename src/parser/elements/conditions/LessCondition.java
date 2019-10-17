package parser.elements.conditions;

import parser.elements.expressions.IExpression;

public class LessCondition implements ICondition {
    private IExpression leftExpression;
    private IExpression rightExpression;

    public LessCondition(IExpression leftExpression, IExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public String toString() {
        return leftExpression.toString() + " < " + rightExpression.toString();
    }

    @Override
    public boolean getResult() {
        return leftExpression.getResult()<rightExpression.getResult();
    }
}